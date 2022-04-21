package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteClassNewsRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_DeleteClassNews.php";
    private final Map<String, String> map;

    public DeleteClassNewsRequest(String newsTitle, String className, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("newsTitle", newsTitle);
        map.put("className", className);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
