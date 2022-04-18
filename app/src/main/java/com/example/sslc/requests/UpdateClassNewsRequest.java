package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateClassNewsRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateClassNews.php";
    private final Map<String, String> map;

    public UpdateClassNewsRequest(String newsOldTitle, String newsNewTitle, String newsDescription, String className, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("newsOldTitle", newsOldTitle);
        map.put("newsNewTitle", newsNewTitle);
        map.put("newsDescription", newsDescription);
        map.put("className", className);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
