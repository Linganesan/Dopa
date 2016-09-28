package com.kuviam.dopa;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.kuviam.dopa.mindpalace.Mindpalace;

public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity newMainTestActivity;
    private ImageButton Mindpalacebtn;
    private ImageButton Arenabtn;
    private Intent mLaunchIntent;

    public MainActivityTests() {
        super(MainActivity.class);
    }

    //verifies existence of that object
    public void testActivityExists() {
        assertNotNull( newMainTestActivity);
    }

    protected void setUp() throws Exception {
        super.setUp();
        //Sets the initial touch mode for the Activity under test. This must be called before
        //getActivity()
        setActivityInitialTouchMode(true);
        //Get a reference to the Activity under test, starting it if necessary.
        newMainTestActivity = getActivity();
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);

        //Get references to all views
        Mindpalacebtn = (ImageButton) newMainTestActivity.findViewById(R.id.btnmind);
        Arenabtn = (ImageButton) newMainTestActivity.findViewById(R.id.btnarena);
    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {

        assertNotNull("MainActivity is null", newMainTestActivity);
        assertNotNull("Mindpalace Button is null", Mindpalacebtn);
        assertNotNull("Arena Button is null", Arenabtn);
    }

    @MediumTest
    public void testMindpalaceButton_layout() {
        //Retrieve the top-level window decor view
        final View decorView = newMainTestActivity.getWindow().getDecorView();

        //Verify that the mClickMeButton is on screen
        ViewAsserts.assertOnScreen(decorView, Mindpalacebtn);

        //Verify width and heights
        final ViewGroup.LayoutParams layoutParams = Mindpalacebtn.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }




}
