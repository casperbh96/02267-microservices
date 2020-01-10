package com.calculator.database;

import com.calculator.Token;
import com.calculator.database.exceptions.*;

import java.util.UUID;

public interface ITokenAdapter {
    Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken, CustomerDoesNotExist;
    Token createTokenByCustomerId(int id);
    void getAccessByToken(UUID token) throws TokenAlreadyUsed, FakeToken;
}
