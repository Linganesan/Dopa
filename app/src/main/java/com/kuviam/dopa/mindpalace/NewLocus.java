/*
	Keep Score: keep track of player scores during a card game.
	Copyright (C) 2009 Michael Elsdörfer <http://elsdoerfer.name>

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kuviam.dopa.mindpalace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kuviam.dopa.Arena.Configure;
import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;
import com.kuviam.dopa.model.Locus_text_list;
import com.kuviam.dopa.model.Locus_text_listDao;

import java.util.ArrayList;
import java.util.List;

public class NewLocus extends Activity {

    private Button done, add;
    private EditText name, text;
    private ListView l;
    private Long locusID;
    private Locus custom;
    private boolean firstcon;

    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private LocusDao mLocusDao;
    private Locus_text_listDao mLocus_text_listDao;
    private ArrayList<String> list = new ArrayList<String>();
    private List<Locus_text_list> itemlist;

    private int lcID;
    private int disID;

    //Declaring an ArrayAdapter to set items to ListView
    private ArrayAdapter<String> adapter;

    //Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /** Setting a custom layout for the list activity */
        setContentView(R.layout.activity_new_locus);
        Intent mIntent = getIntent();
        lcID = mIntent.getIntExtra("VariableName", -1);
        disID = mIntent.getIntExtra("intVariableName", -1);

        Set_Add_Update_Screen();
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mLocusDao = mDaoSession.getLocusDao();

        //If edit button is clicked in the Areana
        if (lcID >= 0 && disID < 0) {
            checkEdit();
            firstcon = true;
        }


        /** Defining a click event listener for the button "Add" */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = text.getText().toString();
                if (tmp == null || text.getText().toString().length() <= 0) {
                    text.setError("Enter the Item");
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
                        l.setAdapter(adapter);
                    }
                }

            }
        };

        /** Setting the event listener for the add button */
        add.setOnClickListener(listener);

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (custom != null) {
                    custom.setName(name.getText().toString());
                    mLocusDao.insertOrReplace(custom);

                    locusID = custom.getId();
                    mLocus_text_listDao = mDaoSession.getLocus_text_listDao();

                    List<Locus_text_list> items = custom.getLocus_text_listList();
                    mLocus_text_listDao.deleteInTx(items);
                    mDaoSession.clear();
                    //add each item to the locus list database
                    for (int i = 0; i < list.size(); i++) {
                        Locus_text_list temp = new Locus_text_list();
                        temp.setLocusId(locusID);
                        temp.setItem(list.get(i));
                        mLocus_text_listDao.insert(temp);
                        //showToast(temp.getId().toString());


                    }
                    if (disID >= 0) {
                        Intent myIntent = new Intent(NewLocus.this, Configure.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        myIntent.putExtra("intVariableName", disID);
                        startActivity(myIntent);
                        overridePendingTransition(0, 0);

                    } else {
                        Intent myIntent = new Intent(NewLocus.this, Mindpalace.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(myIntent);
                        overridePendingTransition(0, 0);
                    }

                } else

                {
                    showToast("Error in Db connection");
                }

            }
        });

        //Delete the record by click it
        l.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String item = l.getItemAtPosition(position).toString();
                //showToast("You selected : " + item);
                AlertDialog.Builder alert = new AlertDialog.Builder(NewLocus.this);
                alert.setTitle("Alert!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(item);
                        try {
                            list.remove(position);
                        }catch(Exception e){}
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
    When we click edit buttom in the Mindpalace for a particular locus, this method will call
    in new Locus activity to auto fill the information vacancies by proper locus
     */
    private void checkEdit() {
        List<Locus> loci = mLocusDao.loadAll();
        custom = loci.get(lcID);
        name.setText(custom.getName().toString());

        mLocus_text_listDao = mDaoSession.getLocus_text_listDao();
        itemlist = custom.getLocus_text_listList();

        for (int i = 0; i < itemlist.size(); i++) {
            list.add(itemlist.get(i).getItem().toString());
            adapter.notifyDataSetChanged();
            l.setAdapter(adapter);
        }
    }

    //Method to initialize the Locus and checks the userinputs
    public void addDb() {
        boolean check;
        if (name.getText().toString() != null) {
            List<Locus> loci = mLocusDao.loadAll();
            String ckname = name.getText().toString();
            check = true;
            if (lcID < 0)
                for (int i = 0; i < loci.size(); i++) {
                    String d = loci.get(i).getName();
                    // showToast(d);
                    if (d.equals(ckname)) {
                        //showToast("ddddd");
                        check = false;
                    }
                }
            if (check) {
                String validname = name.getText().toString();

                String creator = "User";
                custom = new Locus();
                custom.setName(validname);
                custom.setCreator(creator);
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

    //Show messages in android screen by small dialog
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Initialize the layout
    private void Set_Add_Update_Screen() {
        name = (EditText) findViewById(R.id.txtlociname);
        text = (EditText) findViewById(R.id.txtlcitem);
        add = (Button) findViewById(R.id.btnlcadd);
        done = (Button) findViewById(R.id.btnlcdone);
        l = (ListView) findViewById(R.id.list);
        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    }
}