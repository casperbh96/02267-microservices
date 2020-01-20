package com.dtupay.BusinessLogic;

import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import com.dtupay.database.exceptions.NoMerchants;

import java.util.List;

public interface IBusinessLogicForMerchant {
    List<Merchant> GetAllMerchants() throws NoMerchants;

    Merchant GetMerchantByMerchantId(int id) throws MerchantDoesNotExist;

    Merchant CreateMerchant(String cvr, String name);

    Merchant UpdateMerchant(int id, String cvr, String name) throws MerchantDoesNotExist;

    void DeleteMerchantByMerchantId(int id) throws MerchantDoesNotExist;
}
