package hugo.cro.activityanimation.prelollipop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hugo.cro.activityanimation.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentCompletedTripInteractionListener}
 * interface.
 */
public class CompletedTripFragment extends Fragment implements CompletedTripAdapter.OnCompletedTripAdapterListener {

    private OnFragmentCompletedTripInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private CompletedTripAdapter mAdapter;

    public static CompletedTripFragment newInstance() {
        return new CompletedTripFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CompletedTripFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Trip> trips = new ArrayList<Trip>();

        for (int index = 0; index < 30; index++) {
            Trip trip = new Trip();
            trip.setTitle("Boston");
            trips.add(trip);
        }

        mAdapter = new CompletedTripAdapter(getActivity(), 0, trips);
        mAdapter.setmListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentCompletedTripInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, Trip trip) {
        Intent intent = new Intent(getActivity(), DetailCompletedTripActivity.class);
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        intent.putExtra("x_position", screenLocation[0]);
        intent.putExtra("y_position", screenLocation[1]);
        intent.putExtra("width", view.getWidth());
        intent.putExtra("height", view.getHeight());
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentCompletedTripInteractionListener {
        public void onItemClick(Trip trip);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mListView.animate().setDuration(500).alpha(1).start();
    }
}
