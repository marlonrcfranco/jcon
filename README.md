[![GitHub release (latest by date)](https://img.shields.io/github/v/release/marlonrcfranco/jcon)](https://github.com/marlonrcfranco/jcon/releases)
[![Build Status](https://github.com/marlonrcfranco/jcon/workflows/build/badge.svg)](https://github.com/marlonrcfranco/jcon/actions?query=workflow%3Abuild)
[![GitHub repo size](https://img.shields.io/github/repo-size/marlonrcfranco/jcon)](https://github.com/marlonrcfranco/jcon)
[![GitHub top language](https://img.shields.io/github/languages/top/marlonrcfranco/jcon)](https://github.com/marlonrcfranco/jcon)
[![GitHub](https://img.shields.io/github/license/marlonrcfranco/jcon)](https://github.com/marlonrcfranco/jcon/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/marlonrcfranco/jcon?style=social)](https://github.com/marlonrcfranco/jcon/stargazers)

# jcon
Java connector to remote filesystem
****
#### Supported protocols:
- [![](https://img.shields.io/badge/filesystem-local-green)](../master/src/main/java/com/marlonrcfranco/JconFileSystem.java) (using native [java.io](https://docs.oracle.com/javase/7/docs/api/java/io/package-summary.html))
- [![](https://img.shields.io/badge/smb1-remote-blue)](../master/src/main/java/com/marlonrcfranco/JconSMB1.java) (using [jCIFS](https://www.jcifs.org/))
- [![](https://img.shields.io/badge/smb23-remote-blue)](../master/src/main/java/com/marlonrcfranco/JconSMB23.java) (using [smbj :octocat:](https://github.com/hierynomus/smbj))
- [![](https://img.shields.io/badge/nfs-remote-blue)](../master/src/main/java/com/marlonrcfranco/JconNFS.java) (at this version, it uses native [java.io](https://docs.oracle.com/javase/7/docs/api/java/io/package-summary.html))

****
## How to get it
**Add the dependency to the pom.xml of your maven project** 
```xml
<dependency>
  <groupId>com.marlonrcfranco</groupId>
  <artifactId>jcon</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

**Or direct download the jcon.jar clicking here: [![GitHub Releases](https://img.shields.io/github/downloads/marlonrcfranco/jcon/v1.0/total)](https://github.com/marlonrcfranco/jcon/releases/download/v1.0/jcon.jar "Click to download the .jar")**

****
## How to use it

* [Via Java](#via_java)
  * [List all files and directories in a given path](#j_list)
  * [Read contents of a file](#j_read)
  * [Write contents to a file](#j_write)
  * [Delete a file or directory](#j_delete)
* [Via console command](#via_cmd)
  * [\[h\] help](#c_help)
  * [\[c\] connectors](#c_connectors)
  * [\[l\] list](#c_list)
  * [\[r\] read](#c_read)
  * [\[w\] write](#c_write)
  * [\[d\] delete](#c_delete)

<a name="via_java"></a>
****

### Via java code:
You can import and instantiate the `Jcon` class.
```java
import com.marlonrcfranco.Jcon;
```

<a name="j_list"></a>
****
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

<a name="j_read"></a>
****
#### Read contents of a file: :page_with_curl:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Reads the content as a String */
String sContent = jcon.read("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");

/** Reads the content as byte[] (recommended for PDF and image files) */
byte[] bContent = jcon.readBytes("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");
```

<a name="j_write"></a>
****
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

<a name="j_delete"></a>
****
#### Delete a file or directory: :boom:
```java

Jcon jcon = new Jcon("smb1"); // "smb1", "smb23", "nfs" or "filesystem"

/** Deletes the directory "SharedFolder/subfolder/" and everything inside it */
jcon.delete("192.168.XXX.XXX", "SharedFolder/subfolder/", "Username", "Password");

/** Deletes only the file "test.txt" in "SharedFolder/subfolder/" */
jcon.delete("192.168.XXX.XXX", "SharedFolder/subfolder/test.txt", "Username", "Password");

```

****

<a name="via_cmd"></a>
**** 

### Via console command:
If you have Java installed, simply open a new terminal (Unix) or cmd (Windows) and type:
```bash
java -jar jcon.jar
```
It will appear as follows:

<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon01.png">

The interface provides easy access to remotely `list`, `read`, `write` or `delete` files and directories. 

<a name="c_help"></a>
****
#### help[h] :thinking: 
If you need some help, just type `help` or `h`

<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon02.png">

<a name="c_connectors"></a>
****
#### connectors[c] :electric_plug: 
For a quick info about the supported protocols, just type `connectors` or `c`

<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon03.png">

<a name="c_list"></a>
****
#### list[l] :open_file_folder:
List all the files and sub-directories in a remote path. 
```
 list[l] <connector> <path>,<IP>,<username>,<password>
```
For example:
Using the `smb23` connector to list files ins a remote machine that uses **SMB2** or **SMB3** protocols
```
 l smb23 \shared\my_directory,10.0.0.7,marlon,pass100%S3cuR3
```
<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon04.png">

Using the `smb1` connector to list files ins a remote machine that uses **SMB1** protocol
```
 l smb1 \shared\my_directory,10.0.0.7,marlon,pass100%S3cuR3
```
<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon09.png">


**Obs:** at version v1.0, when using the connector `filesystem`, you don't need to provide any parameter besides the path.
Example:
```
 l fylesystem C:\User\marlon\Documents
```
<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon10.png">

<a name="c_read"></a>
****
#### read[r] :page_with_curl:
Read a given file and print its content on terminal.
It can read from  **any file format** (.pdf, .txt, .jpg, .png, ...)

```
 read[r] <connector> <full_file_path>,<IP>,<username>,<password>
```
For example:
```
 r smb23 \shared\my_directory\my_file.txt,10.0.0.7,marlon,pass100%S3cuR3
```

<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon07.png">

**Obs:** at version v1.0, when using the connector `filesystem`, you don't need to provide any parameter besides the path.
Example:
```
 r fylesystem C:\User\marlon\Documents\my_file.html
```

<a name="c_write"></a>
****
#### write[w] :pencil2:
Write to a given file whatever content you provide as the last parameter.
It can write to **any file format** (.pdf, .txt, .jpg, .png, ...) and the content can be specified in binary.

```
 write[w] <connector> <full_file_path>,<IP>,<username>,<password>,<content>
```
For example:
```
 w smb23 \shared\my_directory\my_file.csv,10.0.0.7,marlon,pass100%S3cuR3,This is my content right here, (no matter if it is in between " or not)
```

<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon05.png">

**Obs:** at version v1.0, when using the connector `filesystem`, you don't need to provide any parameter besides the path.
Example:
```
 w fylesystem C:\User\marlon\Documents\photo.png
```

<a name="c_delete"></a>
****
#### delete[d] :boom:
Delete a given file.

```
 delete[d] <connector> <full_file_path>,<IP>,<username>,<password>
```
For example:
```
 d smb23 \shared\my_directory\my_file.csv,10.0.0.7,marlon,pass100%S3cuR3
```

<img src="https://raw.githubusercontent.com/marlonrcfranco/jcon/master/img/jcon08.png">

**Obs:** at version v1.0, when using the connector `filesystem`, you don't need to provide any parameter besides the path.
Example:
```
 d fylesystem C:\User\marlon\Documents\photo.png
```




