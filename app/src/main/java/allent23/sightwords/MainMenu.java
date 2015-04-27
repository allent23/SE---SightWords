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
                                Intent writing = new Intent(MainMenu.this, Writing.class);
                                startActivity(writing);
                                break;

                            case 3:
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
        return 4;
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
                resId = R.layout.optionfrag4;
                view = inflater.inflate(resId, container, false);
                ((ViewPager) container).addView(view, 0);
                break;

            case 3:
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



