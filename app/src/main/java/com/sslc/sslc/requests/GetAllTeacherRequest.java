package com.sslc.sslc.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetAllTeacherRequest extends StringRequest {

    private final static String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_GetAllTeacher.php";
    private final Map<String, String> map;

    public GetAllTeacherRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
