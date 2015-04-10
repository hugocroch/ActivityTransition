package com.appcom.explorer.ui.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by hugocrochetiere on 2014-05-28.
 */

public class CircleListView extends ListView {

    private int mItemCount;
    private int mItemOffsetY[];
    private boolean scrollIsComputed = false;
    private int mHeight;


    public CircleListView(Context context) {
        super(context);
    }

    public CircleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getListHeight() {
        return mHeight;
    }

    public void computeScrollY() {
        mHeight = 0;
        mItemCount = getAdapter().getCount();
        if (mItemOffsetY == null) {
            mItemOffsetY = new int[mItemCount];
        }
        for (int i = 0; i < mItemCount; ++i) {
            View view = getAdapter().getView(i, null, this);
            view.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mItemOffsetY[i] = mHeight;
            mHeight += view.getMeasuredHeight();
            System.out.println(mHeight);
        }
        scrollIsComputed = true;
    }

    public boolean scrollYIsComputed() {
        return scrollIsComputed;
    }

    public int getComputedScrollY() {
        int pos, nScrollY, nItemY;
        View view = null;
        pos = getFirstVisiblePosition();
        view = getChildAt(0);
        nItemY = view.getTop();
        nScrollY = mItemOffsetY[pos] - nItemY;
        return nScrollY;
    }
}
