package com.github.saphyra.apphub.app.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.saphyra.apphub.app.config.BasicConfig;

import java.util.Objects;

public class WebLayer {
    private static final String TAG = WebLayer.class.getName();

    public static void getRequest(Context context, String endpoint, final Runnable successCallback, final Runnable errorCallBack) {

        String url = String.format("http://%s%s", Objects.requireNonNull(BasicConfig.SERVER_ADDRESS), endpoint);
        Log.i(TAG, "getRequest: url: " + url);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse: " + response);
                        successCallback.run();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse: " + error.getMessage());
                        errorCallBack.run();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
