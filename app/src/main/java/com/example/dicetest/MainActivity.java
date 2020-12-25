package com.example.dicetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView mShowDie1;
    TextView mShowDie2;
    int mDieResult1; //initial die value
    int mDieResult2; //initial die 2 value
    int upperBound = 7; //max value for die roll = upperbound - 1
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowDie1 = findViewById(R.id.dice_1_result);
        mShowDie2 = findViewById(R.id.dice_2_result);

        //restores value if screen rotates
        if(savedInstanceState != null){
            mDieResult1 = savedInstanceState.getInt("die1value");
            mDieResult2 = savedInstanceState.getInt("die2value");

            mShowDie1.setText(Integer.toString(mDieResult1));
            mShowDie2.setText(Integer.toString((mDieResult2)));
        }

    }

    public void rollDie(View view) {
        //generate random number between 1 and 6 for the die and set value of the appropriate textview
        Log.i("Roll Die", "Roll Die button clicked");
        mDieResult1 = rand.nextInt(upperBound);
        mDieResult2 = rand.nextInt(upperBound);

        //if result == 0, set value to 1
        if(mDieResult1 == 0){
            mDieResult1 = 1;
        }

        if(mDieResult2 == 0){
            mDieResult2 = 1;
        }


        mShowDie1.setText(Integer.toString(mDieResult1));
        mShowDie2.setText(Integer.toString(mDieResult2));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("die1value", mDieResult1);
        outState.putInt("die2value", mDieResult2);
    }
}