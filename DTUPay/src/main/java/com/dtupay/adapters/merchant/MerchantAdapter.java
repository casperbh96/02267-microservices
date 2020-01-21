package com.dtupay.adapters.merchant;

import com.dtupay.adapters.merchant.exceptions.MerchantException;
import com.dtupay.adapters.merchant.model.Merchant;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * class to handle all merchant related applications in the database
 */

public class MerchantAdapter implements IMerchantAdapter {

    String baseUrl;

    public MerchantAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * to create new merchant
     * @param cvr number
     * @param name merchant name
     * @return merchant
     * @throws MerchantException
     */
    @Override
    public Merchant createMerchant(String cvr, String name) throws MerchantException {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"cvr\":\"" + cvr + "\",\"name\":\"" + name + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonRaw = "";
            String line;
            while ((line = in.readLine()) != null) {
                jsonRaw += line + "\n";
            }
            return new Merchant(new JSONObject(jsonRaw));
        } catch (Exception e) {
            throw new MerchantException(e.getMessage());
        }
    }

    /**
     * This class updates the merchant with new data values
     * @param id merchant id
     * @param cpr number
     * @param name merchant name
     * @return merchant
     * @throws MerchantDoesNotExist
     */
    @Override
    public Merchant updateMerchant(int merchantId, String cvr, String name) throws MerchantException {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"id\":\"" + merchantId + "\",\"cvr\":\"" + cvr + "\",\"name\":\"" + name + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonRaw = "";
            String line;
            while ((line = in.readLine()) != null) {
                jsonRaw += line + "\n";
            }
            return new Merchant(new JSONObject(jsonRaw));
        } catch (Exception e) {
            throw new MerchantException(e.getMessage());
        }
    }

    /**
     * gets merchant using merchant id
     * @param id
     * @return merchant
     * @throws MerchantDoesNotExist
     */
    @Override
    public Merchant getMerchantById(int merchantId) throws MerchantException {
        try {
            URL url = new URL(baseUrl + merchantId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            Scanner sc = new Scanner(url.openStream());
            String jsonRaw = "";
            while (sc.hasNext()) {
                jsonRaw += sc.nextLine();
            }
            sc.close();
            return new Merchant(new JSONObject(jsonRaw));
        } catch (Exception e) {
            throw new MerchantException(e.getMessage());
        }
    }
}
