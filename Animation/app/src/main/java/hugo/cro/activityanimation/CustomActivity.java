package hugo.cro.activityanimation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;


public class CustomActivity extends ActionBarActivity {


    LinearLayout topMenu;
    int originalOrientation;
    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    int mLeftDelta;
    int mTopDelta;
    private static final int ANIM_DURATION = 800;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        topMenu = (LinearLayout) findViewById(R.id.topMenu);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        Bundle bundle = getIntent().getExtras();

        final int top = bundle.getInt("top");
        final int left = bundle.getInt("left");
        final int width = bundle.getInt("width");
        final int height = bundle.getInt("height");
        originalOrientation = bundle.getInt("orientation");

        if (savedInstanceState == null) {

            ViewTreeObserver observer = topMenu.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onPreDraw() {

                    int[] screenLocation = new int[2];
                    topMenu.getViewTreeObserver().removeOnPreDrawListener(this);
                    topMenu.getLocationOnScreen(screenLocation);

                    mLeftDelta = left - screenLocation[0];
                    mTopDelta = top - screenLocation[1];

                    topMenu.setTranslationY(mTopDelta);
                    topMenu.setTranslationX(mLeftDelta);

                    topMenu.animate().setDuration(ANIM_DURATION)
                                     .translationX(left)
                                     .translationY(0)
                                     .setInterpolator(sDecelerator)
                                     .withEndAction(new Runnable() {
                                         public void run() {
                                             scrollView.setVisibility(View.VISIBLE);
                                             scrollView.animate().alpha(1);
                                         }
                                     });

                    int fromColor = 0x5d51ccff;
                    int toColor = 0xff0077FF;

                    ValueAnimator colorAnim = ObjectAnimator.ofInt(topMenu, "backgroundColor", fromColor, toColor);
                    colorAnim.setDuration(500);
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.start();

                    return true;
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void runExitAnimation(final Runnable endAction) {

        // No need to set initial values for the reverse animation; the image is at the
        // starting size/location that we want to start from. Just animate to the
        // thumbnail size/location that we retrieved earlier

        // Caveat: configuration change invalidates thumbnail positions; just animate
        // the scale around the center. Also, fade it out since it won't match up with
        // whatever's actually in the center
//        final boolean fadeOut;
//        if (getResources().getConfiguration().orientation != originalOrientation) {
//            mImageView.setPivotX(mImageView.getWidth() / 2);
//            mImageView.setPivotY(mImageView.getHeight() / 2);
//            mLeftDelta = 0;
//            mTopDelta = 0;
//            fadeOut = true;
//        } else {
//            fadeOut = false;
//        }

        // First, slide/fade text out of the way

        // Animate image back to thumbnail size/location


        scrollView.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
            @Override
            public void run() {
                topMenu.animate().setDuration(500).
                        translationX(mLeftDelta).translationY(mTopDelta).
                        withEndAction(endAction);


                int RED = 0x5d51ccff;
                int BLUE = 0xff0077FF;

                ValueAnimator colorAnim = ObjectAnimator.ofInt(topMenu, "backgroundColor", BLUE, RED);
                colorAnim.setDuration(500);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.start();
            }
        });

    }


    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it is complete.
     */
    @Override
    public void onBackPressed() {
        runExitAnimation(new Runnable() {
            public void run() {
                // *Now* go ahead and exit the activity
                finish();
            }
        });
    }


    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, R.anim.fade_out);
    }
}
