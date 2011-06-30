package com.taskninjapro.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CustomLayout extends LinearLayout implements Checkable {
	


	private boolean mChecked;
	 private boolean mBroadcasting;
	 
	 private OnCheckedChangeListener mOnCheckedChangeListener;
     private OnCheckedChangeListener mOnCheckedChangeWidgetListener;
     
	 public CustomLayout(Context context) {
			super(context);
//			setFocusable(true);
	 }

	 public CustomLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
//			setFocusable(true);
	 }

	public boolean isChecked() {
		return mChecked;
	}

	public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this ,mChecked);
            }
            if (mOnCheckedChangeWidgetListener != null) {
                mOnCheckedChangeWidgetListener.onCheckedChanged(this , mChecked);
            }

            mBroadcasting = false;
        }
		
	}

	public void toggle() {
		setChecked(!mChecked);
		
	}
	
    @Override
    public boolean performClick() {

        /*
         * XXX: These are tiny, need some surrounding 'expanded touch area',
         * which will need to be implemented in Button if we only override
         * performClick()
         */

        /* When clicked, toggle the state */
        toggle();
        return super.performClick();
    }
    
    /**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes. This callback is used for internal purpose only.
     *
     * @param listener the callback to call on checked state change
     * @hide
     */
    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeWidgetListener = listener;
    }
	
    /**
     * Interface definition for a callback to be invoked when the checked state
     * of a compound button changed.
     */
    public static interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param buttonView The compound button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        void onCheckedChanged(CustomLayout buttonView, boolean isChecked);
    }

}
