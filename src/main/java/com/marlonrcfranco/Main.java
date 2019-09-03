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
                "\n═════════════════════════════════════════════════\n" +
                "Type \"help\" or \"h\" for help.";
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
        String help ="" +
                "\n help[h]                     - Show this help" +
                "\n info[i]                     - Show initial info" +
                "\n connectors[c][con]          - Show info about different connectors" +
                "\n read[r] <type> <params...>  - Read method (see connectors for more info)" +
                "\n write[w] <type> <params...> - Write method (see connectors for more info)" +
                "\n ";
        /**
         * Compartilhamento
         * \\192.168.35.17
         * user: Adapcon
         * pass: 1nfr4#2017
         */

        System.out.println(info);
        while((!"exit".equalsIgnoreCase(input) && !"quit".equalsIgnoreCase(input) && !"bye".equalsIgnoreCase(input)) || "".equalsIgnoreCase(input)) {
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
                    break;
                case "c":
                case "con":
                case "connectors":
                    System.out.println(connectors);
                    break;
                default:break;
            }
            if (input.startsWith("read ") || input.startsWith("r ")) {
                System.out.println(read(input));
            }
            if (input.startsWith("write ") || input.startsWith("w ")) {
                System.out.println(write(input));
            }
        }
    }

    private static String read(String input) {
        String output="";
        String[] lInput = verifyInput(input,"read");
        String error = lInput[0];
        String cmd = lInput[1];
        String type = lInput[2];
        String params = lInput[3];
        if (!"".equalsIgnoreCase(error)) return error;

        return output;
    }

    private static String write(String input) {
        String output="";
        String[] lInput = verifyInput(input,"write");
        String error = lInput[0];
        String cmd = lInput[1];
        String type = lInput[2];
        String params = lInput[3];
        if (!"".equalsIgnoreCase(error)) return error;

        return output;
    }

    private static String[] verifyInput(String input,String operation) {
        String[] response = new String[4];
        response[0]="";
        String oper=operation.substring(0,1);
        String cmd,type,params;
        String[] args = input.split(" ");
        if (args.length != 3) {
            response[0]="Invalid syntax for "+operation+".\n  Expected 3 arguments separated by whitespace.\n    Type: "+operation+"["+oper+"] <type> <params...>";
            return response;
        }
        cmd=args[0].trim();
        type=args[1].trim();
        params=args[2].trim();
        if ("".equalsIgnoreCase(cmd) || (!operation.equalsIgnoreCase(cmd) && !oper.equalsIgnoreCase(cmd))) {
            response[0]="Invalid syntax for "+operation+".\n  Expected 1st argument to be \""+operation+"\" or \""+oper+"\".\n    Type: "+operation+"["+oper+"] <type> <params...>";
            return response;
        }
        try {
            IJcon.types.valueOf(type.toUpperCase().trim());
        } catch (Exception e) {
            response[0]="Invalid syntax for "+operation+".\n  Not regognized type \""+type+"\".\n    Type \"con\" for more info of available connectors.";
            return response;
        }

        return response;
    }

}
