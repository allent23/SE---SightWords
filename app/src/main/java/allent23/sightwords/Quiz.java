package allent23.sightwords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Random;

import static android.content.Context.*;

public class Quiz extends ActionBarActivity {

    Context context = this;
    AlertDialog.Builder alertDialogBuilder;

    int arraySize;

    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    Random rand = new Random();
    StringBuilder builder = new StringBuilder();

    String right_answer;
    String wrong_answer;

    String FILENAME = "SightWords";

    String[] arr;
    int[] shuffled;

    List<Integer> numbersAvail = new ArrayList<>();
    List<String> wordArr = new ArrayList<>();

    String[] temp = new String[10];
    String[] temp2 = new String[10];

    int count = 0;

    Animation question_spin, jump_forward, wrong_shake, button_shake;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlayout);
        alertDialogBuilder = new AlertDialog.Builder(context);

        Button home = (Button) findViewById(R.id.home);
        Button next = (Button) findViewById(R.id.next);
        Button back = (Button) findViewById(R.id.back);
        RadioButton option1 = (RadioButton) findViewById(R.id.option1);
        RadioButton option2 = (RadioButton) findViewById(R.id.option2);
        TextView question = (TextView) findViewById(R.id.question);

        home.setOnClickListener(buttonListener);
        next.setOnClickListener(buttonListener);
        back.setOnClickListener(buttonListener);
        option1.setOnClickListener(radioListener);
        option2.setOnClickListener(radioListener);

        question_spin = AnimationUtils.loadAnimation(this, R.anim.question_full_shake);
        wrong_shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        button_shake = AnimationUtils.loadAnimation(this, R.anim.buttonshake);
        jump_forward = AnimationUtils.loadAnimation(this, R.anim.jumper_forward);

        next.startAnimation(button_shake);
        back.startAnimation(button_shake);

        insertWords();
        //arraySize = wordArr.size();

        arr =  new String[arraySize];
        shuffled = new int[arraySize];

        int arrcounter = 0;

        for (String x : wordArr)
        {
            arr[arrcounter] = x;
            arrcounter++;
        }


        if(arraySize == 0)
        {
            alertDialogBuilder.setTitle("Uh oh");

            // set dialog message
            alertDialogBuilder
                    .setMessage("No words have been inputted :(")
                    .setCancelable(false)
                    .setNegativeButton("Return Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Quiz.this.finish();
                        }
                    })
                    .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing

                            Intent inputwords = new Intent(Quiz.this, InputWords.class);
                            startActivity(inputwords);
                            Quiz.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        else {

            for (int i = 0; i < arraySize ; i++)
            {
                numbersAvail.add(i);
            }

            Collections.shuffle(numbersAvail);

            for (int i = 0; i < numbersAvail.size(); i++)
            {
                shuffled[i] = numbersAvail.get(i);
            }

            nextQuestion(question, option1, option2);
        }
    }

    void nextQuestion(final TextView question, final RadioButton option1, final RadioButton option2)
    {
        alertDialogBuilder = new AlertDialog.Builder(context);
        //store right and wrong in arrays for back button
        option1.setChecked(false);
        option2.setChecked(false);

        option1.setTextColor(Color.BLACK);
        option2.setTextColor(Color.BLACK);
        final Context context = this;

        System.out.println("Count: " + count + " --- ArraySize: " + arraySize);

        if (count < (arraySize))
        {
            String temp = arr[shuffled[count]];

            System.out.println(temp);

            System.out.println(temp.length());

            int random_char = rand.nextInt(temp.length());

            char random_letter = temp.charAt(random_char);
            right_answer = Character.toString(random_letter);

            char wrong_letter = alphabet.charAt(rand.nextInt(alphabet.length()));

            while (wrong_letter == random_letter)
            {
                wrong_letter = alphabet.charAt(rand.nextInt(alphabet.length()));
            }

            wrong_answer = Character.toString(wrong_letter);

            for (int i = 0; i < temp.length(); i++)
            {
                if (temp.charAt(i) == random_letter)
                    builder.append("_");

                else
                    builder.append(temp.charAt(i));
            }

            String modified_word = builder.toString();
            question.setText(modified_word);
            builder.delete(0, temp.length());

            if (rand.nextInt(2) == 1) {
                option1.setText(right_answer);
                option2.setText(wrong_answer);
            }
            else
            {
                option1.setText(wrong_answer);
                option2.setText(right_answer);
            }

            count++;
        }

        else {

            alertDialogBuilder.setTitle("Good Job! You Did It!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Play Again?")
                    .setCancelable(false)
                    .setNegativeButton("Yes, Please!",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            question.setText("All Done! Good Job!");
                            Collections.shuffle(numbersAvail);

                            for (int i = 0; i < numbersAvail.size(); i++)
                                shuffled[i] = numbersAvail.get(i);

                            option1.setText("");
                            option2.setText("");

                            count = 0;

                            nextQuestion(question,option1,option2);
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("No, Thank you!",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            Quiz.this.finish();

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

    private View.OnClickListener buttonListener;

    {
        buttonListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Button activities = (Button) v;
                TextView question = (TextView) findViewById(R.id.question);
                RadioButton option1 = (RadioButton) findViewById(R.id.option1);
                RadioButton option2 = (RadioButton) findViewById(R.id.option2);
                ImageView questionplay = (ImageView) findViewById(R.id.question_play);

                switch(v.getId()) {
                    case R.id.next:
                        question.setText("");

                        question.startAnimation(question_spin);
                        questionplay.startAnimation(question_spin);

                        option1.setChecked(false);
                        option2.setChecked(false);

                        nextQuestion(question, option1, option2);

                        break;

                    case R.id.back:

                        if(count != 0)
                        {
                            count--;
                            question.setText("");

                            question.startAnimation(question_spin);
                            questionplay.startAnimation(question_spin);

                            option1.setChecked(false);
                            option2.setChecked(false);
                            nextQuestion(question, option1, option2);

                            //Pass right and wrong answers into an array and use count to retrieve em
                        }

                        break;

                    case R.id.home:
                        Intent intent = new Intent(Quiz.this, MainMenu.class);
                        startActivities(new Intent[]{intent});
                        break;
                }
            }
        };
    }

    private View.OnClickListener radioListener;

    {
        radioListener = new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                boolean checked = ((RadioButton) v).isChecked();

                RadioButton option1 = (RadioButton) findViewById(R.id.option1);
                RadioButton option2 = (RadioButton) findViewById(R.id.option2);
                TextView question = (TextView) findViewById(R.id.question);
                ImageView questionplay = (ImageView) findViewById(R.id.question_play);

                boolean check_1, check_2;

                // Check which radio button was clicked
                switch(v.getId()) {
                    case R.id.option1:

                        option1.setChecked(true);
                        option2.setChecked(false);

                        check_1 = option1.isChecked();

                        if ( ( check_1 == true )&& (option1.getText() == right_answer))
                        {
                            question.startAnimation(question_spin);
                            questionplay.startAnimation(question_spin);

                            option1.setText("");
                            option2.setText("");

                            check_1 = false;
                            //check_2 = false;


                                nextQuestion(question, option1, option2);
                        }

                        if ( ( check_1 == true )&& (option1.getText() == wrong_answer))
                        {
                            question.startAnimation(wrong_shake);
                            option1.setTextColor(Color.RED);
                            option2.setTextColor(Color.BLACK);
                        }

                        break;

                    case R.id.option2:

                        option1.setChecked(false);
                        option2.setChecked(true);

                        check_2 = option2.isChecked();

                        if ( ( check_2 == true )&& (option2.getText() == right_answer))
                        {
                            question.startAnimation(question_spin);
                            questionplay.startAnimation(question_spin);

                            option1.setChecked(false);
                            option2.setChecked(false);

                            option1.setText("");
                            option2.setText("");

                            //check_1 = false;
                            check_2 = false;


                                nextQuestion(question, option1, option2);
                        }

                        if (( check_2 == true )&& (option2.getText() == wrong_answer))
                        {
                            question.startAnimation(wrong_shake);
                            option2.setTextColor(Color.RED);
                            option1.setTextColor(Color.BLACK);
                        }

                        break;
                }

            }
        };
    }

    public void insertWords()
    {

        FileInputStream in = null;
        try
        {
            in = openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
            }

            String text = sb.toString();
            temp = text.split(" ");

            int blah = 0;
            for (String x : temp)
            {
                wordArr.add(x);
                blah++;
            }
            arraySize = blah ;
            blah = 0;

        }
        catch (FileNotFoundException e)
        {
            alertDialogBuilder.setTitle("Uh oh");

            // set dialog message
            alertDialogBuilder
                    .setMessage("No words have been inputted :(")
                    .setCancelable(false)
                    .setNegativeButton("Return Home", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Quiz.this.finish();
                        }
                    })
                    .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing

                            Intent inputwords = new Intent(Quiz.this, InputWords.class);
                            startActivity(inputwords);
                            Quiz.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
        catch (IOException ioe) {
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


}
