package guru.qa.github;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static guru.qa.github.NamedBy.css;
import static guru.qa.github.NamedBy.named;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class IssueTestsWithLambdas {
    public String url = "https://github.com";
    public String login = "ae-podgor";
    public String password = "11";
    public String repository = "Allure_homework";
    public String title = "Issue from Web";
    public String assignee = "ae-podgor";
    private int issue;


    @Test
    @DisplayName("Пользователь должен иметь возможноть создать новую Issue")
    public void createNewIssue() {

        step("Логинимся на Github", () -> {
            open(url);
            $(withText("Sign in")).click();
            $(By.name("login")).setValue(login);
            $(By.name("password")).setValue(password);
            $(byValue("Sign in")).click();
        });

        step("Переходим в репозиторий " + repository, () -> {
            $x("//*[@aria-label='View profile and more']").click();
            $(withText("Your repositories")).click();
            $(withText(repository)).click();
        });

        step("Создаем новую Issue", () -> {
            $x("//*[@data-content='Issues']").click();
            $(css(".btn-primary > span").as("New Issue button")).click();

            $(css("#labels-select-menu").as("Labels select menu")).click();
            $(named(withText("good first issue")).as("good first issue label")).click();
            $(css("body").as("Body")).click();
            $(css("#assignees-select-menu").as("Assignees select menu")).click();
            $(css(".js-username").as("Assignee " + assignee)).click();
            $(css("#issue_title").as("Issue title field")).setValue(title).pressEnter();
        });

        step("Получаем номер созданной Issue", () -> {
            issue = Integer.parseInt(($x("//span[contains(text(),'#')]").getText())
                    .replace("#", ""));
        });

        step(String.format("Проверяем, что Issue #%s создана", issue), () -> {
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
        });

    }

}
