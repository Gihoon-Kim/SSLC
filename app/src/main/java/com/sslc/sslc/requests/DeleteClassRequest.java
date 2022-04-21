package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteClassRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_DeleteClass.php";
    private final Map<String, String> map;

    public DeleteClassRequest(
            int classNumber,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("classNumber", String.valueOf(classNumber));
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
