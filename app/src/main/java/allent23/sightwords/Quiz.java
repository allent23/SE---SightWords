package allent23.sightwords;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Locale;
import java.util.Random;

import static android.content.Context.*;
import static android.view.View.OnClickListener;

public class Quiz extends ActionBarActivity
{
    Context context = this;
    AlertDialog.Builder alertDialogBuilder;

    int arraySize = 0;

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

    String[] temp = new String[arraySize];
    String[] option1_arr, option2_arr, question_arr, right_arr, wrong_arr;

    int count = 0;
    int backcount = 0;

    Animation question_spin, jump_forward, wrong_shake, button_shake, slide_in, slide_out;

    LinearLayout lin_layout;
    Button option1_play, option2_play, question_play;
    TextToSpeech ttobj;

    boolean backwards = false;

    RadioButton option1, option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlayout);

        ttobj = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.US);
                        }
                    }
                });

        alertDialogBuilder = new AlertDialog.Builder(context);

        Button home = (Button) findViewById(R.id.home);
        Button next = (Button) findViewById(R.id.next);
        Button back = (Button) findViewById(R.id.back);
        RadioButton option1 = (RadioButton) findViewById(R.id.option1);
        RadioButton option2 = (RadioButton) findViewById(R.id.option2);
        lin_layout = (LinearLayout) findViewById(R.id.lin_layout);

        option1_play = (Button) findViewById(R.id.option1_play);
        option2_play = (Button) findViewById(R.id.option2_play);
        question_play = (Button) findViewById(R.id.question_playboi);

        TextView question = (TextView) findViewById(R.id.question);

        home.setOnClickListener(buttonListener);
        next.setOnClickListener(buttonListener);
        back.setOnClickListener(buttonListener);

        option1.setOnClickListener(radioListener);
        option2.setOnClickListener(radioListener);

        question_play.setOnClickListener(buttonListener);
        option1_play.setOnClickListener(buttonListener);
        option2_play.setOnClickListener(buttonListener);

        question_spin = AnimationUtils.loadAnimation(this, R.anim.question_full_shake);
        wrong_shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        button_shake = AnimationUtils.loadAnimation(this, R.anim.buttonshake);
        jump_forward = AnimationUtils.loadAnimation(this, R.anim.jumper_forward);
        slide_in = AnimationUtils.loadAnimation(this, R.anim.cardflip_left_in);
        slide_out = AnimationUtils.loadAnimation(this, R.anim.cardflip_left_out);

        next.startAnimation(button_shake);
        back.startAnimation(button_shake);

        insertWords();

        question_arr = new String[arraySize];

        arr = new String[arraySize];
        shuffled = new int[arraySize];

        if (arraySize == 0)
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
        else
        {
            int arrcounter = 0;

            for (String x : wordArr) {
                arr[arrcounter] = x;
                arrcounter++;
            }

            for (int i = 0; i < arraySize; i++) {
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

    void nextQuestion(final TextView question, final RadioButton option1, final RadioButton option2) {
        alertDialogBuilder = new AlertDialog.Builder(context);
        //store right and wrong in arrays for back button
        option1.setChecked(false);
        option2.setChecked(false);

        option1.setTextColor(Color.BLACK);
        option2.setTextColor(Color.BLACK);
        final Context context = this;

        final TableRow tb1 = (TableRow) findViewById(R.id.tb1);
        final TableRow tb2 = (TableRow) findViewById(R.id.tb2);

        if (count < (arraySize))
        {

                String temp = arr[shuffled[count]];

                question_arr[count] = temp;

                int random_char = rand.nextInt(temp.length());

                char random_letter = temp.charAt(random_char);
                right_answer = Character.toString(random_letter);

                char wrong_letter = alphabet.charAt(rand.nextInt(alphabet.length()));

                while (wrong_letter == random_letter) {
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

                if (rand.nextInt(2) == 1)
                {
                    option1.setText(right_answer);
                    option2.setText(wrong_answer);
                } else
                {
                    option1.setText(wrong_answer);
                    option2.setText(right_answer);
                }
                count++;
        }
        else
        {

            tb1.setVisibility(View.INVISIBLE);
            tb2.setVisibility(View.INVISIBLE);

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

                            option1.setText("");
                            option2.setText("");

                            count = 0;
                            backcount = 0;

                            nextQuestion(question, option1, option2);
                            dialog.cancel();
                            tb1.setVisibility(View.VISIBLE);
                            tb2.setVisibility(View.VISIBLE);

                        }
                    })
                    .setPositiveButton("No, Thank you!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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


    private OnClickListener buttonListener;

    {
        buttonListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button activities = (Button) v;
                TextView question = (TextView) findViewById(R.id.question);
                RadioButton option1 = (RadioButton) findViewById(R.id.option1);
                RadioButton option2 = (RadioButton) findViewById(R.id.option2);


                switch (v.getId()) {
                    case R.id.next:

                        option1.setChecked(false);
                        option2.setChecked(false);

                        backwards = false;

                        if (backcount <= count  || count >= arraySize)
                        {

                            option1.setVisibility(View.VISIBLE);
                            option2.setVisibility(View.VISIBLE);
                            lin_layout.setVisibility(View.VISIBLE);

                            question.setText("");
                            lin_layout.setAnimation(slide_in);
                            question.startAnimation(slide_in);
                            question.setText("");
                            question_play.startAnimation(slide_in);

                            nextQuestion(question, option1, option2);

                        }
                        else
                        {
                            question.setText("");
                            question.startAnimation(slide_in);
                            question.setText("");
                            question.setText(question_arr[count]);
                            lin_layout.setVisibility(View.INVISIBLE);

                            count++;
                        }

                        break;

                    case R.id.back:

                        if (count > 1)
                        {
                            count--;

                            if(backcount < count )
                                backcount = count;

                            lin_layout.setVisibility(View.INVISIBLE);

                            question.setText("");
                            question.startAnimation(slide_out);
                            question_play.startAnimation(slide_out);

                            option1.setChecked(false);
                            option2.setChecked(false);

                            question.setText(question_arr[count-1]);

                        }

                        break;

                    case R.id.home:
                        Intent intent = new Intent(Quiz.this, MainMenu.class);
                        startActivities(new Intent[]{intent});
                        break;

                    case R.id.question_playboi:
                        speakText(arr[shuffled[count-1]]);
                        break;
                    case R.id.option1_play:
                        speakText(option1.getText().toString());
                        break;
                    case R.id.option2_play:
                        speakText(option2.getText().toString());
                        break;
                }
            }
        };
    }

    private OnClickListener radioListener;

    {
        radioListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();

                RadioButton option1 = (RadioButton) findViewById(R.id.option1);
                RadioButton option2 = (RadioButton) findViewById(R.id.option2);
                TextView question = (TextView) findViewById(R.id.question);

                boolean check_1, check_2;

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.option1:

                        option1.setChecked(true);
                        option2.setChecked(false);

                        check_1 = option1.isChecked();

                        if ((check_1 == true) && (option1.getText() == right_answer)) {

                            option1.setText("");
                            option2.setText("");

                            question.startAnimation(slide_in);
                            question_play.startAnimation(slide_in);
                            lin_layout.startAnimation(slide_in);

                            check_1 = false;

                            nextQuestion(question, option1, option2);
                        }

                        if ((check_1 == true) && (option1.getText() == wrong_answer)) {
                            question.startAnimation(wrong_shake);
                            option1.setTextColor(Color.RED);
                            option2.setTextColor(Color.BLACK);
                        }


                        break;

                    case R.id.option2:

                        option1.setChecked(false);
                        option2.setChecked(true);

                        check_2 = option2.isChecked();

                        if ((check_2 == true) && (option2.getText() == right_answer)) {

                            option1.setChecked(false);
                            option2.setChecked(false);

                            option1.setText("");
                            option2.setText("");

                            question.startAnimation(slide_in);
                            question_play.startAnimation(slide_in);
                            lin_layout.startAnimation(slide_in);

                            check_2 = false;

                            nextQuestion(question, option1, option2);
                        }

                        if ((check_2 == true) && (option2.getText() == wrong_answer)) {
                            question.startAnimation(wrong_shake);
                            option2.setTextColor(Color.RED);
                            option1.setTextColor(Color.BLACK);
                        }

                        break;
                }

            }
        };
    }

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
            temp = text.split(" ");

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
    @Override
    public void onPause(){
        if(ttobj !=null){
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();
    }

    public void speakText(String toSpeak) {

        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();

        Bundle bundle = intent.getExtras();

        //has a red underline on .speak below, but works fine.
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_ADD, bundle, null);

    }

}

