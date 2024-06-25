package stepdefs;

import framework.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;

import java.util.Objects;


public class StepDefs {
    CommonLib commonLib;

    int timeout = 30;
    WebDriver oDriver;

// Otomasyon calisirken ayaga kalkacaklar

    @Before
    public void setReportName() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Bilgisayar/Desktop/ogunBaskokJava-master/drivers/chromedriver.exe");
        oDriver = new ChromeDriver();
        oDriver.manage().window().maximize();
        oDriver.manage().deleteAllCookies();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-translate");
        chromeOptions.addArguments("start-maximized");

        commonLib = new CommonLib(oDriver);
    }

    /* Senaryo bitiminde yapılacak islemler
        Senaryo fail olarsa bir yere yönlendirme falan yapılabilir
     */
    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("After hook");
        oDriver.manage().deleteAllCookies();
        if (scenario.isFailed()) {
            try {
                System.out.println("Test finished with error. Initiate the log out process.");
                Thread.sleep(3000);
                // openUrl("https://automationexercise.com");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("action for failed scenario.");
        } else {
            oDriver.close();
            oDriver.quit();
            System.out.println("action for passed scenario.");
        }
    }

    //ilk step baslangici
    @Given("Navigate to {string} url")
    public void login(String url) {
        System.out.println("login");
        try {
            commonLib.navigateToURL(oDriver, url);
        } catch (Exception e) {
            System.out.println("failed" + e);
            Assert.fail("failed assert");
        }
        seePage("test");
    }

    @Then("I see {string} page")
    public void seePage(String page) {
        try {
            commonLib.seePage(page);
        } catch (Exception e) {
            System.out.println("failed" + e);
        }
    }


    /* Bir text ve hangi element üzerine oldugu bilgisini alıp
        bu eleman 'null' degilse islemlerini yapıyoruz
    */
    @Then("I enter {string} text to {string}")
    public boolean enterText(String text, String element) {
        WebElement object;
        object = commonLib.waitElement(element, timeout);
        boolean flag = false;
        try {
            if (object != null) {
                object.sendKeys(text);
                System.out.println("The text has been entered:" + text);
                return true;
            }
        } catch (Exception e) {
            Assert.fail("Could not entered the text:" + text);
        }
        return flag;
    }

    @And("I wait {int} seconds")
    public void waitElement(int timeout) {
        try {
            int wait = timeout * 1000;
            Thread.sleep(wait);
        } catch (Exception e) {
            System.out.println("failed" + e);
        }
    }

    //Anasayfa goruntuleniyor mu kontrolu
    @Then("Verify that home page is visible successfully")
    public boolean verifyThatHomePageIsVisibleSuccessfully() {
        boolean eleSelected = commonLib.findElement("anasayfa").isDisplayed();
        System.out.println(eleSelected);
        try {
            if (eleSelected) {
                System.out.println("object found ");
            } else {
                System.out.println("object not found");
            }
            return eleSelected;
        } catch (Exception e) {
            System.out.println("fail" + e);
        }
        return eleSelected;
    }

    @When("Click {string} button")
    public void clickButton(String element) {
        try {
            waitElement(5);
            WebElement object = commonLib.findElement(element);
            commonLib.click(object);
        } catch (Exception e) {
            System.out.println("failed" + e);
            Assert.fail("failed assert");
        }
    }

    /*
        Burada bazen popUp çıkıyor bazen çıkmıyor.
         çıkan urli kontrol edip eğer url farklıysa yani
          reklam geldiyse beklenen urli tekrar set ediyoruz
     */
    @Then("Hover over {string}")
    public void hoverOverFirstProductAndClickAddToCart(String element) {
        String expectedUrl = AutomationConstants.productURL;
        String actualUrl = oDriver.getCurrentUrl();
        if (!Objects.equals(actualUrl, expectedUrl))
            oDriver.get(expectedUrl);
        try {
            waitElement(5);
            WebElement object = commonLib.findElement(element);
            commonLib.moveToElement(object);
        } catch (Exception e) {
            System.out.println("failed" + e);
            Assert.fail("failed assert");
        }
    }

    // elementler null mu değil mi kontrolü biri nullsa kontrol patlıyor
    @Then("Verify both {string} and {string} are added to Cart")
    public void iSeeElementIsNotNullAtIndex(String element1, String element2) {
        try {
            waitElement(5);
            String getElement1Text = commonLib.getTheItemValue(element1);
            System.out.println("text element" + getElement1Text);
            String getElement2Text = commonLib.getTheItemValue(element2);
            System.out.println("text element" + getElement1Text);

            if (getElement1Text != null && getElement2Text != null) {
                System.out.println("passed");
            } else {
                System.out.println("failed");
                Assert.fail("failed Verify both element");
            }
        } catch (Exception e) {
            System.out.println("failed" + e);
            Assert.fail("failed Verify both element");
        }
    }

    /*
        Alanların value degerlerini alıyoruz ,
         beklenen degerlerini AutamationConst. tutup bir degiskene atıyoruz.
          Burada ki exp ve actual olarak elementleri kıyaslıyoruz
           biri bile beklenen değilse hata döndürüyoruz
*/
    @Then("Verify their {string} {string} {string} {string} and {string} {string}")
    public boolean verifyTheirAnd(String priceElement1, String priceElement2, String quantityElement1, String quantityElement2, String totalElement1, String totalElement2) {
        String fieldPriceElemet1 = commonLib.getTheItemValue(priceElement1);
        String fieldPriceElemet2 = commonLib.getTheItemValue(priceElement2);
        String fieldQuantityElement1 = commonLib.getTheItemValue(quantityElement1);
        String fieldQuantityElement2 = commonLib.getTheItemValue(quantityElement2);
        String fieldTotalElement1 = commonLib.getTheItemValue(totalElement1);
        String fieldTotalElement2 = commonLib.getTheItemValue(totalElement2);

        String fieldPrice1 = AutomationConstants.fieledPrice1;
        String fieldPrice2 = AutomationConstants.fieledPrice2;
        String fieldQuantity1 = AutomationConstants.fieldQuantity1;
        String fieldQuantity2 = AutomationConstants.fieldQuantity2;
        String fieldTotal1 = AutomationConstants.fieldTotal1;
        String fieldTotal2 = AutomationConstants.fieldTotal2;

        boolean flag = false;
        try {
            if (fieldPrice1.equals(fieldPriceElemet1) && fieldPrice2.equals(fieldPriceElemet2)
                    && fieldQuantity1.equals(fieldQuantityElement1) && fieldQuantity2.equals(fieldQuantityElement2)
                    && fieldTotal1.equals(fieldTotalElement1) && fieldTotal2.equals(fieldTotalElement2)
            ) {
                System.out.println("islem basarili");
                flag = true;
            } else {
                System.out.println("islem basarisiz");
                Assert.fail("failed");
            }
        } catch (Exception e) {
            System.out.println("failed" + e);
            Assert.fail("failed assert");
        }
        return flag;
    }
}


