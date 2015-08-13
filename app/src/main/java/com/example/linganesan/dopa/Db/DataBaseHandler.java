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

	// Contacts table name
	private static String TABLE_NAME = "Default";

	// Loci Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_IMAGE = "image";

	// Table Create Statements

	private static String CREATE_LOCI_TABLE;


	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOCI_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_IMAGE + " BLOB" + ")";
		db.execSQL(CREATE_LOCI_TABLE);
	}

	//Generate table query
	public String getTable(String tname){
		CREATE_LOCI_TABLE = "CREATE TABLE " + tname + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_IMAGE + " BLOB" + ")";

		return CREATE_LOCI_TABLE;
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

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

		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Loci
	public Loci getLoci(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
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
	public List<Loci> getAllLoci() {
		List<Loci> lociList = new ArrayList<Loci>();
		// Select All Query
		String selectQuery = "SELECT  * FROM new ORDER BY name";

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
	public int updateContact(Loci loci) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, loci.getName());
		values.put(KEY_IMAGE, loci.getImage());

		// updating row
		return db.update(TABLE_NAME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(loci.getID()) });

	}

	// Deleting single Loci
	public void deleteLoci(Loci loci) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(loci.getID()) });
		db.close();
	}

	// Getting Locuses Count
	public int getLocusCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
