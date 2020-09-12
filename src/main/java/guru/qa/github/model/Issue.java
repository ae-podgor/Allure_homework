package guru.qa.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue implements Serializable {

    private String title;
    private Assignee assignee;
    private List<Labels> labels;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public void setLabels(List<Labels> labels) {
        this.labels = labels;
    }
}

