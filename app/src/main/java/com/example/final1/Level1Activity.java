package com.example.final1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class Level1Activity extends AppCompatActivity {

    private TextView timerText,scoreText;
    private View[] viewBox;
    private View highlightedView;
    private int timer = 5;
    private int score = 0;
    private Random random;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        //time
        timerText = findViewById(R.id.timerText);

        //score
        scoreText = findViewById(R.id.scoreText);


        //views
        //create a array of view to find all 4 views in xml
        viewBox = new View[]{
                findViewById(R.id.view1),
                findViewById(R.id.view2),
                findViewById(R.id.view3),
                findViewById(R.id.view4),
        };

        //put random
        random = new Random();

        //start the game
        //startGame();
        readyNotification();


    }


    private void startGame(){
        //reset the score and timer
        score = 0;
        scoreText.setText("Score: " + score);
        timerText.setText("Timer: " + timer);

        //set on click listener to all views
        for(View view : viewBox){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // if user click on the view is highlighted then got 10 score
                    if(highlightedView != null && view == highlightedView) {
                        //player click on highlighted view and reset the highlight
                        score += 10;
                        scoreText.setText("Score： " + score);
                        highlightedView.setBackgroundColor(getResources().getColor(R.color.white));
                        highlightedView = null;
                    }
                    //else if user click on the view is not highlighted then reduce 5 score
                    else if (highlightedView != null && view != highlightedView){
                        score -= 5;
                        scoreText.setText("Score： " + score);
                        highlightedView.setBackgroundColor(getResources().getColor(R.color.white));
                        highlightedView = null;
                    }
                }
            });
        }

        //start countdown timer

        countDownTimer = new CountDownTimer(6000,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                //update timer text to system
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerText.setText("Timer: " + secondsLeft);


                //check is there a highlighted
                if(highlightedView == null){
                    int randomHighlight = random.nextInt(viewBox.length);
                    highlightedView = viewBox[randomHighlight];
                    highlightedView.setBackgroundResource(R.drawable.black_view);

                }
            }

            @Override
            public void onFinish() {
                // reset views to white color
                for(View view:viewBox){
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    view.setTag(null);
                }
                highlightedView = null;


                showLevelCompletion(score);
            }
        };
        countDownTimer.start();
    }

    protected void onDestroy(){
        super.onDestroy();

        //Stop countdown Timer
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }


    private void showLevelCompletion(final int score){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Finished!!!");
        builder.setMessage("You have finish level 1 \n with the score of " + score);


        builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //Go to level2
                Intent intent = new Intent(Level1Activity.this, Level2Activity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });

        builder.show();
    }

    private void readyNotification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ready!!!");
        builder.setMessage(" Please be ready, the game is starting ");


        builder.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                startGame();
            }
        });

        builder.show();
    }

}