package com.dtupay.adapters.token;

import com.dtupay.adapters.token.exceptions.TokenException;
import com.dtupay.adapters.token.model.Token;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Dumitru
 * class to handle all token related applications in the database
 */

public class TokenAdapter implements ITokenAdapter {

    String baseUrl;

    public TokenAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * create a list of new tokens for customer using rest service
     * @param customerId
     * @param numOfTokens
     * @return lsit of tokens
     * @throws TokenException
     */
    @Override
    public List<Token> getNewTokensForCustomer(int customerId, int numOfTokens) throws TokenException {
        try {
            URL url = new URL(baseUrl + "newTokens");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"customerId\":" + customerId + ",\"numberOfTokens\":" + numOfTokens + "}";
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
            List<Token> tokens = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonRaw);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                tokens.add(new Token(json));
            }
            return tokens;
        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }
    }

    /**
     * checks for token if it is valid or not
     * @param tokenId
     * @return boolean value i.e. true or false
     */
    @Override
    public boolean isTokenValid(int tokenId) throws TokenException {
        try {
            URL url = new URL(baseUrl + tokenId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            Scanner sc = new Scanner(url.openStream());
            String jsonRaw = "";
            while (sc.hasNext()) {
                jsonRaw += sc.nextLine();
            }
            sc.close();
            return Boolean.parseBoolean(jsonRaw);
        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }
    }

    /**
     * makes the token a used token for the customer if it is unused
     * @param tokenId
     */
    @Override
    public void markTokenAsUsed(int tokenId) throws TokenException {
        try {
            URL url = new URL(baseUrl + tokenId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.getResponseCode();
        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }
    }
}
