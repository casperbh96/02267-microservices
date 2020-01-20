package java.com.merchant.junit;

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

public class RestMerchantTest {

    IMerchantManager m = new MerchantManager();
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
