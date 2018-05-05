package org.inmogr.sample.images.downloader.connection;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author INMOGR
 * @version 2:0:180503
 *
 * Volley Connector Ext
 * Is an extension to Volley Connector to perform http/https connection and download
 */

@SuppressWarnings("ALL")
public abstract class Connector extends VolleyConnector {

    public StringRequest addGetRequestOfString(String url, final Map<String, String> params) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        completedSuccessfully(RESPONSE_STRING, response);
                    }
                },
                new ResponseErrorListener()
        ){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public StringRequest addPostRequestOfString(String url, final JSONObject requestBody) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        completedSuccessfully(RESPONSE_STRING, response);
                    }
                },
                new ResponseErrorListener()
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.toString().getBytes();
                } catch (Exception e) {
                    return null;
                }
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public JsonObjectRequest addGetRequestOfJsonObject(String url, JSONObject params) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        completedSuccessfully(RESPONSE_JSON_OBJECT, response);
                    }
                },
                new ResponseErrorListener()
        );
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public JsonObjectRequest addPostRequestOfJsonObject(String url, final JSONObject requestBody) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        completedSuccessfully(RESPONSE_JSON_OBJECT, response);
                    }
                },
                new ResponseErrorListener()
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.toString().getBytes();
                } catch (Exception e) {
                    return null;
                }
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public JsonArrayRequest addGetRequestOfJsonArray(String url, JSONArray params) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        completedSuccessfully(RESPONSE_JSON_ARRAY, response);
                    }
                },
                new ResponseErrorListener()
        );
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public JsonArrayRequest addPostRequestOfJsonArray(String url, final JSONObject requestBody) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        completedSuccessfully(RESPONSE_JSON_ARRAY, response);
                    }
                },
                new ResponseErrorListener()
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.toString().getBytes();
                } catch (Exception e) {
                    return null;
                }
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public ImageRequest addGetRequestOfImage(String url, final Map<String,String> params, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig) {
        return addGetRequestOfImage(url, params, 0, 0, scaleType, decodeConfig);
    }

    public ImageRequest addGetRequestOfImage(String url, final Map<String,String> params, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        completedSuccessfully(RESPONSE_IMAGE, response);
                    }
                },
                maxWidth,
                maxHeight,
                scaleType,
                decodeConfig,
                new ResponseErrorListener()
        ){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public ImageRequest addPostRequestOfImage(String url, final JSONObject params, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig) {
        return addPostRequestOfImage(url, params, 0, 0, scaleType, decodeConfig);
    }

    public ImageRequest addPostRequestOfImage(String url, final JSONObject requestBody, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        completedSuccessfully(RESPONSE_IMAGE, response);
                    }
                },
                maxWidth,
                maxHeight,
                scaleType,
                decodeConfig,
                new ResponseErrorListener()
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.toString().getBytes();
                } catch (Exception e) {
                    return null;
                }
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public InputStreamRequest addGetRequestOfFile(String url, final Map<String, String> params) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        InputStreamRequest request = new InputStreamRequest(Request.Method.GET, url,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        completedSuccessfully(RESPONSE_STRING, response);
                    }
                },
                new ResponseErrorListener()
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

    public InputStreamRequest addPostRequestOfFile(String url, final JSONObject requestBody) {
        //if the queue not yet started or the queue had been stopped
        //make sure to not to send the request so you avoid errors ;)
        if (requestQueue == null) return null;
        InputStreamRequest request = new InputStreamRequest(Request.Method.POST, url,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        completedSuccessfully(RESPONSE_STRING, response);
                    }
                },
                new ResponseErrorListener()
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.toString().getBytes();
                } catch (Exception e) {
                    return null;
                }
            }
        };
        request.setShouldCache(true);
        requestQueue.add(request);
        return request;
    }

}
