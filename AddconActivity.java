package com.project.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AddconActivity extends AppCompatActivity {

    private String cus_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcon);

        Intent intent = getIntent();
        cus_id = intent.getExtras().getString("cus_id");

    }
}
