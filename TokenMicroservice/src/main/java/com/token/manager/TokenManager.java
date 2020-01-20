package com.token.manager;

import com.token.app.Customer;
import com.token.database.ITokenAdapter;
import com.token.app.Token;
import com.token.database.TokenAdapter;
import com.token.database.exceptions.*;

import java.util.List;
import java.util.UUID;

public class TokenManager implements ITokenManager {

    ITokenAdapter tokenAdapter = new TokenAdapter();

    @Override
    public Token createToken(int customerId, UUID uuid, boolean used) {
        return tokenAdapter.createToken(customerId, uuid, used);
    }

    @Override
    public boolean isTokenValid(int tokenId) {
        try {
            return tokenAdapter.isTokenValid(tokenId);
        } catch (TokenAlreadyUsed | FakeToken e) {
            return false;
        }
    }

    @Override
    public void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.markTokenAsUsed(tokenId);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenAdapter.getAllTokens();
    }

    @Override
    public Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken {
        return tokenAdapter.getUnusedTokenByCustomerId(customerId);
    }

    @Override
    public UUID getToken() {
        UUID token = UUID.randomUUID();
        //database.system_tokens.add(token);
        return token;
    }

    @Override
    public void customerGetTokens(Customer customer, int numTokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest {
        if (canCustomerGetTokens(customer, numTokens)) {
            for (int i = 0; i < numTokens; i++) {
                Token token = tokenAdapter.createToken(customer.getId(), getToken(), false);
                customer.addToken(token);
            }
        } else {
            throw new CustomerIsUnableToReceiveNewTokens("Customer is unable to receive new tokens");
        }
    }

    @Override
    public boolean canCustomerGetTokens(Customer customer, int numTokens) throws TooManyTokenRequest {
        if (numTokens > 5) {
            throw new TooManyTokenRequest("Too many token request: " + numTokens);
        }

        if (customer.getTokens().isEmpty()) {
            return true;
        }

        int unusedToken = 0;

        for (Token t : customer.getTokens()) {
            if (!t.getUsed()) {
                unusedToken++;
            }
        }
        return unusedToken == 1;
    }

}
