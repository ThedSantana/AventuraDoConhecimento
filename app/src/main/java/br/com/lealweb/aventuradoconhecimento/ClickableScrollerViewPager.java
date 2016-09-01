package br.com.lealweb.aventuradoconhecimento;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import github.chenupt.springindicator.viewpager.ScrollerViewPager;

/**
 * Created by leonardoleal on 01/09/16.
 */
public class ClickableScrollerViewPager extends ScrollerViewPager {

    private OnItemClickListener mOnItemClickListener;

    public ClickableScrollerViewPager(Context context) {
        super(context);
        setup();
    }

    public ClickableScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }


    private void setup() {
        final GestureDetector tapGestureDetector = new GestureDetector(getContext(), new TapGestureListener());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, MotionEvent e);
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getCurrentItem(), e);
            }
            return true;
        }
    }
}
