package tinkoff.fintech.ninethlab.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import tinkoff.fintech.ninethlab.model.IModel;
import tinkoff.fintech.ninethlab.model.Model;
import tinkoff.fintech.ninethlab.view.IView;

public class Presenter implements IPresenter {

    private IView view;
    private IModel model;

    public Presenter(IView view){
        this.view = view;
        this.model = new Model(this);
    }

    @Override
    public void updateModel() {
        Log.i("Presenter", "Send request to model");
        model.update();
    }

    @Override
    public void newData(HashMap<Long, String> data){
        Log.i("Presenter", "Send request to View");

        Set<Long> localKeys = data.keySet();
        List<Long> localDates = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        localDates.addAll(localKeys);
        Collections.sort(localDates);
        Collections.reverse(localDates);

        for (int i = 0; i < localDates.size(); i++){
            titles.add(data.get(localDates.get(i)));
        }

        view.updateScreen(titles);
    }
}
