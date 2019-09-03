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
                " ╔═══════════════╦════════════════════════════════════════╦════════════════════════════════════╗\n" +
                " ║ type          ║ Description                            ║ Parameters needed                  ║\n" +
                " ╠═══════════════╩════════════════════════════════════════╩════════════════════════════════════╣\n" +
                " ║ filesystem    │ Access the local filesystem without    │ basePath                           ║\n" +
                " ║               │ informing the IP address.              │                                    ║\n" +
                " ║───────────────┼────────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ smb1          │ Access files in remote filesystem      │ basePath,ip,username,password      ║\n" +
                " ║               │ using SMB 1 protocol.                  │                                    ║\n" +
                " ║               │ (works for versions before Windows 10) │                                    ║\n" +
                " ║───────────────┼────────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ smb23         │ Access files in remote filesystem      │ basePath,ip,username,password      ║\n" +
                " ║               │ using SMB 2 or SMB 3 protocols.        │                                    ║\n" +
                " ║               │ (works for versions after Windows 10)  │                                    ║\n" +
                " ║───────────────┼────────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ nfs           │ Access files in remote filesystem      │ basePath,ip,username,password      ║\n" +
                " ║               │ using NFS protocol. [NOT IMPLEMENTED]  │                                    ║\n" +
                " ║               │ (Linux, Unix based OS)                 │                                    ║\n" +
                " ╚═════════════════════════════════════════════════════════════════════════════════════════════╝\n" +
                " \n";
        String help ="" +
                "\n help[h]                     - Show this help" +
                "\n info[i]                     - Show initial info" +
                "\n connectors[c][con]          - Show info about different connectors" +
                "\n read[r] <type> <p1,p2,...>  - Read method (see connectors for more info)" +
                "\n write[w] <type> <p1,p2,...> - Write method (see connectors for more info)" +
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
        String[] lParam=params.split(",");
        String IP=params.split(",")[0];
        return output;
    }

    private static String[] verifyInput(String input,String operation) {
        String[] response = new String[4];
        response[0]="";
        String oper=operation.substring(0,1);
        String cmd,type;
        String[] params;
        String[] args = input.split(" ");
        if (args.length != 3) {
            response[0]="Invalid syntax for "+operation+".\n  Expected 3 arguments separated by whitespace.\n    Type: "+operation+"["+oper+"] <type> <p1,p2,...>";
            return response;
        }
        cmd=args[0].trim();
        type=args[1].trim();
        params=args[2].trim().split(",");
        if ("".equalsIgnoreCase(cmd) || (!operation.equalsIgnoreCase(cmd) && !oper.equalsIgnoreCase(cmd))) {
            response[0]="Invalid syntax for "+operation+".\n  Expected 1st argument to be \""+operation+"\" or \""+oper+"\".\n    Type: "+operation+"["+oper+"] <type> <p1,p2,...>";
            return response;
        }
        try {
            IJcon.types.valueOf(type.toUpperCase().trim());
        } catch (Exception e) {
            response[0]="Invalid syntax for "+operation+".\n  Not regognized type \""+type+"\".\n    Type \"con\" for more info about available connectors.";
            return response;
        }
        response[1]=cmd;
        response[2]=type;
        if (IJcon.types.FILESYSTEM.equals(type) && params.length < 1) {
            response[0]="Invalid syntax for "+operation+".\n  Type \""+type+"\" expects at least 1 parameter (filePath).\n    Type \"con\" for more info about available connectors.";
            return response;
        }
        if (params.length > 0) {
            response[3]=
        }

        return response;
    }

}
