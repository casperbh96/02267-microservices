package com.token.database.helper;

import com.token.app.Token;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Casper
 * defined class which contains functions to get a new token with information from result sets.
 */


public class TokenResultSetToObject {

    /**
     * gives information about token from result set
     * @param set
     * @return new token
     * @throws SQLException
     */

    public Token resultSetToToken(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();

        int id = set.getInt(1);
        int customerId = set.getInt(2);
        UUID name = UUID.fromString(set.getString(3));
        boolean used = set.getBoolean(4);

        return new Token(id, customerId, name, used);
    }

    /**
     * gives list which consists information in result set
     * @param set
     * @return list of tokens
     * @throws SQLException
     */
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
