package hugo.cro.activityanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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



    public void onSlideButtonPressed(View view) {

        Intent intent = new Intent(this, SlideActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_reverse_right);
    }

    public void onFadeButtonPressed(View view) {

        Intent intent = new Intent(this, FadeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onSlideScaleButtonPressed(View view) {

        Intent intent = new Intent(this, SlideScaleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_scale_right, R.anim.slide_scale_reverse_right);
    }

    public void onCustomButtonPressed(View view) {


        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);

        Intent intent = new Intent(this, CustomActivity.class);
        intent.putExtra("orientation", getResources().getConfiguration().orientation).
                putExtra("left",screenLocation[0]).
                putExtra("top", screenLocation[1]).
                putExtra("width", view.getWidth()).
                putExtra("height", view.getHeight());

        startActivity(intent);
        overridePendingTransition(0,0);
    }

}
