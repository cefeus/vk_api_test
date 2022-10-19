package Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UploadedImageResponse {
    public String server;
    public String photo;
    public String hash;
}
