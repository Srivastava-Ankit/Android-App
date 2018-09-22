package org.githubissue.rest;

import android.content.Context;
import android.database.Cursor;

import org.githubissue.DataBaseHelper;
import org.githubissue.ext.DateModel;
import org.githubissue.ext.ICallback;
import org.githubissue.ext.IUpdate;
import org.githubissue.ext.Issues;
import org.githubissue.ext.PojoMapper;
import org.githubissue.ext.SLog;
import org.githubissue.ext.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ModelEntry {

    private static final ModelEntry INSTANCE = new ModelEntry();
    IUpdate update;
    Context context;
    static int reqNo = 2;
    private DataBaseHelper dbEntry;
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
    private String dbID = "Invalid";

    public List<Issues> getIssuesList() {
        return issuesList;
    }

    public void setContext(Context pContext) {
        context = pContext;
    }

    private List<Issues> issuesList;
    private Retroclient mClient;

    private ICallback<List<Issues>> mICallback = new ICallback<List<Issues>>() {
        @Override
        public void onResponse(List<Issues> pResponse) {
            SLog.i(Utils.TAG, "Got response");
            issuesList.addAll(pResponse);
            reqNo--;

            if (update != null && reqNo <= 0) {
                update.update(true);
                if (dbEntry == null) {
                    dbEntry = new DataBaseHelper(context);
                }

                dbEntry.insertData(dbID, issuesList, getTime());

            }

        }

        @Override
        public void onFailure(int pErrorCode) {
            issuesList.clear();
            if (update != null) {
                update.update(true);
            }
        }

        @Override
        public void onError(Throwable pThrowable) {
            issuesList.clear();
            if (update != null) {
                update.update(true);
            }
        }
    };

    private ModelEntry() {
        issuesList = new ArrayList<>();
        mClient = new Retroclient();
    }

    public static ModelEntry getInstance() {
        return INSTANCE;
    }

    private void updateID(String org, String repo){
        dbID = org +"_"+repo;
    }

    public void fetch(String org, String repo, IUpdate updateCall) {
        updateID(org, repo);
        String id = dbID;
        dbEntry = new DataBaseHelper(context);
        DateModel model = dbEntry.getAllData(id);
        if (model != null){
            if(!hasTimeCrossed(model)) {
                issuesList.clear();
                SLog.i(Utils.TAG, "DB enteries", model.issue);
                issuesList.addAll(model.issue);
                updateCall.update(true);
                return;
            }else{
                issuesList.clear();
                SLog.i(Utils.TAG, "Clearing Db");
                dbEntry.deleteData(id);
            }
        }
        reqNo = 2;
        IGithub api = null;
        update = updateCall;
        issuesList.clear();
        SLog.i(Utils.TAG, "Calling retrofit");
        try {
            api = mClient.getClient().create(IGithub.class);
            final Map<String, String> queries = new HashMap<>();
            queries.put("state", "closed");
            Call<List<Issues>> call = api.getOpenIssue(org, repo, queries);
            Callback<List<Issues>> callback = Utils.getCallback(
                    new PojoMapper<List<Issues>, List<Issues>>() {

                        @Override
                        public List<Issues> mapTo(List<Issues> pIssues) {
                            return pIssues;
                        }
                    }, mICallback);

            call.enqueue(callback);
        } catch (WsException pE) {
            SLog.e(Utils.TAG, pE.getMessage());
        }

        try {
            api = mClient.getClient().create(IGithub.class);
            final Map<String, String> queries = new HashMap<>();
            queries.put("state", "open");
            Call<List<Issues>> call = api.getOpenIssue(org, repo, queries);
            Callback<List<Issues>> callback = Utils.getCallback(
                    new PojoMapper<List<Issues>, List<Issues>>() {

                        @Override
                        public List<Issues> mapTo(List<Issues> pIssues) {
                            return pIssues;
                        }
                    }, mICallback);

            call.enqueue(callback);
        } catch (WsException pE) {
            SLog.e(Utils.TAG, pE.getMessage());
        }
    }

    private boolean hasTimeCrossed(DateModel model) {
        String timeStamp = model.time;
        Date entry = null;
        Date latest = null;

        try {
            SLog.i(Utils.TAG, sdf != null);
            entry = sdf.parse(timeStamp);
            latest = sdf.parse(sdf.format(new Date(System.currentTimeMillis())));

        } catch (ParseException e) {
            SLog.e(Utils.TAG, e.getMessage());
            return false;
        }

        long diff = entry.getTime() - latest.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;

        return diffMinutes > 30;
    }

    private String getTime() {
        return sdf.format(new Date(System.currentTimeMillis()));
    }
}
