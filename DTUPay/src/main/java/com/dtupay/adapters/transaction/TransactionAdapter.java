package com.dtupay.adapters.transaction;

import com.dtupay.adapters.token.exceptions.TokenException;
import com.dtupay.adapters.token.model.Token;
import com.dtupay.adapters.transaction.exceptions.TransactionException;
import com.dtupay.adapters.transaction.model.TransactionCustomer;
import com.dtupay.adapters.transaction.model.TransactionMerchant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter implements ITransactionAdapter {

    String baseUrl;

    public TransactionAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<TransactionCustomer> getMonthlyCustomerReport(int customerId, int month, int year) throws TransactionException {
        try {
            URL url = new URL(baseUrl + "customer");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"id\":" + customerId + ",\"month\":" + month + ",\"year\":" + year + "}";
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonRaw = "";
            String line;
            while ((line = in.readLine()) != null) {
                jsonRaw += line + "\n";
            }
            List<TransactionCustomer> transactions = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonRaw);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                transactions.add(new TransactionCustomer(json));
            }
            return transactions;
        } catch (Exception e) {
            throw new TransactionException(e.getMessage());
        }
    }

    @Override
    public List<TransactionMerchant> getMonthlyMerchantReport(int merchantId, int month, int year) throws TransactionException {
        try {
            URL url = new URL(baseUrl + "merchant");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"id\":" + merchantId + ",\"month\":" + month + ",\"year\":" + year + "}";
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonRaw = "";
            String line;
            while ((line = in.readLine()) != null) {
                jsonRaw += line + "\n";
            }
            List<TransactionMerchant> transactions = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonRaw);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                transactions.add(new TransactionMerchant(json));
            }
            return transactions;
        } catch (Exception e) {
            throw new TransactionException(e.getMessage());
        }
    }

    @Override
    public void registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund) throws TransactionException {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"timestamp\": \"" + timestamp.toString() + "\",\"fromId\":" + fromId + ",\"toId\":" + toId + ",\"tokenId\":" + tokenId + ",\"amount\":" + amount + ",\"isRefund\":" + isRefund + "}";
            System.out.println(jsonRequest);
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            System.out.println(connection.getResponseCode());
        } catch (Exception e) {
            throw new TransactionException(e.getMessage());
        }
    }
}
