package com.github.saphyra.apphub.app.activity.account_creation;

import android.content.Context;
import android.widget.EditText;

import com.github.saphyra.apphub.app.R;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordValidator implements Validator {
    private final EditText passwordInput;
    private final TextInputLayout passwordError;

    public PasswordValidator(EditText passwordInput, TextInputLayout passwordError) {
        this.passwordInput = passwordInput;
        this.passwordError = passwordError;
    }

    @Override
    public Boolean validate(Context context) {
        String password = passwordInput.getText().toString();

        if (password.length() < 6) {
            passwordError.setErrorEnabled(true);
            passwordError.setError(context.getString(R.string.password_too_short));
            return false;
        }

        if (password.length() > 30) {
            passwordError.setErrorEnabled(true);
            passwordError.setError(context.getString(R.string.password_too_long));
            return false;
        }

        passwordError.setErrorEnabled(false);
        return true;
    }
}
