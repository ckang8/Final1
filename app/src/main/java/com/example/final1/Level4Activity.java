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

public class Level4Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_level4);


        //get the score from level 3
        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);


        //time
        timerText = findViewById(R.id.timerText);

        //score
        scoreText = findViewById(R.id.scoreText);


        //views
        viewBox = new View[]{
                findViewById(R.id.view1),
                findViewById(R.id.view2),
                findViewById(R.id.view3),
                findViewById(R.id.view4),
                findViewById(R.id.view5),
                findViewById(R.id.view6),
                findViewById(R.id.view7),
                findViewById(R.id.view8),
                findViewById(R.id.view9),
                findViewById(R.id.view10),
                findViewById(R.id.view11),
                findViewById(R.id.view12),
                findViewById(R.id.view13),
                findViewById(R.id.view14),
                findViewById(R.id.view15),
                findViewById(R.id.view16),
                findViewById(R.id.view17),
                findViewById(R.id.view18),
                findViewById(R.id.view19),
                findViewById(R.id.view20),
                findViewById(R.id.view21),
                findViewById(R.id.view22),
                findViewById(R.id.view23),
                findViewById(R.id.view24),
                findViewById(R.id.view25),
        };

        //put random
        random = new Random();

        //start the game
        startGame();

    }


    private void startGame(){
        //reset the score and timer
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
        countDownTimer = new CountDownTimer(6000,1000) {
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



                //Alert and go to level 4
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
        builder.setMessage("You have finish level 4 \n with the score of " + score);


        builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //Go to level2
                Intent intent = new Intent(Level4Activity.this, Level5Activity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }


}