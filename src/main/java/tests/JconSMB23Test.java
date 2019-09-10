package tests;

import com.marlonrcfranco.JconSMB23;
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
            System.out.println("Response: {"+response+"}");
            assert !response.contains("Erro");

            response = jSMBJ.write(IP, "Marlon/Marlon/Teste2/Teste777.txt", user, pass,"Teste Content 1237");
            System.out.println("Response: {"+response+"}");
            assert !response.contains("Erro");

            response = jSMBJ.write(IP, "//Marlon/Marlon/Teste2/Teste7778.txt", user, pass,"Teste Content 12378");
            System.out.println("Response: {"+response+"}");
            assert !response.contains("Erro");

            response = jSMBJ.write(IP, "Marlon//Teste2/Teste777.txt", user, pass, "Teste content 12357 Teste2/Teste777.txt");
            System.out.println("Response: {"+response+"}");
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jSMBJ.write(IP, "Teste777.txt", user, pass,"Teste2 content 12357");
            System.out.println("Response: {"+response+"}");
            assert response.contains("Erro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        System.out.println("*******\nDelete\n*******\n");
        try {
            jSMBJ.write(IP,"/Marlon/Teste/fileTest123.xml",user,pass,"Test File 123 XML to be deleted.\n^\n^\n^\n7");
            assert !response.contains("Erro");
            response = jSMBJ.delete(IP,"/Marlon/Teste/fileTest123.xml",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");

            jSMBJ.write(IP,"/Marlon/Teste/newFolder/fileTest123.xml",user,pass,"Test File 123 XML to be deleted.\n^\n^\n^\n7");
            assert !response.contains("Erro");
            response = jSMBJ.delete(IP,"/Marlon/Teste/newFolder/",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");

        } catch (IOException e) {
            assert false;
        }
    }

    @Test
    public void listFiles() {
        System.out.println("*******\nlistFiles\n*******\n");
        try {

            jSMBJ.write(IP,"/Marlon/Teste/fileTest123.xml",user,pass,"Test File 123 XML to be deleted.\n^\n^\n^\n7");
            assert !response.contains("Erro");
            jSMBJ.write(IP,"/Marlon/fileTest123.xml",user,pass,"Test File 123 XML to be deleted.\n^\n^\n^\n7");
            assert !response.contains("Erro");

            response = jSMBJ.listFiles(IP,"/Marlon/Teste/",user,pass);
            assert response.contains("fileTest123.xml");
            response = jSMBJ.listFiles(IP,"/Marlon/Teste",user,pass);
            assert response.contains("fileTest123.xml");
            response = jSMBJ.listFiles(IP,"/Marlon/",user,pass);
            assert response.contains("fileTest123.xml");
            response = jSMBJ.listFiles(IP,"/Marlon",user,pass);
            assert response.contains("fileTest123.xml");
            response = jSMBJ.listFiles(IP,"Marlon/",user,pass);
            assert response.contains("fileTest123.xml");
            response = jSMBJ.listFiles(IP,"Marlon",user,pass);
            assert response.contains("fileTest123.xml");

            response = jSMBJ.delete(IP,"/Marlon/Teste/fileTest123.xml",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");
            response = jSMBJ.delete(IP,"/Marlon/fileTest123.xml",user,pass);
            System.out.println(response);
            assert !response.contains("Erro");
        } catch (IOException e) {
            assert false;
        }
    }

    @Test
    void copyFileTo() {
    }

}