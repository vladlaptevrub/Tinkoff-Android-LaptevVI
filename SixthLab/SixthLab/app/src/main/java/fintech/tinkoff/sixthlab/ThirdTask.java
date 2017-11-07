package fintech.tinkoff.sixthlab;

import java.math.BigDecimal;

public class ThirdTask {
    public BigDecimal money_amount;

    public ThirdTask(BigDecimal money_amount){
        this.money_amount = money_amount;
    }

    public BigDecimal getMoney_amount(){
        return money_amount;
    }
}
