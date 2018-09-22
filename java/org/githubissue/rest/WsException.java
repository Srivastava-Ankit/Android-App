package org.githubissue.rest;

/**
 * Created on 9/21/2018.
 */
public class WsException extends Exception {

    public WsException() {
    }

    public WsException(Throwable e) {
        super(e);
    }

    public WsException(String msg) {
        super(msg);
    }

    public WsException(String msg, Throwable e) {
        super(msg + ": " + e.getMessage(), e);
    }
}

