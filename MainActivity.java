package org.githubissue;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.githubissue.ext.ICallback;
import org.githubissue.ext.IUpdate;
import org.githubissue.ext.Issues;
import org.githubissue.ext.PojoMapper;
import org.githubissue.ext.SLog;
import org.githubissue.ext.Utils;
import org.githubissue.rest.IGithub;
import org.githubissue.rest.ModelEntry;
import org.githubissue.rest.Retroclient;
import org.githubissue.rest.WsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IUpdate {

    EditText orgEditText;
    EditText repoEditText;
    Button signinBtn;
    ICallback<List<Issues>> mICallback;
    private ModelEntry modelEntry;

    private String org;
    private String repo;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelEntry = ModelEntry.getInstance();
        modelEntry.setContext(getApplicationContext());
        orgEditText = findViewById(R.id.org);
        repoEditText = findViewById(R.id.repo);
        signinBtn = findViewById(R.id.signin);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                org = orgEditText.getText().toString();
                repo = repoEditText.getText().toString();
                if (org != null && !org.isEmpty() && repo != null && !repo.isEmpty()) {
                    startSign(org, repo);
                } else {
                    Utils.showToast(MainActivity.this, R.string.error_sign);
                }
            }
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }

    private void startSign(String pOrg, String pRepo) {
        modelEntry.fetch(pOrg, pRepo, this);
        if(progress != null){
            progress.show();
        }

    }

    private void moveToList() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void update(boolean param) {
        if (param) {
            moveToList();
        } else {
            Utils.showToast(this, R.string.server_error);
        }
        if(progress != null){
            progress.dismiss();
        }
    }
}
