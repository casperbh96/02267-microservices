package com.dtupay.junit;

import com.dtupay.app.Merchant;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.MerchantAdapter;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MerchantAdapterTest {
    IMerchantAdapter m;

    @Before
    public void Setup(){
        m = new MerchantAdapter();
    }

    @Test
    public void CreateMerchantTest() throws MerchantDoesNotExist {
        Merchant merchant = new Merchant("1234", "Test");
        Merchant newMerchant = m.createMerchant(merchant);
        Assert.assertEquals(merchant.getCvr(), newMerchant.getCvr());
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        Merchant merchant = new Merchant("1234", "Test");
        Merchant newMerchant = m.createMerchant(merchant);
        m.deleteMerchantByMerchantId(newMerchant.getId());
        m.getMerchantByMerchantId(newMerchant.getId());
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        String newName = "UpdatedMerchant";

        Merchant merchant = new Merchant("1234", "Test");
        Merchant newMerchant = m.createMerchant(merchant);

        newMerchant.setName(newName);
        newMerchant = m.updateMerchant(newMerchant);

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
