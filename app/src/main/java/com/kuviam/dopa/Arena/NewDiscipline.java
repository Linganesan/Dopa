package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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

    private Button done, add;
    private EditText name, text;
    private ListView dislist;
    private CheckBox chkbx;
    private Long disciplineID;
    private Discipline custom;
    private boolean firstcon;
    private Discipline discipline;

    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private DisciplineDao mDisciplineDao;
    private Discipline_text_listDao mDiscipline_text_listDao;
    private ArrayList<String> list = new ArrayList<String>();
    private List<Discipline_text_list> itemlist;


    // Declaring an ArrayAdapter to set items to ListView
    private ArrayAdapter<String> adapter;
    private int disID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discipline);
        Intent mIntent = getIntent();
        try {
            disID = mIntent.getIntExtra("VariableName", -1);
        } catch (Exception e) {
        }


        Set_Add_Update_Screen();
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mDisciplineDao = mDaoSession.getDisciplineDao();

        //Defining the ArrayAdapter to set items to ListView
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        if (disID >= 0) {
            checkEdit();
            firstcon = true;
        }

        // InitSampleData();

        // defaultSetup();

        // Defining a click event listener for the button "Add"
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmp = text.getText().toString();
                if (tmp == null || text.getText().toString().length() <= 0) {
                    text.setError("Enter the Discipline");
                    done.setEnabled(false);
                } else if (name.getText().toString().length() <= 0) {
                    name.setError("Enter the title");

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
                        name.setError(null);
                        text.setError(null);
                        adapter.notifyDataSetChanged();
                        dislist.setAdapter(adapter);
                    }
                }
            }
        };

        add.setOnClickListener(listener);

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (custom != null && list.size() != 0) {
                    custom.setName(name.getText().toString());
                    custom.setIs_Ordered(chkbx.isChecked());
                    int size = list.size();
                    custom.setNo_of_items(size);
                    custom.setRuns_to_sync((long) 0);

                    mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();

                    long toatalpracticetime = list.size() * 5;
                    long toatalrecalltime = list.size() * 10;
                    custom.setPractice_time(toatalpracticetime);
                    custom.setRecall_time(toatalrecalltime);
                    custom.setPer_practice_time((long) 5);
                    custom.setPer_recall_time((long) 10);
                    mDisciplineDao.insertOrReplace(custom);
                    disciplineID = custom.getId();
                    List<Discipline_text_list> items = custom.getDiscipline_text_listList();
                    mDiscipline_text_listDao.deleteInTx(items);
                    mDaoSession.clear();

                    //add each item to the discipline list database
                    for (int i = 0; i < list.size(); i++) {
                        Discipline_text_list temp = new Discipline_text_list();
                        temp.setDisciplineId(disciplineID);
                        temp.setItem(list.get(i));
                        mDiscipline_text_listDao.insert(temp);
                        //showToast(temp.getId().toString());

                    }

                    Intent myIntent = new Intent(NewDiscipline.this, Arena.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(myIntent);
                    overridePendingTransition(0, 0);

                } else {
                    showToast("Enter the Discipline Name & add items");
                }

            }
        });

        //Delete the record by click it
        dislist.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = dislist.getItemAtPosition(position).toString();
                //showToast("You selected : " + item);
                AlertDialog.Builder alert = new AlertDialog.Builder(NewDiscipline.this);
                alert.setTitle("Alert!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(item);
                        list.remove(item);
                        adapter.notifyDataSetChanged();
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
        });
    }

    /*
  When we click edit buttom in the Arena for a particular discipline this method will call
  in new discipline activity to auto fill the information vacancies by proper discipline
   */
    private void checkEdit() {
        List<Discipline> disciplines = mDisciplineDao.loadAll();
        discipline = disciplines.get(disID);
        name.setText(discipline.getName().toString());

        mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();
        itemlist = discipline.getDiscipline_text_listList();

        for (int i = 0; i < itemlist.size(); i++) {
            list.add(itemlist.get(i).getItem().toString());
            adapter.notifyDataSetChanged();
            dislist.setAdapter(adapter);
        }
        custom = discipline;
    }

    //Method to initialize the Discipline and checks the userinputs
    public void addDb() {
        boolean check;
        if (name.getText().toString() != null) {
            List<Discipline> disciplines = mDisciplineDao.loadAll();
            String ckname = name.getText().toString();
            check = true;

            if (disID < 0)
                for (int i = 0; i < disciplines.size(); i++) {
                    String d = disciplines.get(i).getName();
                    // showToast(d);
                    if (d.equals(ckname)) {
                        //showToast("ddddd");
                        check = false;

                    }
                }
            if (check) {
                String validname = name.getText().toString();
                boolean is_ordered = chkbx.isChecked();
                String creator = "User";
                custom = new Discipline();
                custom.setName(validname);
                custom.setCreator(creator);
                custom.setIs_Ordered(is_ordered);

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

    //show messages in screen
    void showToast(CharSequence msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toast.setGravity(Gravity.TOP, 0, 40);
        toastTV.setTextSize(35);
        toast.show();
    }

    public void defaultSetup() {
        mDisciplineDao.deleteAll();
        mDiscipline_text_listDao.deleteAll();
    }

    private void Set_Add_Update_Screen() {
        name = (EditText) findViewById(R.id.txtdisname);
        text = (EditText) findViewById(R.id.txtdis);
        add = (Button) findViewById(R.id.btndisadd);
        done = (Button) findViewById(R.id.btndisdone);
        dislist = (ListView) findViewById(R.id.listdis);
        chkbx = (CheckBox) findViewById(R.id.cb_dis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_discipline, menu);
        return true;

    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(NewDiscipline.this, Arena.class);
        startActivity(myIntent);
        finish();
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
