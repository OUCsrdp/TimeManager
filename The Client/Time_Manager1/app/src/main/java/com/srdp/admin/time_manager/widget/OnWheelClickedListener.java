package com.srdp.admin.time_manager.widget;

/**
 * Created by lizliz on 2018/5/7.
 */

public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     * @param wheel the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}
