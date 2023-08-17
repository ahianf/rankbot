package cl.ahian.rankbot.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.openhft.hashing.LongTupleHashFunction;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GoogleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String pictureUrl;
    private UUID uuid;

    public static GoogleUser fromOauth2User(OAuth2User user){
        GoogleUser googleUser = GoogleUser.builder()
                .email(user.getName())
                .name(user.getAttributes().get("name").toString())
                .givenName(user.getAttributes().get("given_name").toString())
                .familyName(user.getAttributes().get("family_name").toString())
                .pictureUrl(user.getAttributes().get("picture").toString())
                .uuid(stringToUUID(user.getName()))
                .build();
        return googleUser;
    }


    private static UUID stringToUUID(String string){
        long[] hash = LongTupleHashFunction.xx128().hashChars(string);
        return new UUID(hash[0], hash[1]);
    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
