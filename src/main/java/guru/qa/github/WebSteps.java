package guru.qa.github;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.*;

public class WebSteps {
    public String url = "https://github.com";
    public String login = "ae-podgor";
    public String password = "11";
    public String repository = "Allure_homework";
    public String title = "Issue from Web";
    public String assignee = "ae-podgor";

    @Step("Login to Github")
    void login() {
        parameter("Login", login);

        open(url);
        $(withText("Sign in")).click();
        $(By.name("login")).setValue(login);
        $(By.name("password")).setValue(password);
        $(byValue("Sign in")).click();
    }

    @Step("Go to repository")
    void goToRepository() {
        parameter("Repository", repository);

        $x("//*[@aria-label='View profile and more']").click();
        $(withText("Your repositories")).click();
        $(withText(repository)).click();
    }

    @Step("Create new Issue")
    void createIssue(String... labels) {
        parameter("Title", title);
        parameter("Assignee", assignee);
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
        $("#issue_title").setValue(title).pressEnter();
    }

    @Step("Get Issue number")
    int getIssueNumber() {
        return Integer.parseInt(($x("//span[contains(text(),'#')]").getText())
                .replace("#", ""));
    }

}
