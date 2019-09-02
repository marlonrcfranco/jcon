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
        System.out.println("*******\nRead\n*******\n");
        try {
            response = jconFS.read( "C:\\Users\\marlon.franco\\Documents\\teste.xml");
            assert !response.contains("Erro");

            response = jconFS.read("C:/Users/marlon.franco/Documents/teste2.xml");
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jconFS.read("C:/Users/marlon.franco/Documents/testeInexistente.xml");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.read("/marlon.franco/Documents/teste2.xml");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.read("~/marlon.franco/Documents/teste2.xml");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.read("C:/Users/../Documents/teste2.xml");
            showMessage();
            assert response.contains("Erro");

        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void write() {
        System.out.println("*******\nWrite\n*******\n");
        try {
            response = jconFS.write( "C:\\Users\\marlon.franco\\Documents\\teste.xml","TEste.123.#$#%#%#% texto de conteúdo.\n.\n-\n^\n| teste.");
            assert !response.contains("Erro");

            response = jconFS.write("C:/Users/marlon.franco/Documents/teste2.xml","TEste.123.#$#%#%#% texto de conteúdo.\n.\n-\n^\n| teste.");
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jconFS.write("C:/Users/marlon.franco/Documents/diretorioInexistente/testeInexistente.xml","Conteudo do arquivo inexistente.");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.write("/marlon.franco/Documents/teste2.xml","Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.write("~/marlon.franco/Documents/teste2.xml","Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.write("C:/Users/../Documents/teste2.xml","Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");

        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void copyFileTo() {
        System.out.println("*******\nCopyFileTo\n*******\n");
        try {
            response = jconFS.copyFileTo( "C:\\Users\\marlon.franco\\Documents\\teste.xml","C:\\Users\\marlon.franco\\Documents\\testeCopia.xml");
            assert !response.contains("Erro");

            response = jconFS.copyFileTo( "C:/Users/marlon.franco/Documents/teste2.xml","C:/Users/marlon.franco/Documents/testeCopia2.xml");
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/diretorioInexistente/testeInexistente.xml","C:/Users/marlon.franco/Documents/testeInexistenteCopia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("/marlon.franco/Documents/teste2.xml","C:/Users/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("~/marlon.franco/Documents/teste2.xml","C:/Users/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("C:/Users/../Documents/teste2.xml","C:/Users/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/teste.xml","/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/teste.xml","~/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/teste.xml","C:/Users/../Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");

        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
    }

    private void showMessage() {
        if (response.contains("Erro")) {
            System.out.println(response);
        }
    }

}