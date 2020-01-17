package com.dtupay.BusinessLogic;

import com.dtupay.app.Token;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.util.List;

public interface IBusinessLogicForToken {
    Token GetUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    Token CreateToken(Token token);

    boolean CheckToken(Token token) throws TokenAlreadyUsed, FakeToken;

    List<Token> GetAllUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken;

    List<Token> GetAllTokens();
}
