package fintech.tinkoff.thirdlab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstFragment extends Fragment {

    private FirstFragmentListener listener;

    public interface FirstFragmentListener{
        void firstCallBack(int number);
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

        Button confirmButton = (Button) getActivity().findViewById(R.id.firstConfirmButton);
        final EditText firstEditText = (EditText) getView().findViewById(R.id.firstNumberEditText);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstEditText.getText().toString().length() > 0) {
                    int firstNumber = Integer.parseInt(firstEditText.getText().toString());
                    listener.firstCallBack(firstNumber);

                } else {
                    Toast toast = Toast.makeText(
                            getActivity(),
                            "Enter your first number",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
            }
        });
    }
}