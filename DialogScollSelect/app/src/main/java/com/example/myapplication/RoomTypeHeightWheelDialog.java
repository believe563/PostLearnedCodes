package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class RoomTypeHeightWheelDialog {
    private Context context;

    private HeightWheelDialogcallback dialogcallback;
    private Dialog dialog;
    private TextView mTv_entry, mTv_cancel;
    private RoomTypeHeightPicker mHeightPicker;
    private String mHeight = "民宿";
    RoomTypeHeightPicker.OnChangeListener onchanghelistener;

    /**
     * init the dialog
     *
     * @return
     */
    public RoomTypeHeightWheelDialog(Context con, WindowManager m) {
        this.context = con;
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_height_wheel);
        mTv_entry = (TextView) dialog.findViewById(R.id.dialog_height_entry);
        mTv_cancel = (TextView) dialog.findViewById(R.id.dialog_height_cancel);
        mHeightPicker = (RoomTypeHeightPicker) dialog.findViewById(R.id.RoomTypeHeightPicker);

        //对话框中滑动监听
        onchanghelistener = new RoomTypeHeightPicker.OnChangeListener() {
            @Override
            public void onChange(String height) {
                mHeight = height;
            }
        };
        mHeightPicker.setOnChangeListener(onchanghelistener);

        //设置对话框的位置及大小
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogAnimationStyle);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth());
        dialogWindow.setAttributes(p);

        //确定
        mTv_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogcallback.dialogdo(mHeight, dialog);
                dismiss();
            }
        });
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    //设置回调接口，使对话框选中的值可传给Activity
    public interface HeightWheelDialogcallback {
        public void dialogdo(String string, Dialog dialog);
    }

    public void setDialogCallback(HeightWheelDialogcallback dialogcallback) {
        this.dialogcallback = dialogcallback;
    }

    /**
     * @category Set The Content of the TextView
     */

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setCanceledOnTouchOutside() {
        dialog.setCanceledOnTouchOutside(true);
    }


} 
