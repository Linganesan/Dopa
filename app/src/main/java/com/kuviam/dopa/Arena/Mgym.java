package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Discipline_text_list;
import com.kuviam.dopa.model.Discipline_text_listDao;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;
import com.kuviam.dopa.model.Locus_text_list;
import com.kuviam.dopa.model.Locus_text_listDao;
import com.kuviam.dopa.model.Run;
import com.kuviam.dopa.model.RunDao;

import java.util.List;

public class Mgym extends Activity {
    Button done;
    ImageButton next, pre, hint;
    TextSwitcher textsw;
    Chronometer timer;
    Discipline discipline;
    Locus locus;
    Run run;
    List<Discipline_text_list> dislist;
    List<Locus_text_list> lclist;

    GreenDaoApplication mApplication;
    DaoSession mDaoSession;
    LocusDao mLocusDao;
    DisciplineDao mDisciplineDao;
    RunDao mRunDao;
    Discipline_text_listDao mDiscipline_text_list;
    Locus_text_listDao mLocus_text_list;
    List<Locus_text_list> hints;

    List<Discipline> disciplines;
    List<Locus> loci;
    long runid;
    int counter = 0;
    int dissize, lcsize;
    String showhint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mgym);
        Intent mIntent = getIntent();
        runid = mIntent.getLongExtra("longVariableName", 0);
        String val = Long.toString(runid);
        showToast(val);

        Set_Add_Update_Screen();

        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();

        mRunDao = mDaoSession.getRunDao();
        run = mRunDao.load(runid);

        setupDb();

        textsw.setFactory(new ViewFactory() {

            @Override
            public View makeView() {
                TextView textView = new TextView(Mgym.this);
                textView.setTextSize(40);
                textView.setTextColor(Color.RED);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
               // textView.setShadowLayer(10, 10, 10, Color.BLACK);
                return textView;
            }
        });


        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        textsw.setInAnimation(in);
        textsw.setOutAnimation(out);

        pre.setEnabled(false);
        textsw.setText(dislist.get(counter).getItem().toString());
        showhint=hints.get(counter).getItem().toString();

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(Mgym.this, Recall.class);

                startActivity(myIntent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ++counter;
                if (counter >= 0 && counter < dissize) {
                    textsw.setText(dislist.get(counter).getItem().toString());
                    showhint=hints.get(counter).getItem().toString();
                    pre.setEnabled(true);

                } else {
                    next.setEnabled(false);
                    --counter;
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                --counter;
                if (counter > -1 && counter <= dissize) {
                    textsw.setText(dislist.get(counter).getItem().toString());
                    showhint=hints.get(counter).getItem().toString();
                    next.setEnabled(true);
                } else {
                    pre.setEnabled(false);
                    ++counter;
                }
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                showToast(showhint);
            }
        });

    }

    private void setupDb() {
        if (run != null) {
            showToast("uggkk");

            mDisciplineDao = mDaoSession.getDisciplineDao();
            disciplines = mDisciplineDao.loadAll();
            String disname = run.getDiscipline().toString();
            for (Discipline dis : disciplines) {
                if (disname.equals(dis.getName())) {
                    discipline = dis;
                }
            }

            mLocusDao = mDaoSession.getLocusDao();
            loci = mLocusDao.loadAll();
            String locusname = run.getLocus();
            for (Locus lc : loci) {
                if (locusname.equals(lc.getName())) {
                    locus = lc;
                }
            }

            hints=locus.getLocus_text_listList();


            mDiscipline_text_list = mDaoSession.getDiscipline_text_listDao();
            dislist = discipline.getDiscipline_text_listList();
            dissize = dislist.size();

            if (dissize <= 0) {
                next.setEnabled(false);
                pre.setEnabled(false);
            }


        } else {

            showToast("MANI FOOL");
        }
    }

    private void Set_Add_Update_Screen() {

        done = (Button) findViewById(R.id.btnmgymdone);
        next = (ImageButton) findViewById(R.id.btnmgymnext);
        pre = (ImageButton) findViewById(R.id.btnmgymback);
        hint = (ImageButton) findViewById(R.id.btnmgymhint);
        timer = (Chronometer) findViewById(R.id.mgmtime);
        textsw = (TextSwitcher) findViewById(R.id.mgymtextSwitcher);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mgym, menu);
        return true;
    }

    void showToast(CharSequence msg) {

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
