package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UploadImageRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UploadImage.php";
    private final Map<String, String> map;

    public UploadImageRequest(String userName, String isTeacher, String imageData, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userName", userName);
        map.put("isTeacher", isTeacher);
        map.put("imageData", imageData);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
