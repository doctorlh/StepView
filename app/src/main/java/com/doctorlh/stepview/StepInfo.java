package com.doctorlh.stepview;

import android.support.annotation.DrawableRes;

/**
 * Created by Administrator on 2018/5/29.
 */

public class StepInfo {
    public String des;

    @DrawableRes
    public int drawableId;
    @DrawableRes
    public int disDrawableId;

    public StepInfo(String des, int drawableId, int disDrawableId) {
        this.des = des;
        this.drawableId = drawableId;
        this.disDrawableId = disDrawableId;
    }
}
