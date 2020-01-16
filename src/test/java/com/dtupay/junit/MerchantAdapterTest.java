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
        Merchant merchant = new Merchant(57, "Test");
        m.createMerchant(merchant);
        Assert.assertEquals(merchant, m.getMerchantByMerchantId(57));
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        int id = 58;
        m.createMerchant(new Merchant(id, "Test"));
        m.deleteMerchantByMerchantId(id);
        m.getMerchantByMerchantId(id);
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        Merchant merchant = new Merchant(59, "Merchant");
        m.createMerchant(merchant);
        merchant.setName("UpdatedMerchant");
        m.updateMerchant(merchant);
        Assert.assertEquals(merchant, m.getMerchantByMerchantId(59));
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsMerchantDoesNotExist() throws MerchantDoesNotExist{
        thrown.expect(MerchantDoesNotExist.class);
        throw new MerchantDoesNotExist("");
    }
}
