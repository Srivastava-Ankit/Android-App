package org.githubissue.ext;

/**
 * Created on 9/21/2018.
 */
public class Issues {

    long number;
    String title;
    User user;
    PullRequest pull_request;

    @Override
    public String toString() {
        return "Issues{" +
                "number=" + number +
                ", title=" + title +
                ", user=" + user +
                ", pull_request=" + pull_request +
                '}';
    }

    public long getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user.login;
    }

    public String getPull_request() {
        return pull_request.patch_url;
    }
}
