package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.kuviam.dopa.MainActivity;
import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Discipline_text_list;
import com.kuviam.dopa.model.Discipline_text_listDao;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;
import com.kuviam.dopa.model.Run;
import com.kuviam.dopa.model.RunDao;
import com.kuviam.dopa.model.Run_discipline_item_list;
import com.kuviam.dopa.model.Run_discipline_item_listDao;

import java.util.ArrayList;
import java.util.List;

public class Score extends Activity {
    private Button done;
    private TableLayout table;
    private Discipline discipline;
    private Locus locus;
    private Run run;
    private Run_discipline_item_list runitem;
    private List<Discipline_text_list> dislist;
    private List<Run_discipline_item_list> runlist;

    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private LocusDao mLocusDao;
    private DisciplineDao mDisciplineDao;
    private RunDao mRunDao;
    private Run_discipline_item_listDao mRun_discipline_item_listDao;
    private Discipline_text_listDao mDiscipline_text_list;

    private List<Discipline> disciplines;
    private List<Locus> loci;
    private long runid;
    private int dissize;
    private ArrayList<String> missinputs;
    private int miss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent mIntent = getIntent();
        runid = mIntent.getLongExtra("longVariableName", 0);
        String val = Long.toString(runid);
        //showToast(val);

        Set_Add_Update_Screen();

        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();

        mRunDao = mDaoSession.getRunDao();
        mRun_discipline_item_listDao = mDaoSession.getRun_discipline_item_listDao();
        run = mRunDao.load(runid);


        setupDb();

        runlist = run.getRun_discipline_item_listList();


        findoutMisses();

        // Go through each item in the array
        setupTable();


        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(Score.this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });


    }

    private void setupTable() {
        setupTablelayout();
        for (int current = 0; current < dissize; current++) {

            if (!missinputs.get(current).equals("")) {
                // Create a TableRow and give it an ID
                TableRow tr = new TableRow(this);
                tr.setId(100 + current);
                tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
                //Create a TextView to the missed discipline item ID
                TextView valueTV = new TextView(this);
                int realvalue = current + 1;
                valueTV.setId(current);
                valueTV.setText(String.valueOf(realvalue) + ":");
                valueTV.setTextColor(Color.BLACK);
                valueTV.setTextSize(20);
                valueTV.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                tr.addView(valueTV);

                //Create a TextView to the missed discipline item
                TextView labelTV = new TextView(this);
                labelTV.setId(200 + current);
                labelTV.setTextSize(20);
                //labelTV.setText(missinputs.get(current).toString());
                labelTV.setText(missinputs.get(current).toString());
                labelTV.setTextColor(Color.BLACK);
                labelTV.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                tr.addView(labelTV);


                // Add the TableRow to the TableLayout
                table.addView(tr, new TableLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private void setupTablelayout() {
        int current = (int) (Math.random() * 10);

        // Create a Missed items heading TableRow and give it an ID
        TableRow Missed = new TableRow(this);
        Missed.setId(100 + current);
        Missed.setBackgroundColor(Color.RED);
        Missed.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        //Create a TextView to the missed discipline item ID
        TextView Missed1 = new TextView(this);
        Missed1.setId(current);
        Missed1.setText("Missed Items");
        Missed1.setTextColor(Color.BLACK);
        Missed1.setTextSize(20);
        Missed1.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Missed.addView(Missed1);

        //Create a TextView to the missed discipline item
        TextView Missed2 = new TextView(this);
        Missed2.setId(200 + current);
        Missed2.setTextSize(20);
        Missed2.setText(String.valueOf(miss));
        Missed2.setTextColor(Color.BLACK);
        Missed2.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Missed.addView(Missed2);


        // Add the Title TableRow to the TableLayout
        table.addView(Missed, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        /***************************************************/
        // Create a Missed items heading TableRow and give it an ID
        TableRow success = new TableRow(this);
        success.setBackgroundColor(Color.GREEN);
        success.setId(100 + current);
        success.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        //Create a TextView to the missed discipline item ID
        TextView success1 = new TextView(this);
        success1.setId(current);
        success1.setText("Succeed  Items");
        success1.setTextColor(Color.BLACK);
        success1.setTextSize(20);
        success1.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        success.addView(success1);

        //Create a TextView to the missed discipline item
        TextView success2 = new TextView(this);
        success2.setId(200 + current);
        success2.setTextSize(20);
        success2.setText(String.valueOf(dissize - miss));
        success2.setTextColor(Color.BLACK);
        success2.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        success.addView(success2);


        // Add the Title TableRow to the TableLayout
        table.addView(success, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        /***************************************************/


        // Create a Missed items heading TableRow and give it an ID
        TableRow head = new TableRow(this);
        head.setId(100 + current);
        head.setBackgroundColor(Color.GRAY);
        head.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        //Create a TextView to the missed discipline item ID
        TextView headno = new TextView(this);
        headno.setId(current);
        headno.setText("No");
        headno.setTextColor(Color.BLACK);
        headno.setTextSize(20);
        headno.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        head.addView(headno);

        //Create a TextView to the missed discipline item
        TextView headtitle = new TextView(this);
        headtitle.setId(200 + current);
        headtitle.setTextSize(20);
        headtitle.setText("Missed Items");
        headtitle.setTextColor(Color.BLACK);
        headtitle.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        head.addView(headtitle);


        // Add the Title TableRow to the TableLayout
        table.addView(head, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));


    }

    private void setupDb() {
        if (run != null) {
            //Identify the Discipline
            mDisciplineDao = mDaoSession.getDisciplineDao();
            disciplines = mDisciplineDao.loadAll();
            String disname = run.getDiscipline().toString();
            for (Discipline dis : disciplines) {
                if (disname.equals(dis.getName())) {
                    discipline = dis;
                }
            }
            //Identify the Locus
            mLocusDao = mDaoSession.getLocusDao();
            loci = mLocusDao.loadAll();
            String locusname = run.getLocus();
            for (Locus lc : loci) {
                if (locusname.equals(lc.getName())) {
                    locus = lc;
                }
            }
            //get discipline list for findout the size of userinput array
            mDiscipline_text_list = mDaoSession.getDiscipline_text_listDao();
            dislist = discipline.getDiscipline_text_listList();
            dissize = dislist.size();

            missinputs = new ArrayList<String>(dissize);
            for (int i = 0; i < dissize; i++) {
                missinputs.add("");
            }

        } else {
            showToast("ERROR! Please reopen");
        }
    }

    public void findoutMisses() {
        //showToast(String.valueOf(runlist.size()));
        for (int i = 0; i < runlist.size(); i++) {
            String temp = dislist.get((int) runlist.get(i).getDiscipline_item()).getItem();
            if (!runlist.get(i).getStatus()) {
                missinputs.set(i, temp);
                miss++;
                Log.d(String.valueOf(i), temp);
            } else {
                missinputs.set(i, "");
            }

        }
    }

    private void Set_Add_Update_Screen() {
        done = (Button) findViewById(R.id.btnscoreok);
        table = (TableLayout) findViewById(R.id.scoretable);
    }

    //show messages in screen
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Score.this, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
        overridePendingTransition(0, 0);
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
}
