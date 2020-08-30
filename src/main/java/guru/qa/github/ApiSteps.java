package guru.qa.github;

import guru.qa.github.model.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class ApiSteps {

    @Step("Check that issue is created")
    public void checkThatIssueIsCreated(int issue, String title, String assignee, List<String> labels) {

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
            .body("labels.name.flatten()", hasItems(labels.get(0)))
            .body("labels.name.flatten()", hasItems(labels.get(1)));
    }

    @Step("Get issue by number")
    public Issue getIssueByNumber(int issue) {
        return
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
        .extract()
                .as(Issue.class);
    }
}
