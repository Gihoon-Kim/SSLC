package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddTeacherRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_AddTeacher.php";
    private final Map<String, String> map;

    public AddTeacherRequest(
            String teacherName,
            String teacherDOB,
            String teacherClass,
            String teacherID,
            String teacherPassword,
            String teacherImage,
            String teacherIntroduce,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("teacherName", teacherName);
        map.put("teacherDOB", teacherDOB);
        map.put("teacherClass", teacherClass);
        map.put("teacherID", teacherID);
        map.put("teacherPassword", teacherPassword);
        map.put("teacherImage", teacherImage);
        map.put("teacherIntroduce", teacherIntroduce);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
