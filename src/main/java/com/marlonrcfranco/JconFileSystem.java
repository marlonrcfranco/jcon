package com.marlonrcfranco;

import java.io.*;

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
        return new String(readBytes(IP,filePath,user,pass));
    }

    @Override
    public byte[] readBytes(String IP, String filePath, String user, String pass) throws IOException {
        byte[] output = "".getBytes(); //2^17
        filePath = filePath.replace("\\", "/");
        FileInputStream file = null;
        try {
            file = new FileInputStream(filePath);
            output = Util.toByteArray(file);
        } catch (FileNotFoundException e) {
            output= ("Erro: Não foi possível localizar o arquivo \""+filePath+"\"").getBytes();
        } catch (IOException e) {
            output= ("Erro: Não foi possível ler o arquivo \""+filePath+"\"").getBytes();
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
        return new String(writeBytes(IP,filePath,user,pass,content.getBytes()));
    }

    @Override
    public byte[] writeBytes(String IP, String filePath, String user, String pass, byte[] content) throws IOException {
        byte[] output="".getBytes();
        filePath = filePath.replace("\\", "/");
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filePath);
            file.write(content);
            output=("Escrita concluída com sucesso").getBytes();
        } catch (FileNotFoundException e) {
            output=("Erro: Não foi possível localizar o caminho \""+filePath+"\"").getBytes();
        } catch (IOException e) {
            output=("Erro: Não foi possível ler o arquivo \""+filePath+"\"").getBytes();
        }
        finally {
            if (file != null) file.close();
        }
        return output;
    }

    public String delete(String filePath) {
        return delete("",filePath,"","");
    }

    @Override
    public String delete(String IP, String filePath, String user, String pass) {
        String output="";
        filePath = filePath.replace("\\", "/");
        File file = null;
        file = new File(filePath);
        if (file.exists()) {
            file.delete();
            output = "File \""+file.getName()+"\" deleted successfully.";
        }else {
            output = "Error: File \""+file.getName()+"\" not found.";
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
