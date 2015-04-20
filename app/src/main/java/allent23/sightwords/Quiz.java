package allent23.sightwords;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Arrays;
import java.util.Random;

public class Quiz extends ActionBarActivity {

    String arr[] = {"sat", "dog", "cat", "hat", "mat", "can", "got", "the", "out", "fat"};
    List<Integer> numbersAvail = new ArrayList<>();
    int shuffled[] = new int[10];
    Random rand = new Random();
    StringBuilder builder = new StringBuilder();

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlayout);

        Button back = (Button) findViewById(R.id.back);
        Button next = (Button) findViewById(R.id.next);
        RadioButton option1 = (RadioButton) findViewById(R.id.option1);
        RadioButton option2 = (RadioButton) findViewById(R.id.option2);
        TextView question = (TextView) findViewById(R.id.question);

        back.setOnClickListener(buttonListener);
        next.setOnClickListener(buttonListener);
        option1.setOnClickListener(radioListener);
        option2.setOnClickListener(radioListener);

        for(int i = 0; i < 9; i++) {
            numbersAvail.add(i);
        }

        Collections.shuffle(numbersAvail);

        for (int i = 0; i < numbersAvail.size(); i++) {
            shuffled[i] = numbersAvail.get(i);
        }

        question.setText(arr[shuffled[count]]);
        count++;
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

                switch(v.getId()) {
                    case R.id.next:

                        if(count != 9)
                        {
                           String temp = arr[shuffled[count]];
                           char random_letter = temp.charAt(rand.nextInt(temp.length()));
                            String right_answer = Character.toString(random_letter);

                            for (int i = 0; i < temp.length(); i++)
                            {
                                if(temp.charAt(i) == random_letter)
                                {
                                    builder.append("_");
                                }
                                else
                                    builder.append(temp.charAt(i));
                            }

                            String modified_word = builder.toString();

                            question.setText(modified_word);
                            count++;
                            builder.delete(0,temp.length());

                            if(rand.nextInt(2) == 1)
                            {
                                option1.setText(right_answer);
                                option2.setText("random letter");
                            }
                            else
                            {
                                option2.setText(right_answer);
                                option1.setText("random letter");
                            }

                        }

                        else
                        {
                            question.setText("All Done! Good Job!");
                            Collections.shuffle(numbersAvail);

                            for (int i = 0; i < numbersAvail.size(); i++) {
                                shuffled[i] = numbersAvail.get(i);
                            }

                            count = 0;
                        }

                        option1.setChecked(false);
                        option2.setChecked(false);

                        break;

                    case R.id.back:
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

                // Check which radio button was clicked
                switch(v.getId()) {
                    case R.id.option1:
                        if (checked)
                            option1.setText("picked me #1!");
                            option2.setChecked(false);
                            break;
                    case R.id.option2:
                        if (checked)
                            option2.setText("picked me #2!");
                            option1.setChecked(false);
                            break;
                }

            }
        };
    }
}
