package com.dtupay.database;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface ITokenAdapter {
    Token getTokenByTokenId(int tokenId);

    Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    Token createToken(int customerId, UUID uuid, boolean used);

    boolean isTokenValid(Token token) throws FakeToken, TokenAlreadyUsed;

    List<Token> getAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    List<Token> getAllTokens();
}
