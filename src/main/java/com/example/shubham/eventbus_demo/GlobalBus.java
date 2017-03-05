package com.example.shubham.eventbus_demo;

/**
 * Created by shubh on 2/22/2017.
 */

import org.greenrobot.eventbus.EventBus;

public class GlobalBus {
    private static EventBus sBus;
    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }
}

