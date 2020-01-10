package com.calculator.database;

import com.calculator.Calculator;
import com.calculator.Customer;
import com.calculator.Token;
import com.calculator.database.exceptions.CustomerDoesNotExist;
import com.calculator.database.exceptions.CustomerHasNoUnusedToken;
import com.calculator.database.exceptions.FakeToken;
import com.calculator.database.exceptions.TokenAlreadyUsed;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TokenAdapter implements ITokenAdapter {
    List<Token> tokens;
    CustomerAdapter dbCustomer = new CustomerAdapter();

    public TokenAdapter() {
        tokens = new ArrayList<>();
        tokens.add(new Token(Calculator.getToken(), 1));
        tokens.add(new Token(Calculator.getToken(), 2));
        tokens.add(new Token(Calculator.getToken(), 3));
        tokens.add(new Token(Calculator.getToken(), 4));
        tokens.add(new Token(Calculator.getToken(), 5));
        tokens.add(new Token(Calculator.getToken(), 6));
        tokens.add(new Token(Calculator.getToken(), 7));
    }

    @Override
    public Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        // if customer does not exist, throws a CustomerDoesNotExist exception
        doesCustomerExist(id);

        for (Token t : tokens) {
            if (t.getCustomerId() == id && t.getUsed() == false) return t;
        }

        throw new CustomerHasNoUnusedToken(MessageFormat.format(
                "Customer id {0} has no unused tokens.", id));
    }

    @Override
    public Token createToken(Token token) {
        tokens.add(token);
        return token;
    }

    private void doesCustomerExist(int id) throws CustomerDoesNotExist {
        Customer customer = dbCustomer.getCustomerByCustomerId(id);
    }
}
