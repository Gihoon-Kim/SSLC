package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteNewsRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_DeleteNews.php";
    private final Map<String, String> map;

    public DeleteNewsRequest(int newsID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("newsID", String.valueOf(newsID));
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
