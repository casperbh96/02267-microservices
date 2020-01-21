package com.dtupay.adapters.token.model;

import org.json.JSONObject;

import java.util.UUID;

public class Token {
    int id;
    int customerId;
    UUID uuid;
    boolean used;

    public Token(JSONObject json) {
        this.id = json.getInt("id");
        this.customerId = json.getInt("customerId");
        this.uuid = UUID.fromString(json.getString("uuid"));
        this.used = json.getBoolean("used");
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

    public String toJsonString() {
        return "{\"id\":" + this.id + ",\"customerId\":" + this.customerId + ",\"uuid\":\"" + this.uuid + "\",\"used\":" + this.used + "}";
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
