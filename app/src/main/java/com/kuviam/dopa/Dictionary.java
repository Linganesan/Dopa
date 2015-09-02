package com.kuviam.dopa;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kuviam.dopa.R;
import com.kuviam.dopa.db.GreenDaoApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by linganesan on 8/30/15.
 */
public class Dictionary extends Application {


    private ArrayList<String> dictionary;
   // private int wordLength; //Set elsewhere
   //a handle to the application's resources
   private Resources resources;
    private Context mCtx;
    Dictionary(){
        createDictionary();
        resources = getResources();


    }


    public void createDictionary(){
        dictionary = new ArrayList<String>();

        BufferedReader dict = null; //Holds the dictionary file
        AssetManager am = this.getAssets();

        try {
            //dictionary.txt should be in the assets folder.
            dict = new BufferedReader(new InputStreamReader(resources.getAssets().open("dictionary.txt")));

            String word;
            while((word = dict.readLine()) != null){
              //  if(word.length() == wordLength){
                    dictionary.add(word);
               // }
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            showToast("sfsdf");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            showToast("sfsdfsdfsfsf");
        }

        try {
            dict.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //show messages in screen
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Precondition: the dictionary has been created.
    public String getRandomWord(){
        return dictionary.get((int)(Math.random() * dictionary.size()));
    }

}
