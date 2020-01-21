package com.dtupay.adapters.token;

import com.dtupay.adapters.token.exceptions.TokenException;
import com.dtupay.adapters.token.model.Token;

import java.util.List;

public interface ITokenAdapter {
    List<Token> getNewTokensForCustomer(int customerId, int numOfTokens) throws TokenException;

    boolean isTokenValid(int tokenId) throws TokenException;

    void markTokenAsUsed(int tokenId) throws TokenException;
}
