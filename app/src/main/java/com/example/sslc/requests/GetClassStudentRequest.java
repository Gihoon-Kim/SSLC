package com.example.sslc.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetClassStudentRequest extends StringRequest {

    private final static String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_GetAllClassStudent.php";
    private final Map<String, String> map;

    public GetClassStudentRequest(String className, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("className", className);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
