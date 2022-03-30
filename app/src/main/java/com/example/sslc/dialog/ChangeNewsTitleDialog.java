package com.example.sslc.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.sslc.R;
import com.example.sslc.interfaces.ChangeNewsTitleDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeNewsTitleDialog extends AppCompatDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_CurrentNewsTitle)
    TextView tv_CurrentNewsTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_NewTitle)
    EditText et_NewTitle;

    String newsTitle;
    ChangeNewsTitleDialogListener listener;

    public ChangeNewsTitleDialog(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View view = layoutInflater.inflate(
                R.layout.dialog_change_news_title,
                null
        );

        ButterKnife.bind(this, view);
        tv_CurrentNewsTitle.setText(newsTitle);

        builder.setView(view);

        return builder.create();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_UpdateTitle)
    void onTitleUpdateButtonClicked() {

        String newTitle = et_NewTitle.getText().toString();
        listener.applyNewTitle(newTitle);
        dismiss();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_Cancel)
    void onCancelButtonClicked() {

        dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChangeNewsTitleDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
