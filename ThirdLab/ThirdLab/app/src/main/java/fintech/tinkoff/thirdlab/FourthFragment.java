package fintech.tinkoff.thirdlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FourthFragment extends Fragment {

    private final static int SUM = 1;
    private final static int DIFFERENCE = 2;
    private final static int MULTIPLICATION = 3;
    private final static int DIVISION = 4;
    private final static int POWER = 5;
    private final static int ERROR = 0;

    private final static String ERROR_STRING = "ERROR";
    private final static String DEAFAULT_TEXT = "Enter numbers and action";

    private FourthFragmentListener listener;

    public interface FourthFragmentListener{
        void fourthCallBack();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (FourthFragment.FourthFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement FourthFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fourth_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) getView().findViewById(R.id.resultTextView);
        textView.setText(DEAFAULT_TEXT);

        Button restartButton = (Button) getView().findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.fourthCallBack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null){
            if ((getArguments().get("firstNumber") != null) &&
                    (getArguments().get("secondNumber") != null) &&
                    (getArguments().get("action") != null)){

                onUpdateView();

            } else {
                TextView textView = (TextView) getView().findViewById(R.id.resultTextView);
                textView.setText(DEAFAULT_TEXT);
            }
        } else {
            TextView textView = (TextView) getView().findViewById(R.id.resultTextView);
            textView.setText(DEAFAULT_TEXT);
        }
    }

    public void onUpdateView(){
        TextView textView = (TextView)getView().findViewById(R.id.resultTextView);
        int firstNumber = getArguments().getInt("firstNumber");
        int secondNumber = getArguments().getInt("secondNumber");
        String result;

        switch (getArguments().getInt("action")){
            case SUM:
                result = String.valueOf(firstNumber + secondNumber);
                textView.setText(buildString(" + ", result));
                break;
            case DIFFERENCE:
                result = String.valueOf(firstNumber - secondNumber);
                textView.setText(buildString(" - ", result));
                break;
            case MULTIPLICATION:
                result = String.valueOf(firstNumber * secondNumber);
                textView.setText(buildString(" * ", result));
                break;
            case DIVISION:
                result = String.valueOf(((double)firstNumber) / ((double)secondNumber));
                textView.setText(buildString(" / ", result));
                break;
            case POWER:
                result = String.valueOf(Math.pow(((double)firstNumber), ((double)secondNumber)));
                textView.setText(buildString(" ^ ", result));
                break;
            case ERROR:
                textView.setText(ERROR_STRING);
                break;
        }

    }

    private String buildString(String sign, String result){
        String firstNumber = String.valueOf(getArguments().getInt("firstNumber"));
        String secondNumber = String.valueOf(getArguments().getInt("secondNumber"));
        String finalString;

        finalString = firstNumber + sign + secondNumber + " = " + result;

        return finalString;
    }

}