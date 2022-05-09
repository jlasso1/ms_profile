package co.com.monks.model.twitter.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class TwitterResponse {
    private List<TwitterModel> data;

    @JsonCreator
    public TwitterResponse(List<TwitterModel> res) {
        this.data = res;
    }

}


