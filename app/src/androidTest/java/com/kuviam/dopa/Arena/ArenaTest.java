package com.kuviam.dopa.Arena;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

import com.kuviam.dopa.MainActivity;
import com.kuviam.dopa.R;

import junit.framework.TestCase;

/**
 * Created by linganesan on 9/8/15.
 */
public class ArenaTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity newArenaTestActivity;
    private Button newlocusbtn;
    private Button donebtn;
    private Intent mLaunchIntent;

    public ArenaTest(String pkg, Class<MainActivity> activityClass) {
        super(pkg, activityClass);
    }

    protected void setUp() throws Exception {


            super.setUp();
            //Sets the initial touch mode for the Activity under test. This must be called before
            //getActivity()
            setActivityInitialTouchMode(true);
            //Get a reference to the Activity under test, starting it if necessary.
            newArenaTestActivity = getActivity();
            mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), Arena.class);

            //Get references to all views
            newlocusbtn = (Button) newArenaTestActivity.findViewById(R.id.btndisnew);
            ;
        }



    public void testOnCreate() throws Exception {

    }

    public void testInitSampleData() throws Exception {

    }

    public void testDefaultSetup() throws Exception {

    }

    public void testSet_Add_Update_Screen() throws Exception {

    }

    public void testOnBackPressed() throws Exception {

    }
}