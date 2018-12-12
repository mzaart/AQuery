package com.mzaart.aquery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mzaart.aquery.exceptions.SDKVersionException;
import com.mzaart.aquery.interfaces.EventListener;
import com.mzaart.aquery.interfaces.SeekBarProgressChangedListener;
import com.mzaart.aquery.utils.Validator;
import com.mzaart.aquery.exceptions.IllegalParentException;
import com.mzaart.aquery.exceptions.IllegalViewActionException;
import com.mzaart.aquery.exceptions.ViewNotFoundException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess", "Convert2Lambda", "Anonymous2MethodRef"})
public class AQ {

    private Context context;
    private View raw;

    /**
     * Instantiates an AQuery instance from a context and sets
     * the Context's root view as the current view.
     *
     * @param context Context context
     *
     * @throws  IllegalArgumentException If context is null or not an Activity.
     */
     public AQ(@NonNull Context context) {
        requireNotNull(context);

        this.context = context;
        if (!(context instanceof Activity))
            throw new IllegalArgumentException("Context should be an Activity.");

         this.raw = ((Activity) context).findViewById(android.R.id.content);
    }

    /**
     * Instantiates an AQuery instance from a view.
     *
     * @param raw The view to be used.
     *
     * @throws  IllegalArgumentException If view is null.
     */
     public AQ(@NonNull View raw) {
        requireNotNull(raw);

        this.raw = raw;
        this.context = raw().getContext();
    }

    /**
     * Instantiates an AQuery instance from a view given its Id.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param context The Context's context.
     * @param id The view's id.
     *
     * @throws  ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @throws  IllegalArgumentException If context is null or not an Activity.
     */
     public AQ(@NonNull Context context, int id) {
        this.context = context;

         if (!(context instanceof Activity))
             throw new IllegalArgumentException("Context should be an Activity.");
        this.raw = ((Activity) context).findViewById(id);

        if (raw == null)
            throw new ViewNotFoundException();
    }

    /**
     * Instantiates an AQuery instance from a view given its Id in the parent view.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param view The view's parent.
     * @param id The view's id.
     *
     * @throws  ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @throws  IllegalArgumentException If view is null.
     */
     public AQ(View view, int id) {
        requireNotNull(view);

        this.context = view.getContext();
        this.raw = view.findViewById(id);

        if (raw == null)
            throw new ViewNotFoundException();
    }

    /**
     * Instantiates an AQuery instance from a view given its Id in an AQuery object.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param aquery The containing AQuery object.
     * @param id The view's id.
     *
     * @throws  ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @throws  IllegalArgumentException If AQuery is null.
     */
     public AQ(@NonNull AQ aquery, int id) {
        requireNotNull(aquery);

        this.context = aquery.context;
        this.raw = aquery.raw().findViewById(id);

        if (raw == null)
            throw new ViewNotFoundException();
    }

    /**
     * Returns base view of the AQuery object.
     *
     * @return View The base view of the AQuery object.
     */
    @NonNull
    public View raw() {
        return raw;
    }

    /**
     * Returns the context of the AQuery object.
     *
     * @return Context The context of the AQuery object.
     */
    @NonNull
    public Context context() {
        return context;
    }

    /**
     * Returns an AQuery object containing a view with a specific id.
     *
     * @param  id The View's Id.
     * @return View The base view of the AQuery object.
     *
     * @throws  ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     */
    @NonNull
    public AQ find(int id) {
        View target =  raw().findViewById(id);

        if (target == null)
            throw new ViewNotFoundException();

        return new AQ(target);
    }

    /**
     * Returns an AQuery object containing the view's parent.
     *
     * @return AQ AQuery object containing the view's parent.
     *
     * @throws  IllegalParentException If the view's parent is not a view.
     * @see IllegalParentException
     */
    @NonNull
    public AQ parent() {
        try {
            return new AQ((View) raw().getParent());
        } catch (ClassCastException e) {
            throw new IllegalParentException();
        }
    }

