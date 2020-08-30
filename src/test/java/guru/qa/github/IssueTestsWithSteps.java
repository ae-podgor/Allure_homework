package guru.qa.github;


import guru.qa.github.model.Issue;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@Owner("apodgornova")
@Feature("Работа с задачами")
public class IssueTestsWithSteps {

    private static final String BUG_LABEL = "bug";
    private static final String DUPLICATE_LABEL = "duplicate";
    public String title = "Issue from Web";
    public String assignee = "ae-podgor";

    WebSteps webSteps = new WebSteps();
    ApiSteps apiSteps = new ApiSteps();

    @Test
    @DisplayName("Пользователь должен иметь возможность создать новую задачу")
    public void createNewIssue() {

        webSteps.login();
        webSteps.goToRepository();
        webSteps.createIssue(BUG_LABEL, DUPLICATE_LABEL);

        ArrayList<String> labels = new ArrayList<>();
        labels.add(BUG_LABEL);
        labels.add(DUPLICATE_LABEL);
        apiSteps.checkThatIssueIsCreated(webSteps.getIssueNumber(), webSteps.title, webSteps.assignee, labels);
    }

    @Test
    @DisplayName("Пользователь должен иметь возможность создать Issue (тест составлен с применением модели)")
    public void createNewIssueWithModel() {
        webSteps.login();
        webSteps.goToRepository();
        webSteps.createIssue(BUG_LABEL, DUPLICATE_LABEL);

        int issueNumber = webSteps.getIssueNumber();
        Issue createdIssue = apiSteps.getIssueByNumber(issueNumber);
//        String title = createdIssue.getTitle();
//        String assignee = createdIssue.getAssignee();
//        ArrayList<String> labelsName = new ArrayList<>();
//        labelsName.add(createdIssue.getLabels().get(0).getName());
//        labelsName.add(createdIssue.getLabels().get(1).getName());

        assertThat(createdIssue.getTitle(), is(title));
        assertThat(createdIssue.getAssignee(), is(assignee));
        assertThat(createdIssue.getLabels().get(0).getName(), is(BUG_LABEL));
        assertThat(createdIssue.getLabels().get(1).getName(), is(DUPLICATE_LABEL));



//        apiSteps.checkThatIssueIsCreated(issueNumber, title, assignee, labelsName);

    }

}
