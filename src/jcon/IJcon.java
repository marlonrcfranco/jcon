package jcon;

import java.io.IOException;

public interface IJcon {

    /**
     * public String read(String IP, String filePath, String user, String pass)
     *
     * Returns a String with the content of the file specified in "filePath".
     * If the file is in a remote directory, it's necessary to inform the
     * username (user) and password (pass) with Read privileges.
     *
     * @param IP String IP adress or literal (e.g. "localhost")
     * @param filePath String like:
     *                 Remote: "/Marlon/teste.xml"
     *                 Local: "C:\Users\marlon.franco\Documents\teste.xml"
     * @param user String username
     * @param pass String password
     *
     * @return String with the content of the file specified in filePath
     *
     * @throws IOException
     */
    public String read(String IP, String filePath, String user, String pass) throws IOException;

    /**
     * public String write(String IP, String filePath, String user, String pass, String content)
     *
     * Returns a String with the content of the file specified in "filePath".
     *
     * @param filePath String like: "C:\Users\marlon.franco\Documents\teste.xml"
     * @return String with the content of the file
     *
     * @throws throws IOException
     */
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException;

    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException;


}
