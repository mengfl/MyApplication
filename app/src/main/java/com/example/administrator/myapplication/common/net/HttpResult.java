package com.example.administrator.myapplication.common.net;

/**
 * Created by Administrator on 2017/4/24/024.
 */

public class HttpResult<T> {
    protected boolean status;
    protected String message;
    protected T results;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
