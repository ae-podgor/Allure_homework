package guru.qa.github;


import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.*;

@Owner("apodgornova")
@Feature("Работа с задачами")
public class StepsTests {

    private static final String BUG_LABEL = "bug";
    private static final String DUPLICATE_LABEL = "duplicate";

    WebSteps webSteps = new WebSteps();
    ApiSteps apiSteps = new ApiSteps();

    @Test
    @DisplayName("Пользователь должен иметь возможность создать новую задачу")
    public void createNewIssue() {
        webSteps.login();
        webSteps.goToRepository();
        webSteps.createIssue(BUG_LABEL, DUPLICATE_LABEL);

        apiSteps.checkThatIssueIsCreated(webSteps.getIssueNumber(), webSteps.title, webSteps.assignee, BUG_LABEL, DUPLICATE_LABEL);
    }
}
