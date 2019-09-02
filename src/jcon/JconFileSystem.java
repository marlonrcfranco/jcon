package jcon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JconFileSystem implements IJcon{

    public JconFileSystem() {
        System.out.println(
                        "   ┌──────────────────────────┐\n" +
                        "   │  .:: JconFileSystem ::.  │\n" +
                        "   └──────────────────────────┘\n" +
                        " Read files from local filesystem.\n");
    }

    public String read(String filePath) throws IOException{
        return read("",filePath,"","");
    }

    @Override
    public String read(String IP, String filePath, String user, String pass) throws IOException {
        String output="";
        filePath = filePath.replace("\\", "/");
        FileInputStream file = null;
        try {
            file = new FileInputStream(filePath);
            output=new String(file.readAllBytes());
        } catch (FileNotFoundException e) {
            output= "Erro: Não foi possível localizar o arquivo \""+filePath+"\"";
        } catch (IOException e) {
            output= "Erro: Não foi possível ler o arquivo \""+filePath+"\"";
        }
        finally {
            if (file != null) file.close();
        }
        return output;
    }

    public String write(String filePath,String content) throws IOException{
        return write("",filePath, "", "", content);
    }

    @Override
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException{
        String output="";
        filePath = filePath.replace("\\", "/");
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filePath);
            file.write(content.getBytes());
            output="Escrita concluída com sucesso";
        } catch (FileNotFoundException e) {
            output= "Erro: Não foi possível localizar o caminho \""+filePath+"\"";
        } catch (IOException e) {
            output= "Erro: Não foi possível ler o arquivo \""+filePath+"\"";
        }
        finally {
            if (file != null) file.close();
        }
        return output;
    }

    public String copyFileTo(String sourceFilePath, String destFilePath) throws IOException{
        return copyFileTo("",sourceFilePath,"",destFilePath,"","");
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException{
        String output="";
        boolean bContinue=true;
        sourceFilePath = sourceFilePath.replace("\\", "/");
        destFilePath = destFilePath.replace("\\", "/");
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            output+= "Erro: Nao foi possivel localizar o caminho de origem \"" + sourceFilePath + "\";";
            bContinue=false;
        }
        try {
            if (bContinue) {
                outputStream = new FileOutputStream(destFilePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                output = "Arquivo copiado com sucesso";
            }
        } catch (FileNotFoundException e) {
            output+= "Erro: Nao foi possivel localizar o caminho de destino \"" + destFilePath + "\";";
        } catch (IOException e) {
            output+= "Erro: Nao foi possivel copiar o arquivo de \"" + sourceFilePath + "\" para \"" + destFilePath + "\";";
        }
        finally {
            if (inputStream!=null) inputStream.close();
            if (outputStream!=null) outputStream.close();
        }
        return output;
    }

}
