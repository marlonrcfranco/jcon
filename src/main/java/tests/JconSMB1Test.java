package tests;

import com.marlonrcfranco.JconSMB1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        System.out.println("*******\nRead\n*******\n");
        try {
            response = jSMB1.read(IP,"/Marlon/Teste/arquivo123.xml",user,pass);
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jSMB1.read(IP,"C:/Users/marlon.franco/Documents/testeInexistente.xml",user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.read(IP,"/marlon.franco/Documents/teste2.xml",user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.read(IP,"~/marlon.franco/Documents/teste2.xml",user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.read(IP,"C:/Users/../Documents/teste2.xml",user,pass);
            showMessage();
            assert response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
    }

    @Test
    void write() {
        System.out.println("*******\nWrite\n*******\n");
        try {
            response = jSMB1.write(IP,filePath,user,pass,"Teste Arquivo 123.\n^\n^\n^\n7");
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jSMB1.write(IP,"C:/Users/marlon.franco/Documents/diretorioInexistente/testeInexistente.xml",user,pass,"Conteudo do arquivo inexistente.");
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.write(IP,"/marlon.franco/Documents/teste2.xml",user,pass,"Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.write(IP,"~/marlon.franco/Documents/teste2.xml",user, pass,"Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.write(IP,"C:/Users/../Documents/teste2.xml",user,pass,"Conteudo do arquivo com caminho não encontrado.");
            showMessage();
            assert response.contains("Erro");
        } catch (IOException e) {
            assert false;
            e.printStackTrace();
        }
    }

    @Test
    void copyFileTo() {
        System.out.println("*******\nCopyFileTo\n*******\n");
        try {
            response = jSMB1.copyFileTo( IP,filePath,IP,"/Marlon/Teste/testeSMB1copyFromRemote.xml",user,pass);
            assert !response.contains("Erro");

            /**
             * Error response
             */
            response = jSMB1.copyFileTo( "localhost","C:\\Users\\marlon.franco\\Documents\\testeSMB1.xml","localhost","C:\\Users\\marlon.franco\\Documents\\testeSMB1copyFromLocal.xml",user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.copyFileTo( IP,filePath,"localhost","C:\\Users\\marlon.franco\\Documents\\testeSMB1copyFromRemote.xml",user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.copyFileTo( "localhost","C:\\Users\\marlon.franco\\Documents\\testeSMB1.xml",IP,filePath,user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.copyFileTo( "localhost","C:\\Users\\marlon.franco\\Documents\\testeSMB1.xml",IP,filePath,"","");
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.copyFileTo(IP,"C:/Users/marlon.franco/Documents/diretorioInexistente/testeInexistente.xml",IP,"C:/Users/marlon.franco/Documents/testeInexistenteCopia.xml",user,pass);
            showMessage();
            assert response.contains("Erro");
            response = jSMB1.copyFileTo(IP,"/marlon.franco/Documents/teste2.xml",IP,filePath,user,pass);
            showMessage();
            assert response.contains("Erro");
            response = jSMB1.copyFileTo(IP,"~/marlon.franco/Documents/teste2.xml",IP,filePath,user,pass);
            showMessage();
            assert response.contains("Erro");
            response = jSMB1.copyFileTo(IP,"C:/Users/../Documents/teste2.xml",IP, filePath,user,pass);
            showMessage();
            assert response.contains("Erro");

            response = jSMB1.copyFileTo(IP,filePath,IP,"/marlon.franco/Documents/teste2Copia.xml",user,pass);
            showMessage();
            assert response.contains("Erro");
            response = jSMB1.copyFileTo(IP,filePath,IP,"~/marlon.franco/Documents/teste2Copia.xml",user,pass);
            showMessage();
            assert response.contains("Erro");
            response = jSMB1.copyFileTo(IP,filePath,IP,"C:/Users/../Documents/teste2Copia.xml",user,pass);
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