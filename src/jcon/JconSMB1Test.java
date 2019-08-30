package jcon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JconSMB1Test {

    JconSMB1 jSMB1;
    String response;
    String IP;
    String filePath;
    String user;
    String pass;

    @BeforeEach
    void setUp() {
        jSMB1 = new JconSMB1();
        filePath = "/Marlon/Teste/arquivo123.xml";
        response = "";
        IP = "192.168.35.17";
        user = "Adapcon";
        pass = "1nfr4#2017";
    }

    @Test
    void read() {
        try {
            response = jSMB1.read(IP,filePath,user,pass);
            assert !response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
        finally {
            System.out.println(response);
        }
    }

    @Test
    void write() {
        try {
            response = jSMB1.write(IP,filePath,user,pass,"Teste Arquivo 123.\n^\n^\n^\n7");
            assert !response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
        finally {
            System.out.println(response);
        }
    }

    @Test
    void copyFileTo() {
    }
}