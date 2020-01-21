package com.merchant.manager;

import com.merchant.app.Merchant;
import com.merchant.database.IMerchantAdapter;
import com.merchant.database.MerchantAdapter;
import com.merchant.database.exceptions.MerchantDoesNotExist;
import com.merchant.database.exceptions.NoMerchants;

import java.util.List;

/**
 * provided business logic for merchant. basically, from where we are accessing all the data from database
 */
public class BusinessLogicForMerchant implements IBusinessLogicForMerchant {

    IMerchantAdapter merchantAdapter = new MerchantAdapter();

    /**
     * create new merchant
     * @param cpr
     * @param name
     * @return merchant object with name and cpr number
     */
    public Merchant createMerchant(String cvr, String name) {
        return merchantAdapter.createMerchant(cvr, name);
    }

    /**
     * Update information existing merchant
     * @param id
     * @param cpr
     * @param name
     * @return merchant object with updated values
     * @throws MerchantDoesNotExist
     */
    public Merchant updateMerchant(int id, String cvr, String name) throws MerchantDoesNotExist {
        return merchantAdapter.updateMerchant(id, cvr, name);
    }

    /**
     * removes merchant from the merchant list using id
     * @param id
     * @throws MerchantDoesNotExist
     */
    public void deleteMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        merchantAdapter.deleteMerchantByMerchantId(id);
    }

    /**
     * shows merchant information by merchant id
     * @param id
     * @return merchant object with name and cpr number
     * @throws MerchantDoesNotExist
     */
    public Merchant getMerchantByMerchantId(int id) throws MerchantDoesNotExist {
        return merchantAdapter.getMerchantByMerchantId(id);
    }

    /**
     * @return alist of all the merchants
     * @throws NoMerchants
     */
    public List<Merchant> getAllMerchants() throws NoMerchants {
        return merchantAdapter.getAllMerchants();
    }

}
