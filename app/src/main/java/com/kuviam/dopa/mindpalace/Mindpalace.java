package com.kuviam.dopa.mindpalace;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;

import java.util.ArrayList;
import java.util.List;


public class Mindpalace extends Activity {
    GreenDaoApplication mApplication;
    DaoSession mDaoSession;
    LocusDao mLocusDao;
    ArrayList<String> list = new ArrayList<String>();
    ListView lView;
    LocusListAdapter userAdapter;
    Button newlc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindpalace);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        Set_Add_Update_Screen();
        InitSampleData();

        userAdapter = new LocusListAdapter(Mindpalace.this, R.layout.screen_list,
                list);

        lView.setItemsCanFocus(false);
        lView.setAdapter(userAdapter);
        /**
         * get on item click listener
         */
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(Mindpalace.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

        newlc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Mindpalace.this,
                        NewLocus.class);
                startActivity(myIntent);

            }
        });
    }

    private void Set_Add_Update_Screen() {
        newlc = (Button) findViewById(R.id.bthaddlc);
        lView = (ListView) findViewById(R.id.locuslist);


        // this.setListAdapter(new ArrayAdapter<String>(this, R.layout.screen_list,
        //         R.id.txtTitle, list));

    }

    void InitSampleData() {
        mLocusDao = mDaoSession.getLocusDao();
        // list  =  new ArrayList<String>();
        List<Locus> loci = mLocusDao.loadAll();
        for (Locus locus : loci) {
            list.add("Name :" + locus.getName() + "\n ID:" + locus.getId());
        }
    }

    public void onListItemClick(ListView parent, View v,
                                int position, long id) {
        showToast("You have selected: " + list.get(position));

        if (position == 0) {
            Intent myIntent = new Intent(Mindpalace.this,
                    NewLocus.class);
            startActivity(myIntent);
        }
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mindpalace, menu);
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