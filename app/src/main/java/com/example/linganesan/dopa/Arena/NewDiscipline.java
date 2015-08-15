package com.example.linganesan.dopa.Arena;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.linganesan.dopa.R;

public class NewDiscipline extends Activity {

    Button done,add;
    EditText name,text;
    ListView dislist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discipline);

        Set_Add_Update_Screen();

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
