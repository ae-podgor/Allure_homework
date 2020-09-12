package guru.qa.github.configHelpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {


    @Override
    public WebDriver get() {
        final WebdriverConfig config = ConfigFactory.newInstance().create(WebdriverConfig.class);
        if (config.remote()) {
            return new RemoteWebDriver(config.remoteUrl(), DesiredCapabilities.chrome());
        } else {
            switch (config.browserName()) {
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver();
                default:
                    throw new RuntimeException("Unknown browser name " + config.browserName());
            }
        }
    }
}
