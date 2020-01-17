package com.dtupay.app;

import com.dtupay.database.ITokenAdapter;
import com.dtupay.database.exceptions.CustomerIsUnableToReceiveNewTokens;
import com.dtupay.database.exceptions.TooManyTokenRequest;

import java.util.UUID;

public class TokenManagement implements ITokenManagement {

    @Override
    public UUID GetToken() {
        UUID token = UUID.randomUUID();
        //database.system_tokens.add(token);
        return token;
    }

    @Override
    public void CustomerGetTokens(Customer customer, int numTokens, ITokenAdapter tokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest {
        if (CanCustomerGetTokens(customer, numTokens)) {
            for (int i = 0; i < numTokens; i++) {
                Token token = new Token();
                token.uuid = GetToken();
                token.customerId = customer.getId();
                token.used = false;
                customer.tokens.add(token);
                tokens.createToken(token);
            }
        } else {
            throw new CustomerIsUnableToReceiveNewTokens("Customer is unable to receive new tokens");
        }
    }

    @Override
    public boolean CanCustomerGetTokens(Customer customer, int numTokens) throws TooManyTokenRequest {
        if(numTokens > 5){
            throw new TooManyTokenRequest("Too many token request: " + numTokens);
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
