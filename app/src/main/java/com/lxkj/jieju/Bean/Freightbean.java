package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/12/2 0002.
 * Describe :运费
 */

public class Freightbean extends ResultBean {

    /**
     * amount : 运费
     */

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
