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

public class SecondFragment extends Fragment {
    private SecondFragment.SecondFragmentListener listener;

    private String oldGraphName = "Your graph";
    private String oldAxisNameY = "Axis Y";
    private String oldAxisNameX = "Axis X";

    public interface SecondFragmentListener{
        void secondCallBack(String graphName, String axisNameY, String axisNameX);
        void backFromSecond();
        void nextFromSecond();
        void emptyFromSecond();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (SecondFragment.SecondFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement FirstFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.second_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText graphNameEdit = (EditText) getActivity().findViewById(R.id.editGraphName);
        final EditText axisNameY = (EditText) getActivity().findViewById(R.id.editAxisNameY);
        final EditText axisNameX = (EditText) getActivity().findViewById(R.id.editAxisNameX);
        Button saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        Button backButton = (Button) getActivity().findViewById(R.id.backButtonSecond);
        Button nextButton = (Button) getActivity().findViewById(R.id.nextButtonSecond);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphNameEdit.getText().toString().length() > 0 &&
                        axisNameY.getText().toString().length() > 0 &&
                        axisNameX.getText().toString().length() > 0){

                    oldGraphName = graphNameEdit.getText().toString();
                    oldAxisNameY = axisNameY.getText().toString();
                    oldAxisNameX = axisNameX.getText().toString();
                    listener.secondCallBack(oldGraphName, oldAxisNameY, oldAxisNameX);

                } else {
                    listener.emptyFromSecond();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backFromSecond();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextFromSecond();
            }
        });
    }
}
