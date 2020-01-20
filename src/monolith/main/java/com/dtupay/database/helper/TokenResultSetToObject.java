package com.dtupay.database.helper;

import com.dtupay.app.Token;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TokenResultSetToObject {
    public Token resultSetToToken(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();

        int id = set.getInt(1);
        int customerId = set.getInt(2);
        UUID name = UUID.fromString(set.getString(3));
        boolean used = set.getBoolean(4);

        return new Token(id, customerId, name, used);
    }

    public List<Token> resultSetToListOfTokens(ResultSet set) throws SQLException {
        List<Token> tokenList = new ArrayList<>();

        while(set.next()) {
            int id = set.getInt(1);
            int customerId = set.getInt(2);
            UUID name = UUID.fromString(set.getString(3));
            boolean used = set.getBoolean(4);

            Token newToken = new Token(id, customerId, name, used);
            tokenList.add(newToken);
        }

        return tokenList;
    }
}
