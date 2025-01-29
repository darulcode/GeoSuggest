package git.darul.geocitysuggest.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoSuggestResponse {
    private String name;
    private Double latitude;
    private Double longitude;
    private Double score;
}
