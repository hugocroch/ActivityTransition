package hugo.cro.activityanimation.lollipop;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import hugo.cro.activityanimation.R;


/**
 * Created by hugocrochetiere on 2014-12-08.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CellHolder holder;
        View cell;
        if (convertView == null) {
            cell = LayoutInflater.from(getContext()).inflate(R.layout.cell, null);
            holder = new CellHolder();
            cell.setTag(holder);

        } else {
            cell = convertView;
        }

        holder = (CellHolder) cell.getTag();

        return cell;
    }


    static class CellHolder {
        Image background;
    }
}
