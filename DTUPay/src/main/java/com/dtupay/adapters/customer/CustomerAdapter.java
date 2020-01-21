package com.dtupay.adapters.customer;

import com.dtupay.adapters.customer.exceptions.CustomerException;
import com.dtupay.adapters.customer.model.Customer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * class to handle all customer related applications in the database
 */

public class CustomerAdapter implements ICustomerAdapter {

    String baseUrl;

    public CustomerAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * to create new customer
     * @param cpr number
     * @param name customer name
     * @return customer
     * @throws CustomerException
     */
    @Override
    public Customer createCustomer(String cpr, String name) throws CustomerException {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"cpr\":\"" + cpr + "\",\"name\":\"" + name + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonRaw = "";
            String line;
            while ((line = in.readLine()) != null) {
                jsonRaw += line + "/n";
            }
            return new Customer(new JSONObject(jsonRaw));
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }

    /**
     * This class updates the customer with new data values
     * @param id customer id
     * @param cpr number
     * @param name customer name
     * @return customer
     * @throws CustomerException
     */
    @Override
    public Customer updateCustomer(int customerId, String cpr, String name) throws CustomerException {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            final String jsonRequest = "{\"id\":\"" + customerId + "\",\"cpr\":\"" + cpr + "\",\"name\":\"" + name + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(jsonRequest.getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonRaw = "";
            String line;
            while ((line = in.readLine()) != null) {
                jsonRaw += line + "\n";
            }
            return new Customer(new JSONObject(jsonRaw));
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }

    /**
     * gets customer using customer id
     * @param id
     * @return customer
     * @throws CustomerException
     */
    @Override
    public Customer getCustomerById(int customerId) throws CustomerException {
        try {
            URL url = new URL(baseUrl + customerId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            Scanner sc = new Scanner(url.openStream());
            String jsonRaw = "";
            while (sc.hasNext()) {
                jsonRaw += sc.nextLine();
            }
            sc.close();
            return new Customer(new JSONObject(jsonRaw));
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }


}
