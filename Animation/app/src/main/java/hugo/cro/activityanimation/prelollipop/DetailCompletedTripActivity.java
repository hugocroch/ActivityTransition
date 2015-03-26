package hugo.cro.activityanimation.prelollipop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import hugo.cro.activityanimation.R;

public class DetailCompletedTripActivity extends Activity {

    DetailCompletedFragment detailCompletedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_completed_trip);
        Bundle bundle = getIntent().getExtras();

        final int top = bundle.getInt("y_position");
        final int left = bundle.getInt("x_position");
        final int width = bundle.getInt("width");
        final int height = bundle.getInt("height");
        int[] screenLocation = new int[2];
        screenLocation[0] = left;
        screenLocation[1] = top;
        if (savedInstanceState == null) {

            detailCompletedFragment = DetailCompletedFragment.newInstance(1, screenLocation, width, height);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, detailCompletedFragment, "detail")
                    .commit();
        }
        else {
            detailCompletedFragment = (DetailCompletedFragment) getFragmentManager().findFragmentByTag("detail");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_completed_trip, menu);
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
        detailCompletedFragment.exitAnimation(endAction);
    }

    @Override
    public void onBackPressed() {
        runExitAnimation(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
