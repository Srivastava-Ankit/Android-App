package org.githubissue.ext;

import java.util.List;

public class DateModel {
    public String time;
    public List<Issues> issue;
    public String id;

    public DateModel(){}
    public DateModel(String time, List<Issues> issue, String id) {
        this.time = time;
        this.issue = issue;
        this.id = id;
    }
}
