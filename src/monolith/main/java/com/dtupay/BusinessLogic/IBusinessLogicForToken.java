package com.dtupay.BusinessLogic;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.util.List;
import java.util.UUID;

public interface IBusinessLogicForToken {
    Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken;

    Token createToken(int customerId, UUID uuid, boolean used);

    boolean isTokenValid(int tokenId);

    void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed;

    List<Token> getAllTokens();

}
