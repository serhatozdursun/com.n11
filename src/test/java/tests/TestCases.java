package tests;

import base.PageBase;
import org.junit.Test;
import pages.HomePage;

public class TestCases extends PageBase {

    @Test
    public void coreTest() throws Throwable {
        HomePage homePage = new HomePage(driver);
        homePage.assertHomePageIsLoad()
                .clickBtnSignIn()
                .assertSignInPageIsLoad()
                .typeEmail()
                .typePassword()
                .clickSignIn()
                .assertHomePageIsLoad()
                .assertIsSignIn()
                .typeSearchKeyWord()
                .clickSearch()
                .assertSearchPageIsLoad()
                .clickPagination(2)
                .assertPageIndex(2)
                .clickRandomProduct()
                .assertProductNameAndPrice()
                .addToProductBasketAndGoToBasket()
                .assertMyCartPageIsLoad()
                .assertProductPrice()
                .clickSpinnerUp()
                .checkQuantity(2)
                .removeAllElement()
                .checkBasketIsEmpty();


    }
}
