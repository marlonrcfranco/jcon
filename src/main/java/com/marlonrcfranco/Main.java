package com.marlonrcfranco;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        String input="";
        String info = "\n" +
                "\n╔════════════════════════════════════════════════╗" +
                "\n║                ╔═╗       ╔═╗  ╔╗               ║" +
                "\n║                ║ ╠═══╦═══╣ ╚╗ ║║               ║" +
                "\n║            ╔═╗ ║ ║ ╔═╣╔═╗║╔╗╚╗║║               ║" +
                "\n║            ║ ╚═╝ ║ ╚═╣╚═╝║║╚╗╚╝║               ║" +
                "\n║            ╚═════╩═══╩═══╩╝ ╚══╝ v"+Util.getVersion()+"          ║" +
                "\n║               @marlonrcfranco                  ║" +
                "\n║     https://github.com/marlonrcfranco/jcon     ║" +
                "\n║                                                ║" +
                "\n║ Based on the projects:                         ║" +
                "\n║     jCIFS: https://www.jcifs.org/              ║" +
                "\n║            (for SMB1)                          ║" +
                "\n║      smbj: https://github.com/hierynomus/smbj  ║" +
                "\n║            (for SMB2/3)                        ║" +
                "\n╚════════════════════════════════════════════════╝\n" +
                "    Type \"help\" or \"h\" for help.";
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
                " ║               │ (works for versions before Windows 10) │(basePath is a remote shared folder)║\n" +
                " ║───────────────┼────────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ smb23         │ Access files in remote filesystem      │ basePath,ip,username,password      ║\n" +
                " ║               │ using SMB 2 or SMB 3 protocols.        │                                    ║\n" +
                " ║               │ (works for versions after Windows 10)  │(basePath is a remote shared folder)║\n" +
                " ║───────────────┼────────────────────────────────────────┼────────────────────────────────────║\n" +
                " ║ nfs           │ Access files in remote filesystem      │ basePath,ip,username,password      ║\n" +
                " ║               │ using NFS protocol.                    │                                    ║\n" +
                " ║               │ (Linux, Unix based OS)                 │(basePath is a remote shared folder)║\n" +
                " ╚═════════════════════════════════════════════════════════════════════════════════════════════╝\n" +
                " \n";
        String help ="" +
                "\n connectors[c][con]           - Show info about different connectors" +
                "\n delete[d] <type> <p1,p2,...> - Delete a file " +
                "\n examples[e]                  - Show examples of possible operations" +
                "\n exit[quit][bye]              - Close the execution" +
                "\n help[h]                      - Show this help" +
                "\n info[i]                      - Show initial info" +
                "\n list[l] <type> <p1,p2,...>   - List all files" +
                "\n read[r] <type> <p1,p2,...>   - Read method (see connectors for more info)" +
                "\n write[w] <type> <p1,p2,...>  - Write method (see connectors for more info)" +
                "\n ";
        String examples = "" +
                "Examples:\n" +
                "\n" +
                "  l filesystem <local_directory>\n" +
                "  w filesystem <local_file_path>,<content>\n" +
                "  r filesystem <local_file_path>\n" +
                "  d filesystem <local_file_path>\n" +
                "\n" +
                "  l smb1 <remote_directory>,<IP>,<username>,<password>\n" +
                "  w smb1 <remote_file_path>,<IP>,<username>,<password>,<content>\n" +
                "  r smb1 <remote_file_path>,<IP>,<username>,<password>\n" +
                "  d smb1 <remote_file_path>,<IP>,<username>,<password>\n" +
                "\n" +
                "  l smb23 <remote_directory>,<IP>,<username>,<password>\n" +
                "  w smb23 <remote_file_path>,<IP>,<username>,<password>,<content>\n" +
                "  r smb23 <remote_file_path>,<IP>,<username>,<password>\n" +
                "  d smb23 <remote_file_path>,<IP>,<username>,<password>\n" +
                "";
        System.out.println(info);
        while((!"exit".equalsIgnoreCase(input) && !"quit".equalsIgnoreCase(input) && !"bye".equalsIgnoreCase(input)) || "".equalsIgnoreCase(input)) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            input = input.trim();
            switch (input) {
                case "exit":
                case "quit":
                case "bye":
                    break;
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
                case "e":
                case "examples":
                    System.out.println(examples);
                    break;
                default:
                    if (input.startsWith("read ") || input.startsWith("r ") || "r".equalsIgnoreCase(input)) {
                        System.out.println("\n"+read(input)+"\n");
                    }else
                    if (input.startsWith("write ") || input.startsWith("w ") || "w".equalsIgnoreCase(input)) {
                        System.out.println("\n"+write(input)+"\n");
                    }else
                    if (input.startsWith("delete ") || input.startsWith("d ") || "d".equalsIgnoreCase(input)) {
                        System.out.println("\n"+delete(input)+"\n");
                    }else
                    if (input.startsWith("list ") || input.startsWith("l ") || "l".equalsIgnoreCase(input)) {
                        System.out.println("\n"+list(input)+"\n");
                    }else {
                        System.out.println("\""+input+"\" is not a recognized command.");
                    }
            }
        }
    }

    private static String read(String input) {
        String output="";
        HashMap<String,String> aInput = verifyInput(input,"read");
        if (!"".equalsIgnoreCase(aInput.get("error"))) return aInput.get("error");
        Jcon jcon = new Jcon(aInput.get("type"));
        try {
            return jcon.read(aInput.get("IP"), aInput.get("basePath"),aInput.get("username"),aInput.get("password"));
        } catch (Exception e) {
            return "";
        }
    }

    private static String write(String input) {
        String output="";
        HashMap<String,String> aInput = verifyInput(input,"write");
        if (!"".equalsIgnoreCase(aInput.get("error"))) return aInput.get("error");
        Jcon jcon = new Jcon(aInput.get("type"));
        try {
            return jcon.write(aInput.get("IP"), aInput.get("basePath"),aInput.get("username"),aInput.get("password"),aInput.get("content"));
        } catch (Exception e) {
            return "";
        }
    }

    private static String delete(String input) {
        String output="";
        HashMap<String,String> aInput = verifyInput(input,"delete");
        if (!"".equalsIgnoreCase(aInput.get("error"))) return aInput.get("error");
        Jcon jcon = new Jcon(aInput.get("type"));
        try {
            return jcon.delete(aInput.get("IP"), aInput.get("basePath"),aInput.get("username"),aInput.get("password"));
        } catch (Exception e) {
            return "";
        }
    }

    private static String list(String input) {
        String output="";
        HashMap<String,String> aInput = verifyInput(input,"list");
        if (!"".equalsIgnoreCase(aInput.get("error"))) return aInput.get("error");
        Jcon jcon = new Jcon(aInput.get("type"));
        try {
            return jcon.listFiles(aInput.get("IP"), aInput.get("basePath"),aInput.get("username"),aInput.get("password"));
        } catch (Exception e) {
            return "";
        }
    }

    private static HashMap<String,String> verifyInput(String input, String operation) {
        HashMap<String,String> response = new HashMap<>();
        response.put("error", "");
        response.put("cmd", "");
        response.put("type", "");
        response.put("basePath", "");
        response.put("IP", "");
        response.put("username", "");
        response.put("password", "");
        response.put("content", ""); // only for "write" operation
        String oper=operation.substring(0,1);
        String cmd,type;
        String[] params;
        String[] args = input.split(" ",3);
        if (args.length < 3) {
            response.put("error","Invalid syntax for "+operation+".\n  Expected 3 arguments separated by whitespace.\n    Type: "+operation+"["+oper+"] <type> <p1,p2,...>");
            return response;
        }
        cmd=args[0].trim();
        type=args[1].trim();
        params=args[2].trim().split(",");
        if ("".equalsIgnoreCase(cmd) || (!operation.equalsIgnoreCase(cmd) && !oper.equalsIgnoreCase(cmd))) {
            response.put("error","Invalid syntax for "+operation+".\n  Expected 1st argument to be \""+operation+"\" or \""+oper+"\".\n    Type: "+operation+"["+oper+"] <type> <p1,p2,...>");
            return response;
        }
        try {
            IJcon.types.valueOf(type.toUpperCase().trim());
        } catch (Exception e) {
            response.put("error","Invalid syntax for "+operation+".\n  Not regognized type \""+type+"\".\n    Type \"con\" for more info about available connectors.");
            return response;
        }
        response.put("cmd",cmd);
        response.put("type", type.toUpperCase().trim());
        if (type.equalsIgnoreCase(IJcon.types.FILESYSTEM.toString()) || type.equalsIgnoreCase(IJcon.types.NFS.toString())) {
            if (params.length < 1) {
                response.put("error","Invalid syntax for " + operation + ".\n  Type \"" + type + "\" expects at least 1 parameter (basePath) separated by commas.\n    Type \"con\" for more info about available connectors.");
                return response;
            }
            response.put("basePath", params[0]); // filePath
            response.put("IP","");
            response.put("username","");
            response.put("password","");
            if (operation.equalsIgnoreCase("write")) {
                if (params.length <2) {
                    response.put("error","Invalid syntax for " + operation + ".\n  Type \"" + type + "\" expects at least 2 parameters (basePath,content) separated by commas.\n    Type \"con\" for more info about available connectors.");
                    return response;
                }
                response.put("content",params[1]);
            }
        }else {
            if (params.length < 4) {
                response.put("error","Invalid syntax for " + operation + ".\n  Type \"" + type + "\" expects at least 4 parameters (basePath,ip,username,password) separated by commas.\n    Type \"con\" for more info about available connectors.");
                return response;
            }
            response.put("basePath", params[0]); // basePath (remote shared folder)
            response.put("IP",params[1]); // IP
            response.put("username",params[2]); // username
            response.put("password",params[3]); // password
            if (operation.equalsIgnoreCase("write")) {
                if (params.length <5) {
                    response.put("error","Invalid syntax for " + operation + ".\n  Type \"" + type + "\" expects at least 5 parameters (basePath,ip,username,password,content) separated by commas.\n    Type \"con\" for more info about available connectors.");
                    return response;
                }
                response.put("content",params[4]);
            }
        }
        return response;
    }

}
