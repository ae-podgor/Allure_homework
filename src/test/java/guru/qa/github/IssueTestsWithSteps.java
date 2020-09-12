package guru.qa.github;


import guru.qa.github.model.Issue;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Owner("apodgornova")
@Feature("Работа с задачами")
public class IssueTestsWithSteps {

    private static final String BUG_LABEL = "bug";
    private static final String DUPLICATE_LABEL = "duplicate";

    WebSteps webSteps = new WebSteps();
    ApiSteps apiSteps = new ApiSteps();

    @BeforeEach
    public void initLogger() {
        addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Пользователь должен иметь возможность создать новую задачу")
    public void createNewIssue() {
        webSteps.openMainPage();
        webSteps.login();
        webSteps.goToRepository();
        webSteps.createIssue(BUG_LABEL, DUPLICATE_LABEL);

        ArrayList<String> labels = new ArrayList<>();
        labels.add(BUG_LABEL);
        labels.add(DUPLICATE_LABEL);
        apiSteps.checkThatIssueIsCreated(webSteps.getIssueNumber(), webSteps.testDataConfig.title(), webSteps.testDataConfig.assignee(), labels);

        closeWindow();
    }

    @Test
    @DisplayName("Пользователь должен иметь возможность создать Issue (тест составлен с применением модели)")
    public void createNewIssueWithModel() {
        webSteps.openMainPage();
        webSteps.login();
        webSteps.goToRepository();
        webSteps.createIssue(BUG_LABEL, DUPLICATE_LABEL);

        int issueNumber = webSteps.getIssueNumber();
        Issue createdIssue = apiSteps.getIssueByNumber(issueNumber);

        assertThat(createdIssue.getTitle(), is(webSteps.testDataConfig.title()));
        assertThat(createdIssue.getAssignee().getLogin(), is(webSteps.testDataConfig.assignee()));
        assertThat(createdIssue.getLabels().get(0).getName(), is(BUG_LABEL));
        assertThat(createdIssue.getLabels().get(1).getName(), is(DUPLICATE_LABEL));

        closeWindow();
    }

}
