package com.merchant.junit;

import com.merchant.manager.MerchantManager;
import com.merchant.manager.IMerchantManager;
import com.merchant.app.Merchant;
import com.merchant.database.exceptions.MerchantDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MerchantManagerTest {
    IMerchantManager m;

    @Before
    public void Setup() {
        m = new MerchantManager();
    }

    @Test
    public void CreateMerchantTest() throws MerchantDoesNotExist {
        String cvr = "57";
        Merchant newMerchant = m.createMerchant(cvr, "Test");
        Assert.assertEquals(cvr, newMerchant.getCvr());
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        Merchant newMerchant = m.createMerchant("57", "Test");

        m.deleteMerchantByMerchantId(newMerchant.getId());
        m.getMerchantByMerchantId(newMerchant.getId());
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        String newName = "UpdatedMerchant";

        Merchant newMerchant = m.createMerchant("57", "Test");

        newMerchant.setName(newName);
        newMerchant = m.updateMerchant(newMerchant.getId(), newMerchant.getCvr(), newMerchant.getName());

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
