package com.example.shubham.eventbus_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shubh on 2/23/2017.
 */

public class Constants {

    public static class call_db extends AsyncTask<Void, Integer, Void> {
        String Response = "";
        String from = "";
        Context context;

        public call_db(Context context, String from){
            this.context = context;
            this.from = from;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {

        }

        @SuppressLint("NewApi")
        @Override
        protected Void doInBackground(Void... params1) {
            Response = performPostCall_Old(context);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Events.AppConstants appConstants = new Events.AppConstants(Response,from);

            GlobalBus.getBus().post(appConstants);
        }
    }

    public static String performPostCall_Old(final Context context) {
        String requestURL;
        URL url;
        String response = "", result;
        OutputStream os;
        requestURL = context.getResources().getString(R.string.request_url);
        Log.d("srr",requestURL);

        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();
            result = urlResultCode(responseCode);
            Log.d("srr",result);

            if (result.equals("ok")) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                    Log.d("srr",response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();

        }
        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            if (entry.getValue() != null) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }
        return result.toString();
    }

    public static String urlResultCode(int code) {
        String result = "";
        try {
            switch (code) {
                case HttpURLConnection.HTTP_OK:
                    result = "ok";
                    break; // fine, go on
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    result = "http_gateway_timeout";
                    break;// retry
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                    result = "http_client_timeout";
                    break;// retry
                case HttpURLConnection.HTTP_BAD_GATEWAY:
                    result = "http_bad_gateway";
                    break;// retry
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    result = "http_internal_error";
                    break;// retry, server is unstable
                case HttpURLConnection.HTTP_REQ_TOO_LONG:
                    result = "http_req_too_long";
                    break;// retry, server is unstable
                default:
                    result = "";
                    break; // abort
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static boolean responseCheck(String res) {

        boolean check = false;
        if (res == null || res.equals("") || res.equals("http_gateway_timeout") ||
                res.equals("http_client_timeout") ||
                res.equals("http_bad_gateway") ||
                res.equals("http_internal_error") ||
                res.equals("http_req_too_long") ||
                res.contains("failed to connect to www.cartradeexchange.com") ||
                res.contains("failed to connect to www.cartradeexchange.com") ||
                res.contains("Unable to resolve host")) {
            check = true;
        }
        return check;
    }

}
