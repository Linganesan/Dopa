package com.com.kuviam.dopa;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.kuviam.dopa.MainActivity;
import com.kuviam.dopa.R;
import com.kuviam.dopa.mindpalace.NewLocus;

/**
 * Created by linganesan on 7/26/15.
 */
public class MainActivityTests extends ActivityUnitTestCase<MainActivity> {

    private MainActivity newMainTestActivity;
    private Button Mindpalace;
    private Button Arena;
    private Intent mLaunchIntent;

    public MainActivityTests() {
        super(MainActivity.class);
    }

    //verifies existence of that object
    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    protected void setUp() throws Exception {

        super.setUp();

        //Get a reference to the Activity under test, starting it if necessary.
        newMainTestActivity = getActivity();

        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),MainActivity.class);


        //Get references to all views
        Mindpalace= (Button) newMainTestActivity .findViewById(R.id.btnmind);
        Arena = (Button) newMainTestActivity .findViewById(R.id.btnarena);
    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {
        startActivity(mLaunchIntent, null, null);
        assertNotNull("MainActivity is null", newMainTestActivity);
        assertNotNull("Mindpalace Button is null", Mindpalace);
        assertNotNull("Arena Button is null", Arena);
    }

    @MediumTest
    public void testMindpalaceButton_layout() {
        //Retrieve the top-level window decor view
        final View decorView = newMainTestActivity.getWindow().getDecorView();

        //Verify that the mClickMeButton is on screen
        ViewAsserts.assertOnScreen(decorView, Mindpalace);

        //Verify width and heights
        final ViewGroup.LayoutParams layoutParams = Mindpalace.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @MediumTest
    public void testMindpalaceButton_labelText() {
        //Verify that mClickMeButton uses the correct string resource
        final String expectedNextButtonText = newMainTestActivity.getString(R.string.MindpalaceButton);
        final String actualNextButtonText = Mindpalace.getText().toString();
        assertEquals(expectedNextButtonText, actualNextButtonText);
    }





}
