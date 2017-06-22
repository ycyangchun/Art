package com.funs.appreciate.art.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.funs.appreciate.art.ArtConfig;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.ArtConstants;
import com.funs.appreciate.art.base.BaseFragment;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.utils.MsgHelper;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/6/15.
 *  今日推荐
 */

public class RecommendFragment extends BaseFragment implements  PictureFocusRelative.PictureFocusKeyEvent{
    List<PictureModel> lms = new ArrayList<>();
    View view;
    FragmentActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = getActivity();
        view = inflater.inflate(R.layout.fragment_recommend,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        intViewData();
        fr = (PictureFocusRelative) view.findViewById(R.id.focus_relative);
        fr.addViews(lms);
        fr.setAnimation(R.anim.scale_small, R.anim.scale_big);
        fr.setmPictureFocusKeyEvent(this);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        String lays = args.getString("layout");
        LayoutModel lm = new Gson().fromJson(lays, LayoutModel.class);
        List<LayoutModel.LayoutBean> list =  lm.getLayout();
        for(int i = 0 ; i < list.size() ; i ++){
            LayoutModel.LayoutBean lb = list.get(i);
            PictureModel pm  = new PictureModel(lb, ArtConfig.getMainActivity());
            lms.add(pm);
        }
    }

    private void intViewData() {

        PictureModel lm1  = new PictureModel(1,560,484,0,0,mainActivity);
        PictureModel lm2  = new PictureModel(2,560,200,1,0,mainActivity);
        PictureModel lm3  = new PictureModel(3,270,484,0,1,mainActivity);
        PictureModel lm4  = new PictureModel(4,560,200,3,1,mainActivity);
        PictureModel lm5  = new PictureModel(5,270,484,0,3,mainActivity);
        PictureModel lm6  = new PictureModel(6,270,484,0,5,mainActivity);
        PictureModel lm7  = new PictureModel(7,560,200,6,5,mainActivity);
        PictureModel lm8  = new PictureModel(8,270,484,0,6,mainActivity);
        PictureModel lm9  = new PictureModel(9,270,484,0,8,mainActivity);
        PictureModel lm10  = new PictureModel(10,560,200,9,8,mainActivity);
        PictureModel lm11  = new PictureModel(11,270,484,0,9,mainActivity);
        PictureModel lm12  = new PictureModel(12,270,484,0,11,mainActivity);
        PictureModel lm13  = new PictureModel(13,560,200,12,11,mainActivity);
        PictureModel lm14  = new PictureModel(14,270,484,0,12,mainActivity);

        lms.add(lm1);
        lms.add(lm2);
        lms.add(lm3);
        lms.add(lm4);
        lms.add(lm5);
        lms.add(lm6);
        lms.add(lm7);
        lms.add(lm8);
        lms.add(lm9);
        lms.add(lm10);
        lms.add(lm11);
        lms.add(lm12);
        lms.add(lm13);
        lms.add(lm14);

    }

    /////////// PictureFocusKeyEvent ↓↓↓↓↓↓

    @Override
    public void pictureListener(String keyType, PictureModel lm , RelativeLayout rl) {

        if("top".equals(keyType)) {
            MsgHelper.sendMessage(null, ArtConstants.KEYTOP);
        } else if("left".equals(keyType)) {
            MsgHelper.sendMessage(null, ArtConstants.KEYLEFT);
         } else if("right".equals(keyType)) {
            MsgHelper.sendMessage(null, ArtConstants.KEYRIGHT  , rl);
         } else if("center".equals(keyType)) {
            System.out.println("======================  center ==>"+keyType);
        }
    }

    /////////// PictureFocusKeyEvent ↑↑↑↑↑↑
}
