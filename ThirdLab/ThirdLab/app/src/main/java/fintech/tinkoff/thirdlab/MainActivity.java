package fintech.tinkoff.thirdlab;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
        FirstFragment.FirstFragmentListener,
        SecondFragment.SecondFragmentListener,
        ThirdFragment.ThirdFragmentListener,
        FourthFragment.FourthFragmentListener{

    private static final int FIRST_PAGE = 0;
    private static final int SECOND_PAGE = 1;
    private static final int THIRD_PAGE = 2;
    private static final int FOURTH_PAGE = 3;

    private static final int FIRST_STEP = R.id.radioButton1;
    private static final int SECOND_STEP = R.id.radioButton2;
    private static final int THIRD_STEP = R.id.radioButton3;
    private static final int FOURTH_STEP = R.id.radioButton4;

    private static final int NUM_PAGES = 4;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private RadioGroup radioGroup;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;

    private Bundle args = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        radioGroup = (RadioGroup) findViewById(R.id.stepsRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case FIRST_STEP:
                        mPager.setCurrentItem(FIRST_PAGE);
                        break;
                    case SECOND_STEP:
                        mPager.setCurrentItem(SECOND_PAGE);
                        break;
                    case THIRD_STEP:
                        mPager.setCurrentItem(THIRD_PAGE);
                        break;
                    case FOURTH_STEP:
                        mPager.setCurrentItem(FOURTH_PAGE);
                        break;
                }
            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (mPager.getCurrentItem()){
                    case FIRST_PAGE:
                        radioGroup.check(FIRST_STEP);
                        break;
                    case SECOND_PAGE:
                        radioGroup.check(SECOND_STEP);
                        break;
                    case THIRD_PAGE:
                        radioGroup.check(THIRD_STEP);
                        break;
                    case FOURTH_PAGE:
                        radioGroup.check(FOURTH_STEP);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == FIRST_PAGE) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void firstCallBack(int number) {
        args.putInt("firstNumber", number);
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public void secondCallBack(int number) {
        args.putInt("secondNumber", number);
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);

    }

    @Override
    public void thirdCallBack(Integer action) {

        if ((args.get("secondNumber") != null) && (args.get("firstNumber") != null)) {
            if (action.equals(0)){
                Toast toast = Toast.makeText(
                        MainActivity.this,
                        "Choose action",
                        Toast.LENGTH_SHORT);
                toast.show();

            } else if (fourthFragment != null) {
                args.putInt("action", action);
                fourthFragment.setArguments(args);
                fourthFragment.onUpdateView();
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);

            } else {
                Toast toast = Toast.makeText(
                        MainActivity.this,
                        "ERROR",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {
            Toast toast = Toast.makeText(
                    MainActivity.this,
                    "Enter numbers",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void fourthCallBack() {
        mPager.setCurrentItem(mPager.getCurrentItem() - 3);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){

                case FIRST_PAGE:
                    firstFragment = new FirstFragment();
                    return firstFragment;

                case SECOND_PAGE:
                    secondFragment = new SecondFragment();
                    return secondFragment;

                case THIRD_PAGE:
                    thirdFragment = new ThirdFragment();
                    return thirdFragment;

                case FOURTH_PAGE:
                    fourthFragment = new FourthFragment();
                    fourthFragment.setArguments(args);
                    return fourthFragment;

                default:
                    return new FirstFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
