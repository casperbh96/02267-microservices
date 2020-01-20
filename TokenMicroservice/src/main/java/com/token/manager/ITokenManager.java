package com.token.manager;

import com.token.app.Customer;
import com.token.app.Token;
import com.token.database.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface ITokenManager {
    Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken;

    Token createToken(int customerId, UUID uuid, boolean used);

    boolean isTokenValid(int tokenId);

    void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed;

    List<Token> getAllTokens();

    UUID getToken();

    void customerGetTokens(Customer customer, int numTokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest;

    boolean canCustomerGetTokens(Customer customer, int numTokens) throws TooManyTokenRequest;
}
