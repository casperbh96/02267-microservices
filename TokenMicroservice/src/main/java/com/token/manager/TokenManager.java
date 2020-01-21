package com.token.manager;

import com.token.app.Customer;
import com.token.database.ITokenAdapter;
import com.token.app.Token;
import com.token.database.TokenAdapter;
import com.token.database.exceptions.*;

import java.util.ArrayList;
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
    public List<Token> getTokensForCustomer(int customerId, int numTokens) {
        List<Token> newTokens = new ArrayList<>();
        for (int i = 0; i < numTokens; i++) {
            Token token = tokenAdapter.createToken(customerId, getToken(), false);
            newTokens.add(token);
        }
        return newTokens;
    }

}
