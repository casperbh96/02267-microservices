package com.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dtu.ws.fastmoney.Bank;

public class Calculator {

    public static void main(String[] args) {

        System.out.println("Hello World");
        System.out.println(add("3,6\n15"));
        Bank b = new Bank();

        
    }

    public static UUID getToken(){
        UUID token = UUID.randomUUID();
        //database.system_tokens.add(token);
        return token;
    }

    // The customer can request 1 to 5 tokens if he either has spent all tokens (or if it is the first time he
    // requests tokens) or has only one unused token left. Overall, a customer can only have at most 6 unused tokens.
    // If the user has more than 1 unused token and he requests again a set of tokens, his request will
    // be denied
    public static void customerGetTokens(Customer customer, int numTokens) {
        if (customer.tokens.isEmpty() || customer.tokens.size() == 1) {
            int pos = 0;
            if (customer.tokens.size() == 1) pos = 1;
            for (int i = pos; i < numTokens; i++) {
                Token token = new Token();
                token.id = getToken();
                token.used = false;
                customer.tokens.add(token);
            }
            return;
        } else throw new RuntimeException("Customer " + customer.id + "can't request " + numTokens + " tokens");
    }

    // There is a function to use a token. That means, the software is provided with the number/string
    // from one of the issued tokens and the application should accept the token if the token was not
    // used before.
    // If the token was already used before, e.g. presented before, then the function should reject the
    // token.
    // If the token is not known to the system (e.g. a fake token), then the again the function should
    // reject the token
    public static boolean use_token(Token token, DatabaseTokens database){
        if (database.system_tokens.contains(token.id)){
            return false;
        }
        return token.used;
    }

    public static int add(final String numbers) {
        String delimiter = ",|\n";
        String numbersWithoutDelimiter = numbers;
        if (numbers.startsWith("//")) {
            int delimiterIndex = numbers.indexOf("//") + 2;
            delimiter = numbers.substring(delimiterIndex, delimiterIndex + 1);
            numbersWithoutDelimiter = numbers.substring(numbers.indexOf("\n") + 1);
        }
        return add(numbersWithoutDelimiter, delimiter);
    }

    private static int add(final String numbers, final String delimiter) {
        int returnValue = 0;
        String[] numbersArray = numbers.split(delimiter);
        List<Integer> negativeNumbers = new ArrayList<Integer>();
        for (String number : numbersArray) {
            if (!number.trim().isEmpty()) {
                int numberInt = Integer.parseInt(number.trim());
                if (numberInt < 0) {
                    negativeNumbers.add(numberInt);
                } else if (numberInt <= 1000) {
                    returnValue += numberInt;
                }
            }
        }
        if (negativeNumbers.size() > 0) {
            throw new RuntimeException("Negatives not allowed: " + negativeNumbers.toString());
        }
        return returnValue;
    }

}