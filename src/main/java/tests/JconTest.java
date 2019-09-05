package tests;

import com.marlonrcfranco.IJcon;
import com.marlonrcfranco.Jcon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JconTest {

    private Jcon jcon;
    private String response;
    private String ip;
    private String user;
    private String password;
    private IJcon.types type;
    private String basePath;
    private String remoteFilePath= "/Teste/arquivo123.xml";
    private String remoteImagePath= "/Teste/images.png";
    private String remotePDFPath= "/Teste/images.png";
    private String localFilePath= "/Teste/images.png";
    private String localImagePath= "/Teste/images.png";
    private String localPDFPath= "C:\\Users\\marlon.franco\\Documents\\DOUX - Agosto.pdf";

    @BeforeEach
    void setUp() {
        ip = "192.168.35.17";
        user = "Adapcon";
        password = "1nfr4#2017";
        type = IJcon.types.SMB23;
        basePath = "Marlon";
        this.jcon = new Jcon(type);
        this.response="";
    }
    @Test
    void read() {

    }

    @Test
    void write() {
        String localBasePath = "C:\\Users\\marlon.franco\\";
        try {

            /** Test with txt file*/
            byte[] TextContent = new Jcon(IJcon.types.FILESYSTEM).readBytes("",localBasePath+"Documents\\teste.txt","","");
            this.response = new String(new Jcon(IJcon.types.SMB23).writeBytes(ip,basePath+"/Teste/text.txt",user,password,TextContent));
            System.out.println(response);
            // read to check if it was written correctly
            byte[] TextContent2 = (new Jcon(IJcon.types.SMB23).readBytes(ip,basePath+"/Teste/text.txt",user,password));
            assert Arrays.equals(TextContent, TextContent2);

            /** Test with PDF file*/
            byte[] PDFcontent = new Jcon(IJcon.types.FILESYSTEM).readBytes("",localBasePath+"Documents\\DOUX - Agosto.pdf","","");
            this.response = new String(new Jcon(IJcon.types.SMB23).writeBytes(ip,basePath+"/Teste/arquivo.pdf",user,password,PDFcontent));
            System.out.println(response);
            // read to check if it was written correctly
            byte[] PDFcontent2 = (new Jcon(IJcon.types.SMB23).readBytes(ip,basePath+"/Teste/arquivo.pdf",user,password));
            assert Arrays.equals(PDFcontent, PDFcontent2);

            /** Test with png file*/
            byte[] ImageContent = new Jcon(IJcon.types.FILESYSTEM).readBytes("",localBasePath+"Documents\\images.png","","");
            this.response = new String(new Jcon(IJcon.types.SMB23).writeBytes(ip,basePath+"/Teste/imageCopy.png",user,password,ImageContent));
            System.out.println(response);
            // read to check if it was written correctly
            byte[] ImageContent2 = (new Jcon(IJcon.types.SMB23).readBytes(ip,basePath+"/Teste/imageCopy.png",user,password));
            assert Arrays.equals(ImageContent, ImageContent2);

            /** ******* Initialize with type String ******* */
            /** Test with txt file*/
            TextContent = new Jcon("filesystem").readBytes("",localBasePath+"Documents\\teste.txt","","");
            this.response = new String(new Jcon(IJcon.types.SMB23).writeBytes(ip,basePath+"/Teste/text.txt",user,password,TextContent));
            System.out.println(response);
            // read to check if it was written correctly
            TextContent2 = (new Jcon("smb23").readBytes(ip,basePath+"/Teste/text.txt",user,password));
            assert Arrays.equals(TextContent, TextContent2);

            /** Test with PDF file*/
            PDFcontent = new Jcon("filesystem").readBytes("",localBasePath+"Documents\\DOUX - Agosto.pdf","","");
            this.response = new String(new Jcon(IJcon.types.SMB23).writeBytes(ip,basePath+"/Teste/arquivo.pdf",user,password,PDFcontent));
            System.out.println(response);
            // read to check if it was written correctly
            PDFcontent2 = (new Jcon("smb23").readBytes(ip,basePath+"/Teste/arquivo.pdf",user,password));
            assert Arrays.equals(PDFcontent, PDFcontent2);

            /** Test with png file*/
            ImageContent = new Jcon("filesystem").readBytes("",localBasePath+"Documents\\images.png","","");
            this.response = new String(new Jcon(IJcon.types.SMB23).writeBytes(ip,basePath+"/Teste/imageCopy.png",user,password,ImageContent));
            System.out.println(response);
            // read to check if it was written correctly
            ImageContent2 = (new Jcon("smb23").readBytes(ip,basePath+"/Teste/imageCopy.png",user,password));
            assert Arrays.equals(ImageContent, ImageContent2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}