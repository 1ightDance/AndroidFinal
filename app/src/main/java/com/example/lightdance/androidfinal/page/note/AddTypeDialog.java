package com.example.lightdance.androidfinal.page.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Classify;
import com.example.lightdance.androidfinal.dao.ClassifyCurd;

/**
 * file description
 *
 * @author LightDance
 * @date 2018/12/17.
 */
public class AddTypeDialog extends DialogFragment {
    private static final String ARG_DATE = "DATE";
    public static final String EXTRA_DATE = "CRIMINAL_INTENT_DATE";

    public static AddTypeDialog newInstance() {
        AddTypeDialog fragment = new AddTypeDialog();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_new_type, null);
        final EditText editText = v.findViewById(R.id.et_add_new_type);
        final TextView textView = v.findViewById(R.id.tv_new_type_warning);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("新增类别")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String typename = editText.getText().toString();
                        if ("".equals(typename.trim())){
                            ClassifyCurd classifyCurd = new ClassifyCurd(getActivity());
                            Classify classify = Classify.builder()
                                    .id((int)(Math.random()*100))
                                    .classifyName(typename)
                                    .build();
                            classifyCurd.createClassify(classify);

                            sendResult(Activity.RESULT_OK);
                        }else {
                            textView.setVisibility(View.VISIBLE);
                            sendResult(Activity.RESULT_CANCELED);
                        }
                    }
                }).create();
        return dialog;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
