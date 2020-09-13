package com.github.saphyra.apphub.app.activity.account_creation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.saphyra.apphub.app.R;
import com.github.saphyra.apphub.app.activity.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class AccountCreationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);

        Button submitButton = findViewById(R.id.registerSubmit);

        EditText emailInput = findViewById(R.id.registerEmail);
        TextInputLayout emailErrorLayout = findViewById(R.id.registerEmailError);

        EditText usernameInput = findViewById(R.id.registerUsername);
        TextInputLayout usernameErrorLayout = findViewById(R.id.registerUsernameError);

        EditText passwordInput = findViewById(R.id.registerPassword);
        TextInputLayout passwordErrorLayout = findViewById(R.id.registerPasswordError);

        EditText confirmPasswordInput = findViewById(R.id.registerConfirmPassword);
        TextInputLayout confirmPasswordErrorLayout = findViewById(R.id.registerConfirmPasswordError);

        List<Validator> validators = Arrays.asList(
            new EmailValidator(emailInput, emailErrorLayout),
            new UsernameValidator(usernameInput, usernameErrorLayout),
            new PasswordValidator(passwordInput, passwordErrorLayout),
            new ConfirmPasswordValidator(passwordInput, confirmPasswordInput, confirmPasswordErrorLayout)
        );
        RegistrationParameterValidator registrationParameterValidator = new RegistrationParameterValidator(
            this,
            validators,
            submitButton
        );

        emailInput.addTextChangedListener(registrationParameterValidator);
        usernameInput.addTextChangedListener(registrationParameterValidator);
        passwordInput.addTextChangedListener(registrationParameterValidator);
        confirmPasswordInput.addTextChangedListener(registrationParameterValidator);

        validators.forEach(validator -> validator.validate(this));
        submitButton.setEnabled(false);
    }

    public void createAccount(View view) {
        TextInputLayout emailError = findViewById(R.id.registerEmailError);
        TextInputLayout usernameError = findViewById(R.id.registerUsernameError);
        TextInputLayout passwordError = findViewById(R.id.registerPasswordError);
        TextInputLayout confirmPasswordError = findViewById(R.id.registerConfirmPasswordError);

        if (emailError.isErrorEnabled() || usernameError.isErrorEnabled() || passwordError.isErrorEnabled() || confirmPasswordError.isErrorEnabled()) {
            Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
