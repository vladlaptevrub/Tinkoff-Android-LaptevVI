package fintech.tinkoff.fifthlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FourthFragment extends Fragment{

    private FourthFragment.FourthFragmentListener listener;

    public interface FourthFragmentListener{
        void fourthCallBack(float first, float second);
        void emptyFromFourth();
        void backFromFourth();
        void nextFromFourth();
        void clearFromFourth();
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

        final EditText firstNumberInput = (EditText) getActivity().findViewById(R.id.firstInputNumber);
        final EditText secondNumberInput = (EditText) getActivity().findViewById(R.id.secondInputNumber);
        Button backButton = (Button) getActivity().findViewById(R.id.fourthBackButton);
        Button acceptButton = (Button) getActivity().findViewById(R.id.acceptButton);
        Button nextButton = (Button) getActivity().findViewById(R.id.fourthNextButton);
        Button clearLimitButton = (Button) getActivity().findViewById(R.id.clearLimitButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backFromFourth();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextFromFourth();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumberInput.getText().toString().length() > 0 &&
                        secondNumberInput.getText().toString().length() > 0) {
                    float firstNumber = Float.parseFloat(firstNumberInput.getText().toString());
                    float secondNumber = Float.parseFloat(secondNumberInput.getText().toString());
                    if (firstNumber > secondNumber){
                        float tmp = firstNumber;
                        firstNumber = secondNumber;
                        secondNumber = tmp;
                    }
                    listener.fourthCallBack(firstNumber, secondNumber);
                } else {
                    listener.emptyFromFourth();
                }
            }
        });

        clearLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNumberInput.setText("");
                secondNumberInput.setText("");
                listener.clearFromFourth();
            }
        });

    }
}