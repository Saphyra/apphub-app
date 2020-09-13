package com.github.saphyra.apphub.app.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.github.saphyra.apphub.app.activity.modules.ModulesActivity;
import com.github.saphyra.apphub.app.config.BasicConfig;
import com.github.saphyra.apphub.app.web.Endpoints;
import com.github.saphyra.apphub.app.web.StandardErrorHandler;
import com.github.saphyra.apphub.app.web.WebLayer;
import com.github.saphyra.apphub.app.web.model.request.LoginRequest;
import com.github.saphyra.apphub.app.web.model.response.ErrorResponse;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

import static com.github.saphyra.apphub.app.util.StringUtils.isNullOrEmpty;

public class LoginService {
    private static final String TAG = LoginService.class.getName();

    public void login(Context context, String email, String password) {
        WebLayer.postRequest(
            context,
            Endpoints.LOGIN,
            new LoginRequest(email, password),
            response -> {
                Log.i(TAG, "Login successful. Session token: " + response);
                BasicConfig.USER_SESSION = response;
                context.startActivity(new Intent(context, ModulesActivity.class));
            },
            error -> {
                if (error.networkResponse.statusCode == 401) {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    Log.w(TAG, "errorResponseBody: " + responseBody);
                    ErrorResponse errorResponse = new Gson().fromJson(responseBody, ErrorResponse.class);
                    if (!isNullOrEmpty(errorResponse.getLocalizedMessage())) {
                        Toast.makeText(context, errorResponse.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                new StandardErrorHandler(context).handle(error);
            }
        );
    }
}
