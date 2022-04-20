package com.example.sslc.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateClassHomeworkRequest extends StringRequest {

    final static private String URL = "http://hoonyhosting.dothome.co.kr/Practice/SSLC/php/SSLC_UpdateClassHomework.php";
    private final Map<String, String> map;

    public UpdateClassHomeworkRequest(
            String homeworkOldTitle,
            String homeworkNewTitle,
            String homeworkScript,
            String homeworkDeadline,
            String className,
            Response.Listener<String> listener
    ) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("homeworkOldTitle", homeworkOldTitle);
        map.put("homeworkNewTitle", homeworkNewTitle);
        map.put("homeworkScript", homeworkScript);
        map.put("homeworkDeadline", homeworkDeadline);
        map.put("className", className);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
