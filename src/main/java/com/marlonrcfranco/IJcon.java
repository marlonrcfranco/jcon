package com.marlonrcfranco;

import java.io.IOException;

public interface IJcon {

    enum types {
        FILESYSTEM,SMB1,SMB23,NFS
    }

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
    public byte[] readBytes(String IP, String filePath, String user, String pass) throws IOException;

    /**
     * public String write(String IP, String filePath, String user, String pass, String content)
     *
     * Returns a String with a success or error message after write the "content" in the file specified in "filePath".
     * If the file is in a remote directory, it's necessary to inform the
     * username (user) and password (pass) with Write privileges.
     *
     * @param IP String IP adress or literal (e.g. "localhost")
     * @param filePath String like:
     *                 Remote: "/Marlon/teste.xml"
     *                 Local: "C:\Users\marlon.franco\Documents\teste.xml"
     * @param user String username
     * @param pass String password
     *
     * @return String with the content of the file
     *
     * @throws IOException
     */
    public String write(String IP, String filePath, String user, String pass, String content) throws IOException;
    public byte[] writeBytes(String IP, String filePath, String user, String pass, byte[] content) throws IOException;

    /**
     * public String delete(String IP, String filePath, String user, String pass)
     *
     * Returns a String with a success or error message after deleting the file specified in "filePath".
     * If the file is in a remote directory, it's necessary to inform the
     * username (user) and password (pass) with Write privileges.
     *
     * @param IP String IP adress or literal (e.g. "localhost")
     * @param filePath String like:
     *                 Remote: "/Marlon/teste.xml"
     *                 Local: "C:\Users\marlon.franco\Documents\teste.xml"
     * @param user String username
     * @param pass String password
     * @return
     */
    public String delete(String IP, String filePath, String user, String pass) throws IOException;

    /**
     * public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass)
     *
     * Copy the file from sourceFilePath to destFilePath.
     * If the file is in (or will be moved to) a remote directory, it's necessary to inform the
     * username (user) and password (pass) with Write privileges.
     *
     * @param sourceIP String IP adress or literal (e.g. "localhost") from source
     * @param sourceFilePath String like:
     *                      Remote source: "/Marlon/teste.xml"
     *                      Local source: "C:\Users\marlon.franco\Documents\teste.xml"
     * @param destIP String IP adress or literal (e.g. "localhost") from destination
     * @param destFilePath String like:
     *                      Remote destination: "/Marlon/teste.xml"
     *                      Local destination: "C:\Users\marlon.franco\Documents\teste.xml"
     * @param user String username
     * @param pass String password
     *
     * @return String with the content of the file
     *
     * @throws IOException
     */
    public String copyFileTo(String sourceIP, String sourceFilePath, String destIP, String destFilePath, String user, String pass) throws IOException;

}
