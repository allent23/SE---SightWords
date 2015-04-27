package allent23.sightwords;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.view.View.OnClickListener;

public class FlashCards extends ActionBarActivity
{
    Context context = this;
    AlertDialog.Builder alertDialogBuilder; //Dialog boxes

    //size of the array thats being passed in from Input Words
    int arraySize = 0;

    Random rand = new Random();

    String FILENAME = "SightWords"; //filename from Input Words

    String[] arr; //the array that contains the words
    int[] shuffled; //array that holds randoms ints produced


    List<Integer> numbersAvail = new ArrayList<>();
    List<String> wordArr = new ArrayList<>();

    String[] temp = new String[arraySize];

    //count for which word we're on
    int count = 0;

    //declare all the animation
    Animation wrong_shake, button_shake, slide_in, slide_out;
    Button question_play;

    //declare the object for speaking
    TextToSpeech ttobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcards);

        ttobj = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.US);
                        }
                    }
                });

        //initialize the dialog box builder
        alertDialogBuilder = new AlertDialog.Builder(context);

        //find the id's for each item needed
        Button home = (Button) findViewById(R.id.home);
        Button next = (Button) findViewById(R.id.next);
        Button back = (Button) findViewById(R.id.back);
        question_play = (Button) findViewById(R.id.question_playboi);
        TextView question = (TextView) findViewById(R.id.question);

        //set listeners
        home.setOnClickListener(buttonListener);
        next.setOnClickListener(buttonListener);
        back.setOnClickListener(buttonListener);
        question_play.setOnClickListener(buttonListener);

        //initialize all the animations
        wrong_shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        button_shake = AnimationUtils.loadAnimation(this, R.anim.buttonshake);
        slide_in = AnimationUtils.loadAnimation(this, R.anim.cardflip_left_in);
        slide_out = AnimationUtils.loadAnimation(this, R.anim.cardflip_left_out);

        //start the small button shaking
        next.startAnimation(button_shake);
        back.startAnimation(button_shake);

        //get the words from the file
        insertWords();

        //initialize the arrays using data retrieved from file
        arr = new String[arraySize];
        shuffled = new int[arraySize];

        //if the file doesn't contain anything, go back home or to settings
        if (arraySize == 0)
        {
            alertDialogBuilder.setTitle("Uh oh");

            // set dialog message
            alertDialogBuilder
                    .setMessage("No words have been inputted :(")
                    .setCancelable(false)
                    .setNegativeButton("Return Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            FlashCards.this.finish();
                        }
                    })
                    .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing

                            Intent inputwords = new Intent(FlashCards.this, InputWords.class);
                            startActivity(inputwords);
                            FlashCards.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        else //or else, get started
        {
            //enter the words into the array
            int arrcounter = 0;
            for (String x : wordArr) {
                arr[arrcounter] = x;
                arrcounter++;
            }

            //enter the numbers into the array thats gonna get shuffled
            for (int i = 0; i < arraySize; i++) {
                numbersAvail.add(i);
            }

            Collections.shuffle(numbersAvail); //shuffle it

            for (int i = 0; i < numbersAvail.size(); i++)
            {
                shuffled[i] = numbersAvail.get(i);
            }

            //move to the first question
            nextQuestion(question);
        }
    }

    //Function nextQuestion moves the app to the next question in the array
    //or it ends the game
    void nextQuestion(final TextView question) {
        alertDialogBuilder = new AlertDialog.Builder(context);
        //store right and wrong in arrays for back button

        final Context context = this;

        //keep going
        if (count < (arraySize))
        {
            String next_word = arr[shuffled[count]];
            question.setText(next_word);
            count++;
        }
        else //otherwise, you finished the game
        {
            //display dialog with choices of what to do
            alertDialogBuilder.setTitle("Good Job! You Did It!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Play Again?")
                    .setCancelable(false).setInverseBackgroundForced(true)
                    .setNegativeButton("Yes, Please!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            question.setText("All Done! Good Job!");
                            Collections.shuffle(numbersAvail);

                            for (int i = 0; i < numbersAvail.size(); i++)
                                shuffled[i] = numbersAvail.get(i);

                            count = 0;

                            nextQuestion(question);
                            dialog.cancel();

                        }
                    })
                    .setPositiveButton("No, Thank you!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            FlashCards.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    private OnClickListener buttonListener;
    {
        buttonListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button activities = (Button) v;
                TextView question = (TextView) findViewById(R.id.question);

                switch (v.getId()) {
                    case R.id.next:
                            //skip to the next word
                            question.setText("");
                            question.startAnimation(slide_in);
                            question.setText("");
                            question_play.startAnimation(slide_in);
                            nextQuestion(question);
                        break;

                    case R.id.back:
                        //go back one, unless count is <= 1
                        if (count > 1)
                        {
                            count = count - 2;
                            question.setText("");
                            question.startAnimation(slide_out);
                            question_play.startAnimation(slide_out);
                            nextQuestion(question);
                        }

                        break;

                    case R.id.home:
                        Intent intent = new Intent(FlashCards.this, MainMenu.class);
                        startActivities(new Intent[]{intent});
                        break;

                    case R.id.question_playboi: //play button
                        speakText(arr[shuffled[count-1]]);
                        break;
                }
            }
        };
    }

    //Function insertWords, reads the file and enters it into an array
    public void insertWords() {

        FileInputStream in = null;
        try {
            in = openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String text = sb.toString();
            temp = text.split(" "); //split on the spaces

            int blah = 0;
            for (String x : temp)
            {
                if (x != "")
                {
                    wordArr.add(x);
                    blah++;
                }
            }
            arraySize = blah;
            blah = 0;

        } catch (FileNotFoundException e) {
            alertDialogBuilder.setTitle("Uh oh");

            // set dialog message
            alertDialogBuilder
                    .setMessage("No words have been inputted :(")
                    .setCancelable(false)
                    .setNegativeButton("Return Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            FlashCards.this.finish();
                        }
                    })
                    .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing

                            Intent inputwords = new Intent(FlashCards.this, InputWords.class);
                            startActivity(inputwords);
                            FlashCards.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        } finally {
            // close the streams using close method
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    //used for the speaking
    @Override
    public void onPause(){
        if(ttobj !=null){
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();
    }

    //used for speaking
    public void speakText(String toSpeak)
    {
        //make a small message appear on the bottom, with the word to be spoken
        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();

        Bundle bundle = intent.getExtras();

        //has a red underline on .speak below, but works fine as long as API 21+
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_ADD, bundle, null);

    }

}

