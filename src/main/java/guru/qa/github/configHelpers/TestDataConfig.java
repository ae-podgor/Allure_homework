package guru.qa.github.configHelpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:default.properties",
        "file:D:\\Java_learning\\Allure_homework\\secrets.txt"
})
public interface TestDataConfig extends Config {

    @Key("test.data.login")
    String login();

    @Key("test.data.password")
    String password();

    @Key("test.data.repository")
    String repository();

    @Key("test.data.title")
    String title();

    @Key("test.data.assignee")
    String assignee();

    @Key("test.data.token")
    String token();

}
