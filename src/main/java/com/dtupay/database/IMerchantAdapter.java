package com.dtupay.database;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.*;

import java.util.List;

public interface IMerchantAdapter {
    List<Merchant> getAllMerchants() throws NoMerchants;
    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    Merchant createMerchant(String cvr, String name);
    Merchant updateMerchant(Merchant merchant)  throws MerchantDoesNotExist;
    void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
