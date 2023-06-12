import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Notepad_Test {

    WindowsDriver driver = null;
    /* Notepad application path */
    public static String appPath="C:\\Windows\\notepad.exe";
    public static String strContent = "This is a demo of WinAppDriver";
    public static String saveFilePath="notepade test";
    WebElement editBox = null;

    /* In ideal setup, the same cap instance can be used. This is only for demo */
    @BeforeTest
    public void testSetUp() throws MalformedURLException {

        WinDriver.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DesiredCapabilities capability = new DesiredCapabilities();

        capability.setCapability("ms:experimental-webdriver", true);
        capability.setCapability("app",appPath);
        capability.setCapability("platformName", "Windows");
        capability.setCapability("deviceName", "amr");

        /* Start WinAppDriver.exe so that it can start listening to incoming requests */

        driver = new WindowsDriver(new URL("http://127.0.0.1:4723/"), capability);

    }

    /* Certain tests are derived from the one present on official WinAppDriver website */
    /* https://github.com/microsoft/WinAppDriver/blob/master/Tests/WebDriverAPI/SendKeys.cs */

    @Test(description="Demonstration of entering content in the Edit Box of Notepad", priority = 0)
    public void test_add_content() throws InterruptedException, MalformedURLException
    {
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

        String windowHandle = driver.getWindowHandle();
        System.out.println(windowHandle);

        /* Locate the Edit button to add content in the TextArea */
        editBox = driver.findElement(By.name("Text editor"));
        editBox.click();
        editBox.sendKeys(strContent);
    }

    @Test(description="Using ActionChains to clear content in Notepad", priority = 1)
    public void test_delete_content() throws InterruptedException, MalformedURLException
    {
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

        String parent=driver.getWindowHandle();

        driver.findElement(By.name("Edit")).click();

        driver.findElement(By.name("Font")).click();

         driver.findElementByAccessibilityId("FontStyleComboBox"
         ).click();
        driver.findElement(By.name("Italic")).click();
        driver.findElementByAccessibilityId("FontSizeComboBox").sendKeys("24");
        driver.findElementByAccessibilityId("Button").click();

        }
    @Test(description="Using sendKeys to save file in the system", priority = 3)
    public void test_save_file() throws InterruptedException, MalformedURLException
    {

        System.out.println("Start Saving File");

        Actions action = new Actions(driver);

        /* Send CONTROL + S --> Save */
        action.sendKeys(Keys.chord(Keys.CONTROL, "s"));

        /* The FileName text area is highlighted by default, simply enter the filename */
       /* File Rewrite is not covered but that can also be replicated by following
       the same steps
       */
        action.sendKeys(saveFilePath);

        /* Send ALT + S --> Shortcut for Save */
        action.sendKeys(Keys.chord(Keys.ALT, "s"));

        /* Perform the action */
        action.perform();

        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

        System.out.println("Test Complete\n");
    }


}
