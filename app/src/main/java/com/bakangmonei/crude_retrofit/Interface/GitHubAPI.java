package com.bakangmonei.crude_retrofit.Interface;

import com.bakangmonei.crude_retrofit.Model.GitHubUser;
import com.bakangmonei.crude_retrofit.Repo.GitHubRepo;
import com.bakangmonei.crude_retrofit.Service.GitHubUserSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubAPI {

    @GET("search/users")
    Call<GitHubUserSearchResponse> searchUsers(@Query("q") String query);

    @GET("users/{username}")
    Call<GitHubUser> getUser(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<List<GitHubRepo>> getUserRepos(@Path("username") String username);
}
