package com.sslc.sslc.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetStudentRequest extends StringRequest {

    private final static String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_GetStudent.php";
    private final Map<String, String> map;

    public GetStudentRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
