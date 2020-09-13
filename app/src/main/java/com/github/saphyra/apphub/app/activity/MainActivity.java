package com.github.saphyra.apphub.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.saphyra.apphub.app.R;
import com.github.saphyra.apphub.app.activity.account_creation.AccountCreationActivity;
import com.github.saphyra.apphub.app.config.BasicConfig;
import com.github.saphyra.apphub.app.service.LoginService;
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

        ((CheckBox) findViewById(R.id.offlineModeInput))
            .setChecked(getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.offline_mode_key), false));

        boolean rememberEmail = getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.remember_my_email_key), false);
        ((CheckBox) findViewById(R.id.rememberMyEmail))
            .setChecked(rememberEmail);

        EditText emailInput = findViewById(R.id.loginEmail);
        emailInput.setText(getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.login_email_key), ""));

        EditText passwordInput = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setEnabled(false);

        CredentialsValidator validator = new CredentialsValidator(emailInput, passwordInput, loginButton);
        emailInput.addTextChangedListener(validator);
        passwordInput.addTextChangedListener(validator);

        (rememberEmail ? passwordInput : emailInput).requestFocus();
    }

    public void login(View view) {
        EditText serverAddress = findViewById(R.id.serverAddress);
        BasicConfig.SERVER_ADDRESS = serverAddress.getText().toString();

        CheckBox offlineMode = findViewById(R.id.offlineModeInput);
        BasicConfig.OFFLINE_MODE = offlineMode.isChecked();

        boolean rememberMyEmail = ((CheckBox) findViewById(R.id.rememberMyEmail)).isChecked();
        String email = ((EditText) findViewById(R.id.loginEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();

        saveInputs(rememberMyEmail, email);

        if (BasicConfig.OFFLINE_MODE) {
            Toast.makeText(this, R.string.unsupported_operation, Toast.LENGTH_LONG).show();
        } else {
            new LoginService().login(this, email, password);
        }
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

        boolean rememberMyEmail = ((CheckBox) findViewById(R.id.rememberMyEmail)).isChecked();
        String email = ((EditText) findViewById(R.id.loginEmail)).getText().toString();

        WebLayer.getRequest(
            this,
            Endpoints.CHECK_SERVER_AVAILABILITY,
            r -> {
                Log.i(TAG, "navigateToAccountCreation: Server is accessible.");
                saveInputs(rememberMyEmail, email);
                startActivity(new Intent(this, AccountCreationActivity.class));
            },
            r -> {
                Log.i(TAG, "navigateToAccountCreation: Server cannot be accessed.");
                Toast.makeText(this, R.string.server_not_accessible, Toast.LENGTH_LONG).show();
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

    private static class CredentialsValidator implements TextWatcher {
        private final EditText emailInput;
        private final EditText passwordInput;
        private final Button loginButton;

        public CredentialsValidator(EditText emailInput, EditText passwordInput, Button loginButton) {
            this.emailInput = emailInput;
            this.passwordInput = passwordInput;
            this.loginButton = loginButton;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginButton.setEnabled(!emailInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}