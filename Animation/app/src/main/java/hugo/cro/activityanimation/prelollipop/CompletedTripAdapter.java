package hugo.cro.activityanimation.prelollipop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hugo.cro.activityanimation.R;

/**
 * Created by hugocrochetiere on 2014-09-26.
 */
public class CompletedTripAdapter extends ArrayAdapter<Trip> {
    public CompletedTripAdapter(Context context, int resource, List<Trip> objects) {
        super(context, resource, objects);
    }

    OnCompletedTripAdapterListener mListener;

    public OnCompletedTripAdapterListener getmListener() {
        return mListener;
    }

    public void setmListener(OnCompletedTripAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TripHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.completed_trip_cell, null);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.completed_trip_cell, null);
            holder = new TripHolder();
            holder.lblTitle = (TextView) convertView.findViewById(R.id.completed_trip_cell_title);
            holder.imgBackground = (ImageView) convertView.findViewById(R.id.completed_trip_cell_background);
            holder.imgBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, getItem(position));
                }
            });
            convertView.setTag(holder);
        }

        holder = (TripHolder) convertView.getTag();
        Trip trip = getItem(position);
        holder.lblTitle.setText(trip.getTitle());
        holder.imgBackground.setImageDrawable(getContext().getResources().getDrawable(R.drawable.boston));
        //Picasso.with(getContext()).load(trip.getImagePath()).placeholder(R.drawable.boston);

        return convertView;
    }

    static class TripHolder {
        TextView lblTitle;
        ImageView imgBackground;
    }

    public interface OnCompletedTripAdapterListener {
        public void onItemClick(View view, Trip trip);
    }


}
