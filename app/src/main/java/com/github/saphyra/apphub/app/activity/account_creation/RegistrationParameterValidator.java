package com.github.saphyra.apphub.app.activity.account_creation;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import java.util.List;
import java.util.stream.Collectors;

class RegistrationParameterValidator implements TextWatcher {
    private final Context context;
    private final List<Validator> validators;
    private final Button submitButton;

    public RegistrationParameterValidator(Context context, List<Validator> validators, Button submitButton) {
        this.context = context;
        this.validators = validators;
        this.submitButton = submitButton;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<Boolean> results = validators.stream()
            .map(validator -> validator.validate(context))
            .collect(Collectors.toList());

        boolean allValid = results.stream().allMatch(aBoolean -> aBoolean);

        submitButton.setEnabled(allValid);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
