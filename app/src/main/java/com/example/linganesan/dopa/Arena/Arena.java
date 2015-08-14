package com.example.linganesan.dopa.Arena;

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


public class Arena extends ListActivity {

    String[] discipline ={
            "Cards",
            "Custom",
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);


        this.setListAdapter(new ArrayAdapter<String>(
                this, R.layout.activity_discipline_list,
                R.id.Itemname,discipline ));

        Set_Add_Update_Screen();
    }

    public void onListItemClick(ListView parent, View v,
                                int position, long id) {
        showToast("You have selected: " + discipline[position]);

        if(position==0){
            Intent myIntent = new Intent(Arena.this,
                    Configure.class);
            startActivity(myIntent);

        }else if(position==1){
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
