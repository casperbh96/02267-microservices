package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import com.dtupay.database.helper.TokenResultSetToObject;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.dtupay.database.Connector.createConnection;

public class TokenAdapter implements ITokenAdapter {
    public List<Token> tokens;
    public List<Token> allTokens;
    ICustomerAdapter dbCustomer;
    ITokenManagement tokenManager;
    TokenResultSetToObject converter = new TokenResultSetToObject();

    public TokenAdapter() {
        dbCustomer = new CustomerAdapter();
        tokenManager = new TokenManagement();
        tokens = new ArrayList<>();
        /*
        tokens.add(new Token(1, tokenManager.GetToken(), 1));
        tokens.add(new Token(2, tokenManager.GetToken(), 2));
        tokens.add(new Token(3, tokenManager.GetToken(), 3));
        tokens.add(new Token(4, tokenManager.GetToken(), 4));
        tokens.add(new Token(5, tokenManager.GetToken(), 5));
        tokens.add(new Token(6, tokenManager.GetToken(), 6));
        tokens.add(new Token(7, tokenManager.GetToken(), 7));

         */
    }

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

//    @Override
//    public List<Token> getAllUnusedTokenByCustomerId(int customerId) throws CustomerHasNoUnusedToken {
//        List<Token> tokens = new ArrayList<>();
//        try (Connection connection = createConnection()) {
//            PreparedStatement query = connection.prepareStatement(
//                    "SELECT * FROM token WHERE customerId = ?;");
//            query.setInt(1, customerId);
//            ResultSet rs = query.executeQuery();
//
//            if (!rs.next()) throw new CustomerHasNoUnusedToken(MessageFormat.format(
//                    "Customer customerId {0} has no unused tokens", customerId));
//
//            tokens = converter.resultSetToListOfTokens(rs);
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return tokens;
//    }

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

    @Override
    public boolean isTokenValid(Token token) throws FakeToken, TokenAlreadyUsed {
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM token WHERE id = ?");

            query.setInt(1, token.getId());

            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new FakeToken("The token is not known to the system");

            token = converter.resultSetToToken(rs);

            if (token.getUsed()) throw new TokenAlreadyUsed("The token is already used");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

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

    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }
}

//{
//    "name": "John Doe",
//    "cpr": "23143253253"
// }
