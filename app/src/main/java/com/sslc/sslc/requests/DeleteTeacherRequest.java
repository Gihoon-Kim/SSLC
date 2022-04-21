package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteTeacherRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_DeleteTeacher.php";
    private final Map<String, String> map;

    public DeleteTeacherRequest(int teacherID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("teacherNumber", String.valueOf(teacherID));
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
