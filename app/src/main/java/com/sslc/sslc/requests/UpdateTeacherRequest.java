package com.sslc.sslc.requests;

import android.util.Log;

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
            int hasProfileImage,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("teacherID", String.valueOf(teacherID));
        map.put("teacherName", teacherName);
        map.put("teacherDOB", teacherDOB);
        map.put("teacherClass", teacherClass);
        map.put("teacherIntroduce", teacherIntroduce);
        Log.i("UpdateTeacherRequest", String.valueOf(hasProfileImage));
        map.put("hasProfileImage", String.valueOf(hasProfileImage));
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
