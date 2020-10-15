package com.example.smallfish.ui.publish;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libnavannotation.ActivityDestination;
import com.example.smallfish.R;

@ActivityDestination(pageUrl = "main/tabs/publish", needLogin = true)
public class PublishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
    }
}