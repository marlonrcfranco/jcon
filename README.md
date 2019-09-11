# jcon
Java connector to remote filesystem
****
#### Supported protocols:
- **FileSystem** [local] *(using native java.io)*
- **SMB1** [remote] *(using jCIFS <https://www.jcifs.org/>)*
- **SMB2/3** [remote] *(using smbj <https://github.com/hierynomus/smbj>)*
- **NFS** [remote] *(at this version, works using the same as FileSystem)*

****
### How to use
**Build the project by yourself** or **[Download the jar](https://github.com/marlonrcfranco/jcon/blob/master/target/jcon.jar?raw=true)**

****

### Examples:
```java
import com.marlonrcfranco.Jcon;
```

#### Read the contents of a file:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Reads the content as a String */
String sContent = jcon.read("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");

/** Reads the content as byte[] (recommended for PDF and image files) */
byte[] bContent = jcon.readBytes("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");
```

#### Write contents to a file:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Write the content as a String */
String sContent = "some string";
jcon.write("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password",sContent);

/** Write the content as byte[] (recommended for PDF and image files)*/
byte[] bContent = "some string".getBytes();
jcon.writeBytes("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password",bContent);

```
