package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.mindpalace.NewLocus;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Discipline_text_list;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;
import com.kuviam.dopa.model.Locus_text_list;
import com.kuviam.dopa.model.Run;
import com.kuviam.dopa.model.RunDao;

import java.util.ArrayList;
import java.util.List;

public class Configure extends Activity {
    private Button start, newlocus;
    private EditText ptime, rtime, items;
    private ListView locilist;
    private TextView disname;
    private int intValue;
    private Run run;
    private Long pti, rti;
    private TextView slocus;

    private List<Locus> loci;
    private List<Locus> updatedloci;


    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private LocusDao mLocusDao;
    private Locus locus;
    private DisciplineDao mDisciplineDao;
    private Discipline discipline;
    private RunDao mRunDao;

    private ArrayList<String> list = new ArrayList<String>();
    private LocusListAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("intVariableName", 0);

        Set_Add_Update_Screen();

        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mDisciplineDao = mDaoSession.getDisciplineDao();

        List<Discipline> disciplines = mDisciplineDao.loadAll();
        discipline = disciplines.get(intValue);
        disname.setText(discipline.getName().toString());


        InitSampleData();
        userAdapter = new LocusListAdapter(Configure.this, R.layout.layout_locus_list,
                list);
        locilist.setItemsCanFocus(false);
        locilist.setAdapter(userAdapter);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                if (addDb()) {
                    Long runid = run.getId();
                    Intent myIntent = new Intent(Configure.this, Mgym.class);
                    myIntent.putExtra("longVariableName", runid);
                    startActivity(myIntent);
                }
            }
        });

        newlocus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Configure.this, NewLocus.class);
                myIntent.putExtra("intVariableName", intValue);
                startActivity(myIntent);
            }
        });


    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void InitSampleData() {
        mLocusDao = mDaoSession.getLocusDao();
        loci = mLocusDao.loadAll();
        updatedloci = mLocusDao.loadAll();
        List<Discipline_text_list> ref = discipline.getDiscipline_text_listList();
        int size = ref.size();
        for (Locus locus : loci) {
            List<Locus_text_list> checklist = locus.getLocus_text_listList();

            if (checklist.size() >= size) {
                list.add("Name :" + locus.getName() + "\n ID:" + locus.getId());
            } else {
                updatedloci.remove(locus);
            }
        }
    }

    public boolean addDb() {
        if (ptime.getText().toString().length() > 0 && rtime.getText().toString().length() > 0
                && locus != null && discipline != null) {
            pti = Long.valueOf(ptime.getText().toString());
            rti = Long.valueOf(rtime.getText().toString());
            mRunDao = mDaoSession.getRunDao();
            run = new Run();
            run.setAssigned_practice_time(pti);
            run.setAssigned_recall_time(rti);
            run.setDiscipline(discipline.getName());
            run.setLocus(locus.getName());
            mRunDao.insert(run);
            return true;

        } else if (ptime.getText().toString().length() <= 0) {
            ptime.setError("Enter the pracice time");
            return false;

        } else if (rtime.getText().toString().length() <= 0) {
            rtime.setError("Enter the Recall time");
            return false;
        } else {
            showToast("Error");
            return false;

        }
    }

    private void Set_Add_Update_Screen() {
        ptime = (EditText) findViewById(R.id.txtmgymtime);
        rtime = (EditText) findViewById(R.id.txtrecalltime);
        newlocus = (Button) findViewById(R.id.btnmgmnewlocus);
        // items=(EditText)findViewById(R.id.nonetxt);
        start = (Button) findViewById(R.id.btnstart);
        locilist = (ListView) findViewById(R.id.listloci);
        disname = (TextView) findViewById(R.id.disname);
        slocus = (TextView) findViewById(R.id.selectedloucs);

    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Configure.this, Arena.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card__dis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class LocusListAdapter extends ArrayAdapter<String> {
        Context context;
        int layoutResourceId;
        ArrayList<String> data = new ArrayList<String>();

        public LocusListAdapter(Context context, int layoutResourceId,
                                ArrayList<String> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.textName = (TextView) row.findViewById(R.id.txtTitle);
                holder.btnEdit = (ImageButton) row.findViewById(R.id.btnlcedit);
                holder.btnDelete = (ImageButton) row.findViewById(R.id.btnlcclose);
                holder.btnPlay = (ImageButton) row.findViewById(R.id.btnlcselect);
                holder.btnDelete.setVisibility(RelativeLayout.GONE);
                holder.btnEdit.setVisibility(RelativeLayout.GONE);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            String tname = data.get(position);
            holder.textName.setText(tname);
            holder.btnPlay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("Run Button Clicked", "**********");
                    mLocusDao = mDaoSession.getLocusDao();
                    locus = updatedloci.get(position);
                    //showToast(locus.getName().toString());
                    slocus.setText(locus.getName().toString());
                }

            });
            return row;

        }

        class UserHolder {
            TextView textName;
            ImageButton btnEdit;
            ImageButton btnDelete;
            ImageButton btnPlay;
        }
    }
}
