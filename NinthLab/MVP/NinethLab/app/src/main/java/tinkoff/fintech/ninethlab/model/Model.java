package tinkoff.fintech.ninethlab.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import tinkoff.fintech.ninethlab.presenter.IPresenter;

public class Model implements IModel {
    private List<String> data;
    private IPresenter presenter;

    public Model(IPresenter presenter){
        data = new ArrayList<>();
        this.presenter = presenter;
    }

    @Override
    public void update(){
        Thread thread = new Updater();
        try {
            thread.start();
            thread.join();
            presenter.newData(data);
        } catch (InterruptedException e) {
            Log.i("Model", "Didn't update the data");
        }
    }

    @Override
    public List<String> returnData() {
        return data;
    }

    class Updater extends Thread{

        @Override
        public void run() {
            super.run();
            Log.i("Model", "Start updating");
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify("api.tinkoff.ru", session);
                }
            };

            try {
                URL url = new URL("https://api.tinkoff.ru/v1/news");
                HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
                urlConnection.setHostnameVerifier(hostnameVerifier);
                BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String test = input.readLine();
                String parts[] = test.split("\"");
                data.clear();
                for (int i = 0; i < parts.length; i++){
                    if (parts[i].equals("text")){
                        data.add(parts[i+2]);
                    }
                }
                Log.i("Model", "Data is updated");
            } catch (MalformedURLException e) {
                Log.i("Model", "Didn't update");
            } catch (IOException e){
                Log.i("Model", "Didn't update");
            }
        }
    }
}
