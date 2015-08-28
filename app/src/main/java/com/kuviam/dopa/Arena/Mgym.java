package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.kuviam.dopa.model.Locus_text_list;
import com.kuviam.dopa.model.Locus_text_listDao;
import com.kuviam.dopa.model.Run;
import com.kuviam.dopa.model.RunDao;

import java.util.List;

public class Mgym extends Activity {

    private MalibuCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private TextView text;
    private Button done;
    private ImageButton next, pre, hint;
    private TextSwitcher textsw;
    private Chronometer timer;
    private Discipline discipline;
    private Locus locus;
    private Run run;
    private List<Discipline_text_list> dislist;
    private List<Locus_text_list> lclist;

    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private LocusDao mLocusDao;
    private DisciplineDao mDisciplineDao;
    private RunDao mRunDao;
    private Discipline_text_listDao mDiscipline_text_list;
    private Locus_text_listDao mLocus_text_list;
    private List<Locus_text_list> hints;

    private List<Discipline> disciplines;
    private List<Locus> loci;
    private long runid;
    private int counter = 0;
    private int dissize;
    private String showhint;

    private long startTime;
    private final long interval = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mgym);
        Intent mIntent = getIntent();
        runid = mIntent.getLongExtra("longVariableName", 0);
        //String val = Long.toString(runid);
        //showToast(val);

        Set_Add_Update_Screen();


        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();

        mRunDao = mDaoSession.getRunDao();
        run = mRunDao.load(runid);

        setupDb();

        countDownTimer = new MalibuCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime));
        startTimer();
        //setup textswitcher
        textsw.setFactory(new ViewFactory() {

            @Override
            public View makeView() {
                TextView textView = new TextView(Mgym.this);
                textView.setTextSize(40);
                textView.setTextColor(Color.RED);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                return textView;
            }
        });


        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        textsw.setInAnimation(in);
        textsw.setOutAnimation(out);

        pre.setEnabled(false);
        textsw.setText(dislist.get(counter).getItem().toString());
        showhint = hints.get(counter).getItem().toString();

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Long runid = run.getId();
                Intent myIntent = new Intent(Mgym.this, Recall.class);
                myIntent.putExtra("longVariableName", runid);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ++counter;
                if (counter >= 0 && counter < dissize) {
                    textsw.setText(dislist.get(counter).getItem().toString());
                    showhint = hints.get(counter).getItem().toString();
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
                    showhint = hints.get(counter).getItem().toString();
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

    //Startup the countdown time
    private void startTimer() {
        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }
    }

    //Initialize the Run entity and check the Db connection
    private void setupDb() {
        if (run != null) {
            //showToast("uggkk");
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

            hints = locus.getLocus_text_listList();


            mDiscipline_text_list = mDaoSession.getDiscipline_text_listDao();
            dislist = discipline.getDiscipline_text_listList();
            dissize = dislist.size();

            if (dissize <= 0) {
                next.setEnabled(false);
                pre.setEnabled(false);
            }

            startTime = run.getAssigned_practice_time() * 1000;


        } else {

            showToast("MANI FOOL");
        }
    }

    //Initialize the layout
    private void Set_Add_Update_Screen() {

        done = (Button) findViewById(R.id.btnmgymdone);
        next = (ImageButton) findViewById(R.id.btnmgymnext);
        pre = (ImageButton) findViewById(R.id.btnmgymback);
        hint = (ImageButton) findViewById(R.id.btnmgymhint);
        textsw = (TextSwitcher) findViewById(R.id.mgymtextSwitcher);
        text = (TextView) this.findViewById(R.id.timer);

    }

    // CountDownTimer class
    public class MalibuCountDownTimer extends CountDownTimer {

        public MalibuCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
            Long runid = run.getId();
            Intent myIntent = new Intent(Mgym.this, Recall.class);
            myIntent.putExtra("longVariableName", runid);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(myIntent);
            overridePendingTransition(0, 0);

            // timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("Time remain:" + millisUntilFinished / 1000);
            //timeElapsed = (startTime - millisUntilFinished)/1000;
            //timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
        }
    }

    //show messages in screen
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mgym, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Mgym.this);
        alert.setTitle("Abort!");
        alert.setMessage("Are you sure to leave");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(Mgym.this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                overridePendingTransition(0, 0);
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
