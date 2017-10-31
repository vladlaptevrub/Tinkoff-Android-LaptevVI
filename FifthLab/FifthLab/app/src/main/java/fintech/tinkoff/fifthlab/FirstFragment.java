package fintech.tinkoff.fifthlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

public class FirstFragment extends Fragment {
    private FirstFragmentListener listener;

    private boolean visible;
    private int scaleProgress;

    public interface FirstFragmentListener{
        void firstCallBack(int progress, boolean isVisible);
        void backFromThird();
        void acceptGridFromFirst(float gridStep);
        void defaultFromFirst();
        void emptyFromFirst();
        void impossibleFromFirst();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (FirstFragmentListener) activity;
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

        return inflater.inflate(R.layout.first_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SeekBar scaleBar = (SeekBar) getActivity().findViewById(R.id.scaleSeekBar);
        CheckBox visibleCheckBox = (CheckBox) getActivity().findViewById(R.id.visibleCheckBox);
        Button backButton = (Button) getActivity().findViewById(R.id.backButtonThird);
        Button defaultButton = (Button) getActivity().findViewById(R.id.defaultButtonFirst);
        Button acceptButton = (Button) getActivity().findViewById(R.id.acceptButtonFirst);
        final EditText gridStepInput = (EditText) getActivity().findViewById(R.id.gridStepInput);

        scaleProgress = scaleBar.getProgress();
        visible = visibleCheckBox.isChecked();

        scaleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scaleProgress = progress;
                listener.firstCallBack(scaleProgress, visible);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        visibleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visible = isChecked;
                listener.firstCallBack(scaleProgress, visible);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backFromThird();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float stepSize;
                if (gridStepInput.getText().toString().length() > 0) {
                    stepSize = Float.parseFloat(gridStepInput.getText().toString());
                    if (stepSize < 0.5f){
                        listener.impossibleFromFirst();
                    } else {
                        listener.acceptGridFromFirst(stepSize);
                    }
                } else {
                    listener.emptyFromFirst();
                }

            }
        });

        defaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.defaultFromFirst();
            }
        });
    }
}
