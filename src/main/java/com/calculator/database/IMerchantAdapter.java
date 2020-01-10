package com.calculator.database;

import com.calculator.Merchant;
import com.calculator.database.exceptions.*;

public interface IMerchantAdapter {
    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    Merchant createMerchant(Merchant merchant);
    Merchant updateMerchant(Merchant merchant)  throws MerchantDoesNotExist;
    void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
