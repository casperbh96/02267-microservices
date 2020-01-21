package com.merchant.database;

import com.merchant.app.Merchant;
import com.merchant.database.exceptions.*;

import java.util.List;

/**
 * @author Danial
 * interface to handle merchant adapter class
 */

public interface IMerchantAdapter {
    List<Merchant> getAllMerchants() throws NoMerchants;
    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    Merchant createMerchant(String cvr, String name);
    Merchant updateMerchant(int id, String cvr, String name)  throws MerchantDoesNotExist;
    void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
