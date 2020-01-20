package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForMerchant;
import com.dtupay.BusinessLogic.IBusinessLogicForMerchant;
import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import com.dtupay.service.MerchantResource;
import com.dtupay.service.exceptions.MerchantResourceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RestMerchantTest {

    IBusinessLogicForMerchant m = new BusinessLogicForMerchant();
    MerchantResource mr = new MerchantResource();
    Merchant merchant;

    @Before
    public void Setup() {
        merchant = m.createMerchant("12345678", "Ismaa");
    }

    @After
    public void cleanUp() throws MerchantDoesNotExist {
        m.deleteMerchantByMerchantId(merchant.getId());
    }

    @Test
    public void GetAllCustomersWithNoException() {
        mr.getMerchants();
    }

    @Test
    public void GetCustomerWithNoException() {
        mr.getMerchant(merchant.getId());
    }

    @Test
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongIDToGetCustomers() throws MerchantResourceException {
        Assert.assertEquals(400, mr.getMerchant(1111111111).getStatus());
    }

    @Test
    public void PostCustomerWithNoException() {
        String merchantName = "Merchant Manolo";
        String merchantCVR = "merchant-999999";
        final String POST_PARAMS = "{\n" + "\"name\": \""+merchantName+"\",\r\n" +
                "    \"cvr\": \""+merchantCVR+"\"" + "\n}";
        mr.postMerchant(POST_PARAMS);
    }

    @Test
    public void PutCustomerWithNoException() {
        String merchantName = "Updated Merchant Manolo";
        String merchantCVR = "update-merchant-999999";
        final String PUT_PARAMS = "{\n" + "\"name\": \""+merchantName+"\",\r\n" +
                "    \"id\": "+merchant.getId()+",\r\n" +
                "    \"cvr\": \""+merchantCVR+"\"" + "\n}";
        mr.putMerchant(PUT_PARAMS);
    }

    @Test
    public void DeleteCustomerWithNoException() {
        mr.deleteMerchant(merchant.getId());
    }
}
