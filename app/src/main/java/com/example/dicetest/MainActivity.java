package com.example.dicetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Button rollDice;
    String mPhase; //Come out or Point
    int mDieResult1; //initial die value
    int mDieResult2; //initial die 2 value
    int mPoint; //point value once set
    int mDieTotal; //total of dice, to be used to compare
    int upperBound = 7; //max value for die roll = upperbound - 1
    Random rand = new Random();
    boolean comeoutRoll = true;
    boolean startNewGame; //need to pass this to restore values if rotate immediately after comeout roll
    boolean rollDieEnabled; // needed to pass to restore values if rotate

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
        rollDice = findViewById(R.id.roll_die_button);

        mPhaseValue.setText(R.string.roll_value_come_out);
        mPhase = mPhaseValue.getText().toString();
        rollDieEnabled = true;
        rollDice.setEnabled(rollDieEnabled); //enables roll dice button

        //restores value if screen rotates
        if (savedInstanceState != null) {
            mDieResult1 = savedInstanceState.getInt("die1value");
            mDieResult2 = savedInstanceState.getInt("die2value");
            mDieTotal = savedInstanceState.getInt("rollValue");
            mPoint = savedInstanceState.getInt("pointValue");
            mPhase = savedInstanceState.getString("phaseValue");
            comeoutRoll = savedInstanceState.getBoolean("comeOutRoll");
            startNewGame = savedInstanceState.getBoolean("gameWon");
            rollDieEnabled = savedInstanceState.getBoolean("rollDieEnabled");

            mShowDie1.setText(String.valueOf(mDieResult1));
            mShowDie2.setText(String.valueOf((mDieResult2)));

            mPointValue.setText(String.valueOf(mPoint));
            mRollValue.setText(String.valueOf(mDieTotal));
            mPhaseValue.setText(mPhase);
            rollDice.setEnabled(rollDieEnabled);

            //logic to show New Game button
            if (startNewGame) {
                newGame.setVisibility(View.VISIBLE);
            }
        }

    }

    public void rollDie(View view) {
        //generate random number between 1 and 6 for the die and set value of the appropriate textview

        mDieResult1 = rand.nextInt(upperBound);
        mDieResult2 = rand.nextInt(upperBound);

        //if result == 0, set value to 1
        if (mDieResult1 == 0) {
            mDieResult1 = 1;
        }

        if (mDieResult2 == 0) {
            mDieResult2 = 1;
        }

        mShowDie1.setText(String.valueOf(mDieResult1));
        mShowDie2.setText(String.valueOf(mDieResult2));

        //show roll value
        mDieTotal = mDieResult1 + mDieResult2;
        mRollValue.setText(String.valueOf(mDieTotal));

        //set point and new game button visibility
        //mPhase = mPhaseValue.getText().toString();

        //
        if (mPhase.equals("Come Out Roll")) {
            switch (mDieTotal) {
                case 7:
                    mPhaseValue.setText(R.string.come_out_7);  //sets message to player
                    mPhase = mPhaseValue.getText().toString(); //sets value for outState
                    newGame.setVisibility(View.VISIBLE); //sets visibility on new game button
                    startNewGame = true; //sets value for outState
                    rollDice.setEnabled(false);
                    break;
                case 2:
                    mPhaseValue.setText(R.string.craps_2_lose);
                    mPhase = mPhaseValue.getText().toString(); //sets value for outState
                    newGame.setVisibility(View.VISIBLE); //sets visibility on new game button
                    startNewGame = true; //sets value for outState
                    rollDice.setEnabled(false);
                    break;
                case 3:
                    mPhaseValue.setText(R.string.craps_3_lose);
                    mPhase = mPhaseValue.getText().toString(); //sets value for outState
                    newGame.setVisibility(View.VISIBLE); //sets visibility on new game button
                    startNewGame = true; //sets value for outState
                    rollDice.setEnabled(false);
                    break;
                case 12:
                    mPhaseValue.setText(R.string.craps_12_lose);
                    mPhase = mPhaseValue.getText().toString(); //sets value for outState
                    newGame.setVisibility(View.VISIBLE); //sets visibility on new game button
                    startNewGame = true; //sets value for outState
                    rollDice.setEnabled(false);
                    break;
                case 11:
                    mPhaseValue.setText(R.string.win_11);
                    mPhase = mPhaseValue.getText().toString(); //sets value for outState
                    newGame.setVisibility(View.VISIBLE); //sets visibility on new game button
                    startNewGame = true; //sets value for outState
                    rollDice.setEnabled(false);
                    break;
                default:
                    mPhaseValue.setText(R.string.phase_point);
                    mPhase = mPhaseValue.getText().toString(); //sets value for outState
                    mPoint = mDieTotal;
                    mPointValue.setText(String.valueOf(mPoint));
                    startNewGame = false;
                    newGame.setVisibility(View.INVISIBLE);
                    break;
            }

        } else if (mPhase.equals("Point")) {
            if (mDieTotal == mPoint) {
                mPhaseValue.setText(R.string.point_winner);
                mPhase = mPhaseValue.getText().toString();  //sets value for outState
                //show new game button
                newGame.setVisibility(View.VISIBLE);
                startNewGame = true;
                rollDieEnabled = false;
                rollDice.setEnabled(rollDieEnabled);
            }
            else if(mDieTotal == 7){
                mPhaseValue.setText(R.string.seven_out_lose);
                mPhase = mPhaseValue.getText().toString();
                newGame.setVisibility(View.VISIBLE);
                startNewGame = true;
                rollDieEnabled = false;
                rollDice.setEnabled(rollDieEnabled);
            } else {
                mPhaseValue.setText(R.string.phase_point);
                mPhase = mPhaseValue.getText().toString();
                newGame.setVisibility(View.INVISIBLE);
                startNewGame = false;
                rollDieEnabled = true;
                rollDice.setEnabled(rollDieEnabled);
            }
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
        outState.putBoolean("gameWon", startNewGame);
        outState.putBoolean("rollDieEnabled", rollDieEnabled);
    }


    public void newGame(View view) {
        //reset all settings to begin a new game
        mDieTotal = 0;
        mRollValue.setText(String.valueOf(mDieTotal));
        mDieResult1 = 0;
        mShowDie1.setText(String.valueOf(mDieResult1));
        mDieResult2 = 0;
        mShowDie2.setText(String.valueOf(mDieResult2));
        mPoint = 0;
        mPointValue.setText(String.valueOf(mPoint));
        mPhaseValue.setText(R.string.roll_value_come_out);
        mPhase = mPhaseValue.getText().toString();
        newGame.setVisibility(View.INVISIBLE);
        comeoutRoll = true;
        //activate roll dice button
        startNewGame = true;
        rollDice.setEnabled(startNewGame);

    }
}