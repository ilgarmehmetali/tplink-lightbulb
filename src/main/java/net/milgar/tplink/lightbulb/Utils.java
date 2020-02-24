package net.milgar.tplink.lightbulb;

public class Utils {

    /**
     * Badly encrypt message in format bulbs use
     *
     * @param data Buffer of data to encrypt
     * @return Encrypted data
     */
    public static byte[] encrypt(byte[] data) {
        return encrypt(data, 0xAB);
    }

    /**
     * Badly encrypt message in format bulbs use
     *
     * @param data Buffer of data to encrypt
     * @param key  Encryption key
     * @return Encrypted data
     */
    public static byte[] encrypt(byte[] data, int key) {
        byte[] encryptedData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            byte c = (byte) (data[i] ^ key);
            encryptedData[i] = c;
            key = c;
        }
        return encryptedData;
    }

    /**
     * Badly decrypt message from format bulbs use
     *
     * @param buffer byte array of data to decrypt
     * @return Decrypted data
     */
    public static char[] decrypt(byte[] buffer) {
        return decrypt(buffer, 0xAB);
    }

    /**
     * Badly decrypt message from format bulbs use
     *
     * @param buffer byte array of data to decrypt
     * @param key    Encryption key
     * @return Decrypted data
     */
    public static char[] decrypt(byte[] buffer, int key) {
        char[] decryptedData = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            char c = (char) (buffer[i] & 0xFF);
            c = (char) (c ^ key);
            decryptedData[i] = c;
            key = (buffer[i] & 0xFF);
        }
        return decryptedData;
    }

}
