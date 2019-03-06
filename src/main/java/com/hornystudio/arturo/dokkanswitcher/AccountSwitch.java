package com.hornystudio.arturo.dokkanswitcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class AccountSwitch extends AppCompatActivity {

    TextView TestoGrande;
    String DokkanPath = "/data/data/com.bandainamcogames.dbzdokkanww";
    String IdentifierPath = "/files/.com.bandainamcogames.dbzdokkanww/";
    Spinner spinner;
    String select;
    ArrayList<String> x;
    ArrayAdapter<String> adapter;
    String command;
    Button Sbutton;

    //*  CLASSES  *//
    RuntimeOutput shell;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_switch);
        setContentView(R.layout.content_account_switch);
        initObj();
        permissionRequest();
        FindAccounts();


        buttonListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_account_switch, menu);

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

    private void buttonListener() {

        Sbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                select = spinner.getSelectedItem().toString();
                command = "cp " + DokkanPath + IdentifierPath + select + " " + DokkanPath + IdentifierPath +  "identifier";
                shell = new RuntimeOutput("su", command);
                Log.d("SELECTED", select);

                try {
                    shell.ExecuteSU();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(AccountSwitch.this, "Switched to " + select, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void permissionRequest() {
        // Assume this Activity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck== PackageManager.PERMISSION_GRANTED){
            //this means permission is granted and you can do read and write
        }else{
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private void initObj() {

        TestoGrande = (TextView) findViewById(R.id.bigText);
        x = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.accountSpinner);
        Sbutton = (Button) findViewById(R.id.sButton);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    public void FindAccounts(){

        command = "ls " + DokkanPath + IdentifierPath;
        shell = new RuntimeOutput("su", command);

        try {
            x = shell.ExecuteSU();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CleanArr(x));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public ArrayList CleanArr(ArrayList<String> x){
    x.remove("identifier");
    x.remove("device");
    return x;
    }


    }



