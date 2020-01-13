package com.dtupay.app;

import com.dtupay.app.Token;

import java.math.BigDecimal;

public interface IDtuPayApp {
    boolean checkTokenValidity(Token token);
    void transferMoney(String merchantId, Token merchantToken, BigDecimal amount, String description);
}