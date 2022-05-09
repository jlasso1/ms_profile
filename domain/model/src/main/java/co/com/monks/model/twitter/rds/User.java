package co.com.monks.model.twitter.rds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String description;
    private String imageUrl;
    private String twitterUserName;
    private String title;
}
