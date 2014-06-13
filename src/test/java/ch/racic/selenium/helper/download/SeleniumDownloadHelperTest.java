/*
 * Copyleft (c) 2014. This code is for learning purposes only. Do whatever you like with it but don't take it as perfect code.
 * Michel Racic (http://rac.su/+) => github.com/rac2030
 */

package ch.racic.selenium.helper.download;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.net.URL;

/**
 * Created by rac on 08.06.14.
 */
public class SeleniumDownloadHelperTest {

    private WebDriver driver;

    // Server testdata
    private static String baseUrl;
    private static String indexPage = "";
    private static String testPdf = "SeleniumDownloadHelper.pdf";
    private File testPdfFile;
    private static File serverBaseFolder = new File("src/test/resources/testServer");
    private byte[] referenceContent;
    private static Server server;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Server server = new Server(0);
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase(serverBaseFolder.toString());
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler});
        server.setHandler(handlers);
        server.start();
        baseUrl = "http://localhost:" + server.getConnectors()[0].getLocalPort() + "/";
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
    }

    @Before
    public void setUpFiles() throws Exception {
        testPdfFile = new File(serverBaseFolder + "/" + testPdf);
        referenceContent = FileUtils.readFileToByteArray(testPdfFile);
    }

    @After
    public void tearDownTestDriver() throws Exception {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void testGetFileFromUrlHtmlUnit() throws Exception {
        // Create HTMLUnit as driver for this test
        driver = new HtmlUnitDriver(true);
        invokeGetFileDataFromUrl();
        invokeGetFileFromUrl();
    }

    @Test
    public void testGetFileFromUrlChrome() throws Exception {
        //TODO put this into pom profiles which are OS specific
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        driver = new ChromeDriver();
        invokeGetFileDataFromUrl();
        invokeGetFileFromUrl();
    }

    @Test
    public void testGetFileFromUrlSafari() throws Exception {
        driver = new SafariDriver();
        invokeGetFileDataFromUrl();
        invokeGetFileFromUrl();
    }

    @Test
    public void testGetFileFromUrlFireFox() throws Exception {
        driver = new FirefoxDriver();
        invokeGetFileDataFromUrl();
        invokeGetFileFromUrl();
    }

    @Test
    public void testGetFileFromUrlInternetExplorer() throws Exception {
        driver = new InternetExplorerDriver();
        invokeGetFileDataFromUrl();
        invokeGetFileFromUrl();
    }

    @Test
    public void testGetFileFromUrlPhantomJS() throws Exception {
        driver = new PhantomJSDriver();
        invokeGetFileDataFromUrl();
        invokeGetFileFromUrl();
    }

    private void invokeGetFileDataFromUrl() throws Exception {
        driver.get(baseUrl + indexPage);
        SeleniumDownloadHelper sdlh = new SeleniumDownloadHelper(driver);
        byte[] fileContent = sdlh.getFileFromUrl(new URL(baseUrl + testPdf));
        Assert.assertArrayEquals("Raw data is correct", referenceContent, fileContent);
    }

    private void invokeGetFileFromUrl() throws Exception {
        driver.get(baseUrl + indexPage);
        SeleniumDownloadHelper sdlh = new SeleniumDownloadHelper(driver);
        File tmpFile = File.createTempFile("testfile", ".pdf");
        tmpFile.deleteOnExit();
        File dlFile = sdlh.getFileFromUrl(new URL(baseUrl + testPdf), tmpFile);
        Assert.assertTrue("File content is correct", FileUtils.contentEquals(testPdfFile, dlFile));
    }

}
