package guru.qa.github;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class ApiSteps {


    public String title = "Issue from Web";
    public String assignee = "ae-podgor";

    @Step("Check that issue is created")
    void checkThatIssueIsCreated(int issue, String title, String assignee, String... labels) {

    given()
            .proxy(3128)
            .filter(new AllureRestAssured())
            .header("Authorization", "token 73f5715e84420bf2a9888689272099bf13c56990")
            .baseUri("https://api.github.com")
            .log().uri()
    .when()
            .get("/repos/ae-podgor/Allure_report_lesson_Alina/issues/{issue}", issue)
    .then()
            .log().body()
            .body("assignee.login", is(assignee))
            .body("title", is(title))
            .body("labels.name.flatten()", hasItems(labels));
    }
}
