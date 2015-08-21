package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;

import java.util.ArrayList;
import java.util.List;

public class Configure extends AppCompatActivity {
    Button start;
    EditText time,items;
    ListView locilist;
    TextView disname;
    Long intValue;

    GreenDaoApplication mApplication;
    DaoSession mDaoSession;
    LocusDao mLocusDao;
    DisciplineDao mDisciplineDao;
    Discipline discipline;
    ArrayList<String> list = new ArrayList<String>();
   LocusListAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        Intent mIntent = getIntent();
        intValue = Long.valueOf(mIntent.getIntExtra("intVariableName", 0))+1;
        Set_Add_Update_Screen();
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();

        mDisciplineDao=mDaoSession.getDisciplineDao();
        discipline=mDisciplineDao.load(intValue);

        disname.setText(discipline.getName());


        InitSampleData();

        userAdapter = new LocusListAdapter(Configure.this, R.layout.screen_list,
                list);

        locilist.setItemsCanFocus(false);
        locilist.setAdapter(userAdapter);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                int intValue =8;
                // Start NewActivity.class
                Intent myIntent = new Intent(Configure.this, Mgym.class);
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
        // list  =  new ArrayList<String>();
        List<Locus> loci = mLocusDao.loadAll();
        for (Locus locus : loci) {
            list.add("Name :" + locus.getName() + "\n ID:" + locus.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card__dis, menu);
        return true;
    }

    private void Set_Add_Update_Screen(){
        time = (EditText) findViewById(R.id.txttime);
        items=(EditText)findViewById(R.id.txtitem);
        start= (Button) findViewById(R.id.btnstart);
        locilist=(ListView)findViewById(R.id.listloci);
        disname=(TextView)findViewById(R.id.disname);

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
        public View getView(int position, View convertView, ViewGroup parent) {
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

                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            String tname = data.get(position);
            holder.textName.setText(tname);
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");
                    Toast.makeText(context, "Edit button Clicked",
                            Toast.LENGTH_LONG).show();
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Delete Button Clicked", "**********");
                    Toast.makeText(context, "Delete button Clicked",
                            Toast.LENGTH_LONG).show();

                }
            });

            holder.btnPlay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Run Button Clicked", "**********");
                    Toast.makeText(context, "Run button Clicked",
                            Toast.LENGTH_LONG).show();

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
