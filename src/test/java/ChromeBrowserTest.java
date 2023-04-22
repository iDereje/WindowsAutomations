import io.appium.java_client.windows.WindowsDriver;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ChromeBrowserTest {

    private static WindowsDriver<WebElement> ChromeSession = null;
    private static WebElement ChromeResults = null;

    @BeforeClass
    public static void setup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "chrome");
        try {
            ChromeSession = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ChromeSession.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        ChromeResults = ChromeSession.findElementByAccessibilityId("addressEditBox");
        Assert.assertNotNull(ChromeResults);
    }

    @AfterClass
    public static void tearDown() {
        ChromeResults = null;
        if (ChromeSession != null) {
            ChromeSession.quit();
        }
        ChromeSession = null;
    }

    @Test
    public void getChromeResultText() {
        // Trim extra text and whitespace off of the display value
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void openNewTab() {
        for (int i = 0; i < 7; i++) {
            ChromeSession.findElementByName("New Tab").click();
            ChromeSession.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
    }

    protected String getCalculatorResultText() {
        // Trim extra text and whitespace off of the display value
        return ChromeResults.getText().replace("Display is", "").trim();
    }
}
