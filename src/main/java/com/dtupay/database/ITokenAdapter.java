package com.dtupay.database;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.*;

import java.util.List;

public interface ITokenAdapter {
    Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;
    Token createToken(Token token);
    boolean checkToken(Token token) throws FakeToken, TokenAlreadyUsed;
    List<Token> getAllUnusedTokenByCustomerId(String id) throws CustomerHasNoUnusedToken;
    List<Token> getAllTokens();
}
