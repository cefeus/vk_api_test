package Pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class VkSignInPage extends Form {

    private ITextBox emailPhoneBox = getElementFactory().getTextBox(By.xpath("//input[@id = 'index_email']"), "Email or Phone Box");
    private IButton signInBtn = getElementFactory().getButton(By.xpath("//button[contains(@class,'VkIdForm__signInButton')]//span"), "SinIn button");
    private ITextBox passwordBox = getElementFactory().getTextBox(By.xpath("//input[@name = 'password']"), "Password Box");
    private IButton continueBtn = getElementFactory().getButton(By.xpath("//span[contains(@class,'vkuiButton__in')]"), "Continue button");
    public VkSignInPage()
    {
        super(By.xpath("//a[contains(@class,'LoginMobilePromoDevice--ios')]"), "IOS vk promo img");
    }
    public void enterLogin(String keys)
    {
        emailPhoneBox.focus();
        emailPhoneBox.sendKeys(keys);
        emailPhoneBox.unfocus();
    }

    public void clickSignInBtn()
    {
        signInBtn.click();
    }

    public void enterPassword(String keys)
    {
        passwordBox.focus();
        passwordBox.sendKeys(keys);
        passwordBox.unfocus();
    }
    public void clickContinueBtn()
    {
    continueBtn.click();
    }
}
