package com.mzaart.aquery.exceptions;

/**
 * Signals that a method that is not supported by the current SDK version is called
 *
 * <pre>
 *     {@code
 *     // suppose that SDK version is 19
 *     AQ(this, R.id.some_view).z(2); // throws SDKVersionException
 *     }
 * </pre>
 */
public class SDKVersionException extends RuntimeException {

    public SDKVersionException() {
        super("The requested action is not supported by the current SDK version.");
    }
}
