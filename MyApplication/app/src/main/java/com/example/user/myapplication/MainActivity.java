package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertNull;


public class MainActivity extends ActionBarActivity  {

    EditText entryName;
    EditText entryOtherFeelings;
    EditText entryThoughts;
    EditText entryEvent;
    EditText entryAction;
    TextView moodScore;
    SeekBar entryMoodScore;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDBHandler(this, null, null, 1);

        // Setting the textBox
        entryName = (EditText) findViewById(R.id.entryNameBox);
        entryOtherFeelings = (EditText) findViewById(R.id.entryOtherFeelings);
        entryThoughts = (EditText) findViewById(R.id.entryThoughts);
        entryEvent = (EditText) findViewById(R.id.entryEvents);
        entryAction = (EditText) findViewById(R.id.entryActions);
        entryMoodScore = (SeekBar) findViewById(R.id.entryMoodScore);
        moodScore = (TextView) findViewById(R.id.MoodScoreText);
        entryMoodScore.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                                                      @Override
                                                      public void onStopTrackingTouch(SeekBar seekBar) {
                                                          // TODO Auto-generated method stub
                                                      }

                                                      @Override
                                                      public void onStartTrackingTouch(SeekBar seekBar) {
                                                          // TODO Auto-generated method stub
                                                      }
                                                      @Override
                                                     public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                                                         // TODO Auto-generated method stub

                                                         moodScore.setText(String.valueOf(progress));

                                                     }

                                             });

        // Check for Edit
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity2.EXTRA_MESSAGE);

        if(message != null) {
            if (message.length() != 0) {
                Entry entry = dbHandler.entryNameToEntry(message);
                entryName.setText(entry.get_entryName());
                entryMoodScore.setProgress(entry.get_entryMoodScore());
                entryOtherFeelings.setText(entry.get_entryOtherFeeling());
                entryThoughts.setText(entry.get_entryThoughts());
                entryEvent.setText(entry.get_entryEvent());
                entryAction.setText(entry.get_entryAction());
                dbHandler.deleteEntry(message);
            }
        }
    }

    // Add a product to database
    public void onSaveButtonClicked(View view){
        Calendar cal = Calendar.getInstance();

        Log.v("MainActivity", "Time now: " + cal.getTime().toString());

        Entry entry = new Entry(
                entryName.getText().toString(),
                entryMoodScore.getProgress(),
                entryOtherFeelings.getText().toString(),
                entryThoughts.getText().toString(),
                entryEvent.getText().toString(),
                entryAction.getText().toString(),
                cal.getTime().toString()
                );
        dbHandler.addEntry(entry);

        Intent intent = new Intent(this, MainActivity2.class);
        finish();
        startActivity(intent);
    }

    // Clear the current inputs
    public void onCancelButtonClicked(View view){
        clearEntry();
    }

    public void clearEntry(){
        entryName.setText("");
        entryMoodScore.setProgress(0);
        entryOtherFeelings.setText("");
        entryThoughts.setText("");
        entryEvent.setText("");
        entryAction.setText("");
    }



}
