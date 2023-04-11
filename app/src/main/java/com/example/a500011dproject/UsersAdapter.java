package com.example.a500011dproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.userName);
        }
    }

    private List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
        Log.d("Users", "Users loaded:" + this.users.toString());
    }

    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.item_user, parent, false);

        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        User user = users.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(user.getName());
    }

    public int getItemCount() {
        return users.size();
    }
}

