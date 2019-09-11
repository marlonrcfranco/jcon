package com.marlonrcfranco;

import java.io.*;
import java.util.ArrayList;

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
            output= ("Erro: Não foi possível localizar o arquivo \""+filePath+"\""
                    +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
        } catch (IOException e) {
            output= ("Erro: Não foi possível ler o arquivo \""+filePath+"\""
                    +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
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
        File folder = null;
        try {
            String path = filePath;
            int idx=1;
            // if file is in folder(s), create them first
            while(idx > 0) {
                idx = path.lastIndexOf("/");
                idx = idx<0? 0 : idx;
                path=path.substring(idx);
                String folderPath = filePath.substring(0, idx);
                folder = new File(folderPath);
                if(!folder.exists() && !"".equalsIgnoreCase(folder.getName().trim())) folder.mkdir();
            }
            if (filePath.endsWith("/")) {
                folder = new File(filePath);
                folder.mkdir();
                output=("Diretório criado com sucesso").getBytes();
            }else {
                file = new FileOutputStream(filePath);
                file.write(content);
                output=("Escrita concluída com sucesso").getBytes();
            }
        } catch (FileNotFoundException e) {
            output=("Erro: Não foi possível localizar o caminho \""+filePath+"\""
                    +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
        } catch (IOException e) {
            output=("Erro: Não foi possível escrever no arquivo \""+filePath+"\""
                    +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
        }
        finally {
            if (file != null) file.close();
        }
        return output;
    }

    public String delete(String filePath) throws IOException {
        return delete("",filePath,"","");
    }

    @Override
    public String delete(String IP, String filePath, String user, String pass) throws IOException {
        String output="";
        boolean isDirectory=false;
        boolean isDeleted=false;
        filePath = filePath.replace("\\", "/");
        File file = null;
        file = new File(filePath);
        if (file.exists()) {
            isDirectory = file.isDirectory();
            if (isDirectory) {
                isDeleted=deleteDirectory(file);
            }else {
                isDeleted=file.delete();
            }
            if(isDeleted){
                output = (isDirectory? "Directory":"File")+" \""+file.getName()+"\" deleted successfully.";
            }else {
                output = "Error: Could not delete the "+(isDirectory? "directory":"file")+" \""+file.getName()+"\"";
            }
        }else {
            output = "Error: File \""+file.getName()+"\" not found.";
        }
        return output;
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public String listFiles(String filePath) throws IOException {
        return listFiles("",filePath,"","");
    }

    @Override
    public String listFiles(String IP, String filePath, String user, String pass) throws IOException {
        String output="";
        try {
            ArrayList<File> listFiles = listFilesAsList(IP, filePath, user, pass);
            for (File file : listFiles) {
                output += file.getName() + (file.isDirectory()? "/" : "") + "\n";
            }
        } catch (Exception e) {
            output += "Error: Could not list the files in \""+filePath+"\".";
            if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
        }
        return output;
    }

    @Override
    public ArrayList<File> listFilesAsList(String IP, String filePath, String user, String pass) throws Exception {
        ArrayList<File> output = new ArrayList<>();
        filePath = filePath.replace("\\", "/");
        if (!filePath.endsWith("/")) filePath+="/";
        File curDir = new File(filePath);
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            output.add(f);
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
            if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
        } catch (IOException e) {
            output+= "Erro: Nao foi possivel copiar o arquivo de \"" + sourceFilePath + "\" para \"" + destFilePath + "\";";
            if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
        }
        finally {
            if (inputStream!=null) inputStream.close();
            if (outputStream!=null) outputStream.close();
        }
        return output;
    }

}
