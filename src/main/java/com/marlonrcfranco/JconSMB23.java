package com.marlonrcfranco;


import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

import java.io.IOException;
import java.util.EnumSet;

public class JconSMB23 implements IJcon{

    public JconSMB23() {
        System.out.println(
                        "┌──────────────────────────┐\n" +
                        "│     .:: JconSMB23 ::.    │\n" +
                        "└──────────────────────────┘\n" +
                        "  JconSMB23 supports SMB2 and SMB3 using JconSMBJ.\n" +
                        "  https://github.com/hierynomus/smbj\n");
    }

    @Override
    public String read(String IP, String filePath, String user, String pass) throws IOException {
        filePath=filePath.replace("\\", "/");
        String sharedFolder = extractSharedPathFromPath(filePath);
        filePath = filePath.substring(sharedFolder.length());
        return read(IP,sharedFolder,filePath,user,pass);
    }

    public String read(String IP, String sharedFolder, String filePath, String user, String pass) {
        //AccessMask = FILE_READ_DATA
        String output="";
        sharedFolder = parsePath(sharedFolder);
        filePath = parsePath(filePath);
        SMBClient client = new SMBClient();
        try (Connection connection = client.connect(IP)) {
            AuthenticationContext ac = new AuthenticationContext(user, pass.toCharArray(),"");
            Session session = connection.authenticate(ac);
            // Connect to Share
            try (DiskShare share = (DiskShare) session.connectShare(sharedFolder)) {
                File remoteFile = share.openFile(filePath,
                        EnumSet.of(AccessMask.FILE_READ_DATA),
                        null,
                        EnumSet.of(SMB2ShareAccess.FILE_SHARE_READ),
                        SMB2CreateDisposition.FILE_OPEN,
                        null);
                output=new String(remoteFile.getInputStream().readAllBytes());
                remoteFile.close();
            } catch (SMBApiException e) {
                output="Erro: Nao foi possivel localizar o diretorio "+sharedFolder+"/"+filePath;
                e.printStackTrace();
            } catch (IOException e) {
                output="Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath;
            }
        } catch (IOException e) {
            output="Erro: Nao foi possivel ler o arquivo: "+sharedFolder+"/"+filePath;
        }
        return output;
    }

    @Override
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException {

        return null;
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException {

        return null;
    }

    public String listFiles(String IP, String sharedFolder, String path, String user, String pass) {
        String output="";
        sharedFolder = parsePath(sharedFolder);
        path = parsePath(path);
        SMBClient client = new SMBClient();
        try (Connection connection = client.connect(IP)) {
            AuthenticationContext ac = new AuthenticationContext(user, pass.toCharArray(),"");
            Session session = connection.authenticate(ac);

            // Connect to Share
            try (DiskShare share = (DiskShare) session.connectShare(sharedFolder)) {
                for (FileIdBothDirectoryInformation f : share.list(path, "*.*")) {
                    output+= "\n"+"File : " + f.getFileName();
                }
            } catch (SMBApiException e) {
                output="Erro: Nao foi possivel localizar o diretorio "+sharedFolder+"/"+path;
                e.printStackTrace();
            }
        } catch (IOException e) {
            output="Erro: Nao foi possivel listar os arquivos do diretorio: "+sharedFolder;
        }
        return output;
    }

    private String parsePath(String path) {
        while (path.startsWith("\\") || path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    private String extractSharedPathFromPath(String path) {
        try {
            path=path.replace("\\", "/");
            return path.split("/")[0];
        }catch (Exception e) {
            return "";
        }
    }

}
