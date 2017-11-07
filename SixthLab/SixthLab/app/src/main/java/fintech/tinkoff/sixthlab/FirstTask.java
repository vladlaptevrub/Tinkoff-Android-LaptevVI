package fintech.tinkoff.sixthlab;

import com.google.gson.annotations.Expose;

public class FirstTask {
    private int id = 0;
    private String projectName = "empty";
    @Expose private String product_name;
    @Expose private int number;

    public FirstTask(int id, String projectName, String product_name, int number){
        this.id = id;
        this.projectName = projectName;
        this.product_name = product_name;
        this.number = number;
    }

    public int getObjectId(){
        return id;
    }

    public String getProjectName(){
        return projectName;
    }

    public String getProductName(){
        return product_name;
    }

    public int getNumber(){
        return number;
    }
}
