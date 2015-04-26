package allent23.sightwords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;



/*
public class MainMenu extends ActionBarActivity
{
*//*

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
                if (activities.getText().equals("Input Words"))
                {
                    Intent inputwordspage = new Intent(MainMenu.this, InputWords.class);
                    startActivity(inputwordspage);
                    //Dont forget to add new class intents into the android manifest bruh.
                }

            }

        };
    }*//*


}
*/

public class MainMenu extends ActionBarActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        MyPagerAdapter adapter = new MyPagerAdapter();
        mPager = (ViewPager) findViewById(R.id.pager);

        Button enter = (Button) findViewById(R.id.enter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);

        enter.setOnClickListener(buttonListener);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.buttonshake);

        enter.startAnimation(shake);

    }

    private View.OnClickListener buttonListener;

    {

        buttonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Button activities = (Button) v;

                switch(v.getId()) {
                    case R.id.enter:

                        switch(mPager.getCurrentItem())
                        {
                            case 0:
                                Intent inputwordspage = new Intent(MainMenu.this, InputWords.class);
                                startActivity(inputwordspage);
                                break;
                            case 1:
                                Intent flashcardpage = new Intent(MainMenu.this, FlashCards.class);
                                startActivity(flashcardpage);
                                break;
                            case 2:
                                Intent quizpage = new Intent(MainMenu.this, Quiz.class);
                                startActivity(quizpage);
                                break;
                        }
                        break;
                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

}

class MyPagerAdapter extends PagerAdapter
{
    public int getCount() {
        return 3;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Using different layouts in the view pager instead of images.

        int resId = -1;
        View view = null;

        //Getting my layout's in my adapter. Three layouts defined.
        switch (position) {
            case 0:
                resId = R.layout.optionfrag1;
                view = inflater.inflate(resId, container, false);
                ((ViewPager) container).addView(view, 0);
                break;

            case 1:
                resId = R.layout.optionfrag2;
                view = inflater.inflate(resId, container, false);
                ((ViewPager) container).addView(view, 0);
                break;

            case 2:
                resId = R.layout.optionfrag3;
                view = inflater.inflate(resId, container, false);
                ((ViewPager) container).addView(view, 0);
                break;
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}



