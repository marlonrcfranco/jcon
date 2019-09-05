package com.marlonrcfranco;

public class JconFactory {

    private static IJcon.types type;

    public JconFactory() {
    }

    public IJcon getJcon(String type) {
        try {
            this.type = IJcon.types.valueOf(type.toUpperCase().trim());
        } catch (Exception e) {
            System.out.println("Invalid type \"" + type + "\". Type \"filesystem\" was chosen by default.");
            this.type = IJcon.types.FILESYSTEM;
        }
        return getJcon(this.type);
    }

    public static IJcon getJcon(IJcon.types type) {
        type = type;
        try {
            if (type == IJcon.types.FILESYSTEM) {
                return new JconFileSystem();
            } else if (type == IJcon.types.SMB1) {
                return new JconSMB1();
            } else if (type == IJcon.types.SMB23) {
                return new JconSMB23();
            } else if (type == IJcon.types.NFS) {
                // Not implemented yet
            }
        } catch (Exception e) {
        }
        return null;
    }

}
