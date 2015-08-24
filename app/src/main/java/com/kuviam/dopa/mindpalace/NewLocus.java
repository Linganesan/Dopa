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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    /**
     * Items entered by the user is stored in this ArrayList variable
     */

    Button done, add;
    EditText name, text;
    ListView l;
    Long locusID;
    Locus custom;
    boolean firstcon;

    GreenDaoApplication mApplication;
    DaoSession mDaoSession;
    LocusDao mLocusDao;
    Locus_text_listDao mLocus_text_listDao;
    ArrayList<String> list = new ArrayList<String>();

    int intValue;


    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    ArrayAdapter<String> adapter;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /** Setting a custom layout for the list activity */
        setContentView(R.layout.activity_new_locus);
        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("intVariableName", 0);
        Set_Add_Update_Screen();
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mLocusDao = mDaoSession.getLocusDao();


        /** Defining a click event listener for the button "Add" */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = text.getText().toString();
                if (tmp == null || text.getText().toString().length() <= 0) {
                    text.setError("Enter the Item");
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
                    for (int i = 0; i < list.size(); i++) {
                        Locus_text_list temp = new Locus_text_list();
                        temp.setLocusId(locusID);
                        temp.setItem(list.get(i));
                        mLocus_text_listDao.insert(temp);
                        //showToast(temp.getId().toString());

                    }
                    if (intValue == 5) {
                        onBackPressed();

                    } else {
                        Intent myIntent = new Intent(NewLocus.this, Mindpalace.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(myIntent);
                        overridePendingTransition(0, 0);
                    }

                } else {
                    showToast("Error in Db connection");
                }

            }
        });

        l.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = l.getItemAtPosition(position).toString();
                showToast("You selected : " + item);
                adapter.remove(item);
                list.remove(item);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void addDb() {
        boolean check;
        if (name.getText().toString() != null) {
            List<Locus> loci = mLocusDao.loadAll();
            String ckname = name.getText().toString();
            check = true;
            for (int i = 0; i < loci.size(); i++) {
                String d = loci.get(i).getName();
                // showToast(d);
                if (d.equals(ckname)) {
                    showToast("ddddd");
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

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

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