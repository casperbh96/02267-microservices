package com.dtupay.BusinessLogic;

import com.dtupay.app.Merchant;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.MerchantAdapter;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import com.dtupay.database.exceptions.NoMerchants;

import java.util.List;

public class BusinessLogicForMerchant implements IBusinessLogicForMerchant {

    IMerchantAdapter merchantAdapter = new MerchantAdapter();

    public Merchant createMerchant(String cvr, String name) {
        return merchantAdapter.createMerchant(cvr, name);
    }

    public Merchant updateMerchant(int id, String cvr, String name) throws MerchantDoesNotExist {
        return merchantAdapter.updateMerchant(id, cvr, name);
    }

    public void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        merchantAdapter.deleteMerchantByMerchantId(id);
    }

    public Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        return merchantAdapter.getMerchantByMerchantId(id);
    }

    public List<Merchant> getAllMerchants() throws NoMerchants {
        return merchantAdapter.getAllMerchants();
    }

}
