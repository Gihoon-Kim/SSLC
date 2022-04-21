package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UploadImageRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UploadImage.php";
    private final Map<String, String> map;

    public UploadImageRequest(String userName, boolean isTeacher, String imageData, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userName", userName);
        map.put("isTeacher", String.valueOf(isTeacher));
        map.put("imageData", imageData);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
