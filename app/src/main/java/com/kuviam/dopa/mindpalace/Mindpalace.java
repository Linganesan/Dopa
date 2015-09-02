package com.kuviam.dopa.mindpalace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuviam.dopa.MainActivity;
import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Locus;
import com.kuviam.dopa.model.LocusDao;
import com.kuviam.dopa.model.Locus_text_list;
import com.kuviam.dopa.model.Locus_text_listDao;

import java.util.ArrayList;
import java.util.List;


public class Mindpalace extends Activity {
    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private LocusDao mLocusDao;
    private Locus_text_listDao mLocus_text_listDao;
    private ArrayList<String> list = new ArrayList<String>();
    private ListView lView;
    private LocusListAdapter userAdapter;
    private Button newlc;
    private List<Locus> loci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindpalace);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        Set_Add_Update_Screen();
        InitSampleData();
        //defaut_setup();
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
                Toast.makeText(Mindpalace.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

        //Call new locus creation activity
        newlc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Mindpalace.this,
                        NewLocus.class);
                startActivity(myIntent);
                finish();

            }
        });
    }

    //Initialize the layout
    private void Set_Add_Update_Screen() {
        newlc = (Button) findViewById(R.id.bthaddlc);
        lView = (ListView) findViewById(R.id.locuslist);
        userAdapter = new LocusListAdapter(Mindpalace.this, R.layout.layout_locus_list,
                list);
    }

    //Fill the list view by the loci
    void InitSampleData() {
        mLocusDao = mDaoSession.getLocusDao();
        mLocus_text_listDao = mDaoSession.getLocus_text_listDao();
        // list  =  new ArrayList<String>();
        loci = mLocusDao.loadAll();
        for (Locus locus : loci) {
            list.add(" " + locus.getName() );
        }
    }

    //call when we want to reset the the database
    void defaut_setup() {
        mLocusDao.deleteAll();
        mLocus_text_listDao.deleteAll();
        mDaoSession.clear();
    }

    //show messages in screen
    void showToast(CharSequence msg) {

        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toast.setGravity(Gravity.TOP, 0, 40);
        toastTV.setTextSize(35);
        toast.show();
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

    //Adaper class for loci view
    class LocusListAdapter extends ArrayAdapter<String> {
        Context context;
        int layoutResourceId;
        ArrayList<String> data = new ArrayList<String>();

        public LocusListAdapter(Context context, int layoutResourceId,
                                ArrayList<String> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.textName = (TextView) row.findViewById(R.id.txtTitle);
                holder.btnEdit = (ImageButton) row.findViewById(R.id.btnlcedit);
                holder.btnDelete = (ImageButton) row.findViewById(R.id.btnlcclose);
                holder.btnPlay = (ImageButton) row.findViewById(R.id.btnlcselect);
                holder.btnPlay.setEnabled(false);
                holder.btnPlay.setVisibility(RelativeLayout.GONE);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            String tname = data.get(position);
            // holder.btnDelete.setEnabled(false);
            final int id = position;
            holder.textName.setText(tname);
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("Edit Button Clicked", "**********");
                    //showToast(Integer.toString(id) + ": Edit button Clicked");
                    Intent myIntent = new Intent(Mindpalace.this,
                            NewLocus.class);
                    myIntent.putExtra("VariableName", id);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(myIntent);
                    finish();
                    overridePendingTransition(0, 0);

                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("Delete Button Clicked", "**********");
                    //showToast(Integer.toString(id) + ": Delete button Clicked");
                    final Locus dellc = loci.get(id);
                    //showToast(String.valueOf(dellc.getId()));
                    AlertDialog.Builder alert = new AlertDialog.Builder(Mindpalace.this);
                    alert.setTitle("Alert!");
                    alert.setMessage("Are you sure to delete this Locus");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<Locus_text_list> items = dellc.getLocus_text_listList();
                            mLocus_text_listDao.deleteInTx(items);
                            mLocusDao.delete(dellc);
                            List<Locus> newloci = mLocusDao.loadAll();
                            mDaoSession.clear();
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0, 0);

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

            holder.btnPlay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("Run Button Clicked", "**********");
                    //showToast(Integer.toString(id) + ": Select button Clicked");

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

    //Override the back button press method
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Mindpalace.this, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);
    }


}
