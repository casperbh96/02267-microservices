package com.transaction.database;

import com.transaction.app.Transaction;
import com.transaction.app.TransactionCustomer;
import com.transaction.app.TransactionMerchant;
import com.transaction.database.exceptions.CustomerDoesNotExist;
import com.transaction.database.exceptions.TransactionDoesNotExist;
import com.transaction.database.helper.TransactionResultSetToObject;

import java.math.BigDecimal;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.transaction.database.Connector.createConnection;

/**
 * @author Ionela
 * Transaction adapter class controls all the transactions
 */
public class TransactionAdapter implements ITransactionAdapter {

    private List<Transaction> transactions;
    TransactionResultSetToObject converter = new TransactionResultSetToObject();

    /**
     * initialize transaction adapter
     */
    public TransactionAdapter() {
        transactions = new ArrayList<>();
        //add some sample transactions here if needed
    }

    /**
     * gives monthly record of transaction by customer id
     * @param customerId
     * @param month
     * @param year
     * @return list of transactions
     * @throws CustomerDoesNotExist
     */
    @Override
    public List<TransactionCustomer> getMonthlyTransactionsByCustomerId(int customerId, int month, int year) throws CustomerDoesNotExist {
        List<TransactionCustomer> customerTransactions = new ArrayList<>();
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM transaction  " +
                            "WHERE ((fromId=? AND isRefund=0) "+
                            "OR (toId=? AND isRefund=1)) "+
                            "AND YEAR(timestamp)=? " +
                            "&& MONTH(timestamp)=? ",
                    Statement.RETURN_GENERATED_KEYS);

            query.setInt(1, customerId);
            query.setInt(2, customerId);
            query.setString(3, String.valueOf(year));
            query.setString(4, String.valueOf(month));

            ResultSet set = query.executeQuery();

            if(!set.next())
                return customerTransactions;

            set.beforeFirst();
            while(set.next()){
                customerTransactions.add(converter.resultSetToTransactionCustomer(set));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customerTransactions;
    }

    /**
     * gives monthly record of transaction by customer id
     * @param merchantId
     * @param month
     * @param year
     * @return list of transactions
     */
    @Override
    public List<TransactionMerchant> getMonthlyTransactionsByMerchantId(int merchantId, int month, int year) {
        List<TransactionMerchant> merchantTransactions = new ArrayList<>();
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM transaction  " +
                            "WHERE (fromId=? AND isRefund=1) "+
                            "OR (toId=? AND isRefund=0) "+
                            "AND YEAR(timestamp)=? " +
                            "&& MONTH(timestamp)=? ",
                    Statement.RETURN_GENERATED_KEYS);

            query.setInt(1, merchantId);
            query.setInt(2, merchantId);
            query.setString(3, String.valueOf(year));
            query.setString(4, String.valueOf(month));

            ResultSet set = query.executeQuery();

            if(!set.next())
                return merchantTransactions;

            set.beforeFirst();
            while(set.next()){
                merchantTransactions.add(converter.resultSetToTransactionMerchant(set));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return merchantTransactions;
    }

    /**
     * adds transaction in the report
     * @param timestamp
     * @param fromId
     * @param toId
     * @param tokenId
     * @param amount
     * @param isRefund
     * @return transaction
     */
    @Override
    public Transaction addTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund) {

        Transaction returnTransaction = null;
        int autoGenId = 0;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO transaction (timestamp, fromId, toId, amount, tokenId, isRefund) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setTimestamp(1, timestamp);
            query.setInt(2, fromId);
            query.setInt(3, toId);
            query.setBigDecimal(4, amount);
            query.setInt(5, tokenId);
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

    /**
     * gives transaction by transaction id
     * @param id
     * @return transaction
     * @throws TransactionDoesNotExist
     */
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

    /**
     * gives transaction id from the result set of current transaction
     * @param stmt
     * @return transaction id
     * @throws SQLException
     */
    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

}
