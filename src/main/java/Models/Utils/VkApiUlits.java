package Models.Utils;

import Models.*;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.springframework.http.HttpEntity;
import java.io.File;
import java.util.AbstractMap;

import Models.PostCommentResponse;
import Models.PostLikesResponse;
import Models.WallpostResponse;
import static io.restassured.RestAssured.given;


public class VkApiUlits {

    private static ISettingsFile urls = new JsonSettingsFile("urls.json");
    private static String request;

    private static String uri = urls.getValue("/vkApiUri").toString();

    public static WallpostResponse postTextOnUserWall(String userId, String msg, String token, String vers)
    {
        request = String.format( urls.getValue("/wallpost_method") +"?owner_id=%s&message=%s&access_token=%s&v=%s", userId, msg, token,vers);
        HttpEntity<String> entity = APIUtils.post(uri + request);
        WallpostResponse post = JsonConvertingUtils.convertJsonToObjectFromString(WallpostResponse.class, entity.getBody());
        return post;
    }

    public static AbstractMap.SimpleEntry<WallpostResponse, String> addPhotoAndMessageToPost(String userId, String message, String token, String vers, String postId, String imgPath)
    {
        UploadedImageResponse response = sendPhotoToUploadUrl(userId,token,vers,imgPath);
        ImageResponse image = saveWallPhoto(userId, token, vers, response);
        request = String.format( urls.getValue("/editpost_method") +"?owner_id=%s&post_id=%s&message=%s&attachments=photo%s_%s&access_token=%s&v=%s", userId, postId, message,userId, image.getResponse().get(0).getId(), token,vers);
        HttpEntity<String> entity = APIUtils.post(uri + request);
        return new AbstractMap.SimpleEntry<WallpostResponse, String>(JsonConvertingUtils.convertJsonToObjectFromString(WallpostResponse.class, entity.getBody()), image.getResponse().get(0).getId()) {
        };
    }

    public static ImageResponse saveWallPhoto(String userId, String token, String vers, UploadedImageResponse res)
    {
        request = String.format(urls.getValue("/saveWallPhoto_method") + "?owner_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=%s",userId,res.photo,res.server,res.hash,token,vers);
        HttpEntity<String> entity = APIUtils.post(uri+request);
        return JsonConvertingUtils.convertJsonToObjectFromString(ImageResponse.class, entity.getBody());
    }

    public static void deletePost(String userId, String postId, String token, String vers)
    {
        request = String.format( urls.getValue("/deletepost_method") +"?owner_id=%s&post_id=%s&access_token=%s&v=%s", userId, postId, token, vers);
        APIUtils.post(uri + request);
    }

    public static PostCommentResponse createComment(String userId, String postId, String message, String token, String vers)
    {
        request = String.format( urls.getValue("/createComment_method") +"?owner_id=%s&post_id=%s&message=%s&access_token=%s&v=%s", userId, postId, message, token, vers);
        HttpEntity<String> entity = APIUtils.post(uri + request);
        return JsonConvertingUtils.convertJsonToObjectFromString(PostCommentResponse.class, entity.getBody());
    }

    public static PostLikesResponse getUserPostLikes(String userId, String postId, String token, String vers)
    {
        request = String.format(urls.getValue("/getPostLikes_method")+"?type=post&owner_id=%s&item_id=%s&access_token=%s&v=%s", userId, postId, token, vers);
        HttpEntity<String> entity = APIUtils.post(uri + request);
        return JsonConvertingUtils.convertJsonToObjectFromString(PostLikesResponse.class, entity.getBody());
    }

    public static UploadServerResponse getUploadServer(String userId, String token, String vers)
    {
        request = String.format(urls.getValue("/getWallUploadServer_method") + "?owner_id=%s&access_token=%s&v=%s", userId, token, vers);
        HttpEntity<String> entity = APIUtils.post(uri + request);
        return JsonConvertingUtils.convertJsonToObjectFromString(UploadServerResponse.class, entity.getBody());
    }

    public static UploadedImageResponse sendPhotoToUploadUrl(String userId, String token, String vers, String imgPath)
    {
        String sourceDir = System.getProperty("user.dir");
        JsonPath path =  given().multiPart("photo", new File( sourceDir + imgPath))
                .when().contentType(ContentType.MULTIPART)
                .post(VkApiUlits.getUploadServer(userId, token, vers).response.upload_url).then().extract().jsonPath();
        return new UploadedImageResponse(path.get("server").toString(), path.get("photo").toString(), path.get("hash").toString());
    }
}