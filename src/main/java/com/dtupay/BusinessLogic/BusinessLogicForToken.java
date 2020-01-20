package com.dtupay.BusinessLogic;

import com.dtupay.database.ITokenAdapter;
import com.dtupay.app.Token;
import com.dtupay.database.TokenAdapter;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.util.List;
import java.util.UUID;

public class BusinessLogicForToken implements IBusinessLogicForToken {

    ITokenAdapter tokenAdapter = new TokenAdapter();

    @Override
    public Token createToken(int customerId, UUID uuid, boolean used) {
        return tokenAdapter.createToken(customerId, uuid, used);
    }

    @Override
    public boolean isTokenValid(int tokenId) {
        try {
            return tokenAdapter.isTokenValid(tokenId);
        } catch (TokenAlreadyUsed | FakeToken e) {
            return false;
        }
    }

    @Override
    public void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.markTokenAsUsed(tokenId);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenAdapter.getAllTokens();

    }

    @Override
    public Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken {
        return tokenAdapter.getUnusedTokenByCustomerId(customerId);
    }

}
