package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdatePassword.php";
    private final Map<String, String> map;

    public UpdatePasswordRequest(
            String userID,
            String userPassword,
            String isTeacher,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("isTeacher", isTeacher);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
