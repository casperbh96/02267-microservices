package com.calculator.database;

import com.calculator.Customer;
import com.calculator.Token;
import com.calculator.database.exceptions.CustomerDoesNotExist;
import com.calculator.database.exceptions.CustomerHasNoUnusedToken;
import com.calculator.database.exceptions.FakeToken;
import com.calculator.database.exceptions.TokenAlreadyUsed;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TokenAdapter implements ITokenAdapter {

    public TokenAdapter() {

    }

    @Override
    public Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        return null;
    }

    @Override
    public Token createTokenByCustomerId(int id) {
        return null;
    }

    private boolean doesCustomerExist(int id) {
        return true;
    }
}
