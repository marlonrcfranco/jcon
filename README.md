# jcon
Java connector to remote filesystem
****
#### Supported protocols:
- **FileSystem** [local] *(using native java.io)*
- **SMB1** [remote] *(using jCIFS <https://www.jcifs.org/>)*
- **SMB2/3** [remote] *(using smbj [:octocat: hierynomus/smbj](https://github.com/hierynomus/smbj))*
- **NFS** [remote] *(at this version, works using the same as FileSystem)*

****
### How to use
**Build the project by yourself** or **[Download the jar](../master/target/jcon.jar?raw=true "Click to download the jar")**

****

### Examples:
```java
import com.marlonrcfranco.Jcon;
```

#### Read the contents of a file: :page_with_curl:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Reads the content as a String */
String sContent = jcon.read("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");

/** Reads the content as byte[] (recommended for PDF and image files) */
byte[] bContent = jcon.readBytes("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");
```

#### Write contents to a file: :pencil2:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Writes the content as a String */
String sContent = "some string";
jcon.write("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password", sContent);

/** Writes the content as byte[] (recommended for PDF and image files)*/
byte[] bContent = "some string".getBytes();
jcon.writeBytes("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password", bContent);

```

#### Delete a file or directory: :boom:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Deletes the directory "SharedFolder/subfolder/" and everything inside it */
jcon.delete("192.168.XXX.XXX", "SharedFolder/subfolder/", "Username", "Password");

/** Deletes only the file "test.txt" in "SharedFolder/subfolder/" */
jcon.delete("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");

```


#### List all files and directories in a given path: :open_file_folder:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Returns a String with the name of all the files an directories separated by "\n" */
String sList = jcon.listFiles("192.168.XXX.XXX", "SharedFolder/subfolder/", "Username", "Password");

/** Returns an ArrayList of objects according to the protocol. 
E.g. for filesystem protocol, it will return ArrayList<java.io.File>; 
     for smb1 protocol, it will return ArrayList<jcifs.smb.SmbFile>, 
     and so on. */
ArrayList<SmbFile> aList = jcon.listFilesAsList("192.168.XXX.XXX", "SharedFolder/subfolder/", "Username", "Password");

```
