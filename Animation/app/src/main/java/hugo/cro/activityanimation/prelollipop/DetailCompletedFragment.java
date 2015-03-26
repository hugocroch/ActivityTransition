package hugo.cro.activityanimation.prelollipop;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appcom.explorer.R;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailCompletedFragment.OnFragmentCompletedDetailInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailCompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DetailCompletedFragment extends Fragment {
    // the fragment initialization parameters
    private static final String TRIP_ID = "tripId";
    private static final String SCREEN_LOCATION = "screenLocation";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    int originalOrientation;
    private static final TimeInterpolator sDecelerator = new AccelerateDecelerateInterpolator();
    int mLeftOriginalDelta;
    int mTopOriginalDelta;
    private static final int ANIM_DURATION = 800;
    private int tripId;
    private int[] screenLocation;
    private int width;
    private int height;
    LinearLayout contentView;
    ImageView imageView;
    TextView lblCheckin;
    TextView lblList;

    private OnFragmentCompletedDetailInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pTripId Id du voyage.
     * @param pScreenLocation Position X/Y de l'image dans la listview.
     * @param pWidth Largeur de l'image.
     * @param pHeight Hauteur de l'image.
     * @return A new instance of fragment DetailCompletedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailCompletedFragment newInstance(int pTripId, int[] pScreenLocation, int pWidth, int pHeight) {
        DetailCompletedFragment fragment = new DetailCompletedFragment();
        Bundle args = new Bundle();
        args.putInt(TRIP_ID, pTripId);
        args.putIntArray(SCREEN_LOCATION, pScreenLocation);
        args.putInt(WIDTH, pWidth);
        args.putInt(HEIGHT, pHeight);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt(TRIP_ID);
            screenLocation = getArguments().getIntArray(SCREEN_LOCATION);
            width = getArguments().getInt(WIDTH);
            height = getArguments().getInt(HEIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_completed, container, false);

        imageView = (ImageView)view.findViewById(R.id.detail_completed_trip_image);
        lblCheckin = (TextView)view.findViewById(R.id.detail_completed_trip_checkin_label);
        lblList = (TextView)view.findViewById(R.id.detail_completed_trip_list_label);
        contentView = (LinearLayout)view.findViewById(R.id.detail_completed_trip_content_view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            ViewTreeObserver observer = imageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onPreDraw() {

                    final int top = screenLocation[1];
                    final int left = screenLocation[0];
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    imageView.getLocationOnScreen(screenLocation);

                    mLeftOriginalDelta = left - screenLocation[0];
                    mTopOriginalDelta = top - screenLocation[1];

                    imageView.setTranslationY(mTopOriginalDelta);
                    imageView.setTranslationX(mLeftOriginalDelta);

                    imageView.animate().setDuration(ANIM_DURATION)
                            .translationX(left)
                            .translationY(0)
                            .setInterpolator(sDecelerator)
                            .withEndAction(new Runnable() {
                                public void run() {
                                    contentView.setVisibility(View.VISIBLE);
                                    contentView.animate().alpha(1).withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            ValueAnimator animator = new ValueAnimator();
                                            animator.setObjectValues(0, 33);
                                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                public void onAnimationUpdate(ValueAnimator animation) {
                                                    lblCheckin.setText(String.valueOf(animation.getAnimatedValue()));
                                                    lblList.setText(String.valueOf(animation.getAnimatedValue()));
                                                }
                                            });
                                            animator.setEvaluator(new TypeEvaluator<Integer>() {
                                                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                                                    return Math.round((endValue - startValue) * fraction);
                                                }
                                            });
                                            animator.setDuration(1000);
                                            animator.start();
                                        }
                                    });
                                }
                            });

                    return true;
                }
            });
        }
        else {
            tripId = savedInstanceState.getInt(TRIP_ID);
            screenLocation = savedInstanceState.getIntArray(SCREEN_LOCATION);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            contentView.setVisibility(View.VISIBLE);
            contentView.setAlpha(1);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentCompletedDetailInteractionListener) activity;
        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentCompletedDetailInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void exitAnimation(final Runnable endAction) {
        contentView.animate().alpha(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                contentView.setVisibility(View.GONE);
                imageView.animate().setDuration(ANIM_DURATION)
                        .translationX(0)
                        .translationY(mTopOriginalDelta)
                        .setInterpolator(sDecelerator)
                        .withEndAction(endAction);
            }
        });

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentCompletedDetailInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
