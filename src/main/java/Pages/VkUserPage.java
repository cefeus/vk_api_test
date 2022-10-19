package Pages;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class VkUserPage extends Form {

    public VkUserPage() {
        super(By.xpath("//img[@class = 'page_avatar_img']"), "User avatar image");
    }

    public String getPostPublisher(String userId, String postId) {
       return getElementFactory().getLabel(By.xpath("//div[@id = 'post" + userId + "_" + postId + "']//h5[@class = 'post_author']"), postId + " post publisher").getText();
    }

    public String getPostContent(String userId, String postId) {
       return getElementFactory().getLabel(By.xpath("//div[@id = 'wpt" + userId + "_" + postId + "']//div[contains(@class,'wall_post_text')]"), postId + " post content").getText();

    }

    public String getCommentAuthor(String userId, String postId, String comId)
    {
       return getElementFactory().getLabel(By.xpath("//div[@id = 'post" + userId + "_" + postId + "']//div[@id = 'post" + userId + "_" + comId + "']//a[@class = 'author']"), comId + " comment author").getText();
    }

    public void showNextClick(String userId, String postId)
    {
        getElementFactory().getButton(By.xpath("//div[@id = 'post" + userId + "_" + postId + "']//a[contains(@class, 'replies_next')]"), "Show Next Button").click();
    }

    public void likeButtonClick(String userId, String postId)
    {
        getElementFactory().getButton(By.xpath("//div[@id = 'post" + userId + "_" + postId + "']//span[contains(@class, '_like_button_icon')]"), "Like Button").click();
    }
    public boolean isPostExist(String userId, String postId)
    {
        return getElementFactory().getButton(By.xpath("//div[@id = 'post" + userId + "_" + postId + "']//span[contains(@class, '_like_button_icon')]"), postId +" Like Button").state().isDisplayed();
    }

    public String getPostImgAttribute(String userId, String postId)
    {
        return getElementFactory().getLabel(By.xpath("//div[@id = 'post" + userId + "_" + postId + "']//a[@aria-label= 'photo']"),"Post Image").getAttribute("href");
    }
}
