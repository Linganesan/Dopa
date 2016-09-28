package com.kuviam.dopa.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kuviam.dopa.MainActivity;
import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;
import com.kuviam.dopa.model.DaoSession;
import com.kuviam.dopa.model.Discipline;
import com.kuviam.dopa.model.DisciplineDao;
import com.kuviam.dopa.model.Discipline_text_listDao;
import com.kuviam.dopa.model.Run;
import com.kuviam.dopa.model.RunDao;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class Analytics extends Activity {
    private GreenDaoApplication mApplication;
    private DaoSession mDaoSession;
    private DisciplineDao mDisciplineDao;
    private Discipline_text_listDao mDiscipline_text_listDao;
    private RunDao mRunDao;

    private ArrayList<String> list = new ArrayList<String>();
    private ListView lView;
    private DisciplineListAdapter userAdapter;
    List<Discipline> disciplines;
    List<Discipline> updateddisciplines;
    private List<Run> runlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mRunDao = mDaoSession.getRunDao();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Set_Add_Update_Screen();
        InitSampleData();

        //defaultSetup();

        lView = (ListView) findViewById(R.id.analyticslist);
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
                Toast.makeText(Analytics.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    void InitSampleData() {
        mDisciplineDao = mDaoSession.getDisciplineDao();
        mDiscipline_text_listDao = mDaoSession.getDiscipline_text_listDao();
        // list  =  new ArrayList<String>();
        disciplines = mDisciplineDao.loadAll();
        updateddisciplines = mDisciplineDao.loadAll();
        for (Discipline discipline : disciplines) {
            if (discipline.getRuns_to_sync() >= 3) {
                list.add(discipline.getName() + "\n Run times :" + discipline.getRuns_to_sync());

            } else {
                updateddisciplines.remove(discipline);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analytics, menu);
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

    //Initialize the layout
    public void Set_Add_Update_Screen() {

        //set item into adapter
        userAdapter = new DisciplineListAdapter(Analytics.this, R.layout.layout_syn_discipline,
                list);

    }

    //Adapter class for discipline view
    class DisciplineListAdapter extends ArrayAdapter<String> {
        Context context;
        int layoutResourceId;
        ArrayList<String> data = new ArrayList<String>();

        public DisciplineListAdapter(Context context, int layoutResourceId,
                                     ArrayList<String> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.textName = (TextView) row.findViewById(R.id.Itemname);
                holder.btnSyn = (ImageButton) row.findViewById(R.id.btnsyn);
                row.setTag(holder);


                //holder.btnSyn.setEnabled(false);

            } else {
                holder = (UserHolder) row.getTag();
            }
            String tname = data.get(position);
            holder.textName.setText(tname);

            holder.btnSyn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Discipline analydis = updateddisciplines.get(position);
                    String name = analydis.getName();

                    runlist = mRunDao.loadAll();
                    QueryBuilder<Run> qb = mRunDao.queryBuilder();
                    qb.where(RunDao.Properties.Discipline.eq(name));
                    runlist = qb.list();
                    List<AnalyticDTO> passingobjects = new ArrayList<AnalyticDTO>();


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://10.0.2.2:8080/dopa-analytical-engine/dopa/postentity");
                    post.setHeader("content-type", "application/json");


                    for (Run run : runlist) {

                        AnalyticDTO analyticDTO = new AnalyticDTO(run.getDiscipline(), run.getLocus(), run.getAssigned_practice_time(),
                                run.getAssigned_recall_time(),
                                run.getNo_of_items());
                        Gson gson = new Gson();
                        String strAnalyticsData = gson.toJson(analyticDTO);
                        StringEntity entity = null;
                        try {
                            entity = new StringEntity(strAnalyticsData);
                            post.setEntity(entity);
                            Log.d("","About to connect");
                            HttpResponse resp = httpClient.execute(post);
                            Log.d(resp.getEntity().toString(),"Connected");
                            String respStr = EntityUtils.toString(resp.getEntity());
                            Log.d("debug","Response");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }




                    //analydis.setRuns_to_sync((long) 0);

                    mDisciplineDao.insertOrReplace(analydis);

                    mDaoSession.clear();
                    Intent intent = getIntent();
                    finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);


                }
            });
            return row;

        }

        class UserHolder {
            TextView textName;
            ImageButton btnSyn;
        }
    }

    //Override the back button press method
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Analytics.this, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);
    }



    }
