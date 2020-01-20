package com.dtupay.BusinessLogic;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import com.dtupay.database.exceptions.NoMerchants;

import java.util.List;

public interface IBusinessLogicForMerchant {
    List<Merchant> getAllMerchants() throws NoMerchants;

    Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist;

    Merchant createMerchant(String cvr, String name);

    Merchant updateMerchant(int id, String cvr, String name) throws MerchantDoesNotExist;

    void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
