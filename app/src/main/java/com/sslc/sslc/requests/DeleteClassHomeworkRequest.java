package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteClassHomeworkRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_DeleteClassHomework.php";
    private final Map<String, String> map;

    public DeleteClassHomeworkRequest(String homeworkTitle, String className, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("homeworkTitle", homeworkTitle);
        map.put("className", className);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
