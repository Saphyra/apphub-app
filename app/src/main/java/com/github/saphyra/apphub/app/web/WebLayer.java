package com.github.saphyra.apphub.app.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.saphyra.apphub.app.config.BasicConfig;

import java.util.Objects;

public class WebLayer {
    private static final String TAG = WebLayer.class.getName();

    public static void getRequest(Context context, String endpoint, ResponseHandler<String> successCallback, ResponseHandler<VolleyError> errorCallBack) {
        String url = getUrl(endpoint);

        StringRequest request = new CustomRequest(
            Request.Method.GET,
            url,
            successCallback,
            errorCallBack
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public static void postRequest(Context context, String endpoint, Object payload, ResponseHandler<String> successCallback) {
        String url = getUrl(endpoint);

        StringRequest request = new CustomRequest(
            context,
            Request.Method.POST,
            url,
            payload,
            successCallback
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public static void postRequest(Context context, String endpoint, Object payload, ResponseHandler<String> successCallback, ResponseHandler<VolleyError> errorResponseHandler) {
        String url = getUrl(endpoint);

        StringRequest request = new CustomRequest(
            Request.Method.POST,
            url,
            payload,
            successCallback,
            errorResponseHandler
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private static String getUrl(String endpoint) {
        String result = String.format("http://%s%s", Objects.requireNonNull(BasicConfig.SERVER_ADDRESS), endpoint);
        Log.i(TAG, "url created:  " + result);
        return result;
    }
}
