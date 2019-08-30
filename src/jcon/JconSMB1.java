package jcon;

import jcifs.smb.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class JconSMB1{

    public JconSMB1() {
        System.out.println(
                        "┌──────────────────────────┐\n" +
                        "│     .:: JconSMB1 ::.     │\n" +
                        "└──────────────────────────┘\n" +
                        "    jCIFS supports SMB1.\n" +
                        "  For SMB2/3 use JconSMBJ.\n" +
                        "   https://www.jcifs.org/\n");
    }

    /**
     * *************************************************
     *  Read
     * *************************************************
     */
    public String read(String filePath, String user, String pass) {
        String output="";
        filePath=filePath.replace("\\", "/");

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        // "smb://IP/filePath";
        String path="smb://"+filePath;
        SmbFile smbFile = null;
        try {
            smbFile = new SmbFile(path,auth);
            SmbFileInputStream smbfin = new SmbFileInputStream(smbFile);
            output = new String(smbfin.readAllBytes());
            smbfin.close();
        } catch (MalformedURLException | UnknownHostException e) {
            output="Erro: Nao foi possivel localizar o arquivo \""+path+"\"";
        } catch (SmbException e) {
            output = "Erro: Verifique se o usuário e senha estão corretos, e se possui permissão de leitura para acessar o caminho \"" + path + "\"";
        } catch (IOException e) {
            output="Erro: Não foi possível ler o arquivo \""+path+"\"";
        }
        return output;
    }

    private String readLocal(String filePath) {
        String output="";

        return output;
    }

    /**
     * *************************************************
     *  Write
     * *************************************************
     */

    public String write (String IP, String user, String pass, String filePath, String content) {
        String output="";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        String path="smb://"+IP+"/"+filePath;
        try {
            SmbFile smbFile = new SmbFile(path,auth);
            SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
            smbfos.write(content.getBytes());
            smbfos.close();
            output="Escrita concluída com sucesso";
        } catch (MalformedURLException | UnknownHostException e) {
            output = "Erro: Nao foi possivel localizar o caminho \"" + path + "\"";
        }catch (SmbException e) {
            output = "Erro: Verifique se o usuário e senha estão corretos, e se possui permissão de escrita para acessar o caminho \"" + path + "\"";
        } catch (IOException e) {
            output="Erro: Não foi possível ler o arquivo \""+path+"\"";
        }
        return output;
    }

    /**
     * *************************************************
     *  Copy to
     * *************************************************
     */
    public String copyTo(String sourcePath, String destinationPath, String user, String pass) {
        String output="";
        //destinationPath = "smb://destinationlocation.net";
        //sourcePath = "smb://sourcelocation.net";

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user,pass);
        SmbFile sFile=null,dFile=null;
        try {
            sFile = new SmbFile(sourcePath, auth);
        } catch (MalformedURLException e) {
            output = "Erro: Nao foi possivel localizar o caminho de origem \"" + sourcePath + "\"";
        }
        try {
            dFile = new SmbFile(destinationPath, auth);
        } catch (MalformedURLException e) {
            output = "Erro: Nao foi possivel localizar o caminho de destino \"" + destinationPath + "\"";
        }
        try {
            sFile.copyTo(dFile);
        } catch (SmbException e) {
            output = "Erro: Nao foi possivel copiar o arquivo de \"" + sourcePath + "\" para \"" + destinationPath + "\"";
        }
        return output;
    }

    /**
     * *************************************************
     *  validate Parameters
     * *************************************************
     */
    private String validateParameters(String filePath, String User, String Pass) {
        String error="";
        if("".equalsIgnoreCase(filePath)) {
            error+= "Caminho para Arquivo não informado;\n";
        }
        if("".equalsIgnoreCase(User)) {
            error+= "Username não informado;\n";
        }
        if("".equalsIgnoreCase(Pass)) {
            error+= "Senha não informada;\n";
        }
        return error;
    }

}
