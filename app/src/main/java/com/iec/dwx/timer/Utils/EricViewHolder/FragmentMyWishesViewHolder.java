package com.iec.dwx.timer.Utils.EricViewHolder;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class FragmentMyWishesViewHolder {
    private final String TAG = FragmentMyWishesViewHolder.class.getSimpleName();
    private ImageView mImg=null;
    private ImageSwitcher imageSwitcher=null;
    private TextView mTv=null;
    private Boolean flag=null;

    private ImageButton intoOthersBtn=null;
    private ImageButton addImgBtn=null;
    private ImageButton deleteImgBtn=null;
    private ImageButton shareBtn=null;


    public FragmentMyWishesViewHolder(){
        flag=true;
    }


    //get
    public Boolean getFlag(){
        return flag;
    }


    public ImageView getmImg() {
        return mImg;
    }

    public ImageSwitcher getImageSwitcher(){
        return imageSwitcher;
    }

    public TextView getmTv() {
        return mTv;
    }


    public ImageButton getAddImgBtn(){
        return addImgBtn;
    }

    public ImageButton getDeleteImgBtn(){
        return deleteImgBtn;
    }

    public ImageButton getIntoOthersBtn(){
        return intoOthersBtn;
    }

    public ImageButton getShareBtn(){
        return shareBtn;
    }

    //set

    public void setImageSwitcher(ImageSwitcher imageSwitcher){
        this.imageSwitcher=imageSwitcher;
    }

    public void setmImg(ImageView mImg) {
        this.mImg = mImg;
    }

    public void setmTv(TextView mTv) {
        this.mTv = mTv;
    }

    public void changeFlag(Boolean flag){
        flag=this.flag;
    }


    public void setAddImgBtn(ImageButton btn){
        this.addImgBtn=btn;
    }

    public void setDeleteImgBtn(ImageButton btn){
        this.deleteImgBtn=btn;
    }

    public void setIntoOthersBtn(ImageButton btn){
        this.intoOthersBtn=btn;
    }

    public void setShareBtn(ImageButton btn){
        this.shareBtn=btn;
    }
}
