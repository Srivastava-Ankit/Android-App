package org.githubissue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.githubissue.ext.Issues;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private final List<Issues> issuesList;

    public ListAdapter(List<Issues> pIssuesList) {
        issuesList = new ArrayList<>();
        issuesList.addAll(pIssuesList);

    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Issues issues = issuesList.get(position);
        holder.userView.setText(issues.getUser());
        holder.titleView.setText(issues.getTitle());
        holder.numberView.setText(String.valueOf(issues.getNumber()));
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    public void add(List<Issues> pIssues) {
        issuesList.clear();
        issuesList.addAll(pIssues);
    }

    static final class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView numberView;
        public TextView titleView;
        public TextView userView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            numberView = itemView.findViewById(R.id.number);
            titleView = itemView.findViewById(R.id.title);
            userView = itemView.findViewById(R.id.user);

        }

        public void bind(Issues pIssues) {

        }
    }
}
