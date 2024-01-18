package framework;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class CommonLib {
    public WebDriver oDriver;
    public String page = "test";
    int timeout = 30;
    Parser parser = new Parser();
    Actions actions;
    WebDriverWait wait;
    public String itemValue;

    // Constructor
    public CommonLib(WebDriver driver) {
        oDriver = driver;
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Element'e tıklama
    public void click(WebElement object) {
        actions.click(object).perform();
    }

    // Element bulma
    public WebElement findElement(String elem) {
        WebElement object;
        String element = parser.getElement(page, elem);

        try {
            if (element != null) {
                if (element.startsWith("//") || element.startsWith("(//")) {
                    object = oDriver.findElements(By.xpath(element)).get(0);
                    System.out.println("Element found : " + elem);
                } else if (element.startsWith("#") || element.startsWith(".")) {
                    object = oDriver.findElements(By.cssSelector(element)).get(0);
                    System.out.println("Element found : " + elem);
                } else {
                    object = oDriver.findElements(By.id(element)).get(0);
                    System.out.println("Element found : " + elem);
                }
            } else {
                object = oDriver.findElement(By.xpath("//*[text()='" + elem + "'or contains(text(),'" + elem + "')]"));
            }

            if (object == null) {
                System.out.println("Element not found: " + elem);
                Assert.fail("Element not found : " + elem);
            }
            return object;
        } catch (Exception e) {
            System.out.println("Element not found: " + elem);
            Assert.fail("Element not found : " + elem);
            return null;
        }
    }

    // Sayfa kontrolü
    public void seePage(String page) {
        List<String> returnValue = parser.isPageExist(page);

        try {
            if (returnValue.get(0).equalsIgnoreCase(page)) {
                System.out.println(page + " page found!");
                this.page = page;

                if (returnValue.get(1).length() > 0) {
                    waitElement(returnValue.get(1), timeout);
                }
            }
        } catch (Exception e) {
            Assert.fail("Waiting element is not found!");
        }
    }

    // Elementin belirli bir süre içinde görünmesini bekleyen metot
    public WebElement waitElement(String element, int timeout) {
        WebElement object;
        try {
            for (int i = 0; i < timeout; i++) {
                object = findElement(element);
                Thread.sleep(2000);
                if (object != null) {
                    return object;
                }
            }
        } catch (Exception e) {
            Assert.fail("Waiting element is not found!");
        }
        return null;
    }

    // URL'ye gitme
    public void navigateToURL(WebDriver oDriver, String URL) {
        try {
            oDriver.manage().deleteAllCookies();
            oDriver.navigate().refresh();
            oDriver.navigate().to(URL);
        } catch (Exception e) {
            System.out.println("failed" + e);
        }
    }

    // Elemente mouse ile hareket etme
    public void moveToElement(WebElement object) {
        Actions act = new Actions(oDriver);
        act.moveToElement(object, 3, 0).perform();
    }

    // Elementin değerini alma
    public String getTheItemValue(String elem) {
        String elementText = (findElement(elem).getText());
        System.out.println(elementText);
        this.itemValue = elementText;
        System.out.println(itemValue);
        return elementText;
    }
}
