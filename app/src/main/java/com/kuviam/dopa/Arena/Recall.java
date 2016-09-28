package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.kuviam.dopa.model.Locus_text_list;
import com.kuviam.dopa.model.Run;
import com.kuviam.dopa.model.RunDao;
import com.kuviam.dopa.model.Run_discipline_item_list;
import com.kuviam.dopa.model.Run_discipline_item_listDao;

import java.util.ArrayList;
import java.util.List;

public class Recall extends Activity {
    private MalibuCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private TextView text;
    private TextView ithitem;
    private Button check;
    private ImageButton next, pre, hint;
    private EditText entertext;
    private Discipline discipline;
    private Locus locus;
    private Run run;
    private Run_discipline_item_list runitem;
    private List<Discipline_text_list> dislist;


    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private LocusDao mLocusDao;
    private DisciplineDao mDisciplineDao;
    private RunDao mRunDao;
    private Run_discipline_item_listDao mRun_discipline_item_listDao;
    private Discipline_text_listDao mDiscipline_text_listDao;
    private List<Locus_text_list> hints;

    private List<Discipline> disciplines;
    private List<Locus> loci;
    private long runid;
    private int counter = 0;
    private int dissize;
    private String showhint;
    private ArrayList<String> userinputs;

    private long startTime;
    private final long interval = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recall);
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
        ithitem.setText(String.valueOf(counter+1));
        countDownTimer = new MalibuCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime));
        startTimer();

        pre.setEnabled(false);
        showhint = hints.get(counter).getItem().toString();


        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {
                    if (userinputs.get(counter).equals("")) {
                        String text = entertext.getText().toString().trim();
                        userinputs.set(counter, text);
                    }
                } catch (Exception e) {
                }

                //showToast(Integer.valueOf(dissize).toString());
                addRecallList();
                long temp =discipline.getRuns_to_sync();
                discipline.setRuns_to_sync(++temp);
                mDisciplineDao.insertOrReplace(discipline);
                countDownTimer.cancel();
                Intent myIntent = new Intent(Recall.this, Score.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.putExtra("longVariableName", runid);
                startActivity(myIntent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String text = entertext.getText().toString().trim();
                //showToast("previous :" + text);
                if (text.length() > 0) {
                    userinputs.set(counter, text);
                    //  showToast("counter/previous :" + Integer.toString(counter) + userinputs.get(counter).toString());
                }
                counter++;
                ithitem.setText(String.valueOf(counter + 1));
                //showToast("counter :" + Integer.toString(counter));
                if (counter >= 0 && counter < dissize) {

                    try {
                        entertext.setText(userinputs.get(counter).toString(), TextView.BufferType.EDITABLE);
                        //showToast("counter/update :" + Integer.toString(counter) + userinputs.get(counter).toString());
                    } catch (Exception e) {
                        entertext.setText("");
                    }
                    try {
                        showhint = hints.get(counter).getItem().toString();
                    } catch (Exception e) {
                    }
                    pre.setEnabled(true);
                } else {
                    //showToast("nextF");
                    next.setEnabled(false);
                    --counter;
                    ithitem.setText(String.valueOf(counter+1));
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String text = entertext.getText().toString().trim();
                //showToast("previous :" + text);
                if (text.length() > 0) {
                    userinputs.set(counter, text);
                    int check = userinputs.indexOf(text);
                    //  showToast("counter/previous :" + Integer.toString(counter) + userinputs.get(counter).toString());
                }
                --counter;
                ithitem.setText(String.valueOf(counter + 1));
                //showToast("counter :" + Integer.toString(counter));
                if (counter > -1 && counter <= dissize) {
                    try {
                        entertext.setText(userinputs.get(counter).toString(), TextView.BufferType.EDITABLE);
                        //showToast("counter/update :" + Integer.toString(counter) + userinputs.get(counter).toString());
                    } catch (Exception e) {
                        entertext.setText("");
                    }
                    try {
                        showhint = hints.get(counter).getItem().toString();
                    } catch (Exception e) {
                    }

                    next.setEnabled(true);

                } else {
                    //showToast("preF");
                    pre.setEnabled(false);
                    ++counter;
                    ithitem.setText(String.valueOf(counter+1));
                }
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                showToast(showhint);
                //logcat checking..
                for (int i = 0; i < dissize; i++)
                    Log.e("item :" + i, userinputs.get(i));
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

    //Initialize the layout
    private void Set_Add_Update_Screen() {
        //Set views
        check = (Button) findViewById(R.id.btnrcdone);
        next = (ImageButton) findViewById(R.id.btnrcnext);
        pre = (ImageButton) findViewById(R.id.btnrcback);
        hint = (ImageButton) findViewById(R.id.btnrchint);
        text = (TextView) findViewById(R.id.recalltimer);
        entertext = (EditText) findViewById(R.id.rcedittext);
        ithitem=(TextView) findViewById(R.id.ithitem);


    }

    //Initialize the Run entity and check the Db connection
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
            //Get locus list hints
            hints = locus.getLocus_text_listList();

            //get discipline list for findout the size of userinput array
            // mDiscipline_text_list = mDaoSession.getDiscipline_text_listDao();
            dislist = discipline.getDiscipline_text_listList();
            dissize = dislist.size();

            //If anychance dissize is zero
            if (dissize <= 0) {
                next.setEnabled(false);
                pre.setEnabled(false);
            }

            //Inialize the userinput array and counter
            userinputs = new ArrayList<String>(dissize);
            for (int i = 0; i <= dissize; i++)
                userinputs.add(i, "");
            counter = 0;

            startTime = run.getAssigned_recall_time() * 1000;

        } else {
            showToast("ERROR! Please reopen");
        }
    }

    //keep the userinputs to a list
    private void addRecallList() {
        Log.d("runID :", String.valueOf(run.getId()));
        for (int i = 0; i < dissize; i++) {
            runitem = new Run_discipline_item_list();
            runitem.setRunId(run.getId());
            runitem.setDiscipline_item(i);
            if (dislist.get(i).getItem().toString().equalsIgnoreCase(userinputs.get(i).toString())) {
                runitem.setStatus(true);
            } else {
                runitem.setStatus(false);
            }
            mRun_discipline_item_listDao.insert(runitem);
            String a;

            if (runitem.getStatus()) {
                a = "true";
            } else {
                a = "false";
            }

            Log.d(String.valueOf(i), a);
        }
    }

    //show messages in screen
    void showToast(CharSequence msg) {

        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toast.setGravity(Gravity.TOP, 0, 40);
        toastTV.setTextSize(45);
        toast.show();
    }

    // CountDownTimer class
    public class MalibuCountDownTimer extends CountDownTimer {

        public MalibuCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
            try {
                if (userinputs.get(counter).equals("")) {
                    String text = entertext.getText().toString().trim();
                    userinputs.set(counter, text);
                }
            } catch (Exception e) {
            }

            //showToast(Integer.valueOf(dissize).toString());
            addRecallList();
            Intent myIntent = new Intent(Recall.this, Score.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            myIntent.putExtra("longVariableName", runid);
            startActivity(myIntent);
            finish();

            // timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("Time remain:" + millisUntilFinished / 1000);
            //timeElapsed = (startTime - millisUntilFinished)/1000;
            //timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recall, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //Alert
        AlertDialog.Builder alert = new AlertDialog.Builder(Recall.this);
        alert.setTitle("Abort");
        alert.setMessage("Are you sure to leave");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                addRecallList();

                run.setStatus("false");
                mRunDao.insertOrReplace(run);
                if (discipline.getName().equals("Default")) {
                    List<Discipline_text_list> dfsf = discipline.getDiscipline_text_listList();
                    mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();
                    mDisciplineDao = mDaoSession.getDisciplineDao();
                    mDiscipline_text_listDao.deleteInTx(dfsf);
                    mDisciplineDao.delete(discipline);
                    mDaoSession.clear();
                }
                Intent myIntent = new Intent(Recall.this, MainActivity.class);
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
