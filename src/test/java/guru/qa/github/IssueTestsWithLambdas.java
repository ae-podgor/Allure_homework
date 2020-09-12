package guru.qa.github;

import guru.qa.github.configHelpers.ServiceConfig;
import guru.qa.github.configHelpers.TestDataConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static guru.qa.github.NamedBy.css;
import static guru.qa.github.NamedBy.named;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class IssueTestsWithLambdas {

    final ServiceConfig serviceConfig = ConfigFactory.newInstance().create(ServiceConfig.class);
    final TestDataConfig testDataConfig = ConfigFactory.newInstance().create(TestDataConfig.class);

    public String url = "https://github.com";
    public String login = "ae-podgor";
    public String password = "Uroborosss666711";
    public String repository = "Allure_homework";
    public String title = "Issue from Web";
    public String assignee = "ae-podgor";
    private int issue;

    @BeforeEach
    public void initLogger() {
        addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Пользователь должен иметь возможноть создать новую Issue")
    public void createNewIssue() {

        step("Логинимся на Github", () -> {
            open(serviceConfig.baseUrl());
            $(withText("Sign in")).click();
            $(By.name("login")).setValue(testDataConfig.login());
            $(By.name("password")).setValue(testDataConfig.password());
            $(byValue("Sign in")).click();
        });

        step("Переходим в репозиторий " + testDataConfig.repository(), () -> {
            $x("//*[@aria-label='View profile and more']").click();
            $(withText("Your repositories")).click();
            $(withText(testDataConfig.repository())).click();
        });

        step("Создаем новую Issue", () -> {
            $x("//*[@data-content='Issues']").click();
            $(css(".btn-primary > span").as("New Issue button")).click();

            $(css("#labels-select-menu").as("Labels select menu")).click();
            $(named(withText("good first issue")).as("good first issue label")).click();
            $(css("body").as("Body")).click();
            $(css("#assignees-select-menu").as("Assignees select menu")).click();
            $(css(".js-username").as("Assignee " + testDataConfig.assignee())).click();
            $(css("#issue_title").as("Issue title field")).setValue(testDataConfig.title()).pressEnter();
        });

        step("Получаем номер созданной Issue", () -> {
            issue = Integer.parseInt(($x("//span[contains(text(),'#')]").getText())
                    .replace("#", ""));
        });

        step(String.format("Проверяем, что Issue #%s создана", issue), () -> {
            given()
//            .proxy(3128)
                    .filter(new AllureRestAssured())
                    .header("Authorization", "token " + testDataConfig.token())
                    .baseUri(serviceConfig.apiUrl())
                    .log().uri()
                    .when()
                    .get("/repos/ae-podgor/Allure_homework/issues/{issue}", issue)
                    .then()
                    .log().body()
                    .body("assignee.login", is(testDataConfig.assignee()))
                    .body("title", is(testDataConfig.title()))
                    .body("labels.name.flatten()", hasItems("good first issue"));
        });

        closeWindow();

    }

}
