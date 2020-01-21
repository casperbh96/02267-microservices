package com.merchant.manager;

import com.merchant.app.Merchant;
import com.merchant.database.exceptions.MerchantDoesNotExist;
import com.merchant.database.exceptions.NoMerchants;

import java.util.List;

/**
 * interface to handle merchant manager class
 */

public interface IMerchantManager {
    List<Merchant> getAllMerchants() throws NoMerchants;

    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;

    Merchant createMerchant(String cvr, String name);

    Merchant updateMerchant(int id, String cvr, String name) throws MerchantDoesNotExist;

    void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
