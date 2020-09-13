package com.github.saphyra.apphub.app.activity.account_creation;

import android.content.Context;
import android.widget.EditText;

import com.github.saphyra.apphub.app.R;
import com.google.android.material.textfield.TextInputLayout;

class ConfirmPasswordValidator implements Validator {
    private final EditText passwordInput;
    private final EditText confirmPasswordInput;
    private final TextInputLayout confirmPasswordError;

    public ConfirmPasswordValidator(EditText passwordInput, EditText confirmPasswordInput, TextInputLayout confirmPasswordError) {
        this.passwordInput = passwordInput;
        this.confirmPasswordInput = confirmPasswordInput;
        this.confirmPasswordError = confirmPasswordError;
    }

    @Override
    public Boolean validate(Context context) {
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        boolean result = password.equals(confirmPassword);

        if (result) {
            confirmPasswordError.setErrorEnabled(false);
        } else {
            confirmPasswordError.setErrorEnabled(true);
            confirmPasswordError.setError(context.getString(R.string.incorrect_confirm_password));
        }

        return result;
    }
}
