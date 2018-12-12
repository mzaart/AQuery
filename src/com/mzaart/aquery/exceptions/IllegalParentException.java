package com.mzaart.aquery.exceptions;

/**
 * Signals that a View's parent isn't a View
 *
 * @see android.view.View
 * @see android.view.ViewGroup
 */
public class IllegalParentException extends RuntimeException {

    public IllegalParentException() {
        super("The view's parent isn't a view.");
    }
}
