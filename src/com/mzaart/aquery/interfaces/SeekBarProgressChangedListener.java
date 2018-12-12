package com.mzaart.aquery.interfaces;

import com.mzaart.aquery.AQ;

/**
 * This interface is to be implemented by classes that listen to a progress change in a SeekBar
 *
 * @see android.widget.SeekBar
 */
public interface SeekBarProgressChangedListener {

    /**
     * This method is called when the progress is changed
     *
     * @param seekBar The current seek bar
     * @param progress The current progress
     * @param fromUser True if the user caused the change, false otherwise
     */
    void onProgressChanged(AQ seekBar, int progress, boolean fromUser);
}
