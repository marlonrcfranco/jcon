package com.marlonrcfranco;

import com.emc.ecs.nfsclient.nfs.io.Nfs3File;
import com.emc.ecs.nfsclient.nfs.io.NfsFileInputStream;
import com.emc.ecs.nfsclient.nfs.io.NfsFileOutputStream;
import com.emc.ecs.nfsclient.nfs.nfs3.Nfs3;
import com.emc.ecs.nfsclient.rpc.CredentialUnix;

import java.io.*;
import java.util.List;

public class JconNFS implements IJcon{
    private String sharedFolder;
    private String sFilePath;

    public JconNFS() {
        sharedFolder="";
        sFilePath="";
        System.out.println(
                        "   ┌──────────────────────────┐\n" +
                        "   │      .:: JconNFS ::.     │\n" +
                        "   └──────────────────────────┘\n" +
                        "      JconNFS supports NFS.\n" +
                        "");
    }

    @Override
    public String read(String IP, String filePath, String user, String pass) throws IOException {
        return new String(readBytes(IP, filePath, user, pass));
    }

    @Override
    public byte[] readBytes(String IP, String filePath, String user, String pass) throws IOException {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        return readBytes(IP,sharedFolder,sFilePath,user,pass,null);
    }

    public byte[] readBytes(String IP, String sharedFolder, String filePath, String user, String pass, String domain) throws IOException {
        byte[] output="".getBytes();
        //AccessMask = FILE_READ_DATA
        sharedFolder = parsePath(sharedFolder);
        filePath = parsePath(filePath);
        Nfs3 nfs3 = null;
        Nfs3File file = null;
        NfsFileInputStream in = null;
        try {
            nfs3 = new Nfs3(IP+":"+sharedFolder, new CredentialUnix(0, 0, null), 3);
            file = new Nfs3File(nfs3, "/");
            in = new NfsFileInputStream(file);
            output = Util.toByteArray(in);
        } catch (IOException e) {
            output=("Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath).getBytes();
        } finally {
            if (in != null) in.close();
        }
        return output;
    }

    @Override
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException {
        return new String(writeBytes(IP,filePath,user,pass,content.getBytes()));
    }

    @Override
    public byte[] writeBytes(String IP, String filePath, String user, String pass, byte[] content) throws IOException {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        byte[] output="".getBytes();
        //AccessMask = FILE_READ_DATA
        sharedFolder = parsePath(sharedFolder);
        sFilePath = parsePath(sFilePath);
        Nfs3 nfs3 = null;
        Nfs3File file = null;
        NfsFileOutputStream out = null;
        try {
            nfs3 = new Nfs3(IP+":"+sharedFolder, new CredentialUnix(0, 0, null), 3);
            file = new Nfs3File(nfs3, "/");
            String path = filePath;
            int idx=1;
            // if file is in folder(s), create them first
            while(idx > 0) {
                idx = path.lastIndexOf("/");
                idx = idx<0? 0 : idx;
                path=path.substring(idx);
                String folderPath = filePath.substring(0, idx);
                Nfs3File folder = new Nfs3File(nfs3, folderPath);
                if(!folder.exists()) folder.mkdir();
            }
            if (filePath.endsWith("/")) {
                Nfs3File folder = new Nfs3File(nfs3, filePath);
                if(!folder.exists()) folder.mkdir();
                output=("Diretório criado com sucesso").getBytes();
            }else {
                out = new NfsFileOutputStream(file);
                out.write(content);
                output=("Escrita concluída com sucesso").getBytes();
            }
        } catch (IOException e) {
            output=("Erro: Nao foi possivel escrever no arquivo: "+sharedFolder+"/"+filePath).getBytes();
        } finally {
          if (out != null) out.close();
        }
        return output;
    }

    @Override
    public String delete(String IP, String filePath, String user, String pass) throws IOException {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        return delete(IP, sharedFolder, sFilePath, user, pass);
    }

    public String delete(String IP, String sharedFolder, String filePath, String user, String pass) throws IOException {
        String output="";
        boolean isDirectory=false;
        filePath = filePath.replace("\\", "/");
        sharedFolder = parsePath(sharedFolder);
        filePath = parsePath(filePath);
        Nfs3 nfs3 = null;
        Nfs3File file = null;
        try {
            nfs3 = new Nfs3(IP+":"+sharedFolder, new CredentialUnix(0, 0, null), 3);
            file = new Nfs3File(nfs3, "/");
            if (file.exists()) {
                isDirectory = file.isDirectory();
                file.delete();
                output = (isDirectory? "Directory":"File")+" \""+file.getName()+"\" deleted successfully.";
            }else {
                output = "Error: File \""+file.getName()+"\" not found.";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public String listFiles(String IP, String filePath, String user, String pass) throws IOException {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        return listFiles(IP, sharedFolder, sFilePath, user, pass);
    }

    public String listFiles(String IP, String sharedFolder, String path, String user, String pass) {
        String output="";
        path = path.replace("\\", "/");
        sharedFolder = parsePath(sharedFolder);
        path = parsePath(path);
        Nfs3 nfs3 = null;
        Nfs3File file = null;
        try {
            nfs3 = new Nfs3(IP+":"+sharedFolder, new CredentialUnix(0, 0, null), 3);
            file = new Nfs3File(nfs3, "/");
            List<Nfs3File> listFiles =  file.listFiles();
            for (Nfs3File f : listFiles) {
                output+= f.getName() + (f.isDirectory()? "/" : "") + "\n";
            }
        } catch (IOException e) {
            output="Erro: Nao foi possivel listar os arquivos do diretorio: "+sharedFolder;
        }
        return output;
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException{
        String output="";
        output+= "Erro: Nao foi possivel copiar o arquivo de \"" + sourceFilePath + "\" para \"" + destFilePath + "\"; [motivo: nao implementado]";
        return output;
    }

    private String parsePath(String path) {
        while (path.startsWith("\\") || path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    private void extractSharedPathFromPath(String path) {
        boolean isDirectory=path.endsWith("/");
        path=parsePath(path);
        sFilePath="";
        sharedFolder="";
        String[] lPath = path.split("/");
        for (int i=0;i<lPath.length;i++) {
            if (i==0) sharedFolder=lPath[0];
            else sFilePath+="/"+lPath[i];
        }
        if (isDirectory) sFilePath+="/";
    }

}
