package jcon;

import java.io.IOException;

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

        return null;
    }

    @Override
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException {

        return null;
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException {

        return null;
    }
}
