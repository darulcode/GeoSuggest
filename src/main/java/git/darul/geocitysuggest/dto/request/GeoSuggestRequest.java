package git.darul.geocitysuggest.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoSuggestRequest {
    private String query;
    private Double latitude;
    private Double longitude;
}
