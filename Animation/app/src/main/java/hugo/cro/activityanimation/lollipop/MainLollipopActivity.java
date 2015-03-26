package hugo.cro.activityanimation.lollipop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import hugo.cro.activityanimation.R;


public class MainLollipopActivity extends ActionBarActivity {


    ImageView imageView;
    RippleView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lollipop_main);
        imageView = (ImageView) findViewById(R.id.photo);
        header = (RippleView) findViewById(R.id.header);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onButtonPressed(View view) {


        if( imageView.getVisibility() == View.VISIBLE ) {
            hideImageCircular();
        } else {
            revealImageCircular();
        }
}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideImageCircular() {
        int x = getX();
        int y = getY();
        int radius = getRadius();

        Animator anim =
                ViewAnimationUtils.createCircularReveal(imageView, x, y, radius, 0);

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.setVisibility( View.INVISIBLE );
            }
        });

        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealImageCircular() {
        int x = getX();
        int y = getY();
        int radius = getRadius();

        Animator anim =
                ViewAnimationUtils.createCircularReveal(imageView, x, y, 0, radius);

        anim.setDuration( 1000 );
        anim.addListener( new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                imageView.setVisibility( View.VISIBLE );
            }
        });

        anim.start();
    }

    private int getX() {
        return ( imageView.getLeft() + imageView.getRight() ) / 2;
    }

    private int getY() {
        return ( imageView.getTop() + imageView.getBottom() ) / 2;
    }

    private int getRadius() {
        return imageView.getWidth();
    }
}
