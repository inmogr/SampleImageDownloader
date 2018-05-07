package org.inmogr.sample.images.downloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void requestOfString(View view) {
        goTo(RequestStringActivity.class);
    }

    public void requestOfJsonObject(View view) {
        goTo(RequestJsonObjectActivity.class);
    }

    public void requestOfJsonArray(View view) {
        goTo(RequestJsonArrayActivity.class);
    }

    public void requestOfImage(View view) {
        goTo(RequestImageActivity.class);
    }

    public void requestOfFile(View view) {
        goTo(RequestFileActivity.class);
    }

    public void requestOfStringExtended(View view) {
        goTo(RequestStringExtendedActivity.class);
    }

    private void goTo(Class aClass) {
        startActivity(new Intent(getApplicationContext(), aClass));
    }
}
