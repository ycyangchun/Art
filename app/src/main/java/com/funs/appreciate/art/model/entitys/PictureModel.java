package com.funs.appreciate.art.model.entitys;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by yc on 2017/6/12.
 *  数据类型转化
 */

public class PictureModel {
    private RelativeLayout mRootView = null;
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
    private int margin = -16;
    private String isbottom;

    private List<LayoutModel.LayoutBean.ContentBean> contentBean;

    public RelativeLayout getRootView() {
        return mRootView;
    }

    public void setRootView(RelativeLayout mRootView) {
        this.mRootView = mRootView;
    }

    public PictureModel(LayoutModel.LayoutBean lb, Context mContext) {
        this.id = Integer.parseInt(lb.getId())  ;
        this.width = Integer.parseInt(lb.getWidth());
        this.height = Integer.parseInt(lb.getHeight());
        Object ot = lb.getTopid();
        Object ol = lb.getLeftid();
        int it  = 0, il = 0;
        if(ot != null && ot instanceof  String)  it = Integer.parseInt((String) ot);
        if(ol != null && ol instanceof  String)  il = Integer.parseInt((String) ol);
        this.topid = it ;
        this.leftid = il ;
        this.isbottom = lb.getIsbottom();
        this.contentBean = lb.getContent();
        // 添加root
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setId(this.id);
        rl.setFocusable(true);
        setRootView(rl);
    }

    public PictureModel(LayoutModel.LayoutBean lb, Context mContext , int index) {
        this.id = (index + 1) * 300 ;
        this.width = Integer.parseInt(lb.getWidth());
        this.height = 50;
        this.topid = 0 ;
        this.leftid = index * 300 ;
        this.contentBean = lb.getContent();
        this.margin = 6;
        // 添加root
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setId(this.id);
        rl.setFocusable(false);
        setRootView(rl);
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
        setRootView(rl);
    }

    public PictureModel(int id, int width, int height, String typeRrc, int toRightId ,List<LayoutModel.LayoutBean.ContentBean>cb, Context mContext) {
        this.id = id * 200 ;
        this.width = width;
        this.height = height;
        this.typeRrc = typeRrc;
        this.leftid = toRightId * 200;
        this.contentBean = cb;
        this.margin = -16;
        // 添加root
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setId(id);
        rl.setFocusable(true);
        setRootView(rl);
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
        setRootView(rl);
    }

    public String getIsbottom() {
        return isbottom;
    }

    public void setIsbottom(String isbottom) {
        this.isbottom = isbottom;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
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

    public List<LayoutModel.LayoutBean.ContentBean> getContentBean() {
        return contentBean;
    }
}
