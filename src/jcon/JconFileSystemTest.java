package jcon;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JconFileSystemTest {

    JconFileSystem jconFS;
    String filePath;
    String response;

    @BeforeEach
    void setUp() {
        jconFS = new JconFileSystem();
        filePath = "C:\\Users\\marlon.franco\\Documents\\teste.xml";
        response="";
    }

    @org.junit.jupiter.api.Test
    void read() {
        try {
            response = jconFS.read(filePath);
            assert !response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
        finally {
            System.out.println(response);
        }
    }

    @org.junit.jupiter.api.Test
    void write() {
        try {
            response = jconFS.write(filePath,"TEste.123.#$#%#%#% texto de conteúdo.\n.\n-\n^\n| teste.");
            assert !response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
        finally {
            System.out.println(response);
        }
    }

    @org.junit.jupiter.api.Test
    void copyFileTo() {
        try {
            response = jconFS.copyFileTo(filePath,"C:\\Users\\marlon.franco\\Documents\\teste7.xml");
            assert !response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
        finally {
            System.out.println(response);
        }
    }
}