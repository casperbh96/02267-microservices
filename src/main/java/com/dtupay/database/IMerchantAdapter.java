package com.dtupay.database;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.*;

public interface IMerchantAdapter {
    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    Merchant createMerchant(Merchant merchant);
    Merchant updateMerchant(Merchant merchant)  throws MerchantDoesNotExist;
    void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
