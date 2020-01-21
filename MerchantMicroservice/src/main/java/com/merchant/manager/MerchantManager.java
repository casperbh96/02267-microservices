package com.merchant.manager;

import com.merchant.app.Merchant;
import com.merchant.database.IMerchantAdapter;
import com.merchant.database.MerchantAdapter;
import com.merchant.database.exceptions.MerchantDoesNotExist;
import com.merchant.database.exceptions.NoMerchants;

import java.util.List;

public class MerchantManager implements IMerchantManager {

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
