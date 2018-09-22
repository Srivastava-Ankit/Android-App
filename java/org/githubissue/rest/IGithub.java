package org.githubissue.rest;

import org.githubissue.ext.Issues;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created on 9/21/2018.
 */
public interface IGithub {

    @GET("repos/{org}/{repo}/issues")
    Call<List<Issues>> getOpenIssue(@Path("org") String org, @Path("repo") String repo, @QueryMap(encoded = true) Map<String, String> params);

}
