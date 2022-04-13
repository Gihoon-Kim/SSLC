package com.example.sslc.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetAllClassNewsRequest extends StringRequest {

    private final static String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_GetAllClassNews.php";
    private final Map<String, String> map;

    public GetAllClassNewsRequest(String classTitle, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("classTitle", classTitle);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
