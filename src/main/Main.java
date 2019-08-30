package main;

import jcon.JconSMB1;

public class Main {

    public static void main(String[] args) {

        /**
         *  Conectores
         *  ╔═══════════════╦═══════════════════════════════════════╦════════════════════════════════════╗
         *  ║ Type		    ║ Descrição							    ║ Parâmetros necessários			 ║
         *  ╠═══════════════╩═══════════════════════════════════════╩════════════════════════════════════╣
         *  ║ filesystem	│ Acesso a arquivos na própria 			│ basePath							 ║
         *  ║				│ máquina, sem a necessidade de IP.		│									 ║
         *  ║───────────────┼───────────────────────────────────────┼────────────────────────────────────║
         *  ║ smb1			│ Acesso a arquivos remotos utilizando	│ basePath,ip,username,password		 ║
         *  ║				│ o protocolo SAMBA 1 					│									 ║
         *  ║				│ até Windows 10).						│									 ║
         *  ║───────────────┼───────────────────────────────────────┼────────────────────────────────────║
         *  ║ smb23		    │ Acesso a arquivos remotos utilziando	│ basePath,ip,username,password		 ║
         *  ║				│ o protocolo SAMBA2 ou SAMBA3			│									 ║
         *  ║				│ (após Windows 10).					│									 ║
         *  ║───────────────┼───────────────────────────────────────┼────────────────────────────────────║
         *  ║ nfs			│ Acesso a arquivos remotos utilizando 	│ basePath,ip,username,password		 ║
         *  ║				│ o protocolo NFS (Linux, Unix based OS)│									 ║
         *  ╚════════════════════════════════════════════════════════════════════════════════════════════╝
         *
         */

        /**
         * Compartilhamento
         * \\192.168.35.17
         * user: Adapcon
         * pass: 1nfr4#2017
         */

//        String IP="192.168.35.17";
        String IP="CSW";
        String user="Adapcon";
        String pass="1nfr4#2017";

        JconSMB1 smb = new JconSMB1();

        String response="";

        //response = smb.write("/123/Teste777.txt", "Texto teste 1237777");
        //response = smb.read("123/Teste777.txt");

        /**
         * Leitura de arquivo remoto sem autenticação
         */
        //response = smb.read("/dados/exportQuery.csv","","");

        /**
         * Leitura de arquivo local sem autenticação
         */
        //response = smb.read("localhost/Users/marlon.franco/Documents/teste.xml","","");

        System.out.println(response);

    }

}
