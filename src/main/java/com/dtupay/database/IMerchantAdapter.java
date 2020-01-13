package com.dtupay.database;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.*;

public interface IMerchantAdapter {
    Merchant getMerchantByMerchantId(String id) throws MerchantDoesNotExist;
    Merchant createMerchant(Merchant merchant);
    Merchant updateMerchant(Merchant merchant)  throws MerchantDoesNotExist;
    void deleteMerchantByMerchantId(String id) throws MerchantDoesNotExist;
}
