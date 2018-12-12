package com.mzaart.aquery.exceptions;

/**
 * Signals that an illegal action is requested from a View.
 *
 * <pre>
 *     {@code
 *     Button button = (Button) findViewById(R.id.some_button);
 *     AQ(button).text("I am setting text on a button"); // throws IllegalViewActionException
 *     }
 * </pre>
 */
public class IllegalViewActionException extends RuntimeException {

    public IllegalViewActionException() {
        super("Can't perform action on view.");
    }
}
