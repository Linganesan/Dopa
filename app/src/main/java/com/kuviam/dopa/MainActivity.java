package com.kuviam.dopa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kuviam.dopa.Arena.Arena;
import com.kuviam.dopa.Arena.Configure;
import com.kuviam.dopa.analytics.Analytics;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.mindpalace.Mindpalace;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Discipline_text_list;
import com.kuviam.dopa.model.Discipline_text_listDao;

import java.util.List;

public class MainActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private ImageButton arena, mindpalace, lib, anal;
    private ImageView diskView4;
    private Discipline custom;

    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private DisciplineDao mDisciplineDao;
    private Discipline_text_listDao mDiscipline_text_listDao;

    private List<Discipline> disciplines;

    private boolean analyticcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mDisciplineDao = mDaoSession.getDisciplineDao();
        mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();
        InitSampleData();
        // set screen
        Set_Add_Update_Screen();


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
            // AlertDialog code here
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle("Hi! MemoryLord");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Create your own MindPalace")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            }
                    );

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true);
            editor.commit();
        }

        arena.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        Arena.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });


        lib.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this, Help.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });

        mindpalace.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this, Mindpalace.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });

        anal.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this, Analytics.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });


        diskView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dictionary d = new Dictionary();
                custom = new Discipline();
                custom.setName("Default");
                mDisciplineDao.insertOrReplace(custom);
                List<Discipline_text_list> dfsf = custom.getDiscipline_text_listList();
                mDiscipline_text_listDao.deleteInTx(dfsf);
                mDaoSession.clear();
                String titem;
                Discipline_text_list temp;
                d.createDictionary();

                for (int i = 0; i < 10; i++) {
                    titem = d.getRandomWord();
                    temp = new Discipline_text_list();
                    temp.setDisciplineId(custom.getId());
                    temp.setItem(titem);
                    mDiscipline_text_listDao.insert(temp);

                }
                custom.setNo_of_items(10);
                mDisciplineDao.insertOrReplace(custom);

                Intent myIntent = new Intent(MainActivity.this, Configure.class);
                long longid = custom.getId();
                myIntent.putExtra("defaultVariableName", longid);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //  startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });

    }

    //checks Analytics function activation
    void InitSampleData() {
        mDisciplineDao = mDaoSession.getDisciplineDao();
        //mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();
        // list  =  new ArrayList<String>();
        disciplines = mDisciplineDao.loadAll();

        for (Discipline discipline : disciplines) {
            if (discipline.getRuns_to_sync() >= 3) {
                analyticcheck = true;
                break;
            }

        }

    }

    //Initialize the layout
    public void Set_Add_Update_Screen() {
        arena = (ImageButton) findViewById(R.id.btnarena);
        lib = (ImageButton) findViewById(R.id.lib);
        mindpalace = (ImageButton) findViewById(R.id.btnmind);
        anal = (ImageButton) findViewById(R.id.analytics);
        diskView4 = (ImageView) findViewById(R.id.logoclick);
        diskView4.setEnabled(false);
        if (analyticcheck) {
            anal.setEnabled(true);
            anal.setBackgroundColor(Color.GREEN);
        } else {
            anal.setEnabled(false);
            anal.setBackgroundColor(Color.RED);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Exit
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("EXIT");

        alert.setMessage("Do you want to exit?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }
}
