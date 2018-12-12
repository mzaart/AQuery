package com.mzaart.aquery.interfaces;

import android.view.animation.Animation;

/**
 * This interface is to be implemented by classes that listen to Animation events
 *
 * @see Animation
 */
public interface AnimationListener {

    /**
     * This method is called on Animation events (when the animation starts, repeats or ends)
     * @param animation The animation that is responsible for the event
     */
    void onAnimationEvent(Animation animation);
}
