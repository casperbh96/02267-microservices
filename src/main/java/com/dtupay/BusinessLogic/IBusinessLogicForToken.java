package com.dtupay.BusinessLogic;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.util.List;
import java.util.UUID;

public interface IBusinessLogicForToken {
    // GET /token/unused/{id}
    Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    // POST /token
    Token createToken(int customerId, UUID uuid, boolean used);

    // POST /token/validation
    boolean isTokenValid(Token token) throws TokenAlreadyUsed, FakeToken;

    // PUT /token/validation
    void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed;

//    List<Token> GetAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    // GET /token
    List<Token> getAllTokens();

}
