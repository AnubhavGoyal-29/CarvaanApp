package startup.carvaan.myapplication.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.login.LoginActivity;

public class IntroSliderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout mDotLayout;
    private SliderAdaptar sliderAdaptar;
    //private ImageView[] mDots;
    private final int LENGTH_OF_ITEMS = new SliderAdaptar(IntroSliderActivity.this).getCount();
    private Button prevBtn;
    private Button nextBtn;
    private Button skipBtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro_slider);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        skipBtn = findViewById(R.id.skipBtn);

        viewPager = findViewById(R.id.viewPager);
        mDotLayout = findViewById(R.id.dotLayout);

        sliderAdaptar = new SliderAdaptar(IntroSliderActivity.this);

        viewPager.setAdapter(sliderAdaptar);
        mDotLayout.setupWithViewPager(viewPager, true);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage!=LENGTH_OF_ITEMS-1) {
                    viewPager.setCurrentItem(mCurrentPage + 1);
                }else {
                    startActivity(new Intent(IntroSliderActivity.this, LoginActivity.class));
                    finish();
                    Log.i("TAG", "ENDDDD");
                }

            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroSliderActivity.this, LoginActivity.class));
                finish();
            }
        });

        mDotLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                mCurrentPage = tab.getPosition();


                if(mCurrentPage==0){
                    nextBtn.setEnabled(true);
                    prevBtn.setEnabled(false);
                    prevBtn.setVisibility(View.INVISIBLE);
                    nextBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("Next");
                }else if(mCurrentPage==LENGTH_OF_ITEMS-1){
                    nextBtn.setEnabled(true);
                    prevBtn.setEnabled(true);
                    prevBtn.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("Finish");
                    prevBtn.setText("Back");
                }else{
                    nextBtn.setEnabled(true);
                    prevBtn.setEnabled(true);
                    prevBtn.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("Next");
                    prevBtn.setText("Back");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        addDotsIndicator(0);
//        viewPager.addOnPageChangeListener(viewListener);

    }

//    public void addDotsIndicator(int position) {
//
//        if (mDotLayout != null)
//            mDotLayout.removeAllViews();
//        mDots = new ImageView[sliderAdaptar.getCount()];
//
//
//        for (int i = 0; i < mDots.length; i++) {
//            mDots[i] = new ImageView(getApplicationContext());
//            if (i==position){
//                mDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_dot));
//            }else{
//                mDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_dot));
//            }
//            mDotLayout.addView(mDots[i]);
//        }
//
//        //      mDots[position].setTextColor(getResources().getColor(R.color.white));
//
//
//    }


//    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            addDotsIndicator(position);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };


}