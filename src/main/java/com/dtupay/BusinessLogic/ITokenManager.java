package com.dtupay.BusinessLogic;

import com.dtupay.app.Customer;
import com.dtupay.app.Token;
import com.dtupay.database.exceptions.*;

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
