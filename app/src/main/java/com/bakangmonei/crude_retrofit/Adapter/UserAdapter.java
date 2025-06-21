package com.bakangmonei.crude_retrofit.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bakangmonei.crude_retrofit.Model.GitHubUser;
import com.bakangmonei.crude_retrofit.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<GitHubUser> userList = new ArrayList<>();

    public void setUserList(List<GitHubUser> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        GitHubUser user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username, profileUrl;
        private Context context;

        public UserViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            avatar = itemView.findViewById(R.id.avatarImageView);
            username = itemView.findViewById(R.id.usernameTextView);
            profileUrl = itemView.findViewById(R.id.profileUrlTextView);
        }

        public void bind(GitHubUser user) {
            username.setText(user.getLogin());
            profileUrl.setText(user.getHtml_url());

            Glide.with(context)
                    .load(user.getAvatar_url())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .circleCrop()
                    .into(avatar);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(user.getHtml_url()));
                context.startActivity(intent);
            });
        }
    }
}
