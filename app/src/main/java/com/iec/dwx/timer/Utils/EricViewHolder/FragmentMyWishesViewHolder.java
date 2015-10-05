package com.iec.dwx.timer.Utils.EricViewHolder;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class FragmentMyWishesViewHolder {
    private final String TAG = FragmentMyWishesViewHolder.class.getSimpleName();
    private ImageView mImg=null;
    private TextView mTv=null;
    private Boolean flag=null;

    private Button intoPublicButton=null;
    private ImageButton addImgBtn=null;
    private ImageButton deleteImgBtn=null;


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

    public TextView getmTv() {
        return mTv;
    }

    public Button getIntoPublicButton(){
        return intoPublicButton;
    }

    public ImageButton getAddImgBtn(){
        return addImgBtn;
    }

    public ImageButton getDeleteImgBtn(){
        return deleteImgBtn;
    }

    //set
    public void setmImg(ImageView mImg) {
        this.mImg = mImg;
    }

    public void setmTv(TextView mTv) {
        this.mTv = mTv;
    }

    public void changeFlag(Boolean flag){
        flag=this.flag;
    }

    public void setIntoPublicButton(Button btn){
        this.intoPublicButton=btn;
    }

    public void setAddImgBtn(ImageButton btn){
        this.addImgBtn=btn;
    }

    public void setDeleteImgBtn(ImageButton btn){
        this.addImgBtn=btn;
    }
}
