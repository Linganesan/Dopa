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

package com.example.linganesan.dopa.mindpalace;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.example.linganesan.dopa.Db.Loci;
import com.example.linganesan.dopa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Setup extends Activity {

	/** Items entered by the user is stored in this ArrayList variable */
	ArrayList<String> list = new ArrayList<String>();
	ListView l;


	/** Declaring an ArrayAdapter to set items to ListView */
	ArrayAdapter<String> adapter;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/** Setting a custom layout for the list activity */
		setContentView(R.layout.setup);

		/** Reference to the button of the layout main.xml */
		Button btn = (Button) findViewById(R.id.btnAdd);
		l=(ListView) findViewById(R.id.list);


		/** Defining the ArrayAdapter to set items to ListView */
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);

		/** Defining a click event listener for the button "Add" */
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edit = (EditText) findViewById(R.id.txtItem);
				String tmp= edit.getText().toString();
				if(tmp==null || edit.getText().toString().length() <= 0){
					edit.setError("Enter the Loci");
				}else{
					list.add(edit.getText().toString());
					edit.setText("");
					adapter.notifyDataSetChanged();
					l.setAdapter(adapter);
				}

			}
		};

		/** Setting the event listener for the add button */
		btn.setOnClickListener(listener);

		/** Setting the adapter to the ListView */

		}






	}