package com.github.saphyra.apphub.app.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public class CustomRequest extends StringRequest {
    private static final String TAG = CustomRequest.class.getName();

    private final String payload;

    public CustomRequest(Context context, int method, String url, ResponseHandler<String> successCallback){
        this(context, method, url, null, successCallback);
    }

    public CustomRequest(int method, String url, ResponseHandler<String> successCallback, ResponseHandler<VolleyError> errorCallBack){
        this(method, url, null, successCallback, errorCallBack);
    }

    public CustomRequest(Context context, int method, String url, Object payload, ResponseHandler<String> successCallback){
        this(method, url, payload, successCallback, new StandardErrorHandler(context));
    }

    public CustomRequest(int method, String url, Object payload, ResponseHandler<String> successCallback, ResponseHandler<VolleyError> errorCallback) {
        super(
            method,
            url,
            response -> {
                Log.w(TAG, String.format("Response for url %s: %s", url, response));
                successCallback.handle(response);
            },
            error -> {
                Log.w(TAG, "CustomRequest: error: " + error.getMessage(), error);
                errorCallback.handle(error);
            }
        );

        this.payload = payload == null ? null : new Gson().toJson(payload);
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() {
        return payload == null ? null : payload.getBytes(StandardCharsets.UTF_8);
    }
}
