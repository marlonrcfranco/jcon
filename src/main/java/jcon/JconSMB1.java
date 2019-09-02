package jcon;

import jcifs.smb.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class JconSMB1 implements IJcon {

    public JconSMB1() {
        System.out.println(
                        "┌──────────────────────────┐\n" +
                        "│     .:: JconSMB1 ::.     │\n" +
                        "└──────────────────────────┘\n" +
                        "    jCIFS supports SMB1.\n" +
                        "  For SMB2/3 use JconSMB23.");
    }

    @Override
    public String read(String IP, String filePath, String user, String pass) throws IOException {
        String output="";
        filePath=filePath.replace("\\", "/");

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        // "smb://IP/filePath";
        String path="smb://"+IP+"/"+filePath;
        SmbFile smbFile = null;
        SmbFileInputStream smbfin = null;
        try {
            smbFile = new SmbFile(path,auth);
            smbfin = new SmbFileInputStream(smbFile);
            output = new String(smbfin.readAllBytes());
        } catch (MalformedURLException | UnknownHostException e) {
            output="Erro: Nao foi possivel localizar o arquivo \""+path+"\"";
        } catch (SmbException e) {
            output="Erro: Nao foi possivel localizar o arquivo \""+path+"\". Verifique se o caminho, usuário e senha estão corretos, e se possui permissão de leitura.";
        } catch (IOException e) {
            output="Erro: Não foi possível ler o arquivo \""+path+"\"";
        }
        finally {
            if (smbfin != null) smbfin.close();
        }
        return output;
    }

    @Override
    public String write (String IP, String filePath, String user, String pass, String content) throws IOException {
        String output="";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);

        String path="smb://"+IP+"/"+filePath;
        SmbFile smbFile=null;
        SmbFileOutputStream smbfos=null;
        try {
            smbFile = new SmbFile(path,auth);
            smbfos = new SmbFileOutputStream(smbFile);
            smbfos.write(content.getBytes());
            output="Escrita concluída com sucesso";
        } catch (MalformedURLException | UnknownHostException e) {
            output = "Erro: Nao foi possivel localizar o caminho \"" + path + "\"";
        }catch (SmbException e) {
            output = "Erro: Verifique se o usuário e senha estão corretos, e se possui permissão de escrita para acessar o caminho \"" + path + "\"";
        } catch (IOException e) {
            output="Erro: Não foi possível ler o arquivo \""+path+"\"";
        }
        finally {
            if (smbfos != null) smbfos.close();
        }
        return output;
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException {
        String output="";
        boolean bContinue=true;
        sourceFilePath=sourceFilePath.replace("\\", "/");
        destFilePath=destFilePath.replace("\\", "/");

        String sourcePath = "smb://"+sourceIP+"/"+sourceFilePath;
        String destinationPath = "smb://"+destIP+"/"+destFilePath;

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user,pass);
        SmbFile sFile=null;
        SmbFile dFile=null;
        try {
            try {
                sFile = new SmbFile(sourcePath, auth);
            } catch (MalformedURLException e) {
                output = "Erro: Nao foi possivel localizar o caminho de origem \"" + sourcePath + "\"";
                bContinue=false;
            }
            try {
                if (bContinue) dFile = new SmbFile(destinationPath, auth);
            } catch (MalformedURLException e) {
                output = "Erro: Nao foi possivel localizar o caminho de destino \"" + destinationPath + "\"";
                bContinue=false;
            }
            if (bContinue) {
                sFile.copyTo(dFile);
                output = "Arquivo copiado com sucesso";
            }
        } catch (SmbAuthException e) {
            output = "Erro: Usuário desconhecido ou senha incorreta ";
        } catch (SmbException e) {
            output = "Erro: Nao foi possivel copiar o arquivo de \"" + sourcePath + "\" para \"" + destinationPath + "\"";
        } finally {
            sFile=null;
            dFile=null;
        }
        return output;
    }

}
