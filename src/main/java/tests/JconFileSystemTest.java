package tests;

import com.marlonrcfranco.JconFileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
            response = jconFS.write( "C:\\Users\\marlon.franco\\Documents\\teste.xml","TEste.123.#$#%#%#% texto de conteúdo.\n.\n-\n^\n| teste.");
            assert !response.contains("Erro");

            response = jconFS.read( "C:\\Users\\marlon.franco\\Documents\\teste.xml");
            assert !response.contains("Erro");

            response = jconFS.read("C:/Users/marlon.franco/Documents/teste2.xml");
            assert !response.contains("Erro");

            response = jconFS.read("C:/Users/../Documents/teste2.xml");
            showMessage();
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

            response = jconFS.write("C:/Users/marlon.franco/Documents/diretorioInexistente/testeInexistente.xml","Conteudo do arquivo inexistente.");
            showMessage();
            assert !response.contains("Erro");
            response = jconFS.delete("C:/Users/marlon.franco/Documents/diretorioInexistente/");
            showMessage();
            assert !response.contains("Erro");

            response = jconFS.write("C:/Users/../Documents/teste2.xml","Conteudo do arquivo com caminho não encontrado...7");
            showMessage();
            assert !response.contains("Erro");
            /**
             * Error response
             */
            response = jconFS.write("/marlon.franco/Documents/teste2.xml","Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");


            response = jconFS.write("~/marlon.franco/Documents/teste2.xml","Conteudo do arquivo com caminho não encontrado.");
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
            response = jconFS.write( "C:\\Users\\marlon.franco\\Documents\\teste.xml","TEste.123.#$#%#%#% texto de conteúdo.\n.\n-\n^\n| teste.");
            assert !response.contains("Erro");

            response = jconFS.copyFileTo( "C:\\Users\\marlon.franco\\Documents\\teste.xml","C:\\Users\\marlon.franco\\Documents\\testeCopia.xml");
            assert !response.contains("Erro");

            response = jconFS.copyFileTo( "C:/Users/marlon.franco/Documents/teste2.xml","C:/Users/marlon.franco/Documents/testeCopia2.xml");
            assert !response.contains("Erro");

            response = jconFS.copyFileTo("C:/Users/../Documents/teste2.xml","C:/Users/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert !response.contains("Erro");

            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/teste.xml","C:/Users/../Documents/teste2Copia.xml");
            showMessage();
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jconFS.copyFileTo("/marlon.franco/Documents/teste2.xml","C:/Users/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("~/marlon.franco/Documents/teste2.xml","C:/Users/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");

            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/teste.xml","/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");
            response = jconFS.copyFileTo("C:/Users/marlon.franco/Documents/teste.xml","~/marlon.franco/Documents/teste2Copia.xml");
            showMessage();
            assert response.contains("Erro");

        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        System.out.println("*******\nDelete\n*******\n");
        try {
            jconFS.write("C:\\Users\\marlon.franco\\Documents\\teste10.xml","teste123.content.end ");
            assert !response.contains("Erro");
            response = jconFS.delete( "C:\\Users\\marlon.franco\\Documents\\teste10.xml");
            assert !response.contains("Erro");
            System.out.println(response);

            jconFS.write("C:\\Users\\marlon.franco\\Documents\\123\\","");
            assert !response.contains("Erro");
            response = jconFS.delete( "C:\\Users\\marlon.franco\\Documents\\123\\");
            assert !response.contains("Erro");
            System.out.println(response);
        } catch (IOException e) {
            assert false;
        }
    }

    @Test
    public void listFiles() {
        System.out.println("*******\nlistFiles\n*******\n");
        try {
            jconFS.write("C:\\Users\\marlon.franco\\Documents\\teste10.xml","teste123.content.end ");
            assert !response.contains("Erro");

            response=jconFS.listFiles("C:\\Users\\marlon.franco\\Documents\\");
            System.out.println(response);
            assert !response.contains("Erro");
            response=jconFS.listFiles("C:\\Users\\marlon.franco\\Documents");
            System.out.println(response);
            assert response.contains("teste10.xml");

            ArrayList<File> list = jconFS.listFilesAsList("","C:\\Users\\marlon.franco\\Documents","","");
            assert list.size()==response.split("\\n").length;

            response = jconFS.delete( "C:\\Users\\marlon.franco\\Documents\\teste10.xml");
            assert !response.contains("Erro");
            System.out.println(response);
        } catch (IOException e) {
            assert false;
        } catch (Exception e) {
            assert false;
        }
    }

    private void showMessage() {
        if (response.contains("Erro")) {
            System.out.println(response);
        }
    }

}