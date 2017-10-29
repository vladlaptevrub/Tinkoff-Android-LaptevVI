package fintech.tinkoff.fifthlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class ThirdFragment extends Fragment {
    private ThirdFragment.ThirdFragmentListener listener;

    private String oldGraphName = "Your graph";
    private String oldAxisNameY = "Axis Y";
    private String oldAxisNameX = "Axis X";

    public interface ThirdFragmentListener{
        void thirdCallBack(int funcName, int funcColor);
        void clearCallBack();
        void nextFromFirst();
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

        final Spinner nameSpinner = (Spinner) getActivity().findViewById(R.id.nameSpinner);
        final Spinner colorSpinner = (Spinner) getActivity().findViewById(R.id.colorSpinner);
        final Button addButton = (Button) getActivity().findViewById(R.id.addButton);
        Button clearButton = (Button) getActivity().findViewById(R.id.clearButton);
        Button nextButton = (Button) getActivity().findViewById(R.id.nextButtonFirst);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int name = nameSpinner.getSelectedItemPosition() + 1;
                int color = colorSpinner.getSelectedItemPosition() + 1;
                listener.thirdCallBack(name, color);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clearCallBack();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextFromFirst();
            }
        });
    }
}
