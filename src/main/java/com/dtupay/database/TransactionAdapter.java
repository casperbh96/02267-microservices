package com.dtupay.database;

import com.dtupay.app.Transaction;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.TransactionDoesNotExist;
import com.dtupay.database.helper.TransactionResultSetToObject;

import java.math.BigDecimal;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dtupay.database.Connector.createConnection;

public class TransactionAdapter implements ITransactionAdapter {

    private List<Transaction> transactions;
    TransactionResultSetToObject converter = new TransactionResultSetToObject();

    public TransactionAdapter() {
        transactions = new ArrayList<>();
        //add some sample transactions here if needed
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(int customerId) throws CustomerDoesNotExist {
        return transactions.stream().filter(t -> t.getFromId()==customerId).collect(Collectors.toList());
    }

    @Override
    public Transaction addTransaction(Timestamp timestamp, int fromId, int toId, String tokenId, BigDecimal amount, boolean isRefund) {
//        Transaction transaction = new Transaction(customerCpr, merchantCpr, tokenId, amount);
//        transactions.add(transaction);
//        return transaction;

        Transaction returnTransaction = null;
        int autoGenId = 0;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO transaction (timestamp, fromId, toId, amount, tokenId, isRefund) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setTimestamp(1, timestamp);
            query.setInt(2, fromId);
            query.setInt(3, toId);
            query.setString(4, tokenId);
            query.setBigDecimal(5, amount);
            query.setBoolean(6, isRefund);

            query.executeUpdate();

            autoGenId = getIdFromDbReturn(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            returnTransaction = getTransactionByTransactionId(autoGenId);
        } catch (TransactionDoesNotExist ex) {
            ex.printStackTrace();
        }

        return returnTransaction;
    }

    @Override
    public Transaction getTransactionByTransactionId(int id) throws TransactionDoesNotExist {
        Transaction transaction = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM transaction WHERE id = ?");
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new TransactionDoesNotExist(MessageFormat.format(
                    "Transaction id {0} was not found in transaction table.", id));

            transaction = converter.resultSetToTransaction(rs);

        } catch (SQLException | TransactionDoesNotExist ex) {
            ex.printStackTrace();
        }

        return transaction;
    }

    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

}
