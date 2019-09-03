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
        try {
            response = jSMBJ.readBytes(IP,"Marlon","Teste/Teste7777.txt",user,pass,null).toString();
            System.out.println(response);
            assert !response.contains("Erro");

            response = jSMBJ.read(IP,"Marlon/Marlon/Teste2/Teste777.txt",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");

            response = jSMBJ.read(IP,"//Marlon/Marlon/Teste2/Teste777.txt",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");

            response = jSMBJ.read(IP,"Marlon//Teste2/Teste777.txt",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jSMBJ.read(IP,"Teste777.txt",user,pass);
            System.out.println(response);
            assert response.contains("Erro");

        } catch (IOException e) {
            assert false;
        }

    }

    @Test
    void write() {
        try {
            response = jSMBJ.writeBytes(IP,"Marlon","/Teste/Teste7777.txt",user,pass,"Novo conteudo teste da JconSMB23.......\n\n\n\n\n\n\n7".getBytes(), null).toString();
            System.out.println(response);
            assert !response.contains("Erro");

            response = jSMBJ.write(IP, "Marlon/Marlon/Teste2/Teste777.txt", user, pass,"Teste Content 1237");
            System.out.println(response);
            assert !response.contains("Erro");

            response = jSMBJ.write(IP, "//Marlon/Marlon/Teste2/Teste7778.txt", user, pass,"Teste Content 12378");
            System.out.println(response);
            assert !response.contains("Erro");

            response = jSMBJ.write(IP, "Marlon//Teste2/Teste777.txt", user, pass, "Teste content 12357 Teste2/Teste777.txt");
            System.out.println(response);
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jSMBJ.write(IP, "Teste777.txt", user, pass,"Teste2 content 12357");
            System.out.println(response);
            assert response.contains("Erro");
        } catch (IOException e) {
            e.printStackTrace();
        }
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