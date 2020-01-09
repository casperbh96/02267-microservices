package com.calculator.database;

import com.calculator.database.exceptions.*;

import java.util.UUID;

public interface ITokenAccess {
    UUID getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken, CustomerDoesNotExist;
    UUID createTokenByCustomerId(int id);
    void getAccessByToken(UUID token) throws TokenAlreadyUsed, FakeToken;
}
