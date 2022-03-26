package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteStudentRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_DeleteStudent.php";
    private final Map<String, String> map;

    public DeleteStudentRequest(int studentNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("studentNumber", String.valueOf(studentNumber));
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
