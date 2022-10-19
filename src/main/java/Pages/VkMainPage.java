package Pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class VkMainPage extends Form {

    private IButton myProfileBtn = getElementFactory().getButton(By.xpath("//li[@id='l_pr']"), "MyProfile button");

    public VkMainPage()
    {
        super(By.xpath("//div[@id='stories_feed_items']"),"Stories Container");
    }

    public void myProfileBtnClick()
    {
        myProfileBtn.click();
    }
}
