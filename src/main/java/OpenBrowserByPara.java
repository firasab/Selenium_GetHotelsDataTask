import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;




public class OpenBrowserByPara {


    public static WebDriver openBrowser(String browser) {
        WebDriver driver;
        if (browser.equals("firefox")) {
            //Setting webdriver.gecko.driver property
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\firas\\Desktop\\Selenium_GetHotelsDataTask\\src\\main\\resources\\geckodriver.exe");

            //Instantiating driver object and launching browser
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\firas\\Desktop\\Selenium_GetHotelsDataTask\\src\\main\\resources\\chromedriver.exe");

            //Instantiating driver object
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            System.setProperty(
                    "webdriver.edge.driver",
                    "C:\\Users\\firas\\Desktop\\Selenium_GetHotelsDataTask\\src\\main\\resources\\msedgedriver.exe");
            // Instantiate a ChromeDriver class.
            driver = new EdgeDriver();
        } else {
            driver = null;
        }

        return driver;
    }
}


