package com.marlonrcfranco;

import java.io.IOException;

public class Jcon {
	private IJcon jcon;
	private IJcon.types type;
	private String response;

	public Jcon(IJcon.types type) {
	    this.type = type;
		try {
		    if (type == IJcon.types.FILESYSTEM) {
                this.jcon = new JconFileSystem();
            }
		    else if (type == IJcon.types.SMB1) {
                this.jcon = new JconSMB1();
            }
            else if (type == IJcon.types.SMB23) {
                this.jcon = new JconSMB23();
            }
            else if (type == IJcon.types.NFS) {
                // Not implemented yet
            }
		} catch(Exception e) {
			this.jcon = null;
		}
	}

    public String read(String IP, String filePath, String user, String pass) throws Exception{
        String path = "";
        try {
            path = filePath;
            this.response = this.jcon.read(IP,path,user,pass);

            String jjRows = "\""+this.response+"\"";
            String jjStatus = this.response.contains("Erro")? "500": "200";

            String json = "{\"status\":\""+jjStatus+"\",\"rows\":["+jjRows+"]}";
            return json;
        } catch (IOException e) {
            return "{\"status\":\"500\",\"message\":\"Ocorreu um erro na execução.\",\"rows\":[]}";
        }
    }

    public byte[] readBytes(String IP, String filePath, String user, String pass) throws IOException {
        return this.jcon.readBytes(IP,filePath,user,pass);
    }

    public String write(String IP, String filePath, String user, String pass, String content) throws Exception{
        String path = "";
        try {
            path = filePath;
            this.response = this.jcon.write(IP,path,user,pass,content);

            String jjRows = "\""+this.response+"\"";
            String jjStatus = this.response.contains("Erro")? "500": "200";

            String json = "{\"status\":\""+jjStatus+"\",\"rows\":["+jjRows+"]}";
            return json;
        } catch (IOException e) {
            return "{\"status\":\"500\",\"message\":\"Ocorreu um erro na execução.\",\"rows\":[]}";
        }
    }

    public byte[] writeBytes(String IP, String filePath, String user, String pass, byte[] content) throws IOException {
	    return this.jcon.writeBytes(IP,filePath,user,pass,content);
    }

    public String delete(String IP, String filePath, String user, String pass) throws IOException {
        String path = "";
        try {
            path = filePath;
            this.response = this.jcon.delete(IP,path,user,pass);

            String jjRows = "\""+this.response+"\"";
            String jjStatus = this.response.contains("Erro")? "500": "200";

            String json = "{\"status\":\""+jjStatus+"\",\"rows\":["+jjRows+"]}";
            return json;
        } catch (Exception e) {
            return "{\"status\":\"500\",\"message\":\"Ocorreu um erro na execução.\",\"rows\":[]}";
        }
    }

}
