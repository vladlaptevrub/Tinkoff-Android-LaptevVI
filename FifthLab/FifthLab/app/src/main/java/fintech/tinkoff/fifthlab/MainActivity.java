package fintech.tinkoff.fifthlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
        FirstFragment.FirstFragmentListener,
        SecondFragment.SecondFragmentListener,
        ThirdFragment.ThirdFragmentListener,
        FourthFragment.FourthFragmentListener{

    private final static int FIRST_PAGE = 0;
    private final static int SECOND_PAGE = 1;
    private final static int THIRD_PAGE = 2;
    private final static int FOURTH_PAGE = 3;

    private final static int SQ = 1;
    private final static int SQRT = 2;
    private final static int DIV = 3;
    private final static int COS = 4;
    private final static int SIN = 5;

    private static final int NUM_PAGES = 4;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CustomGraphView customGraphView;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        customGraphView = (CustomGraphView) findViewById(R.id.graphView);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void firstCallBack(int progress, boolean isVisible) {
        customGraphView.setVisible(isVisible);
        customGraphView.setScaleProgress((float)progress);
        customGraphView.invalidate();
    }

    @Override
    public void secondCallBack(String graphName, String axisNameY, String axisNameX) {
        customGraphView.setGraphName(graphName);
        customGraphView.setAxisNameY(axisNameY);
        customGraphView.setAxisNameX(axisNameX);
        customGraphView.invalidate();
        Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void thirdCallBack(int funcName, int funcColor) {
        customGraphView.addFunc(new Func(funcName, funcColor));
        customGraphView.invalidate();
        String function = "error";
        switch (funcName) {
            case SQRT:
                function = "'y = sqrt(2)' added";
                break;
            case SQ:
                function = "'y = x^2' added";
                break;
            case DIV:
                function = "'y = 1/x' added";
                break;
            case COS:
                function = "'y = cos(x)' added";
                break;
            case SIN:
                function = "'y = sin(x)' added";
                break;
            default:
                break;
        }
        Toast toast = Toast.makeText(this, function, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void clearCallBack() {
        customGraphView.clear();
        Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void clearFromFourth(){
        customGraphView.deleteLimit();
        Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void backFromThird(){
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    @Override
    public void acceptGridFromFirst(float gridStep) {
        customGraphView.setGridStep(gridStep);
        Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void defaultFromFirst() {
        customGraphView.setDefaultGridStep();
        Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void emptyFromFirst() {
        Toast toast = Toast.makeText(this, "Fill the grid's step field", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void impossibleFromFirst() {
        Toast toast = Toast.makeText(this, "Sorry, it's impossible", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void backFromSecond(){
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    @Override
    public void backFromFourth() {
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    @Override
    public void nextFromSecond(){
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public void nextFromFirst(){
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public void addCustomCurve(float startX, float startY, float stopX, float stopY, int color, boolean isRounded) {
        customGraphView.addCustomCurve(new Curve(startX, startY, stopX, stopY, color, isRounded));
        customGraphView.invalidate();
        Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void emptyFromThird() {
        Toast toast = Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void nextFromFourth() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public void fourthCallBack(float first, float second) {
        customGraphView.setStartX(first);
        customGraphView.setLastX(second);
        customGraphView.setLimit(true);
        customGraphView.invalidate();
        String text = "Interval: (" + String.valueOf(first) + "; " + String.valueOf(second) + ")";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void emptyFromSecond() {
        Toast toast = Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void emptyFromFourth() {
        Toast toast = Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT);
        toast.show();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){

                case FIRST_PAGE:
                    thirdFragment = new ThirdFragment();
                    return thirdFragment;
                case SECOND_PAGE:
                    fourthFragment = new FourthFragment();
                    return fourthFragment;
                case THIRD_PAGE:
                    secondFragment = new SecondFragment();
                    return secondFragment;
                case FOURTH_PAGE:
                    firstFragment = new FirstFragment();
                    return firstFragment;
                default:
                    return new ThirdFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
