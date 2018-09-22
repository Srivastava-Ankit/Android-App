package org.githubissue.ext;

/**
 * Created on 9/21/2018.
 */
public interface ICallback<T> {

    void onResponse(T pResponse);

    void onFailure(int pErrorCode);

    void onError(Throwable pThrowable);
}
