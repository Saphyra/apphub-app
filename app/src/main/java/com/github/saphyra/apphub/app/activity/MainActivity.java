package com.github.saphyra.apphub.app.activity;

import android.content.Intent;
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
    }

    public void navigateToAccountCreation(View view) {
        final Intent intent = new Intent(this, AccountCreationActivity.class);

        EditText serverAddress = findViewById(R.id.serverAddress);
        BasicConfig.SERVER_ADDRESS = serverAddress.getText().toString();

        CheckBox offlineMode = findViewById(R.id.offlineModeInput);
        BasicConfig.OFFLINE_MODE = offlineMode.isChecked();

        Log.i(TAG, String.format("navigateToAccountCreation: Updated SERVER_ADDRESS to %s and OFFLINE_MODE to %s", BasicConfig.SERVER_ADDRESS, BasicConfig.OFFLINE_MODE));

        if (BasicConfig.OFFLINE_MODE) {
            Toast.makeText(this, "Offline mode must be disabled", Toast.LENGTH_LONG).show();
            return;
        }

        final MainActivity activity = this;
        WebLayer.getRequest(
                activity,
                Endpoints.CHECK_SERVER_AVAILABILITY,
                new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "navigateToAccountCreation: Server is accessible.");
                        startActivity(intent);
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
}