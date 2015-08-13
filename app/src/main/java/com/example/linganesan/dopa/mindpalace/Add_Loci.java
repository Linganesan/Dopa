package com.example.linganesan.dopa.mindpalace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linganesan.dopa.Db.DataBaseHandler;
import com.example.linganesan.dopa.Db.Loci;
import com.example.linganesan.dopa.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class Add_Loci extends ActionBarActivity {
    Button add_btn;
    ListView loci_listview;
    ArrayList<Loci> loci_data = new ArrayList<Loci>();
    Loci_Adapter cAdapter;
    DataBaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loci);
        try {
            loci_listview = (ListView) findViewById(R.id.list);
            loci_listview.setItemsCanFocus(false);
            add_btn = (Button) findViewById(R.id.add_btn);

            Set_Referash_Data();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }
        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent add_user = new Intent(Add_Loci.this,
                        Add_Update_Loci.class);
                add_user.putExtra("called", "add");
                add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(add_user);
                finish();
            }
        });

    }

    public void Set_Referash_Data() {
        loci_data.clear();
        db = new DataBaseHandler(this);
        ArrayList<Loci> Loci_array_from_db = (ArrayList<Loci>) db.getAllLoci();

        for (int i = 0; i < Loci_array_from_db.size(); i++) {

            int tidno = Loci_array_from_db.get(i).getID();
            String name = Loci_array_from_db.get(i).getName();
            byte[] image = Loci_array_from_db.get(i).getImage();
            Loci lc = new Loci();
            lc.setID(tidno);
            lc.setName(name);
            lc.setImage(image);


            loci_data.add(lc);
        }
        db.close();
        cAdapter = new Loci_Adapter(Add_Loci.this, R.layout.listview_row,loci_data);
        loci_listview.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Set_Referash_Data();

    }

    public class Loci_Adapter extends ArrayAdapter<Loci> {
        Activity activity;
        int layoutResourceId;
        Loci loci;
        ArrayList<Loci> data = new ArrayList<Loci>();

        public Loci_Adapter(Activity act, int layoutResourceId,
                            ArrayList<Loci> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.name = (TextView) row.findViewById(R.id.user_name_txt);
                holder.image = (ImageView) row.findViewById(R.id.imageView4);

                holder.edit = (Button) row.findViewById(R.id.btn_update);
                holder.delete = (Button) row.findViewById(R.id.btn_delete);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            loci = data.get(position);
            holder.edit.setTag(loci.getID());
            holder.delete.setTag(loci.getID());
            holder.name.setText(loci.getName());

            byte[] outImage=loci.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);


            holder.image.setImageBitmap(theImage);

            holder.edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_user = new Intent(activity,
                            Add_Update_Loci.class);
                    update_user.putExtra("called", "update");
                    update_user.putExtra("USER_ID", v.getTag().toString());
                    activity.startActivity(update_user);

                }
            });
            holder.delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub

                    // show a message while loader is loading

                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete ");
                    final int user_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // MyDataObject.remove(positionToRemove);
                                    DataBaseHandler dBHandler = new DataBaseHandler(
                                            activity.getApplicationContext());
                                 //   dBHandler.deleteLoci(user_id);
                                    Add_Loci.this.onResume();

                                }
                            });
                    adb.show();
                }

            });
            return row;

        }

        class UserHolder {
            TextView name;
            ImageView image;
            Button edit;
            Button delete;
        }

    }

}
