package com.dtupay.BusinessLogic;

import com.dtupay.database.ITokenAdapter;
import com.dtupay.app.Token;
import com.dtupay.database.TokenAdapter;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.util.List;

public class BusinessLogicForToken implements IBusinessLogicForToken {

    ITokenAdapter tokenAdapter = new TokenAdapter();

    public Token CreateToken(Token token) {
        return tokenAdapter.createToken(token);
    }

    public boolean CheckToken(Token token) throws TokenAlreadyUsed, FakeToken {
        return tokenAdapter.isTokenValid(token);
    }

    public List<Token> GetAllTokens() {
        return tokenAdapter.getAllTokens();
    }

    public Token GetUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken {
        return tokenAdapter.getUnusedTokenByCustomerId(id);
    }

    public List<Token> GetAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken {
        return tokenAdapter.getAllUnusedTokenByCustomerId(id);
    }


}
