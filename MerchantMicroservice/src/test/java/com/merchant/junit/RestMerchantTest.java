package com.merchant.junit;

import com.merchant.manager.MerchantManager;
import com.merchant.manager.IMerchantManager;
import com.merchant.app.Merchant;
import com.merchant.database.exceptions.MerchantDoesNotExist;
import com.merchant.service.MerchantResource;
import com.merchant.service.exceptions.MerchantResourceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * junit testing for merchant rest services
 */

public class RestMerchantTest {

    IMerchantManager m = new MerchantManager();
    MerchantResource mr = new MerchantResource();
    Merchant merchant;

    /**
     * set up the merchant
     */
    @Before
    public void Setup() {
        merchant = m.createMerchant("12345678", "Ismaa");
    }

    /**
     * delete the merchant by merchant id
     * @throws MerchantDoesNotExist
     */
    @After
    public void cleanUp() throws MerchantDoesNotExist {
        m.deleteMerchantByMerchantId(merchant.getId());
    }

    /**
     * test for getting all the merchant on call
     */

    @Test
    public void GetAllMercahntsWithNoException() {
        mr.getMerchants();
    }

    /**
     * test for getting a merchant on call
     */
    @Test
    public void GetMerchantWithNoException() {
        mr.getMerchant(merchant.getId());
    }

    /**
     * checks if it asserts for bad request when asking for merchant with wrong id
     * @throws CustomerResourceException
     */
    @Test
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongIDToGetMerchants() throws MerchantResourceException {
        Assert.assertEquals(400, mr.getMerchant(1111111111).getStatus());
    }

    /**
     * checks for the post request for merchant
     */
    @Test
    public void PostMerchantWithNoException() {
        String merchantName = "Merchant Manolo";
        String merchantCVR = "merchant-999999";
        final String POST_PARAMS = "{\n" + "\"name\": \""+merchantName+"\",\r\n" +
                "    \"cvr\": \""+merchantCVR+"\"" + "\n}";
        mr.postMerchant(POST_PARAMS);
    }

    /**
     * checks for the put request for merchant
     */
    @Test
    public void PutMerchantWithNoException() {
        String merchantName = "Updated Merchant Manolo";
        String merchantCVR = "update-merchant-999999";
        final String PUT_PARAMS = "{\n" + "\"name\": \""+merchantName+"\",\r\n" +
                "    \"id\": "+merchant.getId()+",\r\n" +
                "    \"cvr\": \""+merchantCVR+"\"" + "\n}";
        mr.putMerchant(PUT_PARAMS);
    }

    /**
     * checks for deletion of merchant using rest service
     */
    @Test
    public void DeleteMerchantWithNoException() {
        mr.deleteMerchant(merchant.getId());
    }
}
