package guru.qa.github.configHelpers;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:default.properties"
})
public interface WebdriverConfig extends Config {

    @DefaultValue("false")
    @Key("webdriver.remote")
    boolean remote();

    @Key("webdriver.remote.url")
    URL remoteUrl();

    @Key("webdriver.browser.name")
    BrowserName browserName();

}
