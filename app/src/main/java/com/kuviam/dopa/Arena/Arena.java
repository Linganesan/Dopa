package com.kuviam.dopa.Arena;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linganesan.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;

import java.util.ArrayList;
import java.util.List;


public class Arena extends ListActivity {
    GreenDaoApplication mApplication ;
    DaoSession mDaoSession ;
    DisciplineDao mDisciplineDao ;
    ArrayList< String >  list= new ArrayList<String>();



    String[] discipline = {
            "Cards",
            "Custom",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);

        mApplication = (GreenDaoApplication)getApplication();
        mDaoSession =  mApplication.getDaoSession();

        InitSampleData ();

        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_discipline_list,
                R.id.Itemname, list));

        Set_Add_Update_Screen();
    }

    void  InitSampleData ()  {
        mDisciplineDao  =  mDaoSession.getDisciplineDao();



        list  =  new ArrayList<String>();

        List< Discipline > disciplines  =  mDisciplineDao.loadAll();
        for  ( Discipline discipline  :  disciplines )  {
            list.add( "Name :"  +  discipline.getName ()  +  "\n No of items :"  +  discipline.getNo_of_items());
        }
    }
    void defaultSetup(){
        mDisciplineDao.deleteAll();
        Discipline cards = new Discipline(2L,"Cards",52,"Admin",true,1000F,1000F,10F,10F);
        Discipline custom=new Discipline();
        custom.setId(1L);
        custom.setName("Custom");

        mDisciplineDao.insert(custom);
        mDisciplineDao.insert(cards);


    }
    public void onListItemClick(ListView parent, View v,
                                int position, long id) {
        showToast("You have selected: " + list.get(position));

        if (position == 1) {
            Intent myIntent = new Intent(Arena.this,
                    Configure.class);
            startActivity(myIntent);

        } else if (position == 0) {
            Intent myIntent = new Intent(Arena.this,
                    NewDiscipline.class);
            startActivity(myIntent);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice, menu);
        return true;
    }

    public void Set_Add_Update_Screen() {


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
