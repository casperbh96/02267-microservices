package com.dtupay.BusinessLogic;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;

public interface IBusinessLogicForMerchant {
    Merchant GetMerchantByMerchantId(String id) throws MerchantDoesNotExist;
    Merchant CreateMerchant(Merchant merchant);
    Merchant UpdateMerchant(Merchant merchant)  throws MerchantDoesNotExist;
    void DeleteMerchantByMerchantId(String id) throws MerchantDoesNotExist;
}
