package com.token.database;

import com.token.app.Token;
import com.token.database.exceptions.*;

import java.util.List;
import java.util.UUID;

/**
 * interface to handle token adapter class
 */
public interface ITokenAdapter {
    Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken;

    Token createToken(int customerId, UUID uuid, boolean used);

    boolean isTokenValid(int tokenId) throws FakeToken, TokenAlreadyUsed;

    void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed;

    List<Token> getAllTokens();
}
