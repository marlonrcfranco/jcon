package com.marlonrcfranco;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

public class JconSMB23 implements IJcon{

    private String sharedFolder;
    private String sFilePath;

    public JconSMB23() {
        sharedFolder="";
        sFilePath="";
        System.out.println(
                        "┌──────────────────────────┐\n" +
                        "│     .:: JconSMB23 ::.    │\n" +
                        "└──────────────────────────┘\n" +
                        "  JconSMB23 supports SMB2 and SMB3 using SMBJ.\n" +
                        "  https://github.com/hierynomus/smbj\n");
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
        SMBClient client = new SMBClient();
        try (Connection connection = client.connect(IP)) {
            AuthenticationContext ac = new AuthenticationContext(user, pass.toCharArray(),domain);
            Session session = connection.authenticate(ac);
            // Connect to Share
            try (DiskShare share = (DiskShare) session.connectShare(sharedFolder)) {
                File remoteFile = share.openFile(filePath,
                        EnumSet.of(AccessMask.GENERIC_READ),
                        null,
                        EnumSet.of(SMB2ShareAccess.FILE_SHARE_READ),
                        SMB2CreateDisposition.FILE_OPEN,
                        null);
                output = Util.toByteArray(remoteFile.getInputStream());
                remoteFile.close();
            } catch (SMBApiException e) {
                output=("Erro: Nao foi possivel localizar o caminho "+sharedFolder+"/"+filePath
                        +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
            } catch (IOException e) {
                output=("Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath
                        +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
            }
        } catch (IOException e) {
            output=("Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath
                    +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
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
        return writeBytes(IP, sharedFolder, sFilePath, user, pass, content, null);
    }

    public byte[] writeBytes(String IP, String sharedFolder, String filePath, String user, String pass, byte[] content, String domain) {
        //AccessMask = FILE_READ_DATA
        byte[] output="".getBytes();
        sharedFolder = parsePath(sharedFolder);
        filePath = parsePath(filePath);
        File remoteFile=null;
        SMBClient client = new SMBClient();
        try (Connection connection = client.connect(IP)) {
            AuthenticationContext ac = new AuthenticationContext(user, pass.toCharArray(),domain);
            Session session = connection.authenticate(ac);
            // Connect to Share
            try (DiskShare share = (DiskShare) session.connectShare(sharedFolder)) {
                String path = filePath;
                int idx=1;
                // if file is in folder(s), create them first
                while(idx > 0) {
                    idx = path.lastIndexOf("/");
                    idx = idx<0? 0 : idx;
                    path=path.substring(idx);
                    String folder = filePath.substring(0, idx);
                    try {
                        if(!share.folderExists(folder)) share.mkdir(folder);
                    } catch (SMBApiException ex) {
                        throw new IOException(ex);
                    }
                }
                if(!share.fileExists(filePath)){
                    remoteFile = share.openFile(filePath,
                            new HashSet<>(Arrays.asList(AccessMask.GENERIC_ALL)),
                            new HashSet<>(Arrays.asList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                            SMB2ShareAccess.ALL,
                            SMB2CreateDisposition.FILE_CREATE,
                            new HashSet<>(Arrays.asList(SMB2CreateOptions.FILE_DIRECTORY_FILE))
                    );
                }else {
                    remoteFile = share.openFile(filePath,
                            new HashSet<>(Arrays.asList(AccessMask.GENERIC_ALL)),
                            new HashSet<>(Arrays.asList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                            SMB2ShareAccess.ALL,
                            SMB2CreateDisposition.FILE_OVERWRITE_IF,
                            new HashSet<>(Arrays.asList(SMB2CreateOptions.FILE_DIRECTORY_FILE))
                    );
                }
                OutputStream os = remoteFile.getOutputStream();
                os.write(content);
                os.close();
                output=("Escrita concluída com sucesso").getBytes();
            } catch (SMBApiException e) {
                output=("Erro: Verifique se o usuário e senha estão corretos, e se possui permissão para acessar o caminho \"" + sharedFolder+"/"+filePath + "\""
                        +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
            } catch (IOException e) {
                output=("Erro: Nao foi possivel escrever no arquivo: "+sharedFolder+"/"+filePath
                        +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
            }
        } catch (IOException e) {
            output=("Erro: Nao foi possivel escrever no arquivo: "+sharedFolder+"/"+filePath
                    +((e.getMessage() != null)?" ("+e.getMessage()+")":"")).getBytes();
        }
        return output;
    }

    @Override
    public String delete(String IP, String filePath, String user, String pass) throws IOException {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        return delete(IP, sharedFolder, sFilePath, user, pass, null);
    }

    public String delete(String IP, String sharedFolder, String filePath, String user, String pass, String domain) throws IOException {
        String output="";
        sharedFolder = parsePath(sharedFolder);
        filePath = parsePath(filePath);
        File remoteFile=null;
        SMBClient client = new SMBClient();
        try (Connection connection = client.connect(IP)) {
            AuthenticationContext ac = new AuthenticationContext(user, pass.toCharArray(),domain);
            Session session = connection.authenticate(ac);
            // Connect to Share
            try (DiskShare share = (DiskShare) session.connectShare(sharedFolder)) {
                if(share.fileExists(filePath)){
                    share.rm(filePath);
                    output = "File \""+sharedFolder+"/"+filePath+"\" deleted successfully.";
                }else if (share.folderExists(filePath)){
                    share.rmdir(filePath,true);
                    output = "Directory \""+sharedFolder+"/"+filePath+"\" deleted successfully.";
                }else {
                    output = "Error: File \""+sharedFolder+"/"+filePath+"\" not found.";
                }
            } catch (SMBApiException e) {
                output="Erro: Verifique se o usuário e senha estão corretos, e se possui permissão de escrita para acessar o caminho \"" + sharedFolder+"/"+filePath + "\"";
                if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
            } catch (IOException e) {
                output="Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath;
                if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
            }
        } catch (IOException e) {
            output="Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath;
            if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
        }
        return output;
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException {
        String output="";
        output+= "Erro: Nao foi possivel copiar o arquivo de \"" + sourceFilePath + "\" para \"" + destFilePath + "\"; [motivo: nao implementado]";
        return output;
    }

    @Override
    public String listFiles(String IP, String filePath, String user, String pass) throws IOException {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        String output="";
        boolean isDirectory = false;
        sharedFolder = parsePath(sharedFolder);
        String path = parsePath(sFilePath);
        if (!path.trim().endsWith("/") && !"".equalsIgnoreCase(path.trim())) path+="/";
        try {
            ArrayList<FileIdBothDirectoryInformation> list = listFilesAsList(IP, sharedFolder, sFilePath, user, pass, null);
            for (FileIdBothDirectoryInformation f : list) {
                isDirectory = f.getFileAttributes()==FileAttributes.FILE_ATTRIBUTE_DIRECTORY.getValue();
                output+= f.getFileName() + (isDirectory? "/" : "") + "\n";
            }
        } catch (IOException e) {
             output="Erro: Nao foi possivel listar os arquivos do diretorio: "+sharedFolder;
             if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
        } catch (Exception e) {
            output="Erro: Nao foi possivel localizar o diretorio "+sharedFolder+(!"".equalsIgnoreCase((sharedFolder+path).trim())? "/" : "")+path;
            if (e.getMessage() != null) output+=" ("+e.getMessage()+")";
        }
        return output;
    }

    @Override
    public ArrayList<FileIdBothDirectoryInformation> listFilesAsList(String IP, String filePath, String user, String pass) throws Exception {
        extractSharedPathFromPath(filePath.replace("\\", "/"));
        return listFilesAsList(IP, sharedFolder, sFilePath, user, pass, null);
    }

    public ArrayList<FileIdBothDirectoryInformation> listFilesAsList(String IP, String sharedFolder, String path, String user, String pass, String domain) throws Exception {
        ArrayList<FileIdBothDirectoryInformation> output = new ArrayList<>();
        sharedFolder = parsePath(sharedFolder);
        path = parsePath(path);
        if (!path.trim().endsWith("/") && !"".equalsIgnoreCase(path.trim())) path+="/";
        SMBClient client = new SMBClient();
        Connection connection = client.connect(IP);
        AuthenticationContext ac = new AuthenticationContext(user, pass.toCharArray(),domain);
        Session session = connection.authenticate(ac);
        // Connect to Share
        DiskShare share = (DiskShare) session.connectShare(sharedFolder);
        for (FileIdBothDirectoryInformation f : share.list(path, "*")) {
            output.add(f);
        }
        if (share!=null) share.close();
        if (session!=null) session.close();
        if (connection!=null) connection.close();
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
