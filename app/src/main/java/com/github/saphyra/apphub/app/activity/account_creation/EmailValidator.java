package com.github.saphyra.apphub.app.activity.account_creation;

import android.content.Context;
import android.widget.EditText;

import com.github.saphyra.apphub.app.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

class EmailValidator implements Validator {
    private static final String EMAIL_REGEXP = "^\\s*[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\@[\\w\\-\\+_]+\\.[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\s*$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);

    private final EditText emailInput;
    private final TextInputLayout emailError;

    EmailValidator(EditText emailInput, TextInputLayout emailError) {
        this.emailInput = emailInput;
        this.emailError = emailError;
    }

    @Override
    public Boolean validate(Context context) {
        String email = emailInput.getText().toString();

        if (EMAIL_PATTERN.matcher(email).matches()) {
            emailError.setErrorEnabled(false);
            return true;


        } else {
            emailError.setErrorEnabled(true);
            emailError.setError(context.getString(R.string.invalid_email));
            return false;
        }
    }
}
