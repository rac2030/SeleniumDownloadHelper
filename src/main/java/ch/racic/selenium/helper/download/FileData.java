/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */

package ch.racic.selenium.helper.download;

/**
 * Transport wrapper for raw data
 */
public class FileData {
    private String guessedFilename;
    private byte[] data;

    public FileData(String guessedFilename, byte[] data) {
        this.guessedFilename = guessedFilename;
        this.data = data;
    }

    public String getGuessedFilename() {
        return guessedFilename;
    }

    public byte[] getData() {
        return data;
    }
}
