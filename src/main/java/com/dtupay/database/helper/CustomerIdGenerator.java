package com.dtupay.database.helper;

import java.util.Random;

public class CustomerIdGenerator {
    public int generateCprNumber(){
        Random r = new Random();
        int low     = 1;
        int high    = 999999999;
        return r.nextInt(high-low) + low;
    }
}
