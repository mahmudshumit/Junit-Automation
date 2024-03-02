
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS )  //automation life cycle

public class MyJunitAutomation {
    WebDriver driver;


    @BeforeAll  //anotation initaition
    public void setup() { //prerequisite
        driver = new ChromeDriver();//load
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test //run it
    public void writeSomething() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        //Locator
        driver.findElement(By.id("edit-name")).sendKeys("Md. Mahmudur Rahman");
        driver.findElement(By.id("edit-number")).sendKeys("01660033101");


        WebElement ageRange = driver.findElement(By.id("edit-agnew-20-30"));
        // Use JavaScriptExecutor to execute a script that clicks on the element
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", ageRange);

        //Date
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = formatter.format(currentDate);
        WebElement dateField = driver.findElement(By.id("edit-date")); // Replace with actual ID
        dateField.sendKeys(formattedDate);


        driver.findElement(By.id("edit-email")).sendKeys("rahmanshumit@gmail.com");
        driver.findElements(By.tagName("textarea")).get(0).sendKeys("I'm Mahmudur Rahman, a dedicated learner in the field of Software Quality Assurance. One thing you'll notice about me is my fondness for smiling. I believe in the power of positivity, and a smile can make any situation brighter. ");

        WebElement chooseFileButton = driver.findElement(By.id("edit-uploadocument-upload")); // Replace with actual ID
        chooseFileButton.sendKeys(System.getProperty("user.dir") + "./src/test/resources/IMG.jpg"); // Replace with actual file path

        try {
            // Wait for the alert to be present (adjust timeout as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            // Switch to the alert and accept it (OK button)
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (TimeoutException | NoAlertPresentException e) {
            // Handle the exception (print a message, log, or take any necessary action)
            System.out.println("No alert found within the specified timeout.");
        } finally {
            // Continue with the rest of the code
        }

        WebElement checkBoxElement = driver.findElement(By.id("edit-age")); // Replace with actual ID
        JavascriptExecutor executor1 = (JavascriptExecutor) driver;
        executor1.executeScript("arguments[0].click();", checkBoxElement);


        WebElement submitButton = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-submit")));
            submitButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("Submit button not found.");
        } catch (ElementClickInterceptedException e) {
            System.out.println("Click intercepted, attempting JavaScript click.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", submitButton);
        }


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("container")));
        List<WebElement> containers = driver.findElements(By.className("container"));
// Check if the containers list has at least 4 elements before attempting to access the element at index 3
        if (containers.size() >= 4) {
            String actualMessage = containers.get(3).getText();
            String expectedMessage = "Thank you for your submission!";
            Assertions.assertEquals(actualMessage, expectedMessage);
        } else {
            // Handle the case where there are not enough elements in the list
            System.out.println("Not enough elements in the 'container' list.");
        }
    }

    @AfterAll // Cleanup after all tests
    public void closeDriver() {

        driver.quit();
    }




    // Reusable methods for form interaction

    private void fillElement(By locator, String text) {
        try {
            WebElement element = driver.findElement(locator);
            element.clear(); // Clear existing text (optional)
            element.sendKeys(text);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator);
        }
    }


}
