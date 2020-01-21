package com.token.database;

import com.token.app.Token;
import com.token.database.exceptions.CustomerHasNoUnusedToken;
import com.token.database.exceptions.FakeToken;
import com.token.database.exceptions.TokenAlreadyUsed;
import com.token.database.helper.TokenResultSetToObject;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.token.database.Connector.createConnection;

/**
 * @author Casper
 * class to handle all token related applications in the database
 */
public class TokenAdapter implements ITokenAdapter {
    TokenResultSetToObject converter = new TokenResultSetToObject();

    public TokenAdapter() {
    }

    /**
     * gives token by token id
     * @param tokenId
     * @return token
     */
    private Token getTokenByTokenId(int tokenId) {
        Token token = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM token WHERE id = ?");
            query.setInt(1, tokenId);
            ResultSet rs = query.executeQuery();

            token = converter.resultSetToToken(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return token;
    }

    /**
     * gives unused tokens for the customers by customer id
     * @param customerId
     * @return token
     * @throws CustomerHasNoUnusedToken
     */
    @Override
    public Token getUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken {
        Token token = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM token WHERE customerId = ? AND used = ? LIMIT 1;");
            query.setInt(1, customerId);
            query.setBoolean(2, false);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new CustomerHasNoUnusedToken(MessageFormat.format(
                    "Customer id {0} has no unused token", customerId));

            token = converter.resultSetToToken(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return token;
    }

    /**
     * shows all the tokens in the token list
     * @return list of tokens
     */
    @Override
    public List<Token> getAllTokens() {
        List<Token> tokens = new ArrayList<>();
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM token");
            ResultSet rs = query.executeQuery();

            tokens = converter.resultSetToListOfTokens(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tokens;
    }

    /**
     * creates new token
     * @param customerId
     * @param uuid
     * @param used
     * @return token
     */
    @Override
    public Token createToken(int customerId, UUID uuid, boolean used) {
        if (customerId == 0) throw new NullPointerException("customerId == 0");

        int autoGenId = 0;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO token (customerId, uuid, used) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setInt(1, customerId);
            query.setString(2, String.valueOf(uuid));
            query.setBoolean(3, used);

            query.executeUpdate();

            autoGenId = getIdFromDbReturn(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return getTokenByTokenId(autoGenId);
    }

    /**
     * checks for token if it is valid or not
     * @param tokenId
     * @return boolean value i.e. true or false
     * @throws FakeToken
     * @throws TokenAlreadyUsed
     */
    @Override
    public boolean isTokenValid(int tokenId) throws FakeToken, TokenAlreadyUsed {
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM token WHERE id = ?");

            query.setInt(1, tokenId);

            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new FakeToken("The token is not known to the system");

            Token token = converter.resultSetToToken(rs);

            if (token.getUsed()) throw new TokenAlreadyUsed("The token is already used");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    /**
     * makes the token a used token for the customer if it is unused
     * @param tokenId
     * @throws FakeToken
     * @throws TokenAlreadyUsed
     */
    @Override
    public void markTokenAsUsed(int tokenId) throws FakeToken, TokenAlreadyUsed {
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "UPDATE token SET used = ? WHERE id = ?");

            query.setBoolean(1, true);
            query.setInt(2, tokenId);

            query.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * get token id from database result set
     * @param stmt prepared statement
     * @return token id
     * @throws SQLException
     */
    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }
}