    /**
     * Returns the child count of the ViewGroup
     *
     * @return The child count of the ViewGroup
     *
     * @throws IllegalViewActionException If the view is not a ViewGroup
     * @see IllegalViewActionException
     */
    public int childCount() {
        if (raw() instanceof ViewGroup) {
            return ((ViewGroup) raw()).getChildCount();
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Returns the child of of the ViewGroup at the specified index
     *
     * @param index The index of the child to return
     * @return The child at the specified index
     *
     * @throws IllegalViewActionException If the view is not a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ childAt(int index) {
        if (raw() instanceof ViewGroup) {
            return new AQ(((ViewGroup) raw()).getChildAt(index));
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Returns a list of the ViewGroup's children
     * @return A list containing the ViewGroup's children
     *
     * @throws IllegalViewActionException If the view is not a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public List<AQ> children() {
        if (raw() instanceof ViewGroup) {
            List<AQ> children = new ArrayList<>(childCount());
            for (int i = 0; i < childCount(); i++) {
                children.add(childAt(i));
            }
            return children;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Gets the visibility of the view.
     *
     * @return The visibility of the view.
     */
    public int visibility() {
        return raw().getVisibility();
    }

    /**
     * Sets the visibility of the view.
     * @param visibility The visibility constant.
     * @return The current AQuery object.
     */
    @NonNull
    public AQ visibility(int visibility) {
        raw().setVisibility(visibility);
        return this;
    }

    /**
     * Removes the view.
     *
     * @throws  RuntimeException If the View can't be removed.
     * @see  RuntimeException
     */
    public void remove() {
        try {
            ((ViewGroup) raw().getParent()).removeView(raw);
        } catch (ClassCastException e) {
            throw new RuntimeException("Can't remove view.");
        }
    }

    /**
     * Removes all children of a view.
     *
     * @return AQ The current AQuery object.
     *
     * @throws  IllegalViewActionException If the view isn't a ViewGroup.
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ removeAllViews() {
        try {
            ((ViewGroup) raw()).removeAllViews();
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the view passed is null.
     * @see IllegalArgumentException
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(@NonNull View v) {
        requireNotNull(v);
        try {
            ((ViewGroup) raw()).addView(v);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the AQuery passed is null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(@NonNull AQ v) {
        requireNotNull(v);

        try {
            ((ViewGroup) raw()).addView(v.raw());
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param width The appended view's width
     * @param height The appended view's height
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the view passed is null.
     * @see IllegalArgumentException
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(View v, int width, int height) {
        requireNotNull(v);

        try {
            ((ViewGroup) raw()).addView(v, width, height);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param width The appended view's width
     * @param height The appended view's height
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the AQuery passed is null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(AQ v, int width, int height) {
        requireNotNull(v);

        try {
            ((ViewGroup) raw()).addView(v.raw(), width, height);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the view or layout params passed are null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(View v, ViewGroup.LayoutParams params) {
        requireNotNull(v, params);

        try {
            ((ViewGroup) raw()).addView(v, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the AQuery or layout params passed are null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(AQ v, ViewGroup.LayoutParams params) {
        requireNotNull(v, params);

        try {
            ((ViewGroup) raw()).addView(v.raw(), params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param index The index to append the view at.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the view passed is null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(View v, int index) {
        requireNotNull(v);

        try {
            ((ViewGroup) raw()).addView(v, index);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param index The index to append the view at.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the AQuery passed is null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(AQ v, int index) {
        requireNotNull(v);

        try {
            ((ViewGroup) raw()).addView(v.raw, index);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param index The index to append the view at.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the view or layout params passed are null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(View v, int index, ViewGroup.LayoutParams params) {
        requireNotNull(v, params);

        try {
            ((ViewGroup) raw()).addView(v, index, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param index The index to append the view at.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @throws  IllegalArgumentException If the AQuery or layout params passed are null.
     * @throws  IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ append(AQ v, int index, ViewGroup.LayoutParams params) {
        requireNotNull(v, params);

        try {
            ((ViewGroup) raw()).addView(v.raw(), index, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets an on-click listener.
     *
     * @param onClickListener The onClickListener to be executed when the view is clicked.
     * @return AQ Current AQuery object.
     *
     * @throws  IllegalArgumentException If the onClickListener passed is null.
     */
    @NonNull
    public AQ click(@NonNull final EventListener onClickListener) {
        requireNotNull(onClickListener);
        raw().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onEvent(new AQ(view));
            }
        });

        return this;
    }

    /**
     * Performs a click on the current view.
     *
     * @return The current AQuery object.
     */
    @NonNull
    public AQ click() {
        raw().performClick();
        return this;
    }

    /**
     * Runs a runnable when the view tree is about to be drawn.At this point, all views in the tree
     * have been measured and given a frame.
     *
     * @param runnable The Runnable to run.
     *
     * @return The current AQuery object
     *
     * @throws IllegalArgumentException If the runnable passed in null
     */
    @NonNull
    public AQ preDraw(@NonNull final Runnable runnable) {
        requireNotNull(runnable);
        final ViewTreeObserver treeObserver = raw().getViewTreeObserver();
        treeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                treeObserver.removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        });

        return this;
    }

    /**
     * Runs a runnable when the view tree is rendered. At this point, all views in the tree
     * have been drawn.
     *
     * @param runnable The Runnable to run.
     *
     * @return The current AQuery object
     *
     * @throws IllegalArgumentException If the runnable passed in null
     */
    @NonNull
    public AQ ready(@NonNull final Runnable runnable) {
        requireNotNull(runnable);
        ViewTreeObserver treeObserver = raw().getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onGlobalLayout() {
                runnable.run();
                if (Build.VERSION.SDK_INT < 16) {
                    raw().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    raw().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        return this;
    }

    /**
     * Gets the layout parameters of the view.
     *
     * @return The layout parameters of the view.
     */
    @NonNull
    public ViewGroup.LayoutParams layoutParams() {
        return raw().getLayoutParams();
    }

    /**
     * Gets the visual x position of this view, in pixels.
     *
     * @return The visual x position of this view, in pixels.
     */
    public float x() {
        return raw().getX();
    }

    /**
     * Sets the visual x position of this view, in pixels.
     *
     * @param position The visual x position, in pixels.
     * @return The Current AQuery object.
     */
    @NonNull
    public AQ x(float position) {
        raw().setX(position);
        return this;
    }

    /**
     * Gets the visual y position of this view, in pixels.
     *
     * @return The visual y position of this view, in pixels.
     */
    public float y() {
        return raw().getY();
    }

    /**
     * Sets the visual y position of this view, in pixels.
     *
     * @param position The visual y position, in pixels.
     * @return The Current AQuery object.
     */
    @NonNull
    public AQ y(float position) {
        raw().setY(position);
        return this;
    }

    /**
     * Gets the visual z position of this view.
     *
     * @return The visual z position of this view.
     *
     * @throws  SDKVersionException If the SDK version is lower than 21.
     * @see SDKVersionException
     */
    public float z() {
        if (Build.VERSION.SDK_INT < 21)
                throw new SDKVersionException();
        else
            return raw().getZ();
    }

    /**
     * Sets the visual z position of this view.
     *
     * @param position The visual z position.
     * @return The Current AQuery object.
     *
     * @throws  SDKVersionException If the SDK version is lower than 21.
     * @see SDKVersionException
     */
    @NonNull
    public AQ z(float position) {
        if (Build.VERSION.SDK_INT < 21)
            throw new SDKVersionException();
        else {
            raw().setZ(position);
            return this;
        }
    }

    /**
     * Gets the horizontal location of this view relative to its left position.
     *
     * @return The horizontal location of this view relative to its left position.
     */
    public float translationX() {
        return raw().getTranslationX();
    }

    /**
     * Sets the horizontal location of this view relative to its left position.
     *
     * @param offset  The horizontal position of this view relative to its left position, in pixels
     * @return The current AQuery object.
     */
    @NonNull
    public AQ translationX(float offset) {
        raw().setTranslationX(offset);
        return this;
    }

    /**
     * Gets the vertical location of this view relative to its left position.
     *
     * @return The vertical location of this view relative to its left position.
     */
    public float translationY() {
        return raw().getTranslationY();
    }

    /**
     * Sets the vertical location of this view relative to its top position.
     *
     * @param offset  The vertical position of this view relative to its top position, in pixels
     * @return The current AQuery object.
     */
    @NonNull
    public AQ translationY(float offset) {
        raw().setTranslationY(offset);
        return this;
    }

    /**
     * Gets the depth location of this view relative to its elevation.
     *
     * @return The depth location of this view relative to its elevation.
     *
     * @throws  SDKVersionException If the SDK version is lower than 21.
     * @see SDKVersionException
     */
    public float translationZ() {
        if (Build.VERSION.SDK_INT < 21)
            throw new SDKVersionException();
        else
            return raw().getTranslationZ();
    }

    /**
     * Sets the depth location of this view relative to its elevation.
     *
     * @param offset  The horizontal position of this view relative to its elevation.
     * @return The current AQuery object.
     */
    @NonNull
    public AQ translationZ(float offset) {
        if (Build.VERSION.SDK_INT < 21)
            throw new SDKVersionException();
        else {
            raw().setTranslationZ(offset);
            return this;
        }
    }

    /**
     * Gets the ViewPropertyAnimator of the view.
     *
     * @return The ViewPropertyAnimator of the view.
     */
    @NonNull
    public ViewPropertyAnimator animate() {
        return raw().animate();
    }

    /**
     * Applies an animation on the view
     * 
     * @param animation The animation to apply
     * @return The current AQuery object
     * 
     * @throws  IllegalArgumentException If animation is null
     */
    public AQ animate(@NonNull Animation animation) {
        requireNotNull(animation);
        raw().startAnimation(animation);
        return this;
    }

    /**
     * Brings the current View to the front of the view hierarchy.
     *
     * @return The current AQuery object.
     */
    @NonNull
    public AQ bringToFront() {
        raw().bringToFront();
        return this;
    }

    /**
     * Returns the text of the TextView.
     *
     * @throws  IllegalViewActionException If the view isn't a TextView.
     * @see IllegalViewActionException
     */
    @NonNull
    public String text() {
        if (raw() instanceof EditText) {
            return ((EditText) raw()).getText().toString();
        } else if (raw() instanceof TextView) {
            return (String) ((TextView) raw()).getText();
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets the text of the view.
     *
     * @param  text The text to set.
     * @return Current AQuery object
     *
     * @throws  IllegalViewActionException If the view isn't a TextView or an EditText.
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ text(String text) {
        if (raw() instanceof EditText) {
            ((EditText) raw()).setText(text);
        } else if (raw() instanceof TextView) {
            ((TextView) raw()).setText(text);
        } else {
            throw new IllegalViewActionException();
        }
        
        return this;
    }

    /**
     * Sets the text size of the View
     *
     * @param textSize The text size to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view isn't a TextView or EditText
     * @see IllegalViewActionException
     */
    public AQ textSize(float textSize) {
        if (raw() instanceof EditText) {
            ((EditText) raw()).setTextSize(textSize);
        } else if (raw() instanceof TextView) {
            ((TextView) raw()).setTextSize(textSize);
        } else {
            throw new IllegalViewActionException();
        }

        return this;
    }

    /**
     * Gets the font size
     *
     * @return The font size in pixels
     *
     * @throws IllegalViewActionException If the view isn't a TextView or EditText
     * @see IllegalViewActionException
     */
    public float textSize() {
        if (raw() instanceof EditText) {
            return ((EditText) raw()).getTextSize();
        } else if (raw() instanceof TextView) {
            return ((TextView) raw()).getTextSize();
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets the typeface of the view
     *
     * @param path The path to the font file
     * @return The current AQuery object
     *
     * @throws IllegalArgumentException If path is null
     *
     * @throws IllegalViewActionException If the view isn't a TextView or EditText
     * @see IllegalViewActionException
     */
    public AQ typeFace(@NonNull String path) {
        requireNotNull(path);
        Typeface t = Typeface.createFromAsset(context().getAssets(), path);
        return typeFace(t);
    }

    /**
     * Sets the typeface of the view
     *
     * @param typeface The typeface to set
     * @return The current AQuery object
     *
     * @throws IllegalArgumentException If typeface is null
     *
     * @throws IllegalViewActionException If the view isn't a TextView or EditText
     * @see IllegalViewActionException
     */
    public AQ typeFace(@NonNull Typeface typeface) {
        requireNotNull(typeface);
        if (raw() instanceof EditText) {
            ((EditText) raw()).setTypeface(typeface);
        } else if (raw() instanceof TextView) {
            ((TextView) raw()).setTypeface(typeface);
        } else {
            throw new IllegalViewActionException();
        }

        return this;
    }

    /**
     * Gets the typeface of the view
     *
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view isn't a TextView or EditText
     * @see IllegalViewActionException
     */
    public Typeface typeface() {
        if (raw() instanceof EditText) {
            return ((EditText) raw()).getTypeface();
        } else if (raw() instanceof TextView) {
           return ((TextView) raw()).getTypeface();
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a Bitmap to an ImageView.
     *
     * @param  bitmap The bitmap to set.
     * @return AQ Current AQuery object
     *
     * @throws  IllegalViewActionException If the view isn't an ImageView.
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ bitmap(Bitmap bitmap) {
        try {
            ((ImageView) raw()).setImageBitmap(bitmap);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a drawable as a view's background
     *
     * @param background The drawable to set as background
     * @return The current AQuery object
     *
     * @throws IllegalArgumentException If the background drawable is null
     */
    @NonNull
    public AQ background(@NonNull Drawable background) {
        requireNotNull(background);
        raw().setBackground(background);
        return this;
    }


    /**
     * Sets the maximum value for a SeekBar
     *
     * @param max The max value to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If view isn't a SeekBar
     * @see IllegalViewActionException
     */
    @NonNull
    public AQ max(int max) {
        if (raw() instanceof SeekBar) {
            ((SeekBar) raw()).setMax(max);
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Gets the max value of a SeekBar
     *
     * @return The max value of the SeekBar
     *
     * @throws IllegalViewActionException f view isn't a SeekBar
     * @see IllegalViewActionException
     */
    public int max() {
        if (raw() instanceof SeekBar) {
            return ((SeekBar) raw()).getMax();
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets the minimum value for a SeekBar
     *
     * @param min The min value to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If view isn't a SeekBar
     * @see IllegalViewActionException
     *
     * @throws SDKVersionException If the SDK version is less than O
     * @see SDKVersionException
     */
    @NonNull
    public AQ min(int min) {
        if (raw() instanceof SeekBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((SeekBar) raw()).setMin(min);
            } else {
                throw new SDKVersionException();
            }
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Gets the min value of a SeekBar
     *
     * @return The min value of the SeekBar
     *
     * @throws IllegalViewActionException If view isn't a SeekBar
     * @see IllegalViewActionException
     *
     * @throws SDKVersionException If the SDK version is less than O
     * @see SDKVersionException
     */
    public int min() {
        if (raw() instanceof SeekBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return ((SeekBar) raw()).getMin();
            } else {
                throw new SDKVersionException();
            }
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets the progress of a SeekBar
     *
     * @param progress The progress to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view isn't a SeekBar
     * @see IllegalViewActionException
     *
     * @throws SDKVersionException If the SDK version is less that version N
     * @see SDKVersionException
     */
    @NonNull
    public AQ progress(int progress) {
        return progress(progress, false);
    }

    /**
     * Sets the progress of a SeekBar
     *
     * @param progress The progress to set
     * @param animate If true, the change of progress will be animated
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view isn't a SeekBar
     * @see IllegalViewActionException
     *
     * @throws SDKVersionException If the SDK version is less that version N
     * @see SDKVersionException
     */
    @NonNull
    public AQ progress(int progress, boolean animate) {
        if (raw() instanceof SeekBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ((SeekBar) raw()).setProgress(progress, animate);
            } else {
                throw new SDKVersionException();
            }
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Gets the current progress of the SeekBar
     * @return The current progress of the SeekBar
     *
     * @throws IllegalViewActionException If the view is not a ViewGroup
     * @see IllegalViewActionException
     */
    public int progress() {
        if (raw() instanceof SeekBar) {
            return ((SeekBar) raw()).getProgress();
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a listener for when the user has stopped a touch gesture
     *
     * Note: Do not use this method in conjunction with the methods startTrackingTouch() and
     * progressChanged(). If you want to listen to multiple seek bar events, use seekBarChanged().
     *
     * @param eventListener The listener to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view is not a SeekBar
     * @see IllegalViewActionException
     *
     * @throws IllegalArgumentException If the listener is null
     */
    public AQ stopTrackingTouch(@NonNull final EventListener eventListener) {
        requireNotNull(eventListener);
        if (raw() instanceof SeekBar) {
            ((SeekBar) raw()).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    eventListener.onEvent(new AQ(seekBar));
                }
            });
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a listener for when the user has started a touch gesture
     *
     * Note: Do not use this method in conjunction with the methods stopTrackingTouch() and
     * progressChanged(). If you want to listen to multiple seek bar events, use seekBarChanged().
     *
     * @param eventListener The listener to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view is not a SeekBar
     * @see IllegalViewActionException
     *
     * @throws IllegalArgumentException If the listener is null
     */
    public AQ startTrackingTouch(@NonNull final EventListener eventListener) {
        requireNotNull(eventListener);
        if (raw() instanceof SeekBar) {
            ((SeekBar) raw()).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    eventListener.onEvent(new AQ(seekBar));
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a listener for when the seek bar's progress has changed
     *
     * Note: Do not use this method in conjunction with the methods stopTrackingTouch() and
     * startTrackingTouch(). If you want to listen to multiple seek bar events, use seekBarChanged().
     *
     * @param listener The listener to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view is not a SeekBar
     * @see IllegalViewActionException
     *
     * @throws IllegalArgumentException If the listener is null
     */
    public AQ progressChanged(@NonNull final SeekBarProgressChangedListener listener) {
        requireNotNull(listener);
        if (raw() instanceof SeekBar) {
            ((SeekBar) raw()).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    listener.onProgressChanged(new AQ(seekBar), i, b);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a listener for seek bar change events
     *
     * Note: Do not use this method in conjunction with the methods stopTrackingTouch(),
     * startTrackingTouch() and progressChanged().
     *
     * @param listener The listener to set
     * @return The current AQuery object
     *
     * @throws IllegalViewActionException If the view is not a SeekBar
     * @see IllegalViewActionException
     *
     * @throws IllegalArgumentException If the listener is null
     */
    public AQ seekBarChanged(@NonNull final SeekBar.OnSeekBarChangeListener listener) {
        requireNotNull(listener);
        if (raw() instanceof SeekBar) {
            ((SeekBar) raw()).setOnSeekBarChangeListener(listener);
            return this;
        } else {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Gets the width of the view.
     *
     * @return int the width of the view.
     */
    public int width() {
        return raw().getWidth();
    }

    /**
     * Sets the view's width.
     *
     * @param width The new width.
     * @return The current AQuery object.
     */
    @NonNull
    public AQ width(int width) {
        ViewGroup.LayoutParams params = raw().getLayoutParams();
        params.width = width;
        raw().requestLayout();
        return this;
    }

    /**
     * Gets the height of the view.
     *
     * @return int the height of the view.
     */
    public int height() {
        return raw().getHeight();
    }

    /**
     * Sets the view's width.
     *
     * @param height The new height.
     * @return The current AQuery object.
     */
    @NonNull
    public AQ height(int height) {
        ViewGroup.LayoutParams params = raw().getLayoutParams();
        params.height = height;
        raw().requestLayout();
        return this;
    }


    /**
     * Inflates a layout. Note that this method doesn't attach the layout to its parent.
     *
     * @param context The required context.
     * @param id The layout's Id.
     * @param parent The layout's parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @throws  IllegalArgumentException If the context or parent is null.
     */
    @NonNull
    public static AQ inflate(@NonNull Context context, int id, @NonNull ViewGroup parent) {
        requireNotNull(context, parent);
        return inflate(context, id, parent, false);
    }

    /**
     * Inflates a layout. Note that this method doesn't attach the layout to its parent.
     *
     * @param context The required context.
     * @param id The layout's Id.
     * @param parent The layout's parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @throws  IllegalArgumentException If the context or parent is null or
     * the parent view is a ViewGroup.
     */
    @NonNull
    public static AQ inflate(@NonNull Context context, int id, @NonNull AQ parent) {
        requireNotNull(context, parent);
        return inflate(context, id, parent, false);
    }

    /**
     * Inflates a layout.
     *
     * @param context The required context.
     * @param id The layout's Id.
     * @param parent The layout's parent.
     * @param attachToParent If true the inflated layout will be attached to its parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @throws  IllegalArgumentException If the context or parent is null.
     */
    @NonNull
    public static AQ inflate(Context context, int id, ViewGroup parent, boolean attachToParent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(id, parent, attachToParent);
        return new AQ(layout);
    }

    /**
     * Inflates a layout.
     *
     * @param context The required context.
     * @param id The layout's Id.
     * @param parent The AQuery object containing the layout's parent.
     * @param attachToParent If true the inflated layout will be attached to its parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @throws  IllegalArgumentException If the context or parent is null or
     * the parent view isn't a ViewGroup.
     */
    @NonNull
    public static AQ inflate(Context context, int id, AQ parent, boolean attachToParent) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(id, (ViewGroup) parent.raw(), attachToParent);
            return new AQ(layout);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The parent view isn't a ViewGroup.");
        }
    }

    /**
     * Displays a toast message. Note that the toast's duration is Toast.LENGTH_SHORT. Call
     * toastLong() to display a toast with duration Toast.LENGTH_LONG.
     *
     * @param context The required context.
     * @param msg The message to display.
     *
     * @throws  IllegalArgumentException If context or msg is null.
     */
    public static void toast(Context context, String msg) {
        requireNotNull(context, msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a toast message. Note that the toast's duration is Toast.LENGTH_LONG. Call
     * toast() to display a toast with duration Toast.LENGTH_SHORT.
     *
     * @param context The required context.
     * @param msg The message to display.
     *
     * @throws  IllegalArgumentException If context or msg is null.
     */
    public static void toastLong(Context context, String msg) {
        requireNotNull(context, msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Gets the number of pixels corresponding to a DP value.
     *
     * @param context Required context.
     * @param dp The number of DP pixels to convert.
     * @return The number of pixels corresponding to a DP value.
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Gets the number of DP pixels corresponding to a pixel value.
     *
     * @param context Required context.
     * @param px The number of pixels to convert.
     * @return The number of DP pixels corresponding to a pixel value.
     */
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Gets the number of pixels corresponding to a SP value.
     *
     * @param context Required context.
     * @param sp The SP value.
     * @return The number of DP pixels corresponding to a pixel value.
     */
    public static int spToPx(Context context, float sp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

    /**
     * Return the screen height in pixels.
     *
     * @param context Required context.
     * @return The screen height in pixels.
     *
     * @throws  IllegalArgumentException If context isn't an Activity
     */
    public static int getScreenHeight(Context context) {
        try {
            DisplayMetrics m = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(m);
            return m.heightPixels;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Context is not an Activity");
        }
    }

    /**
     * Return the screen width in pixels.
     *
     * @param context Required context.
     * @return The screen width in pixels.
     *
     * @throws  IllegalArgumentException If context isn't an Activity
     */
    public static int getScreenWidth(Context context) {
        try {
            DisplayMetrics m = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(m);
            return m.widthPixels;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Context is not an Activity");
        }
    }

    /**
     * Returns a {@link Validator} object.
     *
     * @return The {@link Validator} object.
     */
    @NonNull
    public static Validator validator() {
        return new Validator();
    }

    /**
     * Checks if objects are null
     * @param params Array of objects to be checked
     */
    private static void requireNotNull(Object... params) {
        for (Object o : params) {
            if (o == null)
                throw new IllegalArgumentException("Parameter can't be null");
        }
    }
}
