package com.example.lightdance.androidfinal.page.note.type;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Type;
import com.example.lightdance.androidfinal.dao.TypeCurd;

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
        final AlertDialog mDialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("新增类别")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positionButton=mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton=mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String typename = editText.getText().toString();
                        //无类型名，显示提示信息，不做处理
                        if ("".equals(typename.trim())){
                            textView.setVisibility(View.VISIBLE);
                        }else {
                            TypeCurd typeCurd = new TypeCurd(getActivity());
                            Type type = Type.builder()
                                    .id((int)(Math.random()*100))
                                    .typeName(typename)
                                    .build();
                            typeCurd.createType(type);
                            dialog.dismiss();
                            sendResult(Activity.RESULT_OK);
                        }
                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        return mDialog;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
