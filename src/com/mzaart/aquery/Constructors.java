package com.mzaart.aquery;

import android.content.Context;
import android.view.View;

import com.mzaart.aquery.exceptions.ViewNotFoundException;

/**
 * This class contains a set of utility methods, to be imported statically, that allow the
 * instantiation of AQuery objects in a syntactically similar way to JQuery.
 *
 * Note: This class isn't necessary to instantiate AQuery objects. You can still use the
 * 'new' keyword to create AQuery objects. However, this class is obsolete when using Kotlin
 * since objects in Kotlin are instantiated without the 'new' keyword.
 */
public class Constructors {

    /**
     * Instantiates an AQuery instance from a view.
     *
     * @param v The view to be used.
     *
     * @exception IllegalArgumentException If view is null.
     * @see  IllegalArgumentException
     */
    public static AQ AQ(View v) {
        return new AQ(v);
    }

    /**
     * Instantiates an AQuery instance from a context and sets
     * the Context's root view as the current view.
     *
     * @param context Context context
     *
     * @exception IllegalArgumentException If context is null or not an Activity.
     * @see  IllegalArgumentException
     */
    public static AQ AQ(Context context) {
        return new AQ(context);
    }

    /**
     * Instantiates an AQuery instance from a view given its Id.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param context The Context's context.
     * @param id The view's id.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @exception IllegalArgumentException If context is null or not an Activity.
     * @see  IllegalArgumentException
     */
    public static AQ AQ(Context context, int id) {
        return new AQ(context, id);
    }

    /**
     * Instantiates an AQuery instance from a view given its Id in the parent view.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param view The view's parent.
     * @param id The view's id.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @exception IllegalArgumentException If view is null.
     * @see  IllegalArgumentException
     */
    public static AQ AQ(View view, int id) {
        return new AQ(view, id);
    }

    /**
     * Instantiates an AQuery instance from a view given its Id in an AQuery object.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param aquery The containing AQuery object.
     * @param id The view's id.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @exception IllegalArgumentException If AQuery is null.
     * @see  IllegalArgumentException
     */
    public static AQ AQ(AQ aquery, int id) {
        return new AQ(aquery, id);
    }
}
