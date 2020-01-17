package com.dtupay.database;

import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TokenAdapter implements ITokenAdapter {
    public List<Token> tokens;
    public List<Token> allTokens;
    ICustomerAdapter dbCustomer;
    ITokenManagement tokenManager;

    public TokenAdapter() {
        dbCustomer = new CustomerAdapter();
        tokenManager = new TokenManagement();
        tokens = new ArrayList<>();
        tokens.add(new Token(1, tokenManager.GetToken(), 1));
        tokens.add(new Token(2, tokenManager.GetToken(), 2));
        tokens.add(new Token(3, tokenManager.GetToken(), 3));
        tokens.add(new Token(4, tokenManager.GetToken(), 4));
        tokens.add(new Token(5, tokenManager.GetToken(), 5));
        tokens.add(new Token(6, tokenManager.GetToken(), 6));
        tokens.add(new Token(7, tokenManager.GetToken(), 7));
    }

    @Override
    public Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken {
        for (Token t : tokens) {
            if (t.getCustomerId() == customerId && !t.getUsed()) return t;
        }

        throw new CustomerHasNoUnusedToken(MessageFormat.format(
                "Customer id {0} has no unused tokens.", customerId));
    }

    @Override
    public List<Token> getAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken{
        allTokens  = new ArrayList<>();
        for (Token t : tokens) {
            if (t.getCustomerId() == id && !t.getUsed()) {
                allTokens.add(t);
            }
        }
        return allTokens;
    }

    @Override
    public List<Token> getAllTokens(){
        return tokens;
    }

    @Override
    public Token createToken(Token token) {
        tokens.add(token);
        return token;
    }

    @Override
    public boolean checkToken(Token token) throws FakeToken, TokenAlreadyUsed {
        for (Token t : tokens) {
            if (t.equals(token)) {
                if (t.getUsed()) {
                    throw new TokenAlreadyUsed("The token is already used");
                }
                return true;
            }
        }
        throw new FakeToken("The token is not known to the system");
    }
}
