package com.github.saphyra.apphub.app.web;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.saphyra.apphub.app.activity.MainActivity;
import com.github.saphyra.apphub.app.web.model.response.ErrorResponse;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

import static com.github.saphyra.apphub.app.util.StringUtils.isNullOrEmpty;


public class StandardErrorHandler implements ResponseHandler<VolleyError> {
    private static final String TAG = StandardErrorHandler.class.getName();

    private final Context context;

    public StandardErrorHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(VolleyError response) {
        String responseBody = new String(response.networkResponse.data, StandardCharsets.UTF_8);
        Log.w(TAG, "errorResponseBody: " + responseBody);

        if (response.networkResponse.statusCode == 401) {
            context.startActivity(new Intent(context, MainActivity.class));
        }

        ErrorResponse errorResponse = new Gson().fromJson(responseBody, ErrorResponse.class);
        if (!isNullOrEmpty(errorResponse.getLocalizedMessage())) {
            Toast.makeText(context, errorResponse.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(context, String.format("Unknown response from server: %s - %s", response.networkResponse.statusCode, responseBody), Toast.LENGTH_LONG).show();
    }
}
