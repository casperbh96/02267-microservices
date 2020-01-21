package com.token.app;



import java.util.UUID;

/**
 * @author Casper
 * Created a token class to handle all the tokens in the system
 */
public class Token {
    int id;
    int customerId;
    UUID uuid;
    boolean used;

    public Token() {
    }

    /**
     * This is constructor for token class to initiate the token object
     * @param _customerId corresponding to every customer
     * @param _uuid is some random id
     * @param _used if token is used or not
     */
    public Token(int _customerId, UUID _uuid, boolean _used) {
        customerId = _customerId;
        uuid = _uuid;
        used = _used;
    }

    /**
     * add more parameters
     * @param _id is token id
     * @param _customerId corresponding to every customer
     * @param _uuid is some random id
     * @param _used if token is used or not
     */
    public Token(int _id, int _customerId, UUID _uuid, boolean _used) {
        id = _id;
        customerId = _customerId;
        uuid = _uuid;
        used = _used;
    }

    /**
     * sets token id
     * @param _id
     */
    public void setId(int _id) {
        this.id = _id;
    }

    /**
     * shows token id
     * @return token id
     */
    public int getId() {
        return this.id;
    }

    /**
     * sets customer id corresponding to the token
     * @param _customerId
     */
    public void setCustomerId(int _customerId) {
        this.customerId = _customerId;
    }

    /**
     * shows customer id
     * @return customer id
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * set some random id
     * @param newUuid
     */
    public void setUuid(UUID newUuid) {
        this.uuid = newUuid;
    }

    /**
     * shows some random id
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * set if token is used or not
     * @param newUsed
     */
    public void setUsed(boolean newUsed) {
        this.used = newUsed;
    }

    /**
     * shows if token is used or not
     * @return boolean
     */
    public boolean getUsed() {
        return used;
    }

    /**
     * checks if token is equal to existing one
     * @param other token
     * @return if true or false
     */
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
