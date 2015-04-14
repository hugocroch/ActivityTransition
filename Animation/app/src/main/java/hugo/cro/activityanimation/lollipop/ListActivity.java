package hugo.cro.activityanimation.lollipop;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import hugo.cro.activityanimation.CustomActivity;
import hugo.cro.activityanimation.FadeActivity;
import hugo.cro.activityanimation.R;
import hugo.cro.activityanimation.SlideActivity;
import hugo.cro.activityanimation.SlideScaleActivity;


public class ListActivity extends android.app.ListActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setAllowEnterTransitionOverlap(true);
//        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_list);

        String[] ARRAY = new String[5];
        CustomAdapter adapter = new CustomAdapter(this,0);
        adapter.addAll(ARRAY);
        setListAdapter(adapter);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
switch (position) {
    case 0: {
        Intent intent = new Intent(this, SlideActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_reverse_right);
        break;
    }
    case 1: {
        Intent intent = new Intent(this, FadeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        break;
    }
    case 2: {
        Intent intent = new Intent(this, SlideScaleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_scale_right, R.anim.slide_scale_reverse_right);
        break;
    }
    case 3: {
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);

        Intent intent = new Intent(this, CustomActivity.class);
        intent.putExtra("orientation", getResources().getConfiguration().orientation).
                putExtra("left", screenLocation[0]).
                putExtra("top", screenLocation[1]).
                putExtra("width", v.getWidth()).
                putExtra("height", v.getHeight());

        startActivity(intent);
        overridePendingTransition(0, 0);
        break;
    }
    case 4: {
        Intent intent = new Intent(this, DetailActivity.class);
        ImageView background = (ImageView) v.findViewById(R.id.background);
        background.setTransitionName("background");
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, background, "background").toBundle());
        break;
    }
}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
}
