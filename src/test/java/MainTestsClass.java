import Models.PostCommentResponse;
import Models.PostLikesResponse;
import Models.WallpostResponse;
import Pages.VkMainPage;
import Pages.VkSignInPage;
import Pages.VkUserPage;
import Models.Utils.VkApiUlits;
import Models.Utils.RandomGeneratingMethods;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.AbstractMap;

public class MainTestsClass {

    private ISettingsFile testData = new JsonSettingsFile("testData.json");
    private ISettingsFile config  = new JsonSettingsFile("config.json");
    private ISettingsFile urls = new JsonSettingsFile("urls.json");
    private Browser browser;

    @BeforeMethod
    public void beforeTestCase()
    {
        browser = AqualityServices.getBrowser();
    }

    @Test
    public void vkApiTest() {

        VkSignInPage signInPage = new VkSignInPage();
        browser.goTo(urls.getValue("/url").toString());
        browser.maximize();
        browser.waitForPageToLoad();
        Assert.assertTrue(signInPage.state().waitForDisplayed());
        signInPage.enterLogin(testData.getValue("/login").toString());
        signInPage.clickSignInBtn();
        signInPage.enterPassword(testData.getValue("/password").toString());
        signInPage.clickContinueBtn();
        VkMainPage mainPage = new VkMainPage();
        Assert.assertTrue(mainPage.state().waitForDisplayed());
        mainPage.myProfileBtnClick();
        String postContent = RandomGeneratingMethods.getRandomString(Integer.parseInt(config.getValue("/RandomStringSize").toString()),true,false);
        WallpostResponse originalPost = VkApiUlits.postTextOnUserWall(testData.getValue("/userId").toString(), postContent, testData.getValue("/token").toString(), config.getValue("/APIVersion").toString());
        VkUserPage userPage = new VkUserPage();
        Assert.assertEquals(userPage.getPostPublisher(testData.getValue("/userId").toString(),originalPost.response.post_id),testData.getValue("/userName"),"User name and post publisher name aren't equal");
        Assert.assertEquals(userPage.getPostContent(testData.getValue("/userId").toString(), originalPost.response.post_id), postContent, "Actual and expected messages aren't equal");
        String editPostContent = RandomGeneratingMethods.getRandomString(Integer.parseInt(config.getValue("/RandomStringSize").toString()),true,false);
        AbstractMap.SimpleEntry<WallpostResponse, String> editedPost = VkApiUlits.addPhotoAndMessageToPost(testData.getValue("/userId").toString(),
                editPostContent,
                testData.getValue("/token").toString(),
               config.getValue("/APIVersion").toString(), originalPost.response.post_id, testData.getValue("/imagePath").toString());
        String imgRef = String.format("https://vk.com/photo%s_%s", testData.getValue("/userId"), editedPost.getValue());
        Assert.assertEquals(userPage.getPostImgAttribute(testData.getValue("/userId").toString(),editedPost.getKey().getResponse().post_id), imgRef, "Image don't exist or not similar to expected");
        Assert.assertEquals(userPage.getPostContent(testData.getValue("/userId").toString(), editedPost.getKey().getResponse().post_id), editPostContent, "Actual and expected messages aren't equal");
        String commentContent = RandomGeneratingMethods.getRandomString(Integer.parseInt(config.getValue("/RandomStringSize").toString()),true,false);
        PostCommentResponse comment = VkApiUlits.createComment(testData.getValue("/userId").toString(), editedPost.getKey().getResponse().post_id,commentContent,testData.getValue("/token").toString(), config.getValue("/APIVersion").toString());
        userPage.showNextClick(testData.getValue("/userId").toString(),editedPost.getKey().response.post_id);
        Assert.assertEquals(userPage.getCommentAuthor(testData.getValue("/userId").toString(),  editedPost.getKey().getResponse().post_id, comment.getResponse().getComment_id()), testData.getValue("/userName"), "Comment author and user aren't equal");
        userPage.likeButtonClick(testData.getValue("/userId").toString(),editedPost.getKey().response.post_id);
        PostLikesResponse likes = VkApiUlits.getUserPostLikes(testData.getValue("/userId").toString(),editedPost.getKey().response.post_id, testData.getValue("/token").toString(), config.getValue("/APIVersion").toString());
        Assert.assertTrue(likes.response.items.contains(testData.getValue("/userId").toString()), testData.getValue("/userId").toString() +" User like don't exist");
        VkApiUlits.deletePost(testData.getValue("/userId").toString(),editedPost.getKey().getResponse().post_id,testData.getValue("/token").toString(),config.getValue("/APIVersion").toString());
        Assert.assertFalse(userPage.isPostExist(testData.getValue("/userId").toString(),originalPost.response.post_id), "Post still exist");
    }

    @AfterMethod
    public void afterTestCase() {
        browser.quit();
    }
}
