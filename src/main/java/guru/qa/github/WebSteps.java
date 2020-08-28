package guru.qa.github;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class WebSteps {
    public String url = "https://github.com";
    public String login = "ae-podgor";
    public String password = "Uroborosss666711";
    public String repository = "Allure_report_lesson_Alina";
    public String title = "Issue from Web";
    public String assignee = "ae-podgor";


    @Step("Login to Github")
    void login() {
        open(url);
        $(withText("Sign in")).click();
        $(By.name("login")).setValue(login);
        $(By.name("password")).setValue(password);
        $(byValue("Sign in")).click();
    }

    @Step("Go to repository")
    void goToRepository() {
        $x("//*[@aria-label='View profile and more']").click();
        $(withText("Your repositories")).click();
        $(withText(repository)).click();
    }

    @Step("Create new Issue")
    void createIssue(String... labels) {
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
        $(withText("Assignees")).click();
        $(withText("ae.podgor")).click();
        $x("//*[@aria-label='Type or choose a name']").setValue(assignee);
        $("#issue_title").setValue(title).pressEnter();
    }

    @Step("Get Issue number")
    int getIssueNumber() {
        return Integer.parseInt(($x("//span[contains(text(),'#')]").getText())
                .replace("#", ""));
    }





}
