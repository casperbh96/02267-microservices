package com.dtupay.database;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.*;

public interface ITokenAdapter {
    Token getUnusedTokenByCustomerId(String id) throws CustomerHasNoUnusedToken;
    Token createToken(Token token);
    boolean checkToken(Token token) throws FakeToken, TokenAlreadyUsed;
}
