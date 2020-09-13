package com.github.saphyra.apphub.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.saphyra.apphub.app.R;

public class AccountCreationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
