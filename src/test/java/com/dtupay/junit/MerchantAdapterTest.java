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
    public void Setup() {
        m = new MerchantAdapter();
    }

    @Test
    public void CreateMerchantTest() throws MerchantDoesNotExist {
        String cvr = "1234";
        Merchant newMerchant = m.createMerchant(cvr, "Test");
        Assert.assertEquals(cvr, newMerchant.getCvr());
    }

    @Test(expected = MerchantDoesNotExist.class)
    public void DeleteMerchantTest() throws MerchantDoesNotExist {
        Merchant newMerchant = m.createMerchant("1234", "Test");
        m.deleteMerchantByMerchantId(newMerchant.getId());
        m.getMerchantByMerchantId(newMerchant.getId());
    }

    @Test
    public void UpdateMerchantTest() throws MerchantDoesNotExist {
        String newName = "UpdatedMerchant";

        Merchant newMerchant = m.createMerchant("1234", "Test");

        newMerchant.setName(newName);
        newMerchant = m.updateMerchant(newMerchant);

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
