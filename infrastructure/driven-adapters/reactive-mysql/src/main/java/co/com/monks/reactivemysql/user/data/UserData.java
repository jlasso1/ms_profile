package co.com.monks.reactivemysql.user.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("zemoga_test_db.portfolio")
public class UserData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column("idportfolio")
    private String id;

    @Column("description")
    private String description;

    @Column("image_url")
    private String imageUrl;

    @Column("twitter_user_name")
    private String twitterUserName;

    @Column("title")
    private String title;
}
