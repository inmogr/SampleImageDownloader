package org.inmogr.sample.images.downloader.connection;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * @author INMOGR
 * @version 2:0:180503
 *
 * Volley Request Extension
 * Is an extension to Volley Request to support any file download
 */

@SuppressWarnings("All")
public class InputStreamRequest extends Request<byte[]> {

    private final Response.Listener<byte[]> responseListener;
    private Map<String, String> responseHeaders;

    public InputStreamRequest(int method, String url, Response.Listener<byte[]> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.responseListener = responseListener;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        responseListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;
        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}