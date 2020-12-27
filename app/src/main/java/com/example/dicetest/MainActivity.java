package com.example.dicetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView mShowDie1;
    TextView mShowDie2;
    TextView mPointValue;
    TextView mRollValue;
    TextView mPhaseValue;
    Button newGame;
    String mPhase; //Come out or Point
    int mDieResult1; //initial die value
    int mDieResult2; //initial die 2 value
    int mPoint; //point value once set
    int mDieTotal; //total of dice, to be used to compare
    int upperBound = 7; //max value for die roll = upperbound - 1
    Random rand = new Random();
    boolean comeoutRoll = true;
    boolean gameWon; //need to pass this to restore values if rotate immediately after comeout roll

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowDie1 = findViewById(R.id.dice_1_result);
        mShowDie2 = findViewById(R.id.dice_2_result);
        mPointValue = findViewById(R.id.point_value);
        mRollValue = findViewById(R.id.roll_value);
        mPhaseValue = findViewById(R.id.phase_value);
        newGame = findViewById(R.id.new_game_button);

        mPhaseValue.setText(R.string.roll_value_come_out);
        mPhase = mPhaseValue.getText().toString();

        //restores value if screen rotates
        if (savedInstanceState != null) {
            mDieResult1 = savedInstanceState.getInt("die1value");
            mDieResult2 = savedInstanceState.getInt("die2value");
            mDieTotal = savedInstanceState.getInt("rollValue");
            mPoint = savedInstanceState.getInt("pointValue");
            mPhase = savedInstanceState.getString("phaseValue");
            comeoutRoll = savedInstanceState.getBoolean("comeOutRoll");
            gameWon = savedInstanceState.getBoolean("gameWon");

            mShowDie1.setText(Integer.toString(mDieResult1));
            mShowDie2.setText(Integer.toString((mDieResult2)));

            mPointValue.setText(Integer.toString(mPoint));
            mRollValue.setText(Integer.toString(mDieTotal));
            mPhaseValue.setText(mPhase);

            //logic to show New Game button
            if (gameWon) {
                newGame.setVisibility(View.VISIBLE);
            }
        }

    }

    public void rollDie(View view) {
        //generate random number between 1 and 6 for the die and set value of the appropriate textview
        Log.i("Roll Die", "Roll Die button clicked");

        //set tempComeoutRoll
        /*if(mPhase.equals("Come Out Roll")){
            tempComeoutRoll = true;
        } else {
            tempComeoutRoll = false;
        }*/

        mDieResult1 = rand.nextInt(upperBound);
        mDieResult2 = rand.nextInt(upperBound);

        //if result == 0, set value to 1
        if (mDieResult1 == 0) {
            mDieResult1 = 1;
        }

        if (mDieResult2 == 0) {
            mDieResult2 = 1;
        }

        mShowDie1.setText(Integer.toString(mDieResult1));
        mShowDie2.setText(Integer.toString(mDieResult2));

        //show roll value
        mDieTotal = mDieResult1 + mDieResult2;
        mRollValue.setText(Integer.toString(mDieTotal));

        //set point and new game button visibility
        mPhase = mPhaseValue.getText().toString();
        if (mPhase.equals("Come Out Roll")) {
            mPoint = mDieTotal;
            mPointValue.setText(Integer.toString(mDieTotal));
            mPhaseValue.setText(R.string.phase_point);
            mPhase = mPhaseValue.getText().toString();

        } else if (mPhase.equals("Point") && mPoint == mDieTotal) {
            newGame.setVisibility(View.VISIBLE);
            gameWon = true;


        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("die1value", mDieResult1);
        outState.putInt("die2value", mDieResult2);
        outState.putInt("pointValue", mPoint);
        outState.putInt("rollValue", mDieTotal);
        outState.putString("phaseValue", mPhase);
        outState.putBoolean("comeOutRoll", comeoutRoll);
        outState.putBoolean("gameWon", gameWon);
    }


    public void newGame(View view) {
        //reset all settings to begin a new game
        mDieTotal = 0;
        mRollValue.setText(Integer.toString(mDieTotal));
        mDieResult1 = 0;
        mShowDie1.setText(Integer.toString(mDieResult1));
        mDieResult2 = 0;
        mShowDie2.setText(Integer.toString(mDieResult2));
        mPoint = 0;
        mPointValue.setText(Integer.toString(mPoint));
        mPhaseValue.setText(R.string.roll_value_come_out);
        newGame.setVisibility(View.INVISIBLE);
        comeoutRoll = true;

    }
}