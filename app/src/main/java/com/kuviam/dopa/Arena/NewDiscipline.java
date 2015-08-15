package com.kuviam.dopa.Arena;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.linganesan.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;

import java.util.ArrayList;
import java.util.List;

public class NewDiscipline extends Activity {

    Button done,add;
    EditText name,text;
    ListView dislist;

    GreenDaoApplication mApplication ;
    DaoSession mDaoSession ;
    DisciplineDao  mDisciplineDao ;
    ArrayList < String >  list  =  new  ArrayList < String >();



    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discipline);

        Set_Add_Update_Screen();
        mApplication  =  ( GreenDaoApplication )getApplication();
        mDaoSession  =  mApplication.getDaoSession();

        InitSampleData();

       /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        /** Defining a click event listener for the button "Add" */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmp = text.getText().toString();
                if (tmp == null || text.getText().toString().length() <= 0) {
                    text.setError("Enter the Loci");
                } else {
                    list.add(text.getText().toString());
                    text.setText("");
                    adapter.notifyDataSetChanged();
                    dislist.setAdapter(adapter);
                }

            }
        };

        add.setOnClickListener(listener);

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(NewDiscipline.this, Arena.class);
                startActivity(myIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_discipline, menu);
        return true;

    }

    private void Set_Add_Update_Screen(){
        name = (EditText) findViewById(R.id.txtdisname);
        text=(EditText)findViewById(R.id.txtdis);
        add= (Button) findViewById(R.id.btndisadd);
        done= (Button) findViewById(R.id.btndisdone);
        dislist=(ListView)findViewById(R.id.listdis);
    }

    void  InitSampleData ()  {
        mDisciplineDao  =  mDaoSession.getDisciplineDao();

        Discipline temp;

        list  =  new  ArrayList <String>();

        List< Discipline > disciplines  =  mDisciplineDao.loadAll();
        for  ( Discipline discipline  :  disciplines )  {
            list.add( "Name:"  +  discipline.getName ()  +  "\n No of items:"  +  discipline.getNo_of_items());
        }
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
