package com.dtupay.database;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface ITokenAdapter {
    // GET /token/unused/{id}
    Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    // POST /token
    Token createToken(int customerId, UUID uuid, boolean used);

    // POST /token/validation
    boolean isTokenValid(Token token) throws FakeToken, TokenAlreadyUsed;

    // PUT /token/validation
    void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed;

//    // GET /token/all_unused/{id}
//    List<Token> getAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    // GET /token
    List<Token> getAllTokens();
}
