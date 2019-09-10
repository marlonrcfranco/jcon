# jcon
Java connector to remote filesystem
****
#### Supported protocols:
- **FileSystem** [local] *(using native java.io)*
- **SMB1** [remote] *(using jCIFS <https://www.jcifs.org/>)*
- **SMB2/3** [remote] *(using smbj <https://github.com/hierynomus/smbj>)*
- **NFS** [remote] *(at this version, works using the same as FileSystem)*

****

### Examples:

#### Read the contents of a file:
```java
import com.marlonrcfranco.Jcon;

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Reads the content as a String */
String sContent = jcon.read("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");

/** Reads the content as byte[] (recommended for PDF and image files) */
byte[] bContent = jcon.readBytes("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");
```

