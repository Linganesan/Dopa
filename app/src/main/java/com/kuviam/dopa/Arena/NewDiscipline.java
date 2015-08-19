package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Discipline_text_list;
import com.kuviam.dopa.model.Discipline_text_listDao;

import java.util.ArrayList;
import java.util.List;

public class NewDiscipline extends Activity {

    Button done, add;
    EditText name, text;
    ListView dislist;
    CheckBox chkbx;
    Long disciplineID;
    Discipline custom;
    boolean firstcon;

    GreenDaoApplication mApplication;
    DaoSession mDaoSession;
    DisciplineDao mDisciplineDao;
    Discipline_text_listDao mDiscipline_text_listDao;
    ArrayList<String> list = new ArrayList<String>();


    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discipline);

        Set_Add_Update_Screen();
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();


        // InitSampleData();
        // mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();

        mDisciplineDao = mDaoSession.getDisciplineDao();
        // defaultSetup();

        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        /** Defining a click event listener for the button "Add" */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmp = text.getText().toString();
                if (tmp == null || text.getText().toString().length() <= 0) {
                    text.setError("Enter the Discipline");
                } else {
                    if (!firstcon) {
                        addDb();
                    }
                    boolean check = true;
                    for (int i = 0; i < list.size(); i++) {
                        if (tmp.equals(list.get(i))) {
                            text.setError("Already have this item");

                            check = false;
                        }
                    }
                    if (check && firstcon) {
                        list.add(tmp);
                        text.setText("");
                        adapter.notifyDataSetChanged();
                        dislist.setAdapter(adapter);
                    }
                }


            }
        };

        add.setOnClickListener(listener);

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (custom != null) {
                    custom.setName(name.getText().toString());
                    custom.setIs_Ordered(chkbx.isChecked());
                    int size = list.size();
                    custom.setNo_of_items(size);
                    mDisciplineDao.insertOrReplace(custom);
                    mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();
                    for (int i = 0; i < list.size(); i++) {
                        Discipline_text_list temp = new Discipline_text_list();
                        temp.setDisciplineId(custom.getId());
                        temp.setItem(list.get(i));
                        mDiscipline_text_listDao.insert(temp);
                    }
                    Intent myIntent = new Intent(NewDiscipline.this, Arena.class);
                    startActivity(myIntent);

                } else {
                    showToast("Error in Db connection");
                }

            }
        });



    }


    public void addDb() {
        boolean check;
        if (name.getText().toString() != null) {
            List<Discipline> disciplines = mDisciplineDao.loadAll();
            String ckname = name.getText().toString();
            check = true;
            for (int i = 0; i < disciplines.size(); i++) {
                String d = disciplines.get(i).getName();
                // showToast(d);
                if (d.equals(ckname)) {
                    showToast("ddddd");
                    check = false;

                }
            }
            if (check) {
                showToast("HIiiiii");
                String validname = name.getText().toString();
                boolean is_ordered = chkbx.isChecked();
                String creator = "User";
                // Discipline cards = new Discipline(2L,"Cards",52,"Admin",true,1000F,1000F,10F,10F);
                custom = new Discipline();
                //custom.setId(1L);
                custom.setName(validname);
                custom.setCreator(creator);
                custom.setIs_Ordered(is_ordered);

                // mDisciplineDao = mDaoSession.getDisciplineDao();

                mDisciplineDao.insert(custom);
                disciplineID = custom.getId();
                showToast(disciplineID.toString());
                firstcon = true;
            } else {
                name.setError("You already have this name");
                showToast("You already have this name");
                name.setText("");
                firstcon = false;

            }
        } else {
            name.setError("Enter the name");
            firstcon = false;
        }

    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_discipline, menu);
        return true;

    }

    private void Set_Add_Update_Screen() {
        name = (EditText) findViewById(R.id.txtdisname);
        text = (EditText) findViewById(R.id.txtdis);
        add = (Button) findViewById(R.id.btndisadd);
        done = (Button) findViewById(R.id.btndisdone);
        dislist = (ListView) findViewById(R.id.listdis);
        chkbx = (CheckBox) findViewById(R.id.cb_dis);
    }

    void InitSampleData() {
        mDisciplineDao = mDaoSession.getDisciplineDao();

        Discipline temp;

        list = new ArrayList<String>();

        List<Discipline> disciplines = mDisciplineDao.loadAll();
        for (Discipline discipline : disciplines) {
            list.add("Name:" + discipline.getName() + "\n No of items:" + discipline.getNo_of_items());
        }
    }

    void defaultSetup() {
        mDisciplineDao.deleteAll();
        mDiscipline_text_listDao.deleteAll();
        // Discipline cards = new Discipline(2L,"Cards",52,"Admin",true,1000F,1000F,10F,10F);
        //Discipline custom=new Discipline();
        //custom.setId(1L);
        //custom.setName("Custom");

        //mDisciplineDao.insert(custom);
        //    mDisciplineDao.insert(cards);


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
