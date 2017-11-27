package tinkoff.fintech.ninethlab.presenter;

import android.util.Log;

import java.util.List;

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
    public void newData(List<String> data){
        Log.i("Presenter", "Send request to View");
        view.updateScreen(data);
    }
}
