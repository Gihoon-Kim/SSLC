package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateStudentClassRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateStudentClass.php";
    private final Map<String, String> map;

    public UpdateStudentClassRequest(
            int studentNumber,
            String studentClass,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("studentNumber", String.valueOf(studentNumber));
        map.put("studentClass", studentClass);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
