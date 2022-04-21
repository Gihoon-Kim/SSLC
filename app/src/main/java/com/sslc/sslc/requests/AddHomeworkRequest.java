package com.sslc.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddHomeworkRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_AddClassHomework.php";
    private final Map<String, String> map;

    public AddHomeworkRequest(
            String homeworkTitle,
            String homeworkScript,
            String homeworkDeadline,
            String classTitle,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("homeworkTitle", homeworkTitle);
        map.put("homeworkScript", homeworkScript);
        map.put("homeworkDeadline", homeworkDeadline);
        map.put("classTitle", classTitle);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
