/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */

package ch.racic.selenium.helper.download;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import sun.plugin.dom.exception.BrowserNotSupportedException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by rac on 08.06.14.
 */
public class SeleniumDownloadHelper {

    /**
     * URI Paths to JS files *
     */
    private static final String base64JsPath = "ch/racic/selenium/helper/download/js/base64.js";
    private static final String dlHelperJsPath = "ch/racic/selenium/helper/download/js/seleniumDownloadHelper.js";
    private static final String ieHackJsPath = "ch/racic/selenium/helper/download/js/ieHack.js";

    /**
     * JS content Strings *
     */
    private String base64Js;
    private String dlHelperJs;
    private String ieHackJs;
    private String ieHackTestJs = ";var IE_HACK = (/msie/i.test(navigator.userAgent) && !/opera/i.test(navigator.userAgent));";
    private String skipIeHackTestJS = ";var IE_HACK = false;";

    /**
     * Context *
     */
    private final JavascriptExecutor js;

    /**
     * Initialize the SeleniumDownloadHelper with the given driver as context. Javascript will be injected at the
     * current page of this driver at the moment of calling the download method.
     *
     * @param driver
     * @throws IOException
     */
    public SeleniumDownloadHelper(WebDriver driver) throws IOException {
        loadJSHelperFiles();

        /** Store context **/
        js = (JavascriptExecutor) driver;
    }

    /**
     * Loads the JS helper files from resources and returns them as injectable String.
     *
     * @throws IOException
     */
    private void loadJSHelperFiles() throws IOException {
        /** Load JS content from helper files **/
        base64Js = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(base64JsPath));
        dlHelperJs = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(dlHelperJsPath));
        ieHackJs = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(ieHackJsPath));
    }

    /**
     * Generates the calling command with return statement to the given url.
     *
     * @param url
     * @return Javascript string
     */
    private String getDownloadJsCallScript(URL url) {
        return ";return seleniumDownloadHelper.getB64Binary('" + url + "');";
    }

    /**
     * Executes XHR request trough JavaScript to download the given file in the context of the current WebDriver
     * session.
     *
     * @param url
     * @return raw data
     */
    public byte[] getFileFromUrl(URL url) {
        String scriptCollection;
        if (js instanceof InternetExplorerDriver) {
            scriptCollection = ieHackTestJs + ieHackJs + base64Js + dlHelperJs + getDownloadJsCallScript(url);
        } else if (js instanceof HtmlUnitDriver) {
            //throw new BrowserNotSupportedException("JS download hack is not working on HTMLUnit");
            scriptCollection = skipIeHackTestJS + base64Js + dlHelperJs + getDownloadJsCallScript(url);
        } else {
            scriptCollection = ieHackTestJs + base64Js + dlHelperJs + getDownloadJsCallScript(url);
        }
        String encodedContent = (String) js.executeScript(scriptCollection);
        return Base64.decodeBase64(encodedContent);
    }

    /**
     * Executes XHR request trough JavaScript to download the given file in the context of the current WebDriver
     * session.
     *
     * @param url
     * @param outputFile
     * @return outpuFile
     * @throws IOException
     */
    public File getFileFromUrl(URL url, File outputFile) throws IOException {
        byte[] content = getFileFromUrl(url);
        FileUtils.writeByteArrayToFile(outputFile, content);
        return outputFile;
    }

}
