package com.funs.appreciate.art.model.entitys;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by yc on 2017/6/12.
 */

public class PictureModel {
    private RelativeLayout mFocusView = null;
    private int id;
    private int width;
    private int height;
    private int toBelowId = 0;
    private int toRightId = 0;
    private String typeRrc;

    public RelativeLayout getFocusView() {
        return mFocusView;
    }

    public void setFocusView(RelativeLayout mFocusView) {
        this.mFocusView = mFocusView;
    }

    public PictureModel(int id, int width, int height, int toBelowId, int toRightId , Context mContext) {
        this.id = id * 1000 ;
        this.width = width;
        this.height = height;
        this.toBelowId = toBelowId * 1000;
        this.toRightId = toRightId * 1000;
        // 添加root
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setId(id);
        rl.setFocusable(true);
        setFocusView(rl);
    }

    public PictureModel(int id, int width, int height, String typeRrc,int toRightId,  Context mContext) {
        this.id = id * 100;
        this.width = width;
        this.height = height;
        this.toRightId = toRightId * 100;
        this.typeRrc = typeRrc;
        // 添加root
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setId(id);
        rl.setFocusable(true);
        setFocusView(rl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getToBelowId() {
        return toBelowId;
    }

    public void setToBelowId(int toBelowId) {
        this.toBelowId = toBelowId;
    }

    public int getToRightId() {
        return toRightId;
    }

    public void setToRightId(int toRightId) {
        this.toRightId = toRightId;
    }

    public String getTypeRrc() {
        return typeRrc;
    }

    public void setTypeRrc(String typeRrc) {
        this.typeRrc = typeRrc;
    }
}
