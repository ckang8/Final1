package com.example.final1;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class ResultPage extends AppCompatActivity {


    private ListView scoreView;
    private Button saveButton;
    private int score;
    private String pName;
    private int rank;

    private SharedPreferences pref;
    private ArrayList<Score> scoreList;
    private Button exitButton;




    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);


        // get the name and score values from the Intent of the level5 alertDialog
        pName = getIntent().getStringExtra("pName");
        score = getIntent().getIntExtra("score", 0);

        // Retrieve the existing data from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("scores", MODE_PRIVATE);
        String scoresString = prefs.getString("scoresList", "");

        //create an arraylist to store data
        ArrayList<String> scoresList = new ArrayList<>();
        if (!scoresString.isEmpty()) {
            scoresList.addAll(Arrays.asList(scoresString.split(",")));
        }


        //add the new score and name to new arrayList
        scoresList.add(pName + ":" + score);
        Collections.sort(scoresList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // Sort the score in descending order, the high score is top, low score is bottom
                return Integer.compare(Integer.parseInt(o2.split(":")[1]), Integer.parseInt(o1.split(":")[1]));
            }
        });

        // update the ScoreList to SharedPreferences
        prefs.edit().putString("scoresList", TextUtils.join(",", scoresList)).apply();

        // Set up the ListView with the scores data using an ArrayAdapter
        ListView scoreView = findViewById(R.id.scoreView);
        ArrayList<String> scoresData = new ArrayList<>();
        for (int i = 0; i < scoresList.size(); i++) {
            scoresData.add((i + 1) + ". " + scoresList.get(i));
        }
        ArrayAdapter<String> scoresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresData);
        scoreView.setAdapter(scoresAdapter);



        //put a exit button at below
        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultPage.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

}