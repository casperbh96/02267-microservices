package com.dtupay.BusinessLogic;

import com.dtupay.app.Merchant;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.MerchantAdapter;
import com.dtupay.database.exceptions.MerchantDoesNotExist;

public class BusinessLogicForMerchant implements IBusinessLogicForMerchant {

    IMerchantAdapter merchantAdapter = new MerchantAdapter();

    public Merchant CreateMerchant(String cvr, String name) {
        return merchantAdapter.createMerchant(cvr, name);
    }

    public Merchant UpdateMerchant(Merchant merchant) throws MerchantDoesNotExist {
        return merchantAdapter.updateMerchant(merchant);
    }

    public void DeleteMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        merchantAdapter.deleteMerchantByMerchantId(id);
    }

    public Merchant GetMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        return merchantAdapter.getMerchantByMerchantId(id);
    }

}
