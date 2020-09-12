package guru.qa.github;

import guru.qa.github.configHelpers.ServiceConfig;
import guru.qa.github.configHelpers.TestDataConfig;
import guru.qa.github.model.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class ApiSteps {

    ServiceConfig serviceConfig = ConfigFactory.newInstance().create(ServiceConfig.class);
    TestDataConfig testDataConfig = ConfigFactory.newInstance().create(TestDataConfig.class);


    @Step("Check that issue is created")
    public void checkThatIssueIsCreated(int issue, String title, String assignee, List<String> labels) {

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
                .header("Authorization", "token " + testDataConfig.token())
                .baseUri(serviceConfig.apiUrl())
                .log().uri()
        .when()
                .get("/repos/ae-podgor/Allure_homework/issues/{issue}", issue)
        .then()
                .log().body()
        .extract()
                .as(Issue.class);
    }



}
