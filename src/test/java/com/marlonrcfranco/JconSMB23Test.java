package com.marlonrcfranco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JconSMB23Test {

    JconSMB23 jSMBJ;
    String response;
    String IP;
    String filePath;
    String sharedFolder;
    String user;
    String pass;

    @BeforeEach
    void setUp() {
        jSMBJ = new JconSMB23();
        filePath = "/Marlon/Teste/arquivo123.xml";
        sharedFolder = "Marlon";
        response = "";
        IP = "192.168.35.17";
        user = "Adapcon";
        pass = "1nfr4#2017";
    }

    @Test
    void read() {
        response = jSMBJ.read(IP,"Marlon","Teste/Teste777.txt",user,pass);
        System.out.println(response);
        assert !response.contains("Erro");

        try {
            response = jSMBJ.read(IP,"Marlon/Marlon/Teste/Teste777.txt",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void write() {
    }

    @Test
    void copyFileTo() {
    }

    @Test
    void listFiles() {
        response = jSMBJ.listFiles(IP,sharedFolder,"Teste/SubFolderTeste",user,pass);
        System.out.println(response);
        assert !response.contains("Erro");

        response = jSMBJ.listFiles(IP,sharedFolder,"/Teste/SubFolderTeste",user,pass);
        System.out.println(response);
        assert !response.contains("Erro");

        response = jSMBJ.listFiles(IP,sharedFolder,"////Teste/SubFolderTeste",user,pass);
        System.out.println(response);
        assert !response.contains("Erro");

        response = jSMBJ.listFiles(IP,sharedFolder,"////",user,pass);
        System.out.println(response);
        assert !response.contains("Erro");

        response = jSMBJ.listFiles(IP,sharedFolder,"\\Teste\\SubFolderTeste",user,pass);
        System.out.println(response);
        assert !response.contains("Erro");


    }
}