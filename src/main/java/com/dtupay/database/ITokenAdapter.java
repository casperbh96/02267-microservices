package com.dtupay.database;

import com.dtupay.Token;
import com.dtupay.database.exceptions.*;

public interface ITokenAdapter {
    Token getUnusedTokenByCustomerId(int id) throws CustomerHasNoUnusedToken, CustomerDoesNotExist;
    Token createToken(Token token);
}
