package com.dtupay.database;

import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TokenAdapter implements ITokenAdapter {
    public List<Token> tokens;
    ICustomerAdapter dbCustomer;
    ITokenManagement tokenManager;

    public TokenAdapter() {
        dbCustomer = new CustomerAdapter();
        tokenManager = new TokenManagement();
        tokens = new ArrayList<>();
        tokens.add(new Token(tokenManager.GetToken(), "1"));
        tokens.add(new Token(tokenManager.GetToken(), "2"));
        tokens.add(new Token(tokenManager.GetToken(), "3"));
        tokens.add(new Token(tokenManager.GetToken(), "4"));
        tokens.add(new Token(tokenManager.GetToken(), "5"));
        tokens.add(new Token(tokenManager.GetToken(), "6"));
        tokens.add(new Token(tokenManager.GetToken(), "7"));
    }

    @Override
    public Token getUnusedTokenByCustomerId(String id) throws CustomerHasNoUnusedToken{
        for (Token t : tokens) {
            if (t.getCustomerId().equals(id) && !t.getUsed()) return t;
        }

        throw new CustomerHasNoUnusedToken(MessageFormat.format(
                "Customer id {0} has no unused tokens.", id));
    }

    @Override
    public Token createToken(Token token) {
        tokens.add(token);
        return token;
    }

    @Override
    public boolean checkExists(Token token){
        for(Token t : tokens){
            if (t.equals(token))
                return true;
        }
        return false;
    }
}
