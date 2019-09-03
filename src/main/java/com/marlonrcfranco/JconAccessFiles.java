package com.marlonrcfranco;

import java.io.IOException;

public class JconAccessFiles {
	private IJcon jcon;
	private String type;
	private String response;

	public JconAccessFiles(String type) {
        this.type = type;
		try {
			switch (this.type) {
				case "filesystem":
					this.jcon = new JconFileSystem();
					break;
				case "smb1":
					this.jcon = new JconSMB1();
					break;
				case "smb2":
				case "smb23":
				case "smb3":
					this.jcon = new JconSMB23();
					break;
				default:break;
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

}
