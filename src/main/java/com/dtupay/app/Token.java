package com.dtupay.app;

import java.util.UUID;

public class Token {
    UUID id;
    String customerId;
    boolean used;

    public Token() {
    }

    public Token(UUID _id, String _customerId) {
        id = _id;
        customerId = _customerId;
        used = false;
    }

    public void setCustomerId(String id) {
        this.customerId = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean newUsed) {
        this.used = newUsed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID newId) {
        this.id = newId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Token otherToken = (Token) other;
        return customerId.equals(otherToken.customerId) &&
                used == otherToken.used &&
                id.equals(otherToken.id);
    }


}
