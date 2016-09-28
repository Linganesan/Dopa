package com.kuviam.dopa;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.kuviam.dopa.R;
import com.kuviam.dopa.mindpalace.NewLocus;

/**
 * Created by linganesan on 8/23/15.
 */

public class NewLocusTests extends ActivityInstrumentationTestCase2<NewLocus> {

    private NewLocus newLocusTestActivity;
    private Button addlocus;
    private Button done;

    public NewLocusTests() {
        super(NewLocus.class);
    }

    //verifies existence of that object
    public void testActivityExists() {

        assertNotNull(newLocusTestActivity);
    }

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        //Sets the initial touch mode for the Activity under test. This must be called before
        //getActivity()
        setActivityInitialTouchMode(true);
        //Get a reference to the Activity under test, starting it if necessary.
        newLocusTestActivity = getActivity();

        //Get references to all views
        addlocus = (Button) newLocusTestActivity.findViewById(R.id.btnlcadd);
        done = (Button) newLocusTestActivity.findViewById(R.id.btnlcdone);
    }

    /* Method to test pre conditions*/
    /* To test whether the objects of first screen are not null*/
    public void testPreconditions() {
        assertNotNull("FirstScreenTestActivity is null", newLocusTestActivity);
        assertNotNull("AddLocus button is null", addlocus);
        assertNotNull("Done button is null", done);
    }

    /* Method to test the buttons*/
    /* Whether the text related to each button are correctly displayed*/
    public void testScreenButtons() {
        //String expected = newLocusTestActivity.getResources().getString(R.string.);


    }
}
