package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddStudentRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_AddStudent.php";
    private final Map<String, String> map;

    public AddStudentRequest(
            String studentName,
            String studentDOB,
            String studentClass,
            String studentCountry,
            String studentID,
            String studentPassword,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("studentName", studentName);
        map.put("studentDOB", studentDOB);
        map.put("studentClass", studentClass);
        map.put("studentCountry", studentCountry);
        map.put("studentID", studentID);
        map.put("studentPassword", studentPassword);
        map.put("studentIntroduce", "Hi, my name is " + studentName);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
