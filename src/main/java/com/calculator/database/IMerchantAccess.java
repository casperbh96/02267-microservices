package com.calculator.database;

import com.calculator.Merchant;
import com.calculator.database.exceptions.*;

public interface IMerchantAccess {
    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    Merchant createMerchant(Merchant merchant);
    Merchant updateMerchant(Merchant merchant);
    void deleteMerchantByMerchantId(int id);
}
