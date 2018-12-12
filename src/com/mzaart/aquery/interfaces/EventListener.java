package com.mzaart.aquery.interfaces;

import com.mzaart.aquery.AQ;

/**
 * This interface is to be implemented by classes that listen for special View events
 */
public interface EventListener {

    /**
     * This method is called when the event is triggered
     * @param view The view responsible for the event
     */
    void onEvent(AQ view);
}
