package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Recall extends Activity {
    Button check;
    ImageButton next,pre,hint;
    Chronometer timer;
    EditText entertext;
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
    List<Locus_text_list> hints;

    List<Discipline> disciplines;
    List<Locus> loci;
    long runid;
    int counter = 0;
    int dissize;
    String showhint;
    ArrayList<String> userinputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recall);
        Intent mIntent = getIntent();
        runid = mIntent.getLongExtra("longVariableName",0);
        String val = Long.toString(runid);
        showToast(val);

        Set_Add_Update_Screen();

        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();

        mRunDao = mDaoSession.getRunDao();
        run = mRunDao.load(runid);

        setupDb();



        pre.setEnabled(false);
        showhint=hints.get(counter).getItem().toString();




        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                showToast(Integer.valueOf(dissize).toString());
                // Start NewActivity.class
                Intent myIntent = new Intent(Recall.this, Score.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(entertext.getText().toString()!=null && counter==0) {
                    userinputs.add(counter, entertext.getText().toString());
                   // entertext.setText("");
                }
                ++counter;

                if (counter >= 0 && counter <=dissize) {
                    if(entertext.getText().toString()!=null ) {
                        userinputs.add(counter, entertext.getText().toString());
                        // entertext.setText("");
                    }

                    try{entertext.setText(userinputs.get(counter).toString());
                        }catch (Exception e){
                        entertext.setText("");
                        }
                   try{
                        showhint = hints.get(counter).getItem().toString();
                    }catch (Exception e){}
                    pre.setEnabled(true);
                } else {
                    next.setEnabled(false);
                    --counter;
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try {
                    userinputs.add(counter, entertext.getText().toString());
                }catch (Exception e){}

                --counter;


                if (counter > -1 && counter <= dissize) {

                    try{ entertext.setText(userinputs.get(counter).toString());
                    }catch (Exception e){
                        entertext.setText("");
                        userinputs.add(counter, entertext.getText().toString());
                    }


                    try{
                        showhint = hints.get(counter).getItem().toString();
                    }catch (Exception e){}

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

    private void Set_Add_Update_Screen(){

        check= (Button) findViewById(R.id.btnrcdone);
        next=(ImageButton) findViewById(R.id.btnrcnext);
        pre=(ImageButton) findViewById(R.id.btnrcback);
        hint=(ImageButton) findViewById(R.id.btnrchint);
        timer=(Chronometer) findViewById(R.id.rctime);
        entertext=(EditText)findViewById(R.id.rcedittext);


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

            userinputs = new ArrayList<String>(dissize);
            for(int i=0;i<=dissize;i++)
               userinputs.add(i,null);


        } else {

            showToast("MANI FOOL");
        }
    }

    void showToast(CharSequence msg) {

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recall, menu);
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
}
