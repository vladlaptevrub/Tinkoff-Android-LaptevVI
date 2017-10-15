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

public class SecondFragment extends Fragment {

    private SecondFragmentListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (SecondFragment.SecondFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement SecondFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface SecondFragmentListener{
        void secondCallBack(int number);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.second_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button confirmButton = (Button)getActivity().findViewById(R.id.secondConfirmButton);
        final EditText secondEditText = (EditText)getView().findViewById(R.id.secondNumberEditText);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secondEditText.getText().toString().length() > 0) {
                    int secondNumber = Integer.parseInt(secondEditText.getText().toString());
                    listener.secondCallBack(secondNumber);

                } else {
                    Toast toast = Toast.makeText(
                            getActivity(),
                            "Enter your second number",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
            }
        });
    }
}