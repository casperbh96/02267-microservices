package com.token.manager;

import com.token.database.ITokenAdapter;
import com.token.app.Token;
import com.token.database.TokenAdapter;
import com.token.database.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Anshul
 * token manager links the database to DtuPayApp. Acts as an intermediate between both.
 */
public class TokenManager implements ITokenManager {

    ITokenAdapter tokenAdapter = new TokenAdapter();

    /**
     * creates a new token
     * @param customerId
     * @param uuid
     * @param used
     * @return
     */
    @Override
    public Token createToken(int customerId, UUID uuid, boolean used) {
        return tokenAdapter.createToken(customerId, uuid, used);
    }

    /**
     * checks for token if it is valid or not
     * @param tokenId
     * @return boolean value i.e. true or false
     */

    @Override
    public boolean isTokenValid(int tokenId) {
        try {
            return tokenAdapter.isTokenValid(tokenId);
        } catch (TokenAlreadyUsed | FakeToken e) {
            return false;
        }
    }

    /**
     * makes the token a used token for the customer if it is unused
     * @param tokenId
     * @throws FakeToken
     * @throws TokenAlreadyUsed
     */
    @Override
    public void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.markTokenAsUsed(tokenId);
    }

    /**
     * shows all the tokens in the token list
     * @return list of tokens
     */
    @Override
    public List<Token> getAllTokens() {
        return tokenAdapter.getAllTokens();
    }

    /**
     * gives customer's unused token
     * @param customerId
     * @return token
     * @throws CustomerHasNoUnusedToken
     */
    @Override
    public Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken {
        return tokenAdapter.getUnusedTokenByCustomerId(customerId);
    }

    /**
     * gives a random token
     * @return UUID random number
     */
    @Override
    public UUID getToken() {
        return UUID.randomUUID();
    }

    /**
     * gives a list of tokens for customer
     * @param customerId
     * @param numTokens
     * @return list of tokens
     */
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
