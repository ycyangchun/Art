package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.ArtConfig;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.ArtConstants;
import com.funs.appreciate.art.base.BaseFragment;
import com.funs.appreciate.art.di.components.DaggerMainComponent;
import com.funs.appreciate.art.di.components.DaggerRecommendFragmentComponent;
import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.presenter.MainContract;
import com.funs.appreciate.art.presenter.MainPresenter;
import com.funs.appreciate.art.utils.MsgHelper;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/15.
 *  今日推荐
 */

public class RecommendFragment extends BaseFragment implements  PictureFocusRelative.PictureFocusKeyEvent ,MainContract.View{
    List<PictureModel> lms = new ArrayList<>();
    View view;
    FragmentActivity mainActivity;
    String columnId;

    @Inject
    MainPresenter mainPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = getActivity();
        view = inflater.inflate(R.layout.fragment_recommend,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaggerRecommendFragmentComponent.builder()
                .netComponent(ArtApp.get(mainActivity).getNetComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);

        mainPresenter.loadLayout("getLayoutAndContent",columnId);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        columnId = args.getString("columnId");
//        System.out.println("===========setArguments============>"+columnId);
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadLayoutSuccess(String lay) {
        if(lay != null) {
            LayoutModel lm = new Gson().fromJson(lay, LayoutModel.class);
            List<LayoutModel.LayoutBean> list =  lm.getLayout();
            if( list != null) {
                for (int i = 0; i < list.size(); i++) {
                    LayoutModel.LayoutBean lb = list.get(i);
                    PictureModel pm = new PictureModel(lb, ArtConfig.getMainActivity());
                    lms.add(pm);
                }
                fr = (PictureFocusRelative) view.findViewById(R.id.focus_relative);
                fr.addViews(lms);
                fr.setAnimation(R.anim.scale_small, R.anim.scale_big);
                fr.setmPictureFocusKeyEvent(this);
            }
        }
    }

    @Override
    public void loadLayoutFailed(Throwable throwable) {

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
            List<LayoutModel.LayoutBean.ContentBean> contents = lm.getContentBean();
            if(contents != null) {
                LayoutModel.LayoutBean.ContentBean contentBean = contents.get(0);
                String type = contentBean.getType();
                if("0".equals(type) || "1".equals(type)){//图片 or 视频
                    Intent intent = new Intent(mainActivity, DetailActivity.class);
                    intent.putExtra("contentId",contentBean.getContentid());
                    intent.putExtra("type",type);
                    startActivity(intent);
                } else if("2".equals(type)){//专题
                    Intent intent = new Intent(mainActivity, SpecialActivity.class);
                    intent.putExtra("contentId",contentBean.getContentid());
                    startActivity(intent);
                }
            }
        }
    }

    /////////// PictureFocusKeyEvent ↑↑↑↑↑↑
}
