package com.example.user.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Trace;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.io.FileWriter;


import au.com.bytecode.opencsv.CSVWriter;


public class MainActivity2 extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.user.myapplication.MESSAGE";
    public final static int create_new = 2;
    MyDBHandler dbHandler;
    Vector<String> entries;
    TableLayout table;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        refreshTable();

    }

    private void addDrawerItems() {
        String[] osArray = { "Entries","Alarms" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity2.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);

        // Add buttons to action bar
        menu.add(0, create_new, 0, "Add New")
                .setIcon(R.drawable.ic_create_new)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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

        if (id == create_new){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onExportButtonClicked(View view) throws IOException {

        Log.v("MyActivity", "Export Button clicked");


        Log.v("MyActivity", "External Storage writable ");

        Calendar cal = Calendar.getInstance();

        String currentTime = cal.getTime().toString();
        currentTime = currentTime.replaceAll(" ", "_");

        String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Entrylist_" + currentTime + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));

        List<String[]> data = new ArrayList<String[]>();

        List<Entry> list = dbHandler.databaseToArray();

        // Adding the excel title
        data.add(new String[] {"Name of Entry", "Date Time", "Mood Score",
                "Other Feeling", "Thoughts", "Event happen", "Action"});

        // Adding the excel data
        for (Entry item : list) {
            data.add(new String[] {item.get_entryName(), item.get_entryDate(), String.valueOf(item.get_entryMoodScore()),
                    item.get_entryOtherFeeling(), item.get_entryThoughts(), item.get_entryEvent(), item.get_entryAction()});
        }
        writer.writeAll(data);
        writer.close();
        Log.v("MyActivity", "External Storage writable : " + csv);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Exported");
        builder.setMessage("Exported to " + csv);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
            }

        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void refreshTable()
    {
        table = (TableLayout) findViewById(R.id.entriesTable);
        table.removeAllViewsInLayout();

        entries = new Vector<String>();
        dbHandler = new MyDBHandler(this, null, null, 1);

        int counter = 0;
        entries = dbHandler.entryNameToString();

        for (String temp : entries)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView entryName = new TextView(this);

            // Edit Button
            ImageButton edit = new ImageButton(this);
            edit.setImageResource(R.drawable.ic_create_new_holo_light);
            edit.setId(counter);

            // Button Styling
            edit.getBackground().setAlpha(0);

            // Set button on click event
            final ImageButton finalEdit = edit;
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    finish();
                    int entryID = finalEdit.getId();
                    intent.putExtra(EXTRA_MESSAGE, entries.get(entryID));
                    startActivity(intent);
                }
            });

            // Delete Button
            ImageButton delete = new ImageButton(this);
            delete.setImageResource(R.drawable.ic_delete);
            delete.setId(counter);

            // Button Styling
            delete.getBackground().setAlpha(0);

            // Set button on click event
            final ImageButton finalDelete = delete;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int entryID = finalDelete.getId();
                    dbHandler.deleteEntry(entries.get(entryID));
                    refreshTable();
                }
            });

            // Setting the Row
            entryName.setText(temp);
            row.addView(entryName);
            row.addView(edit);
            row.addView(delete);
            row.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.row_color));

            // Add the row into the table
            table.addView(row);
            counter++;
        }

        dbHandler.close();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
