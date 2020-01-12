package com.dtupay.app;

import com.dtupay.database.ITokenAdapter;
import com.dtupay.database.TokenAdapter;

import java.util.UUID;

public class TokenManagement implements ITokenManagement {



    @Override
    public UUID GetToken() {
        UUID token = UUID.randomUUID();
        //database.system_tokens.add(token);
        return token;
    }

    @Override
    public void CustomerGetTokens(Customer customer, int numTokens) {
        if (CanCustomerGetTokens(customer, numTokens)) {
            for (int i = 0; i < numTokens; i++) {
                Token token = new Token();
                token.id = GetToken();
                token.customerId = customer.getId();
                token.used = false;
                customer.tokens.add(token);
            }
        } else {
            throw new RuntimeException("Customer is unable to receive new tokens");
        }
    }

    @Override
    public boolean CanCustomerGetTokens(Customer customer, int numTokens) {
        if(numTokens > 5){
            throw new RuntimeException("Too many token request: " + numTokens);
        }

        if (customer.tokens.isEmpty()) {
            return true;
        }

        int unusedToken = 0;

        for (Token t : customer.tokens) {
            if (!t.used) {
                unusedToken++;
            }
        }
        return unusedToken == 1;
    }
}
