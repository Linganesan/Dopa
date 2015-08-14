package com.example.linganesan.dopa.mindpalace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linganesan.dopa.Db.Loci;
import com.example.linganesan.dopa.Db.DataBaseHandler;

import com.example.linganesan.dopa.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Mindpalace extends ActionBarActivity {
    private Spinner spinner;
    private Button btnSubmit;
    private Button addLoci;
    public List<String> list;
    private ListView dataList;


    private TextView name;

    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindpalace);
        // set screen
        Set_Add_Update_Screen();

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                showToast("Spinner1: position=" + position + " id=" + id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
              //  selectTable();
            }

        });
        /**
         * create DatabaseHandler object
         */
        db = new DataBaseHandler(this);

    }


    public void selectTable(){


    }


    public void Set_Add_Update_Screen() {
        addLoci = (Button) findViewById(R.id.addloci);
        spinner = (Spinner) findViewById(R.id.mindpalaceselect);
        dataList = (ListView) findViewById(R.id.listView);

        spinner = (Spinner) findViewById(R.id.mindpalaceselect);
        list = new ArrayList<String>();
        addItemsOnSpinner("New");
        addItemsOnSpinner("mani");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    //add items into spinner dynamically
    public void addItemsOnSpinner(String item) {

        list.add(item);
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





}
