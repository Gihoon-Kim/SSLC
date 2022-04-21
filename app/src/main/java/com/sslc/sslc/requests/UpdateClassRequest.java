package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateClassRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateClass.php";
    private final Map<String, String> map;

    public UpdateClassRequest(
            int classNumber,
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
        map.put("classNumber", String.valueOf(classNumber));
        map.put("classTitle", classTitle);
        map.put("classTeacher", classTeacher);
        map.put("classDescription", classDescription);
        map.put("classStartTime", classStartTime);
        map.put("classEndTime", classEndTime);
        map.put("classRoom", classRoom);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
