package allent23.sightwords;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainMenu extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button flashcard = (Button) findViewById(R.id.flashcard_button);
        Button writingboard = (Button) findViewById(R.id.writing_button);
        Button quiz = (Button) findViewById(R.id.quiz_button);
        Button inputwords = (Button) findViewById(R.id.input_button);

        flashcard.setOnClickListener(buttonListener);
        writingboard.setOnClickListener(buttonListener);
        quiz.setOnClickListener(buttonListener);
        inputwords.setOnClickListener(buttonListener);

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

        buttonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                Button activities = (Button) v;

                if (activities.getText().equals("Flash Cards"))
                {
                    Intent flashcardpage = new Intent(MainMenu.this, FlashCards.class);
                    startActivity(flashcardpage);
                    //Dont forget to add new class intents into the android manifest bruh.
                }
                if (activities.getText().equals("Quiz"))
                {
                    Intent quizpage = new Intent(MainMenu.this, Quiz.class);
                    startActivity(quizpage);
                    //Dont forget to add new class intents into the android manifest bruh.
                }

            }

        };
    }


}
