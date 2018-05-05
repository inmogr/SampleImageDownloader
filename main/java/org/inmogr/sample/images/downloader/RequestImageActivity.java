package org.inmogr.sample.images.downloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;

import org.inmogr.sample.images.downloader.connection.Connector;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestImageActivity extends AppCompatActivity {

    private Request request;

    private Connector connector = new Connector() {
        @Override
        public void completedSuccessfully(int invokedBy, Object response) {
            if (response instanceof Bitmap) {
                ((ImageView) findViewById(R.id.responseImageRequest)).setImageBitmap((Bitmap) response);
            }
            hideFloatingButton();
        }
        @Override
        public void completedWithError(String error) {
            ((ImageView) findViewById(R.id.responseImageRequest)).setImageResource(android.R.drawable.stat_notify_error);
            hideFloatingButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_image);
        connector.init(getCacheDir(), getWindow().getDecorView().getRootView());
    }

    private void showFloatingButton() {
        findViewById(R.id.cancelImageRequest).setVisibility(View.VISIBLE);
    }

    private void hideFloatingButton() {
        findViewById(R.id.cancelImageRequest).setVisibility(View.GONE);
    }

    private String getUrl() {
        EditText editText = findViewById(R.id.urlImageRequest);
        String url = editText.getText().toString();
        try {
            new URL(url);
            return url;
        } catch (MalformedURLException e) {
            editText.setError(getString(R.string.invalid_url));
            return null;
        }
    }

    public void get(View view) {
        String url = getUrl();
        if (url == null) return;
        request = connector.addGetRequestOfImage(url, null, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565);
        showFloatingButton();
    }

    public void post(View view) {
        String url = getUrl();
        if (url == null) return;
        request = connector.addPostRequestOfImage(url, null, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565);
        showFloatingButton();
    }

    public void cancelRequest(View view) {
        if (request == null) return;
        request.cancel();
        request = null;
        hideFloatingButton();
    }
}
