package tinkoff.fintech.ninethlab.presenter;

import java.util.HashMap;
import java.util.List;

public interface IPresenter {
    void updateModel();
    void newData(HashMap<Long, String> data);
}
