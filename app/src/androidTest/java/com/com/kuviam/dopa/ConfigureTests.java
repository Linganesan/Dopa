package com.com.kuviam.dopa;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.kuviam.dopa.Arena.Configure;
import com.kuviam.dopa.R;

/**
 * Created by linganesan on 8/23/15.
 */
public class ConfigureTests extends ActivityInstrumentationTestCase2<Configure> {

    private Configure newconfigureTestActivity;
    private TextView configureTestText;
    private Button done;

    public ConfigureTests() {
        super(Configure.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        newconfigureTestActivity = getActivity();
        configureTestText =(TextView) newconfigureTestActivity.findViewById(R.id.selectedloucs);

    }
}
