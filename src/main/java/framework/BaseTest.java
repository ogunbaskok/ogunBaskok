package framework;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


public class BaseTest {
    public String sDriverName = "";

    public BaseTest() {
    }

    @BeforeMethod
    @Parameters({"browserName"})
    public void setup(@Optional("") String browserName) throws Exception {

        if (browserName.equalsIgnoreCase("chrome")) {
            this.sDriverName = "chrome";
        } else {
            if (!browserName.equalsIgnoreCase("Unknown")) {
                throw new Exception("Unknown driver name = " + this.sDriverName + "Valid names are: ie,firefox,chrome,htmlunit");
            }

            this.sDriverName = "edge";
        }


    }

    public WebDriver getDriver(String sBrowserName) throws Exception {
        WebDriver oDriver;

        switch (this.getBrowserId(sBrowserName)) {
            case 1:
                oDriver = new ChromeDriver(this.getChromeOptions());
                break;
            case 4:
            default:
                throw new Exception("Unknown browsername =" + sBrowserName + " valid names are: ie,firefox,chrome,htmlunit,edge");
        }

        oDriver.manage().deleteAllCookies();
        return oDriver;
    }

    public DesiredCapabilities getCapability(){
        return new DesiredCapabilities();
    }

    public ChromeOptions getChromeOptions(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(this.getCapability());
        chromeOptions.addArguments("test-type");
        chromeOptions.addArguments("disable-translate");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("disable-popup-blocking");
        //chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("window-size=1920,1080");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        return chromeOptions;
    }


    public int getBrowserId(String sBrowserName){
        if (sBrowserName.equalsIgnoreCase("ie")) {
            return 1;
        } else if (sBrowserName.equalsIgnoreCase("firefox")) {
            return 2;
        } else if (sBrowserName.equalsIgnoreCase("chrome")) {
            return 3;
        } else if (sBrowserName.equalsIgnoreCase("htmlunit")) {
            return 4;
        } else {
            return sBrowserName.equalsIgnoreCase("edge") ? 5 : -1;
        }
    }

}
