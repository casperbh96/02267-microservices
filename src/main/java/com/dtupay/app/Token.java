package com.dtupay.app;

import java.util.UUID;

public class Token {
    int id;
    int customerId;
    UUID uuid;
    boolean used;

    public Token() {
    }

    public Token(int _customerId, UUID _uuid, boolean _used) {
        customerId = _customerId;
        uuid = _uuid;
        used = _used;
    }

    public Token(int _id, UUID _uuid, int _customerId) {
        id = _id;
        customerId = _customerId;
        uuid = _uuid;
        used = false;
    }

    public Token(int _id, int _customerId, UUID _uuid, boolean _used) {
        id = _id;
        customerId = _customerId;
        uuid = _uuid;
        used = _used;
    }

    public void setId(int _id) {
        this.id = _id;
    }
    public int getId() {
        return this.id;
    }

    public void setCustomerId(int _customerId) {
        this.customerId = _customerId;
    }
    public int getCustomerId() {
        return this.customerId;
    }

    public void setUuid(UUID newUuid) {
        this.uuid = newUuid;
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setUsed(boolean newUsed) {
        this.used = newUsed;
    }
    public boolean getUsed() {
        return used;
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
        return id == otherToken.id &&
                customerId == otherToken.customerId &&
                uuid.equals(otherToken.getUuid()) &&
                used == otherToken.used;
    }


}
