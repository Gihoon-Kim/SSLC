package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateTeacherRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateTeacher.php";
    private final Map<String, String> map;

    public UpdateTeacherRequest(
            int teacherID,
            String teacherName,
            String teacherClass,
            String teacherIntroduce,
            String teacherDOB,
            String teacherImage,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("teacherID", String.valueOf(teacherID));
        map.put("teacherName", teacherName);
        map.put("teacherDOB", teacherDOB);
        map.put("teacherClass", teacherClass);
        map.put("teacherIntroduce", teacherIntroduce);
        map.put("teacherImage", teacherImage);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
