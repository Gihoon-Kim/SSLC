package com.sslc.sslc.requests;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateNewsRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateNews.php";
    private final Map<String, String> map;

    public UpdateNewsRequest(int newsID, String newsTitle, String newsDescription, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("newsID", String.valueOf(newsID));
        map.put("newsTitle", newsTitle);
        map.put("newsDescription", newsDescription);

        // Get current date with format dd/MM/yyyy
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String newsCreatedAt = dateFormat.format(date);
        map.put("newsCreatedAt", newsCreatedAt);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
