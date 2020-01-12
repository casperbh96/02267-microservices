package com.dtupay.app;

import com.dtupay.app.Token;

import java.math.BigDecimal;

public interface IDtuPayApp {
//    void scanToken(Token token);
    void transferMoney(String accountIdFrom, String accountIdTo, BigDecimal amount, String description);
}
