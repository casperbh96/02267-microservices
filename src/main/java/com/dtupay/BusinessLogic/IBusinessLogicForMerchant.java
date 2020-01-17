package com.dtupay.BusinessLogic;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;

public interface IBusinessLogicForMerchant {
    Merchant GetMerchantByMerchantId(int id) throws MerchantDoesNotExist;
    Merchant CreateMerchant(Merchant merchant);
    Merchant UpdateMerchant(Merchant merchant)  throws MerchantDoesNotExist;
    void DeleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
