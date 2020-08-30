package guru.qa.github;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static guru.qa.github.NamedBy.css;
import static guru.qa.github.NamedBy.named;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;


@Owner("apodgornova")
@Feature("Работа с задачами")
public class IssueTestsWithListeners {
    public String url = "https://github.com";
    public String login = "ae-podgor";
    public String password = "11";
    public String repository = "Allure_homework";
    public String title = "Issue from Web";
    public String assignee = "ae-podgor";


    @BeforeEach
    public void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Пользователь должен иметь возможность создать задачу")
    public void createNewIssue(){


        open(url);
        $(named(withText("Sign in")).as("Sign in button")).click();
        $(named(By.name("login")).as("Login field")).setValue(login);
        $(named(By.name("password")).as("Password field")).setValue(password);
        $(named(byValue("Sign in")).as("Submit button")).click();

        // Как использовать NamedBy вместе с xpath?
        $x("//*[@aria-label='View profile and more']").click();
        $(named(withText("Your repositories")).as("Your repositories")).click();
        $(named(withText(repository)).as("Repository " + repository)).click();

        // Как использовать NamedBy вместе с xpath?
        $x("//*[@data-content='Issues']").click();
        $(css(".btn-primary > span").as("New Issue button")).click();

        $(css("#labels-select-menu").as("Labels select menu")).click();
        $(named(withText("good first issue")).as("good first issue label")).click();
        $(css("body").as("Body")).click();
        $(css("#assignees-select-menu").as("Assignees select menu")).click();
        $(css(".js-username").as("Assignee " + assignee)).click();
        $(css("#issue_title").as("Issue title field")).setValue(title).pressEnter();

        int issue = Integer.parseInt(($x("//span[contains(text(),'#')]").getText())
                    .replace("#", ""));

        given()
//            .proxy(3128)
                .filter(new AllureRestAssured())
                .header("Authorization", "token e44aa28443ce99f52533308cc2b59c919ddce410")
                .baseUri("https://api.github.com")
                .log().uri()
                .when()
                .get("/repos/ae-podgor/Allure_homework/issues/{issue}", issue)
                .then()
                .log().body()
                .body("assignee.login", is(assignee))
                .body("title", is(title))
                .body("labels.name.flatten()", hasItems("good first issue"));
    }






}
