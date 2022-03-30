package com.example.sslc.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.adapters.TeacherClassDialogAdapter;
import com.example.sslc.interfaces.ApplyClassListListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherClassesDialog {

    private static final String TAG = "TeacherClassDialog";

    private final Context context;
    private TeacherClassDialogAdapter adapter;
    private final ArrayList<String> classList;
    Dialog dialog;

    ApplyClassListListener listener;

    public TeacherClassesDialog(Context context, ArrayList<String> classList) {
        this.context = context;
        this.classList = classList;
    }

    public void callDialog() {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.teacher_class_dialog);
        dialog.show();
        listener = (ApplyClassListListener) context;

        RecyclerView rv_ClassList = dialog.findViewById(R.id.rv_Class);
        rv_ClassList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TeacherClassDialogAdapter(
                context,
                classList
        );
        rv_ClassList.setAdapter(adapter);

        ButterKnife.bind(this, dialog);

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_OK)
    public void onBtnOKClicked() {

        ArrayList<String> arrayList = adapter.getAddedClassList();
        for (int i = 0; i < arrayList.size(); i++) {

            Log.i(TAG, arrayList.get(i));
        }
        listener.applyClassList(arrayList);
        dialog.dismiss();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_Cancel)
    public void onBtnCancelClicked() {

        dialog.dismiss();
    }
}
