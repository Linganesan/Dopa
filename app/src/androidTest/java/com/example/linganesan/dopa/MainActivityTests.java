package com.example.linganesan.dopa;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by linganesan on 7/26/15.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {


    public MainActivityTests() {
        super(MainActivity.class);
    }

    //verifies existence of that object
    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }





}
