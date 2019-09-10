package tests;

import com.marlonrcfranco.JconNFS;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class JconNFSTest {

    @BeforeAll
    public static void setUp() throws IOException {
        /**
         * usuário: filesistem
         * senha: filesistem
         * IP: 192.168.35.16
         *
         * COMPARTILHAMENTOS
         * \\NAS-APOIO\SMBPrivado
         * \\NAS-APOIO\SMBPublico
         * \\NAS-APOIO\NFSPrivado
         * \\NAS-APOIO\NFSPublico
         */

        JconNFS jNFS = new JconNFS();
//        assert !jNFS.write("192.168.35.16","NFSPublico"+"/ArquivoTeste.txt","filesistem","filesistem","Content Teste").contains("Erro");
    }

    @Test
    public void read() throws IOException {
        JconNFS jNFS = new JconNFS();
//        assert !jNFS.read("192.168.35.16","NFSPublico"+"/ArquivoTeste.txt","filesistem","filesistem").contains("Erro");
    }

    @Test
    public void write() {

    }

    @Test
    public void delete() {

    }

    @Test
    public void listFiles() {

    }

    @Test
    public void copyFileTo() {

    }

    @AfterAll
    public static void setDown() throws IOException {
        JconNFS jNFS = new JconNFS();
//        assert !jNFS.delete("192.168.35.16","NFSPublico"+"/ArquivoTeste.txt","filesistem","filesistem").contains("Erro");
    }

}