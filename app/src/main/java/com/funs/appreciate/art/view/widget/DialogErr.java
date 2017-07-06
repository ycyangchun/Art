package com.funs.appreciate.art.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.R;


public class DialogErr extends Dialog {

    TextView dialogContextTv ;

    RelativeLayout dialog_bnt_tv;

    private String content;
    public DialogErr(Context context, String msg) {
        super(context, R.style.ShareDialog);
        this.content = msg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_err);
        dialogContextTv = (TextView) findViewById(R.id.dialog_context_tv);
        dialog_bnt_tv = (RelativeLayout) findViewById(R.id.dialog_bnt_tv);
        dialogContextTv.setText(content);
        dialog_bnt_tv.requestFocus();
        dialog_bnt_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
