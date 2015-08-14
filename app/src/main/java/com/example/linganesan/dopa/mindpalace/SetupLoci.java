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
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.linganesan.dopa.R;

import java.util.ArrayList;

public class SetupLoci extends Activity {

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
		setContentView(R.layout.activity_setup_loci);

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