package com.marlonrcfranco;

import java.io.IOException;
import java.util.ArrayList;

public class Jcon implements IJcon{
	private IJcon jcon;
	private IJcon.types type;

	public Jcon(String type) {
	    try {
            this.type = IJcon.types.valueOf(type.toUpperCase().trim());
        }catch (Exception e) {
            System.out.println("Invalid type \""+type+"\". Type \"filesystem\" was chosen by default.");
            this.type = types.FILESYSTEM;
        }
        this.jcon=JconFactory.getJcon(this.type);
    }

	public Jcon(IJcon.types type) {
	    this.type = type;
        this.jcon=JconFactory.getJcon(this.type);
	}

    @Override
    public String read(String IP, String filePath, String user, String pass) throws IOException{
       return this.jcon.read(IP,filePath,user,pass);
    }

    @Override
    public byte[] readBytes(String IP, String filePath, String user, String pass) throws IOException {
        return this.jcon.readBytes(IP,filePath,user,pass);
    }

    @Override
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException{
        return this.jcon.write(IP,filePath,user,pass,content);
    }

    @Override
    public byte[] writeBytes(String IP, String filePath, String user, String pass, byte[] content) throws IOException {
	    return this.jcon.writeBytes(IP,filePath,user,pass,content);
    }

    @Override
    public String delete(String IP, String filePath, String user, String pass) throws IOException {
       return this.jcon.delete(IP,filePath,user,pass);
    }

    @Override
    public String listFiles(String IP, String filePath, String user, String pass) throws IOException {
        return this.jcon.listFiles(IP,filePath,user,pass);
    }

    @Override
    public ArrayList<String> listFilesAsList(String IP, String filePath, String user, String pass) throws Exception {
        return null;
    }

    @Override
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException {
        return this.jcon.copyFileTo(sourceIP,sourceFilePath,destIP,destFilePath,user,pass);
    }

}
