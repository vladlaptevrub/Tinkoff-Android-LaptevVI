package fintech.tinkoff.fifthlab;

public class Func {
    private int funcName;
    private int funcColor;

    public Func(int funcName, int funcColor){
        this.funcName = funcName;
        this.funcColor = funcColor;
    }

    public int getName(){
        return funcName;
    }

    public int getFuncColor(){
        return funcColor;
    }
}
