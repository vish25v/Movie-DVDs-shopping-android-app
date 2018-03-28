package com.example.vish.homestore;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by VISH on 3/19/2018.
 */

public class VerticalSpace extends RecyclerView.ItemDecoration {
    int space;

    public VerticalSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = space;
        outRect.right =  space;
        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view)==0 || parent.getChildLayoutPosition(view)==1) {
            outRect.top = space;
        }

    }


}
