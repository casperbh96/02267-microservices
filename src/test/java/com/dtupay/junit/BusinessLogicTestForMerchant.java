package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForMerchant;
import com.dtupay.BusinessLogic.IBusinessLogicForMerchant;
import com.dtupay.app.Merchant;
import com.dtupay.database.MerchantAdapter;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BusinessLogicTestForMerchant {
    IBusinessLogicForMerchant m;

    @Before
    public void Setup(){
        m = new BusinessLogicForMerchant();
    }

    @Test
    public void CreateMerchantTest() throws MerchantDoesNotExist {
        Merchant merchant = new Merchant("57", "Test");
        m.CreateMerchant(merchant);
        Assert.assertEquals(merchant, m.GetMerchantByMerchantId("57"));
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        String id = "58";
        m.CreateMerchant(new Merchant(id, "Test"));
        m.DeleteMerchantByMerchantId(id);
        m.GetMerchantByMerchantId(id);
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        Merchant merchant = new Merchant("59", "Merchant");
        m.CreateMerchant(merchant);
        merchant.setName("UpdatedMerchant");
        m.UpdateMerchant(merchant);
        Assert.assertEquals(merchant, m.GetMerchantByMerchantId("59"));
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsMerchantDoesNotExist() throws MerchantDoesNotExist{
        thrown.expect(MerchantDoesNotExist.class);
        throw new MerchantDoesNotExist("");
    }

}
