package com.bakangmonei.crude_retrofit;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakangmonei.crude_retrofit.Adapter.UserAdapter;
import com.bakangmonei.crude_retrofit.Interface.GitHubAPI;
import com.bakangmonei.crude_retrofit.Model.GitHubUser;
import com.bakangmonei.crude_retrofit.Service.GitHubClient;
import com.bakangmonei.crude_retrofit.Service.GitHubUserSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private GitHubAPI gitHubAPI;

    private static final String TAG = "🛰️ MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchInput);
        recyclerView = findViewById(R.id.recyclerViewUsers);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);

        gitHubAPI = GitHubClient.getInstance();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Live search
                if (s.length() >= 3) {
                    searchGitHubUsers(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need
            }
        });
    }

    private void searchGitHubUsers(String query) {
        gitHubAPI.searchUsers(query).enqueue(new Callback<GitHubUserSearchResponse>() {
            @Override
            public void onResponse(Call<GitHubUserSearchResponse> call, Response<GitHubUserSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GitHubUser> users = response.body().getItems();
                    userAdapter.setUserList(users);
                    Log.i(TAG, "🔍 Found " + users.size() + " users.");
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "⚠️ Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GitHubUserSearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "API Failure", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "🔥 Retrofit failure: " + t.getMessage());
            }
        });
    }
}
