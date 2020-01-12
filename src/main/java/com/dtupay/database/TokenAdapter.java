package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TokenAdapter implements ITokenAdapter {
    List<Token> tokens;
    CustomerAdapter dbCustomer = new CustomerAdapter();
    TokenManagement tokenManager = new TokenManagement();

    public TokenAdapter() {
        tokens = new ArrayList<>();
        tokens.add(new Token(tokenManager.GetToken(), 1));
        tokens.add(new Token(tokenManager.GetToken(), 2));
        tokens.add(new Token(tokenManager.GetToken(), 3));
        tokens.add(new Token(tokenManager.GetToken(), 4));
        tokens.add(new Token(tokenManager.GetToken(), 5));
        tokens.add(new Token(tokenManager.GetToken(), 6));
        tokens.add(new Token(tokenManager.GetToken(), 7));
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

    @Override
    public boolean checkExists(Token token){
        for(Token t : tokens){
            if (t.equals(token))
                return true;
        }
        return false;
    }

    private void doesCustomerExist(int id) throws CustomerDoesNotExist {
        Customer customer = dbCustomer.getCustomerByCustomerId(id);
    }
}
