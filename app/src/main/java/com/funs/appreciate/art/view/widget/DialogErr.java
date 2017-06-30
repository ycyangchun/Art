package com.funs.appreciate.art.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.funs.appreciate.art.R;


public class DialogErr extends Dialog {

    TextView dialogContextTv;

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
        dialogContextTv.setText(content);
    }

}
