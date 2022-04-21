package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddClassRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_AddClass.php";
    private final Map<String, String> map;

    public AddClassRequest(
            String classTitle,
            String classTeacher,
            String classDescription,
            String classStartTime,
            String classEndTime,
            String classRoom,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("classTitle", classTitle);
        map.put("classTeacher", classTeacher);
        map.put("classDescription", classDescription);
        map.put("classStartTime", classStartTime);
        map.put("classEndTime", classEndTime);
        map.put("classRoom", classRoom);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
