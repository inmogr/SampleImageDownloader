package org.inmogr.sample.images.downloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;

import org.inmogr.sample.images.downloader.connection.Connector;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestStringExtendedActivity extends AppCompatActivity {

    private Request request;

    private Connector connector = new Connector() {
        @Override
        public void completedSuccessfully(int invokedBy, Object response) {
            try {
                JSONObject json = new JSONObject((String) response);
                String res = "Used String then parsed to JSON just to show sample of extending a request\n";
                res += json.toString();
                ((TextView)findViewById(R.id.responseStringExtendedRequest)).setText(res);
            } catch (Exception ignored) {}
            hideFloatingButton();
        }
        @Override
        public void completedWithError(String error) {
            ((TextView)findViewById(R.id.responseStringExtendedRequest)).setText(error);
            hideFloatingButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_string_extended);
        connector.init(getCacheDir(), getWindow().getDecorView().getRootView());
    }

    private void showFloatingButton() {
        findViewById(R.id.cancelStringExtendedRequest).setVisibility(View.VISIBLE);
    }

    private void hideFloatingButton() {
        findViewById(R.id.cancelStringExtendedRequest).setVisibility(View.GONE);
    }

    private String getUrl() {
        EditText editText = findViewById(R.id.urlStringExtendedRequest);
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
        request = connector.addGetRequestOfString(url, null);
        showFloatingButton();
    }

    public void post(View view) {
        String url = getUrl();
        if (url == null) return;
        request = connector.addPostRequestOfString(url, null);
        showFloatingButton();
    }

    public void cancelRequest(View view) {
        if (request == null) return;
        request.cancel();
        request = null;
        hideFloatingButton();
    }
}
