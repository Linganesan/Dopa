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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuviam.dopa.MainActivity;
import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;

import java.util.ArrayList;
import java.util.List;


public class Arena extends Activity {
    GreenDaoApplication mApplication;
    DaoSession mDaoSession;
    DisciplineDao mDisciplineDao;
    ArrayList<String> list = new ArrayList<String>();
    ListView lView;
    DisciplineListAdapter userAdapter;
    Button newdis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        Set_Add_Update_Screen();
        InitSampleData();
        //defaultSetup();

        /**
         * set item into adapter
         */
        userAdapter = new DisciplineListAdapter(Arena.this, R.layout.activity_discipline_list,
                list);
        lView = (ListView) findViewById(R.id.arenalist);
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
                Toast.makeText(Arena.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

        newdis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Arena.this,
                        NewDiscipline.class);
                startActivity(myIntent);

            }
        });


    }

    void InitSampleData() {
        mDisciplineDao = mDaoSession.getDisciplineDao();
        // list  =  new ArrayList<String>();
        List<Discipline> disciplines = mDisciplineDao.loadAll();
        for (Discipline discipline : disciplines) {
            list.add("Name :" + discipline.getName() + "\n No of items :" + discipline.getNo_of_items());
        }

    }

    void defaultSetup() {
        mDisciplineDao.deleteAll();
        // Discipline cards = new Discipline(2L,"Cards",52,"Admin",true,1000F,1000F,10F,10F);
        //Discipline custom=new Discipline();
        //custom.setId(1L);
        //custom.setName("Custom");

        //mDisciplineDao.insert(custom);
        //    mDisciplineDao.insert(cards);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice, menu);

        return true;
    }

    public void Set_Add_Update_Screen() {
        newdis = (Button) findViewById(R.id.btndisnew);
        //  this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_discipline_list,
        //        R.id.Itemname, list));
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(item));
        //noinspection SimplifiableIfStatement
        //  if (id == R.id.action_settings) {
        //     return true;
        // }

        // return super.onOptionsItemSelected(item);
    }


    class DisciplineListAdapter extends ArrayAdapter<String> {
        Context context;
        int layoutResourceId;
        ArrayList<String> data = new ArrayList<String>();

        public DisciplineListAdapter(Context context, int layoutResourceId,
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
                holder.textName = (TextView) row.findViewById(R.id.Itemname);
                holder.btnEdit = (ImageButton) row.findViewById(R.id.btndisedit);
                holder.btnDelete = (ImageButton) row.findViewById(R.id.btndisdelete);
                holder.btnPlay = (ImageButton) row.findViewById(R.id.btndisplay);
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
                    showToast(Integer.toString(position) + ": Edit button Clicked");
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Delete Button Clicked", "**********");
                    showToast(Integer.toString(position) + ": Delete button Clicked");

                }
            });

            holder.btnPlay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Log.i("Run Button Clicked", "**********");

                    showToast(Integer.toString(position) + ": Test button Clicked");

                    Intent myIntent = new Intent(Arena.this,
                            Configure.class);
                    myIntent.putExtra("intVariableName", position);
                    startActivity(myIntent);


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
