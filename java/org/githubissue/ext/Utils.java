package org.githubissue.ext;

import android.content.Context;
import android.widget.Toast;

import org.githubissue.rest.WsException;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 9/21/2018.
 */
public class Utils {

    public static final String TAG = "[GITHUB]";

    public static void showToast(Context pContext, String pS) {
        Toast.makeText(pContext, pS, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context pContext, int pS) {
        Toast.makeText(pContext, pS, Toast.LENGTH_SHORT).show();
    }

    public static <L, R> Callback<R> getCallback(final PojoMapper<L, R> pMapper, final ICallback<L> pCallback){
        Callback<R> retroCallback = null;
        try {
            retroCallback = new Callback<R>() {
                @Override
                public void onResponse(Call<R> call, Response<R> response) {
                    SLog.i(Utils.TAG, "GOt response", response.isSuccessful());
                    if (response.isSuccessful() && response.code() != HttpURLConnection.HTTP_NO_CONTENT && response.body() != null) {
                        pCallback.onResponse(pMapper.mapTo(response.body()));
                    } else if (response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        pCallback.onFailure(response.code());
                    }else {
                        if (response.errorBody() != null) {
                            try {
                                SLog.e(Utils.TAG, response.errorBody().string());
                            } catch (IOException e) {
                                SLog.e(Utils.TAG, "IOException ::", e.getMessage());
                            }
                        }
                        pCallback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<R> call, Throwable t) {
                    pCallback.onError(new WsException("Error in rest calls", t));
                }
            };
        } catch (Exception pE) {
            pCallback.onError(new WsException("Error in rest calls", pE));
        }
        return retroCallback;
    }
}
