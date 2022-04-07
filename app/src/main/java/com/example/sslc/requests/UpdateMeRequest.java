package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateMeRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateMe.php";
    private final Map<String, String> map;

    public UpdateMeRequest(
            String userID,
            String userDOB,
            String userIntroduce,
            String userImage,
            String isTeacher,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userDOB", userDOB);
        map.put("userIntroduce", userIntroduce);
        map.put("userImage", userImage);
        map.put("isTeacher", isTeacher);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
