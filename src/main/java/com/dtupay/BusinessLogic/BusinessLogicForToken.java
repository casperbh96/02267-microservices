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

    public Token createToken(int customerId, UUID uuid, boolean used) {
        return tokenAdapter.createToken(customerId, uuid, used);
    }

    public boolean isTokenValid(Token token) throws TokenAlreadyUsed, FakeToken {
        return tokenAdapter.isTokenValid(token);
    }

    public void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed {
       tokenAdapter.markTokenAsUsed(tokenId);
    }

    public List<Token> getAllTokens() {
        return tokenAdapter.getAllTokens();
    }

    public Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken {
        return tokenAdapter.getUnusedTokenByCustomerId(id);
    }

//    public List<Token> GetAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken {
//        return tokenAdapter.getAllUnusedTokenByCustomerId(id);
//    }


}
