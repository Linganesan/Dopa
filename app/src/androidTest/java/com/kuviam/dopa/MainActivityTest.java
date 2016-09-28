package com.kuviam.dopa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import junit.framework.TestCase;

/**
 * Created by linganesan on 9/8/15.
 */
public class MainActivityTest extends TestCase {

    protected Context mContext;
    private Context mTestContext;

    public void setUp() throws Exception {
        super.setUp();

        setContext(getContext());

    }

    public void testOnCreate() throws Exception {

    }

    public void testInitSampleData() throws Exception {

    }

    public void testSet_Add_Update_Screen() throws Exception {

    }

    public void testOnBackPressed() throws Exception {

    }
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void setContext(Context context) {
        mContext = context;
    }
    public Context getContext() {
        return mContext;
    }
    /**
     * Test context can be used to access resources from the test's own package
     * as opposed to the resources from the test target package. Access to the
     * latter is provided by the context set with the {@link #setContext}
     * method.
     *
     * @hide
     */
    public void setTestContext(Context context) {
        mTestContext = context;
    }
    /**
     * @hide
     */
    public Context getTestContext() {
        return mTestContext;
    }
    /**
     * Asserts that launching a given activity is protected by a particular permission by
     * attempting to start the activity and validating that a {@link SecurityException}
     * is thrown that mentions the permission in its error message.
     *
     * Note that an instrumentation isn't needed because all we are looking for is a security error
     * and we don't need to wait for the activity to launch and get a handle to the activity.
     *
     * @param packageName The package name of the activity to launch.
     * @param className The class of the activity to launch.
     * @param permission The name of the permission.
     */
    public void assertActivityRequiresPermission(
            String packageName, String className, String permission) {
        final Intent intent = new Intent();
        intent.setClassName(packageName, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getContext().startActivity(intent);
            fail("expected security exception for " + permission);
        } catch (SecurityException expected) {
            assertNotNull("security exception's error message.", expected.getMessage());
            assertTrue("error message should contain " + permission + ".",
                    expected.getMessage().contains(permission));
        }
    }
    /**
     * Asserts that reading from the content uri requires a particular permission by querying the
     * uri and ensuring a {@link SecurityException} is thrown mentioning the particular permission.
     *
     * @param uri The uri that requires a permission to query.
     * @param permission The permission that should be required.
     */
    public void assertReadingContentUriRequiresPermission(Uri uri, String permission) {
        try {
            getContext().getContentResolver().query(uri, null, null, null, null);
            fail("expected SecurityException requiring " + permission);
        } catch (SecurityException expected) {
            assertNotNull("security exception's error message.", expected.getMessage());
            assertTrue("error message should contain " + permission + ".",
                    expected.getMessage().contains(permission));
        }
    }
    /**
     * Asserts that writing to the content uri requires a particular permission by inserting into
     * the uri and ensuring a {@link SecurityException} is thrown mentioning the particular
     * permission.
     *
     * @param uri The uri that requires a permission to query.
     * @param permission The permission that should be required.
     */
    public void assertWritingContentUriRequiresPermission(Uri uri, String permission) {
        try {
            getContext().getContentResolver().insert(uri, new ContentValues());
            fail("expected SecurityException requiring " + permission);
        } catch (SecurityException expected) {
            assertNotNull("security exception's error message.", expected.getMessage());
            assertTrue("error message should contain \"" + permission + "\". Got: \""
                            + expected.getMessage() + "\".",
                    expected.getMessage().contains(permission));
        }
    }

}