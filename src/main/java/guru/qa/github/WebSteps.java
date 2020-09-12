package guru.qa.github;

import guru.qa.github.configHelpers.ServiceConfig;
import guru.qa.github.configHelpers.TestDataConfig;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.*;

public class WebSteps {

    final ServiceConfig serviceConfig = ConfigFactory.newInstance().create(ServiceConfig.class);
    final TestDataConfig testDataConfig = ConfigFactory.newInstance().create(TestDataConfig.class);

    @Step("Open main page")
    void openMainPage() {
        open(serviceConfig.baseUrl());
    }

    @Step("Login to Github")
    void login() {
        parameter("Login", testDataConfig.login());

        $(withText("Sign in")).click();
        $(By.name("login")).setValue(testDataConfig.login());
        $(By.name("password")).setValue(testDataConfig.password());
        $(byValue("Sign in")).click();
    }

    @Step("Go to repository")
    void goToRepository() {
        parameter("Repository", testDataConfig.repository());

        $x("//*[@aria-label='View profile and more']").click();
        $(withText("Your repositories")).click();
        $(withText(testDataConfig.repository())).click();
    }

    @Step("Create new Issue")
    void createIssue(String... labels) {
        parameter("Title", testDataConfig.title());
        parameter("Assignee", testDataConfig.assignee());
        parameter("Labels", labels);

        $x("//*[@data-content='Issues']").click();
        $(".btn-primary > span").click();

        List<String> labelsList = Arrays.asList(labels);
        $("#labels-select-menu").click();
        labelsList.forEach(
                label -> {
                    $(withText(label)).click();
                }
        );
        $("body").click();
        $("#assignees-select-menu").click();
        $(".js-username").click();
        $("#issue_title").setValue(testDataConfig.title()).pressEnter();
    }

    @Step("Get Issue number")
    int getIssueNumber() {
        return Integer.parseInt(($x("//span[contains(text(),'#')]").getText())
                .replace("#", ""));
    }

}
