package com.dtupay.database;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import com.dtupay.database.helper.MerchantResultSetToObject;

import static com.dtupay.database.Connector.*;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.dtupay.database.Connector.createConnection;

public class MerchantAdapter implements IMerchantAdapter {
    List<Merchant> merchants;
    MerchantResultSetToObject converter = new MerchantResultSetToObject();

    public MerchantAdapter() {
        merchants = new ArrayList<>();

        merchants.add(new Merchant(1, "123", "DTU Canteen"));
        merchants.add(new Merchant(2, "1234","DTU Library"));
    }

    @Override
    public Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        Merchant merchant = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM merchant WHERE id = ?");
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new MerchantDoesNotExist(MessageFormat.format(
                    "Merchant id {0} was not found in merchant table.", id));

            merchant = converter.resultSetToMerchant(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return merchant;
    }

    @Override
    public Merchant createMerchant(Merchant merchant) {
        Merchant returnMerchant = null;
        int autoGenId = 0;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO merchant (cvr, name) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setString(1, merchant.getCvr());
            query.setString(2, merchant.getName());

            query.executeUpdate();

            autoGenId = getIdFromDbReturn(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            returnMerchant = getMerchantByMerchantId(autoGenId);
        } catch (MerchantDoesNotExist ex) {
            ex.printStackTrace();
        }

        return returnMerchant;
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) throws MerchantDoesNotExist {
        Merchant returnMerchant = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "UPDATE merchant SET cvr = ?, name = ? WHERE id = ?");

            query.setString(1, merchant.getCvr());
            query.setString(2, merchant.getName());
            query.setInt(3, merchant.getId());

            query.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            returnMerchant = getMerchantByMerchantId(merchant.getId());
        } catch (MerchantDoesNotExist ex) {
            ex.printStackTrace();
        }

        return returnMerchant;
    }

    @Override
    public void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "DELETE FROM merchant WHERE id = ?;");

            query.setInt(1, id);
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

