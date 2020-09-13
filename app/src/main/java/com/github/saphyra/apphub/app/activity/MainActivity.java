package com.github.saphyra.apphub.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.saphyra.apphub.app.R;
import com.github.saphyra.apphub.app.config.BasicConfig;
import com.github.saphyra.apphub.app.web.Endpoints;
import com.github.saphyra.apphub.app.web.WebLayer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((EditText) findViewById(R.id.serverAddress))
            .setText(getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.server_address_key), ""));

        ((EditText) findViewById(R.id.loginEmail))
            .setText(getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.login_email_key), ""));

        ((CheckBox) findViewById(R.id.offlineModeInput))
            .setChecked(getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.offline_mode_key), false));

        ((CheckBox) findViewById(R.id.rememberMyEmail))
            .setChecked(getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.remember_my_email_key), false));
    }

    public void navigateToAccountCreation(View view) {
        EditText serverAddress = findViewById(R.id.serverAddress);
        BasicConfig.SERVER_ADDRESS = serverAddress.getText().toString();

        CheckBox offlineMode = findViewById(R.id.offlineModeInput);
        BasicConfig.OFFLINE_MODE = offlineMode.isChecked();

        Log.i(TAG, String.format("navigateToAccountCreation: Updated SERVER_ADDRESS to %s and OFFLINE_MODE to %s", BasicConfig.SERVER_ADDRESS, BasicConfig.OFFLINE_MODE));

        if (BasicConfig.OFFLINE_MODE) {
            Toast.makeText(this, "Offline mode must be disabled", Toast.LENGTH_LONG).show();
            return;
        }

        final boolean rememberMyEmail = ((CheckBox) findViewById(R.id.rememberMyEmail)).isChecked();
        final String email = ((EditText) findViewById(R.id.loginEmail)).getText().toString();

        final MainActivity activity = this;
        WebLayer.getRequest(
            activity,
            Endpoints.CHECK_SERVER_AVAILABILITY,
            new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "navigateToAccountCreation: Server is accessible.");
                    saveInputs(rememberMyEmail, email);
                    startActivity(new Intent(activity, AccountCreationActivity.class));
                }
            },
            new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "navigateToAccountCreation: Server cannot be accessed.");
                    Toast.makeText(activity, "Server cannot be accessed", Toast.LENGTH_LONG).show();
                }
            }
        );
    }

    private void saveInputs(boolean rememberMyEmail, String email) {
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE)
            .edit();
        editor.putString(getString(R.string.server_address_key), BasicConfig.SERVER_ADDRESS);
        editor.putBoolean(getString(R.string.offline_mode_key), BasicConfig.OFFLINE_MODE);
        editor.putString(getString(R.string.login_email_key), rememberMyEmail ? email : "");
        editor.putBoolean(getString(R.string.remember_my_email_key), rememberMyEmail);
        editor.apply();
    }
}