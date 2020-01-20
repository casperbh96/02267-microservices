package com.dtupay.database;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface ITokenAdapter {
    Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken;

    Token createToken(int customerId, UUID uuid, boolean used);

    boolean isTokenValid(int tokenId) throws FakeToken, TokenAlreadyUsed;

    void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed;

    List<Token> getAllTokens();
}
