package com.mzaart.aquery.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mzaart.aquery.interfaces.AnimationListener;

/**
 * This class builds Animation objects.
 *
 * @see Animation
 */
@SuppressWarnings("unused")
public class AnimationBuilder {

    private int resId;
    private Context context;

    private long duration;

    private AnimationListener onStart;
    private AnimationListener onRepeat;
    private AnimationListener onEnd;

    /**
     * Constructs an AnimationBuilder
     *
     * @param resId The resource ID of the animation.
     * @param context Activity context
     */
    public AnimationBuilder(int resId, Context context) {
        this.resId = resId;
        this.context = context;
    }

    /**
     * Constructs the Animation object
     *
     * @return The constructed Animaion
     * @see Animation
     *
     * @throws IllegalArgumentException If the Resource ID isn't set
     * @see IllegalArgumentException
     */
    public Animation build() {
        if (resId == 0) {
            throw new IllegalArgumentException("Invalid animation resource Id");
        }

        Animation anim = AnimationUtils.loadAnimation(context, resId);

        if (duration > 0) {
            anim.setDuration(duration);
        }

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (onStart != null)
                    onStart.onAnimationEvent(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (onEnd != null)
                    onEnd.onAnimationEvent(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (onRepeat != null)
                    onRepeat.onAnimationEvent(animation);
            }
        });

        return anim;
    }

    /**
     * Sets the duration of the Animation.
     *
     * @param duration The duration to set.
     * @return The current builder.
     */
    public AnimationBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the AnimationListener to be called when the animation starts.
     *
     * @param listener The listener to be called
     * @return The current builder.
     */
    public AnimationBuilder onStart(AnimationListener listener) {
        this.onStart = listener;
        return this;
    }

    /**
     * Sets the AnimationListener to be called when the animation repeats
     * @param listener The listener to be called
     * @return The current builder.
     */
    public AnimationBuilder onRepeat(AnimationListener listener) {
        this.onRepeat = listener;
        return this;
    }

    /**
     * Sets the AnimationListener to be called when the animation ends
     * @param listener The listener to be called
     * @return The current builder.
     */
    public AnimationBuilder onEnd(AnimationListener listener) {
        this.onEnd = listener;
        return this;
    }
}
