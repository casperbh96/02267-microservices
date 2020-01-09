package com.calculator.database;

import java.util.List;
import java.util.UUID;

public interface ITokenAccess {
    List<UUID> getListOfUnusedTokensByCustomerId(int id);
    UUID createTokenByCustomerId(int id);
    void getAccessByToken(UUID token) throws TokenAlreadyUsed, FakeToken;
}
