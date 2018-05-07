package org.inmogr.sample.images.downloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;

import org.inmogr.sample.images.downloader.sample.download.library.Connector;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestJsonArrayActivity extends AppCompatActivity {

    private Request request;

    private Connector connector = new Connector() {
        @Override
        public void completedSuccessfully(int invokedBy, Object response) {
            if (response instanceof JSONArray) {
                JSONArray array = (JSONArray) response;
                try {
                    String res = "The size of the array is: " + array.length()
                            + "\n";
                    if (array.length() > 0) {
                        res += "Only the first item shown"
                                + "\n"
                                + array.getJSONObject(0).toString();
                    } else {
                        res += "No items to show"
                                + "\n";
                    }
                    ((TextView)findViewById(R.id.responseJsonArrayRequest)).setText(res);
                } catch (JSONException ignored) {}
            }
            hideFloatingButton();
        }
        @Override
        public void completedWithError(String error) {
            ((TextView)findViewById(R.id.responseJsonArrayRequest)).setText(error);
            hideFloatingButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_json_array);
        connector.init(getCacheDir(), getWindow().getDecorView().getRootView());
    }

    private void showFloatingButton() {
        findViewById(R.id.cancelJsonArrayRequest).setVisibility(View.VISIBLE);
    }

    private void hideFloatingButton() {
        findViewById(R.id.cancelJsonArrayRequest).setVisibility(View.GONE);
    }

    private String getUrl() {
        EditText editText = findViewById(R.id.urlJsonArrayRequest);
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
        request = connector.addGetRequestOfJsonArray(url, null);
        showFloatingButton();
    }

    public void post(View view) {
        String url = getUrl();
        if (url == null) return;
        request = connector.addPostRequestOfJsonArray(url, null);
        showFloatingButton();
    }

    public void cancelRequest(View view) {
        if (request == null) return;
        request.cancel();
        request = null;
        hideFloatingButton();
    }
}
