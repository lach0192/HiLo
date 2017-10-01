/**
 * HiLo Game - Guess a number Android Application
 *
 * @author Eric Lachapelle (lach0192@algonquinlive.com)
 */

package com.algonquincollege.lach0192.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    private Button guessButton;
    private Button resetButton;
    private EditText userGuessEditText;
    private String userGuessString;
    private int userGuess;
    private int guessCount = 0;
    private boolean guessLimit = false;
    private int theNumber;
    static int MIN = 1;
    static int MAX = 1000;
    static int WINSPLIT = 5;
    Random rn = new Random();
    int range = MAX - MIN + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theNumber = rn.nextInt(range) + MIN;

        Log.i("lach0192", "The Random Number: " + theNumber);

        guessButton = findViewById(R.id.guessButton);
        resetButton = findViewById(R.id.resetButton);
        userGuessEditText = findViewById(R.id.userGuess);

        // RESET ON CLICK
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        // RESET ON LONG CLICK
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String theNumberString = "theNumber: " + theNumber;

                Toast.makeText(getApplicationContext(),
                        theNumberString,
                        Toast.LENGTH_LONG).show();

                return true;
            }
        });

        // GUESS ON CLICK
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userGuessString = userGuessEditText.getText().toString();
                userGuessEditText.setText("");

                // RESET
                if (guessLimit) {
                    reset();
                }
                // GUESS
                else {

                    // make sure guess is not empty
                    if (!userGuessString.isEmpty()) {

                        Log.i("lach0192", "userGuess is not empty.");

                        userGuess = Integer.parseInt(userGuessString);

                        if (userGuess < MIN || userGuess > MAX) {
                            userGuessEditText.setError("Guess must be a number from 1 to 1000");
                            userGuessEditText.requestFocus();
                            return;
                        }

                        guessCount++;

                        Log.i("lach0192", "guessCount:" + guessCount);

                        // check if userGuess matches theNumber
                        if (userGuess == theNumber) {

                            Log.i("lach0192", "YOU WIN!!!");

                            if (guessCount <= WINSPLIT) {
                                Toast.makeText(getApplicationContext(),
                                        R.string.superiorWin,
                                        Toast.LENGTH_SHORT).show();
                            } else if (guessCount > WINSPLIT) {
                                Toast.makeText(getApplicationContext(),
                                        R.string.excellentWin,
                                        Toast.LENGTH_SHORT).show();
                            }

                            guessButton.setEnabled(false);
                            userGuessEditText.setEnabled(false);
                        } else if (userGuess > theNumber) {

                            Toast.makeText(getApplicationContext(),
                                    R.string.tooHigh,
                                    Toast.LENGTH_LONG).show();
                        } else if (userGuess < theNumber) {

                            Toast.makeText(getApplicationContext(),
                                    R.string.tooLow,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    // guess is empty
                    else {
                        Log.i("lach0192", "guess is empty.");
                        userGuessEditText.setError("Guess cannot be empty");
                        userGuessEditText.requestFocus();
                        return;
                    }

                    // ENDGAME
                    if (guessCount > 9) {

                        Toast.makeText(getApplicationContext(),
                                R.string.pleaseReset,
                                Toast.LENGTH_SHORT).show();

                        guessLimit = true;
                        guessButton.setEnabled(false);
                        userGuessEditText.setEnabled(false);
                    }

                    Log.i("lach0192", "userGuess is " + userGuess);
                }
            }
        });
    }

    private void reset() {
        Log.i("lach0192", "Resetting..");

        guessCount = 0;
        guessLimit = false;
        guessButton.setText(R.string.guessButtonText);
        userGuessEditText.setEnabled(true);
        guessButton.setEnabled(true);

        // reset the random number
        theNumber = rn.nextInt(range) + MIN;
        Log.i("lach0192", "The Random Number: " + theNumber);

    }

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
