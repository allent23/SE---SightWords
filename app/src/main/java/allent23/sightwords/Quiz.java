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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Random;

public class Quiz extends ActionBarActivity {

    String arr[] = {"sat", "dog", "cat", "hat", "mat", "can", "got", "the", "out", "fat"};
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    List<Integer> numbersAvail = new ArrayList<>();
    int shuffled[] = new int[10];
    Random rand = new Random();
    StringBuilder builder = new StringBuilder();
    String right_answer;
    String wrong_answer;

    int count = 0;

    Animation question_spin, jump_forward, wrong_shake, button_shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlayout);

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

        for(int i = 0; i < 9; i++) {
            numbersAvail.add(i);
        }

        Collections.shuffle(numbersAvail);

        for (int i = 0; i < numbersAvail.size(); i++) {
            shuffled[i] = numbersAvail.get(i);
        }

        nextQuestion(question, option1, option2);


    }

    void nextQuestion(final TextView question, final RadioButton option1, final RadioButton option2)
    {

        //store right and wrong in arrays for back button
        option1.setChecked(false);
        option2.setChecked(false);

        option1.setTextColor(Color.BLACK);
        option2.setTextColor(Color.BLACK);
        final Context context = this;

        if (count < 10)
        {
            String temp = arr[shuffled[count]];
            char random_letter = temp.charAt(rand.nextInt(temp.length()));
            right_answer = Character.toString(random_letter);

            char wrong_letter = alphabet.charAt(rand.nextInt(alphabet.length()));
            while (wrong_letter == random_letter) {
                wrong_letter = alphabet.charAt(rand.nextInt(alphabet.length()));
            }
            wrong_answer = Character.toString(wrong_letter);

            for (int i = 0; i < temp.length(); i++) {
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

        if(count >= 10) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set title
            alertDialogBuilder.setTitle("Good Job! You Did It!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Play Again?")
                    .setCancelable(false)
                    .setNegativeButton("Yes, Please!",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            question.setText("All Done! Good Job!");
                            Collections.shuffle(numbersAvail);

                            for (int i = 0; i < numbersAvail.size() - 1; i++)
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
                            check_2 = false;

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

                            check_1 = false;
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

}
