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
    public void Setup() {
        m = new BusinessLogicForMerchant();
    }

    @Test
    public void CreateMerchantTest() throws MerchantDoesNotExist {
        String cvr = "57";
        Merchant newMerchant = m.CreateMerchant(cvr, "Test");
        Assert.assertEquals(cvr, newMerchant.getCvr());
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        Merchant newMerchant = m.CreateMerchant("57", "Test");

        m.DeleteMerchantByMerchantId(newMerchant.getId());
        m.GetMerchantByMerchantId(newMerchant.getId());
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        String newName = "UpdatedMerchant";

        Merchant newMerchant = m.CreateMerchant("57", "Test");

        newMerchant.setName(newName);
        newMerchant = m.UpdateMerchant(newMerchant);

        Assert.assertEquals(newName, newMerchant.getName());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsMerchantDoesNotExist() throws MerchantDoesNotExist {
        thrown.expect(MerchantDoesNotExist.class);
        throw new MerchantDoesNotExist("");
    }

}
