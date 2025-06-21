package com.bakangmonei.crude_retrofit.Service;

import com.bakangmonei.crude_retrofit.Interface.GitHubAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubClient {
    private static final String BASE_URL = "https://api.github.com/";
    private static GitHubAPI instance;

    public static GitHubAPI getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(GitHubAPI.class);
        }
        return instance;
    }
}
