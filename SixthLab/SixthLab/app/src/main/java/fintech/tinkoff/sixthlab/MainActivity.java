package fintech.tinkoff.sixthlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstTask();

        secondTask();

        thirdTask();

        fourthTask();
    }

    public void firstTask(){
        String jsonText = "{\"product_name\":\"first_task\",\"number\":\"135\"}";

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();

        FirstTask firstTask = gson.fromJson(jsonText, FirstTask.class);

        Log.i("GSON", "product_name: " + firstTask.getProductName() + ", number: " + firstTask.getNumber());

    }

    public void secondTask(){
        String jsonText = "{\"name\":\"name\",\"any_map\":{\"a\":\"55\",\"b\":\"85\",\"c\":\"56\"}}";

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        SecondTask secondTask = gson.fromJson(jsonText, SecondTask.class);

        HashMap<String, Integer> map = secondTask.getAnyMap();

        Log.i("GSON", "name: " + secondTask.getNameField() + "\n" +
                "any_map: " + "\n" +
                "      a: " + map.get("a") + "\n" +
                "      b: " + map.get("b") + "\n" +
                "      c: " + map.get("c"));
    }

    public void thirdTask(){
        String jsonText = "{\"money_amount\":\"2444,88\"}";

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ThirdTask thirdTask = gson.fromJson(changeJsonText(jsonText), ThirdTask.class);

        Log.i("GSON", "money_amount: " + thirdTask.getMoney_amount());
    }

    public void fourthTask(){
        Date date = new Date();
        date.setDate(26);
        date.setMonth(01);
        date.setYear(1995);

        DateExample dateExample = new DateExample(date);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String jsonText= gson.toJson(dateExample);
        String jsonTextParts[] = jsonText.split(":");
        String newJsonText = jsonTextParts[0] + ":\"" + getFormatedString(dateExample.getDate()) + "\"}";

        Log.i("GSON", newJsonText);
    }

    private String changeJsonText(String jsonText){
        String jsonTextParts[] = jsonText.split("\"");
        String newJsonText = "";
        for (int i = 0; i < jsonTextParts.length; i++){
            char array[] = jsonTextParts[i].toCharArray();
            if ((int)array[0] >= (int)'0' && (int)array[0] <= '9'){
                for (int j = 0; j < array.length; j++){
                    if (array[j] == ','){
                        array[j] = '.';
                    }
                }
            }
            newJsonText += new String(array);
        }
        return  newJsonText;
    }

    private String getFormatedString(Date date){
        String dateString = date.getYear() + "-" + date.getMonth() + "-"  + date.getDate();
        return dateString;
    }
}
