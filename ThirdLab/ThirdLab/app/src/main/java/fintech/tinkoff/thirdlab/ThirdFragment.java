package fintech.tinkoff.thirdlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

public class ThirdFragment extends Fragment {

    private final static int SUM = R.id.radioButtonSum;
    private final static int DIFFERENCE = R.id.radioButtonDifference;
    private final static int MULTIPLICATION = R.id.radioButtonMultiplication;
    private final static int DIVISION = R.id.radioButtonDivision;
    private final static int POWER = R.id.radioButtonPower;

    private ThirdFragmentListener listener;

    public interface ThirdFragmentListener{
        void thirdCallBack(Integer action);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (ThirdFragment.ThirdFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement ThirdFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.third_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RadioGroup radioGroup = (RadioGroup)getActivity().findViewById(R.id.actionRadioGroup);
        Button confirmButton = (Button)getActivity().findViewById(R.id.thirdConfirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int action = radioGroup.getCheckedRadioButtonId();

                switch (action){
                    case SUM:
                        listener.thirdCallBack(1);
                        break;
                    case DIFFERENCE:
                        listener.thirdCallBack(2);
                        break;
                    case MULTIPLICATION:
                        listener.thirdCallBack(3);
                        break;
                    case DIVISION:
                        listener.thirdCallBack(4);
                        break;
                    case POWER:
                        listener.thirdCallBack(5);
                        break;
                    default:
                        listener.thirdCallBack(0);
                        break;
                }

            }
        });
    }
}