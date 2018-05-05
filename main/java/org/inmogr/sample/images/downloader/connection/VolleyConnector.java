package org.inmogr.sample.images.downloader.connection;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.inmogr.sample.images.downloader.R;

import java.io.File;

/**
 * @author INMOGR
 * @version 2:0:180503
 *
 * Volley Connector
 * Is an extension to Volley Library to provide an abstraction for http/https connection and download support
 */

@SuppressWarnings("ALL")
public abstract class VolleyConnector {

    public final static int RESPONSE_STRING = 0;
    public final static int RESPONSE_JSON_OBJECT = 1;
    public final static int RESPONSE_JSON_ARRAY = 2;
    public final static int RESPONSE_IMAGE = 3;
    public final static int RESPONSE_FILE = 4;

    protected RequestQueue requestQueue;
    protected View view;

    /**
     * Initiate the connector by setting up the cache directory.
     *          Assumes a default cache size of 10 MB
     * @param cacheDir, which is the desired cache directory.
     * @param view, is required to run Snackbar on any error.
     */
    public void init(File cacheDir, View view) {
        init(cacheDir, DEFAULT_CACHE_SIZE, view);
    }

    /**
     * Initiate the connector by setting up the cache directory.
     *
     * @param cacheDir, is the desired cache directory.
     * @param fileSize, is the required cache size for the files to be downloaded
     * @param view, is required to run Snackbar on any error.
     */
    public void init(File cacheDir, int fileSize, View view) {
        this.view = view;
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(new DiskBasedCache(cacheDir, fileSize), network);
        requestQueue.start();
    }

    public void stop() {
        requestQueue.stop();
        requestQueue = null;
    }

    private final static int DEFAULT_CACHE_SIZE = 1024 * 10;

    /**
     * This is method will be fired upon successful response with two params
     * @param invokedBy, determines which method had invoked this method
     *                   RESPONSE_STRING = 0
     *                   RESPONSE_JSON_OBJECT = 1
     *                   RESPONSE_JSON_ARRAY = 2
     *                   RESPONSE_IMAGE = 3
     *                   RESPONSE_FILE = 4
     * @param response, is the response of the request with a format based on the
     *                  source of invocation.
     *                  For example, the response is JsonObject if (invokedBy == RESPONSE_JSON_OBJECT)
     **/
    public abstract void completedSuccessfully(int invokedBy, Object response);

    /**
     * This is method will be fired upon an error during the request with one param
     * @param error, is the error of the request with a formatted as String by default.
     **/
    public abstract void completedWithError(String error);

    class ResponseErrorListener implements Response.ErrorListener {

        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         *
         * @param error, is the reason why the request failed
         */
        @Override
        public void onErrorResponse(VolleyError error) {
            if (view == null) {}
            else if (error instanceof NoConnectionError) {
                Snackbar.make(view, R.string.error_not_connected_to_the_internet, Snackbar.LENGTH_SHORT);
            } else if (error instanceof TimeoutError) {
                Snackbar.make(view, R.string.error_network_request_timeout, Snackbar.LENGTH_SHORT);
            } else if (error instanceof AuthFailureError) {
                Snackbar.make(view, R.string.error_not_authenticated, Snackbar.LENGTH_SHORT);
            } else if (error instanceof ServerError) {
                Snackbar.make(view, R.string.error_on_server_side, Snackbar.LENGTH_SHORT);
            } else if (error instanceof NetworkError) {
                Snackbar.make(view, R.string.error_on_network_side, Snackbar.LENGTH_SHORT);
            } else if (error instanceof ParseError) {
                Snackbar.make(view, R.string.error_response_does_not_meet_the_request, Snackbar.LENGTH_SHORT);
            }
            /**
             * No longer in need to pass the error to ui level; however,
             *      invoking #completedWithError REQUIRED to tell the ui to stop any waiting process
             **/
            completedWithError(error.toString());
        }
    }

}