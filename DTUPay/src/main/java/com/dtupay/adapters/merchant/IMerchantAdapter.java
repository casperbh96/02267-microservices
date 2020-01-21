package com.dtupay.adapters.merchant;

import com.dtupay.adapters.merchant.exceptions.MerchantException;
import com.dtupay.adapters.merchant.model.Merchant;

/**
 * interface to handle merchant adapter class
 */

public interface IMerchantAdapter {
    Merchant createMerchant(String cvr, String name) throws MerchantException;

    Merchant updateMerchant(int merchantId, String cvr, String name) throws MerchantException;

    Merchant getMerchantById(int merchantId) throws MerchantException;
}
