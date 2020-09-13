package com.github.saphyra.apphub.app.activity.account_creation;

import android.content.Context;
import android.widget.EditText;

import com.github.saphyra.apphub.app.R;
import com.google.android.material.textfield.TextInputLayout;

class UsernameValidator implements Validator {
    private final EditText usernameInput;
    private final TextInputLayout usernameError;

    public UsernameValidator(EditText usernameInput, TextInputLayout usernameError) {
        this.usernameInput = usernameInput;
        this.usernameError = usernameError;

    }

    @Override
    public Boolean validate(Context context) {
        String username = usernameInput.getText().toString();

        if (username.length() < 3) {
            usernameError.setErrorEnabled(true);
            usernameError.setError(context.getString(R.string.username_too_short));
            return false;
        }

        if (username.length() > 30) {
            usernameError.setErrorEnabled(true);
            usernameError.setError(context.getString(R.string.username_too_long));
            return false;
        }

        usernameError.setErrorEnabled(false);
        return true;
    }
}
