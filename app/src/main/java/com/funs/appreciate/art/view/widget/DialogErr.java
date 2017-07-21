package com.funs.appreciate.art.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.R;


public class DialogErr extends Dialog {

    TextView dialogContextTv ;

    RelativeLayout dialog_bnt_tv;

    DialogListener  dialogListener;
    private String content;
    public DialogErr(Context context, String msg) {
        super(context, R.style.ShareDialog);
        this.content = msg;
    }
    public DialogErr(Context context, String msg ,DialogListener listener ) {
        super(context, R.style.ShareDialog);
        this.content = msg;
        this.dialogListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_err);
        dialogContextTv = (TextView) findViewById(R.id.dialog_context_tv);
        dialog_bnt_tv = (RelativeLayout) findViewById(R.id.dialog_bnt_tv);
        dialogContextTv.setText(content);
        dialog_bnt_tv.requestFocus();
        dialog_bnt_tv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog_bnt_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(dialogListener != null){
                    dialogListener.clickDismiss();
                }
            }
        });

    }

    public interface DialogListener {
        void clickDismiss();
    }
}
