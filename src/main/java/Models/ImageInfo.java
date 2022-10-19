package Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {
    public String albumId;
    public String date;
    public String id;
    public String ownerId;
    public String accessKey;
    public List<Size> sizes = null;
    public String text;
    public String hasTags;
}
