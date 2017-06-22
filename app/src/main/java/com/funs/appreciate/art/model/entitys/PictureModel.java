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
    private String typeRrc;
    private String leftdistance;
    private int leftid ;
    private String topdistance;
    private Object colorid;
    private String columnid;
    private int topid ;

    public RelativeLayout getFocusView() {
        return mFocusView;
    }

    public void setFocusView(RelativeLayout mFocusView) {
        this.mFocusView = mFocusView;
    }

    public PictureModel(LayoutModel.LayoutBean lb, Context mContext) {
        this.id = Integer.parseInt(lb.getId()) * 1000 ;
        this.width = Integer.parseInt(lb.getWidth());
        this.height = Integer.parseInt(lb.getHeight());
        Object ot = lb.getTopid();
        Object ol = lb.getLeftid();
        int it  = 0, il = 0;
        if(!ot.equals(null) && ot instanceof  String)  it = Integer.parseInt((String) ot);
        if(!ol.equals(null) && ol instanceof  String)  il = Integer.parseInt((String) ol);
        this.topid = it * 1000;
        this.leftid = il * 1000;
        // 添加root
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setId(this.id);
        rl.setFocusable(true);
        setFocusView(rl);
    }

    public PictureModel(int id, int width, int height, int toBelowId, int toRightId , Context mContext) {
        this.id = id * 1000 ;
        this.width = width;
        this.height = height;
        this.topid = toBelowId * 1000;
        this.leftid = toRightId * 1000;
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
        this.leftid = toRightId * 100;
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

    public int getTopid() {
        return topid;
    }

    public void setTopid(int topid) {
        this.topid = topid;
    }

    public int getLeftid() {
        return leftid;
    }

    public void setLeftid(int leftid) {
        this.leftid = leftid;
    }

    public String getTypeRrc() {
        return typeRrc;
    }

    public void setTypeRrc(String typeRrc) {
        this.typeRrc = typeRrc;
    }
}
