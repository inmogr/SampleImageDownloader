package org.inmogr.sample.images.downloader;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;

import org.inmogr.sample.images.downloader.connection.Connector;
import org.inmogr.sample.images.downloader.connection.InputStreamRequest;
import org.inmogr.sample.images.downloader.connection.handler.InputStreamResponseHandler;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestFileActivity extends AppCompatActivity {

    private Request request;

    private Connector connector = new Connector() {
        @Override
        public void completedSuccessfully(int invokedBy, Object response) {
            if (response instanceof byte[]) {
                InputStreamResponseHandler isrh = new InputStreamResponseHandler(getApplication());
                String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                boolean saved = isrh.saveFile((InputStreamRequest) request, (byte[]) response, savePath);
                if (saved) {
                    ((TextView) findViewById(R.id.responseFileRequest)).setText(getString(R.string.saved_in_downloads));
                }
                else {
                    ((TextView) findViewById(R.id.responseFileRequest)).setText(getString(R.string.failed_to_save_file));
                }
            }
            hideFloatingButton();
        }
        @Override
        public void completedWithError(String error) {
            ((TextView) findViewById(R.id.responseFileRequest)).setText(error);
            hideFloatingButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_file);
        connector.init(getCacheDir(), getWindow().getDecorView().getRootView());
    }

    private void showFloatingButton() {
        findViewById(R.id.cancelFileRequest).setVisibility(View.VISIBLE);
    }

    private void hideFloatingButton() {
        findViewById(R.id.cancelFileRequest).setVisibility(View.GONE);
    }

    private String getUrl() {
        EditText editText = findViewById(R.id.urlFileRequest);
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
        request = connector.addGetRequestOfFile(url, null);
        showFloatingButton();
    }

    public void post(View view) {
        String url = getUrl();
        if (url == null) return;
        request = connector.addPostRequestOfFile(url, null);
        showFloatingButton();
    }

    public void cancelRequest(View view) {
        if (request == null) return;
        request.cancel();
        request = null;
        hideFloatingButton();
    }
}
