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
        Merchant newMerchant = m.CreateMerchant(merchant);
        Assert.assertEquals(merchant.getCvr(), newMerchant.getCvr());
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        Merchant merchant = new Merchant("57", "Test");
        Merchant newMerchant = m.CreateMerchant(merchant);

        m.DeleteMerchantByMerchantId(newMerchant.getId());
        m.GetMerchantByMerchantId(newMerchant.getId());
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        String newName = "UpdatedMerchant";

        Merchant merchant = new Merchant("57", "Test");
        Merchant newMerchant = m.CreateMerchant(merchant);

        newMerchant.setName(newName);
        newMerchant = m.UpdateMerchant(newMerchant);

        Assert.assertEquals(newName, newMerchant.getName());
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsMerchantDoesNotExist() throws MerchantDoesNotExist{
        thrown.expect(MerchantDoesNotExist.class);
        throw new MerchantDoesNotExist("");
    }

}
