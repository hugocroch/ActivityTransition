package hugo.cro.activityanimation.lollipop;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by hugocrochetiere on 2014-12-08.
 */

public class RippleView extends LinearLayout {
    public RippleView(Context context) {
        super(context);
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RippleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE :
                drawableHotspotChanged(event.getX(), event.getY());
                break;
        }
        return super.onTouchEvent(event);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void drawableHotspotChanged(float x, float y) {

        if (getBackground() != null) {
            getBackground().setHotspot(x, y);
        }
    }
}
