package testingselenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class automationTest {
    WebDriver driver;

    public automationTest() {

    }
    ExtentReports extent;
    ExtentTest test;
    @BeforeTest
    public void config(){
        String path=System.getProperty("user.dir")+"\\reports\\report.html";
        ExtentSparkReporter reporter=new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Test");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Milan Ivanovic");
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='card-body'])[1]")));
    }

    @Test
    public void textBoxFunctions() throws InterruptedException {
        test=extent.createTest("TextBoxFunctions");
        driver.findElement(By.xpath("(//div[@class='card-body'])[1]")).click();
        driver.findElement(By.xpath("(//span[@class='text'])[1]")).click();

        List<WebElement> textBox = driver.findElements(By.cssSelector(".form-control"));
        List<String> textBoxInfo = List.of("Milan Ivanovic", "milan94barsa@gmail.com", "jabucje bb", "lazarevac");

        for (int i = 0; i < textBox.size(); i++) {
            textBox.get(i).sendKeys(textBoxInfo.get(i));
            test.info("Entering text: "+textBoxInfo.get(i));
        }
        driver.findElement(By.xpath("//div/button[@type='button']")).click();
        test.info("Submiting the form");
        String displayedName = driver.findElement(By.id("name")).getText().replace("Name:", "").trim();
        String displayedEmail = driver.findElement(By.id("email")).getText().replace("Email:", "").trim();
        String displayedCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']")).getText().replace("Current Address :", "").trim();
        String displayedPermanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText().replace("Permananet Address :", "").trim();

        Assert.assertEquals(displayedName, textBoxInfo.get(0));
        Assert.assertEquals(displayedEmail, textBoxInfo.get(1));
        Assert.assertEquals(displayedCurrentAddress, textBoxInfo.get(2));
        Assert.assertEquals(displayedPermanentAddress, textBoxInfo.get(3));
        test.pass("All assertions passed");

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null)
            driver.quit();


    }
    @AfterTest
    public void tearDownExtent(){
        extent.flush();
    }

}
