package org.githubissue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.githubissue.ext.Issues;
import org.githubissue.ext.SLog;
import org.githubissue.ext.Utils;
import org.githubissue.rest.ModelEntry;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<Issues> issuesList;
    private ModelEntry modelEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        issuesList = new ArrayList<>();
        listAdapter = new ListAdapter(issuesList);
        recyclerView.setAdapter(listAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        modelEntry = ModelEntry.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        issuesList.clear();
        issuesList.addAll(modelEntry.getIssuesList());
        SLog.i(Utils.TAG, "size ", issuesList.size());
        listAdapter.add(issuesList);
        listAdapter.notifyDataSetChanged();
    }
}
