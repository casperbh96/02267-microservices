package com.calculator.database;

import com.calculator.database.exceptions.*;

public interface IMerchantAccess {
    // TODO: return Merchant class instead of void
    void getMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    void createMerchant(); // TODO: use merchant object instead
    void updateMerchantByMerchantId(); // TODO: use merchant object instead
    void deleteMerchantByMerchantId(int id);
}
