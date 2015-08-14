package com.example.linganesan.dopa.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "mindpalace";

	//Discipline table name
	private static String DIS_TABLE_NAME = "discipline";
	// loci table name
	private static String DEF_TABLE_NAME = "default";


	// Loci Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_IMAGE = "image";

	// Discipline Table Columns names
	private static final String DIS_ID = "id";
	private static final String DIS_NAME = "name";

	// Table Create Statements

	private static String CREATE_LOCI_TABLE;
	private static String CREATE_DIS_TABLE;


	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		 CREATE_LOCI_TABLE = "CREATE TABLE " + DEF_TABLE_NAME + "("
		 + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
		 + KEY_IMAGE + " BLOB" + ")";

		CREATE_DIS_TABLE = "CREATE TABLE " + DEF_TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_IMAGE + " BLOB" + ")";

		db.execSQL(CREATE_LOCI_TABLE);
		db.execSQL(CREATE_DIS_TABLE);


	}

	//Generate table query

	public String getTable(String tname){
		CREATE_LOCI_TABLE = "CREATE TABLE " + tname + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_IMAGE + " BLOB" + ")";

		return CREATE_LOCI_TABLE;
	}

	//Execute statements
	public void execState(SQLiteDatabase db,String st){
		db.execSQL(st);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DEF_TABLE_NAME);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Loci
	public	void addLoci(Loci loci) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, loci._name); // Loci Name
		values.put(KEY_IMAGE, loci._image); // Loci image

		// Inserting Row

		db.insert(DEF_TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Loci
	public Loci getLoci(int id,String tname) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tname, new String[] { KEY_ID,
				KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Loci contact = new Loci(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getBlob(1));

		// return Loci
		return contact;

	}

	// Getting All Contacts
	public List<Loci> getAllLoci(String tname) {
		List<Loci> lociList = new ArrayList<Loci>();
		// Select All Query
		String selectQuery = "SELECT  * FROM"+tname+"ORDER BY id";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Loci loci = new Loci();
				loci.setID(Integer.parseInt(cursor.getString(0)));
				loci.setName(cursor.getString(1));
				loci.setImage(cursor.getBlob(2));
				// Adding Loci to list
				lociList.add(loci);
			} while (cursor.moveToNext());
		}
		// close inserting data from database
		db.close();
		// return loci list
		return lociList;

	}

	// Updating single loci
	public int updateContact(Loci loci,String tname) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, loci.getName());
		values.put(KEY_IMAGE, loci.getImage());

		// updating row
		return db.update(tname, values, KEY_ID + " = ?",
				new String[] { String.valueOf(loci.getID()) });

	}

	// Deleting single Loci
	public void deleteLoci(Loci loci,String tname) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tname, KEY_ID + " = ?",
				new String[] { String.valueOf(loci.getID()) });
		db.close();
	}

	// Getting Locuses Count
	public int getLocusCount(String tname) {
		String countQuery = "SELECT  * FROM " + tname;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
