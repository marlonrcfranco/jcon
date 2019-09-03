package com.marlonrcfranco;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String input="";
        String info = "\n" +
                "\n═════════════════════════════════════════════════\n" +
                "     ╔═╗       ╔═╗  ╔╗\n" +
                "     ║ ╠═══╦═══╣ ╚╗ ║║\n" +
                " ╔═╗ ║ ║ ╔═╣╔═╗║╔╗╚╗║║\n" +
                " ║ ╚═╝ ║ ╚═╣╚═╝║║╚╗╚╝║\n" +
                " ╚═════╩═══╩═══╩╝ ╚══╝ v0.1\n" +
                "\n@marlonrcfranco" +
                "\nhttps://github.com/marlonrcfranco/jcon" +
                "\n\nBased on the projects:" +
                "\n  jCIFS: https://www.jcifs.org/ (for SMB1)" +
                "\n  smbj : https://github.com/hierynomus/smbj (for SMB2/3)" +
                "\n═════════════════════════════════════════════════";
        String connectors =
        "*  Connectors\n" +
                " ╔═══════════════╦═══════════════════════════════════════╦════════════════════════════════════╗\n" +
                " ║ type          ║ Description                           ║ Parameters needed                  ║\n" +
                " ╠═══════════════╩═══════════════════════════════════════╩════════════════════════════════════╣\n" +
                " ║ filesystem    │ Access the local filesystem without   │ basePath                           ║\n" +
                " ║               │ informing the IP address.             │                                    ║\n" +
                " ║───────────────┼───────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ smb1          │ Acesso a arquivos remotos utilizando  │ basePath,ip,username,password      ║\n" +
                " ║               │ o protocolo SMB 1                     │                                    ║\n" +
                " ║               │ até Windows 10).                      │                                    ║\n" +
                " ║───────────────┼───────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ smb23         │ Acesso a arquivos remotos utilziando  │ basePath,ip,username,password      ║\n" +
                " ║               │ o protocolo SMB 2 ou SMB 3            │                                    ║\n" +
                " ║               │ (após Windows 10).                    │                                    ║\n" +
                " ║───────────────┼───────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ nfs           │ Acesso a arquivos remotos utilizando  │ basePath,ip,username,password      ║\n" +
                " ║               │ o protocolo NFS (Linux, Unix based OS)│                                    ║\n" +
                " ╚════════════════════════════════════════════════════════════════════════════════════════════╝\n" +
                " \n";
        String help ="\n" +
                "\n help [h]         - Show this help" +
                "\n info [i]         - Show initial info" +
                "\n connectors [con] - Show info about different connectors" +
                "\n ";
        /**
         * Compartilhamento
         * \\192.168.35.17
         * user: Adapcon
         * pass: 1nfr4#2017
         */

        System.out.println(info);
        while(!"exit,quit,halt,bye".contains(input) || "".equalsIgnoreCase(input)) {
            System.out.println("\n Type \"help\" for Help.");
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            switch (input) {
                case "h":
                case "help":
                    System.out.println(help);
                    break;
                case "i":
                case "info":
                case "about":
                    System.out.println(info);
                case "con":
                case "connectors":
                    System.out.println(connectors);
                default:break;
            }
        }


    }

}
