package com.elvis.magicdevice.MainScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EditDialog extends AlertDialog implements DialogInterface.OnClickListener{


    private String text = "";
    private EditText edit;
    private OnDataSetListener mCallback;
    private LinearLayout layout;
    /**
     *构造一个回调接口
     */
    public interface OnDataSetListener{

        void onDataSet(String text);

    }
    public EditDialog(Context context,String title,String value,OnDataSetListener callback) {

        super(context);
        mCallback = callback;
        TextView label = new TextView(context);
        label.setText("hint");
        edit = new EditText(context);
        edit.setText(value);
        edit.setPadding(30, 0, 0, 0);
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(200,50);
        param2.leftMargin=30;
        layout.addView(edit, param2);
        setView(layout);
        setTitle(title);
        setButton("确认", this);
        setButton2("取消", (OnClickListener) null);

    }

    public void onClick(DialogInterface dialog, int which) {

        text = edit.getText().toString();
        Log.e("elvisding","text="+text);
        if (mCallback != null)
            mCallback.onDataSet(text);


    }


}