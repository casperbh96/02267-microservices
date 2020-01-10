package com.calculator;

import java.util.UUID;

public class Token {
    UUID id;
    boolean used;

    public Token(){}

    public Token(UUID _id, boolean _used){
        id = _id;
        used = _used;
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
}
