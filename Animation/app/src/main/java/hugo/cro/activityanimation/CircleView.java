package com.appcom.explorer.ui.listview;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.appcom.explorer.R;

/**
 * Created by hugocrochetiere on 2014-05-26.
 */
public class CircleView extends RelativeLayout {

    ImageButton btnCircleOption;
    CircleListView listView;
    private CircleCallbacks mCallbacks;
    private int mQuickReturnHeight;

    private boolean isAnimating;
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;

    private TranslateAnimation anim;

    public CircleView(Context context) {
        super(context);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.list_circle, this, true);
        listView = (CircleListView)view.findViewById(R.id.circleListView);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.list_circle, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        listView = (CircleListView)view.findViewById(R.id.circleListView);
        btnCircleOption = (ImageButton) view.findViewById(R.id.btnOption);

        btnCircleOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("touch", "touch button");
            }
        });

        listView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mQuickReturnHeight = btnCircleOption.getHeight();
                        mQuickReturnHeight += ((MarginLayoutParams)btnCircleOption.getLayoutParams()).bottomMargin;
                        listView.computeScrollY();
                    }
                });


        if (!a.getBoolean(R.styleable.CircleView_showButton,false)) {
            btnCircleOption.setVisibility(View.GONE);
        }
        else {

btnCircleOption.setImageDrawable(a.getDrawable(R.styleable.CircleView_imageButton));

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    int currentScroll = 0;
                    int rawY = 0;
                    int translationY = 0;

                    if (listView.scrollYIsComputed()) {
                        rawY = listView.getComputedScrollY();
                    }

                    currentScroll = rawY;

                    switch (mState) {
                        case STATE_OFFSCREEN:
                            if (rawY >= mMinRawY) {
                                mMinRawY = rawY;
                            } else {
                                mState = STATE_RETURNING;
                            }
                            translationY = rawY;
                            break;

                        case STATE_ONSCREEN:
                            if (rawY > mQuickReturnHeight) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            translationY = rawY;
                            break;

                        case STATE_RETURNING:

                            translationY = (rawY - mMinRawY) + mQuickReturnHeight;


                            if (translationY < 0) {
                                translationY = 0;
                                mMinRawY = rawY + mQuickReturnHeight;
                            }

                            if (rawY == 0) {
                                mState = STATE_ONSCREEN;
                                translationY = 0;
                            }

                            if (translationY > mQuickReturnHeight) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            break;
                    }

                    System.out.println("current scroll : " + currentScroll);
                    System.out.println("scroll : " + mScrollY);
                    /** this can be used if the build is below honeycomb **/
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                        anim = new TranslateAnimation(0, 0, translationY,
                                translationY);
                        anim.setFillAfter(true);
                        anim.setDuration(0);
                        btnCircleOption.startAnimation(anim);
                    } else {

                        if (currentScroll + 10 < mScrollY) {
                            mScrollY = currentScroll;
                            if (!isAnimating) {
                                isAnimating = true;
                                btnCircleOption.animate().translationY(0).setDuration(100).setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        isAnimating = false;
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {
                                        isAnimating = false;
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                            }
                        } else if (currentScroll > mScrollY + 10) {
                            mScrollY = currentScroll;
                            if (!isAnimating) {
                                isAnimating = true;
                                btnCircleOption.animate().setDuration(200).translationY(mQuickReturnHeight).setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        isAnimating = false;
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {
                                        isAnimating = false;
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                            }
                        }


                    }


                }
            });

//            listView.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if (mCallbacks != null) {
//                        switch (motionEvent.getActionMasked()) {
//                            case MotionEvent.ACTION_DOWN:
//                                mCallbacks.onDownMotionEvent();
//                                break;
//                            case MotionEvent.ACTION_UP:
//                            case MotionEvent.ACTION_CANCEL:
//                                int position = listView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
//                                if(position!= ListView.INVALID_POSITION){
//                                    listView.performItemClick(listView.getChildAt(position-listView.getFirstVisiblePosition()), position, listView.getItemIdAtPosition(position));
//                                }
//                                //mCallbacks.onUpOrCancelMotionEvent();
//                                break;
//                        }
//                    }
//                    return view.onTouchEvent(motionEvent);
//                }
//            });

        }

    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.list_circle, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        listView = (CircleListView)findViewById(R.id.circleListView);
        btnCircleOption = (ImageButton) view.findViewById(R.id.btnOption);

        if (!a.getBoolean(R.styleable.CircleView_showButton,false)) {
            btnCircleOption.setVisibility(View.GONE);
        }
        else {

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    mScrollY = 0;
                    int translationY = 0;

                    if (listView.scrollYIsComputed()) {
                        mScrollY = listView.getComputedScrollY();
                    }

                    int rawY = mScrollY;

                    switch (mState) {
                        case STATE_OFFSCREEN:
                            if (rawY >= mMinRawY) {
                                mMinRawY = rawY;
                            } else {
                                mState = STATE_RETURNING;
                            }
                            translationY = rawY;
                            break;

                        case STATE_ONSCREEN:
                            if (rawY > mQuickReturnHeight) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            translationY = rawY;
                            break;

                        case STATE_RETURNING:

                            translationY = (rawY - mMinRawY) + mQuickReturnHeight;

                            System.out.println(translationY);
                            if (translationY < 0) {
                                translationY = 0;
                                mMinRawY = rawY + mQuickReturnHeight;
                            }

                            if (rawY == 0) {
                                mState = STATE_ONSCREEN;
                                translationY = 0;
                            }

                            if (translationY > mQuickReturnHeight) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            break;
                    }

                    /** this can be used if the build is below honeycomb **/
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                        anim = new TranslateAnimation(0, 0, translationY,
                                translationY);
                        anim.setFillAfter(true);
                        anim.setDuration(0);
                        btnCircleOption.startAnimation(anim);
                    } else {
                        btnCircleOption.animate().translationY(translationY);
                    }



                }
            });



//            listView.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if (mCallbacks != null) {
//                        switch (motionEvent.getActionMasked()) {
//                            case MotionEvent.ACTION_DOWN:
//                                mCallbacks.onDownMotionEvent();
//                                break;
//                            case MotionEvent.ACTION_UP:
//                            case MotionEvent.ACTION_CANCEL:
//                                mCallbacks.onUpOrCancelMotionEvent();
//                                break;
//                        }
//                    }
//                    return view.onTouchEvent(motionEvent);
//                }
//            });

        }

    }


    public void setAdapter(ArrayAdapter<?> adapter) {
        listView.setAdapter(adapter);
        listView.invalidate();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }


    public static interface CircleCallbacks {
        public void onScrollChanged(int scrollY);
        public void onDownMotionEvent();
        public void onUpOrCancelMotionEvent();
    }
}
