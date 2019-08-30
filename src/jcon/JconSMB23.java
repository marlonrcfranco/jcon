package jcon;

import jcifs.smb.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class JconSMB23 {

    private String sIP;
    private String sUsername;
    private String sPassword;

    public JconSMB23() {
        System.out.println(
                        "┌──────────────────────────┐\n" +
                        "│       .:: JCON ::.       │\n" +
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
        String IP = filePath.split("/")[0];
        /**
         * Check if th file is in local
         */
        if ("localhost,127.0.0.1".contains(IP)) {


        }
        /**
         * If the file is not local, tries to find it remotely
         */

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
    public String write (String fileName, String content) throws IOException {
        return write(getsIP(), getsUsername(), getsPassword(), fileName, content);
    }

    public String write (String IP, String user, String pass, String filePath, String content) {
        String output="";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        String path="smb://"+IP+"/"+filePath;
        try {
            SmbFile smbFile = new SmbFile(path,auth);
            SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
            smbfos.write(content.getBytes());
            smbfos.close();
            output="Escrita concluída com sucesso.";
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
     *  Copy to remote
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
     *  Copy From Remote
     * *************************************************
     */
    public String copyFromRemoteToLocal(String remoteFilePath, String localFilePath) throws IOException {
        return copyFromRemoteToLocal(getsIP(),getsUsername(),getsPassword(),remoteFilePath,localFilePath);
    }

    public String copyFromRemoteToLocal(String IP, String user, String pass, String remoteFilePath, String localFilePath) throws IOException {
        String output=validateParameters(remoteFilePath,user,pass);
        if(!"".equalsIgnoreCase(output)) return output;
        String path="smb://"+IP+"/"+remoteFilePath;

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        SmbFile smbFile = new SmbFile(path,auth);
        SmbFileInputStream smbfin = new SmbFileInputStream(smbFile);

        FileOutputStream out = new FileOutputStream( localFilePath );

        long t0 = System.currentTimeMillis();

        byte[] b = new byte[8192];
        int n, tot = 0;
        long t1 = t0;
        while(( n = smbfin.read( b )) > 0 ) {
            out.write( b, 0, n );
            tot += n;
            System.out.print( '#' );
        }
        smbfin.close();
        out.close();
        long t = System.currentTimeMillis() - t0;
        output=tot + " bytes transfered in " + ( t / 1000 ) + " seconds at " + (( tot / 1000 ) / Math.max( 1, ( t / 1000 ))) + "Kbytes/sec";
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

    /**
     * *************************************************
     *  Get's and Set's
     * *************************************************
     */
    public String getsIP() {
        if (sIP == null) {
            sIP="";
        }
        return sIP;
    }

    public void setsIP(String sIP) {
        this.sIP = sIP;
    }

    public String getsUsername() {
        if (sUsername == null) {
            sUsername="";
        }
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsPassword() {
        if (sPassword == null) {
            sPassword="";
        }
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

}
