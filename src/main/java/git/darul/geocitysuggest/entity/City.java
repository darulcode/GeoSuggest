package git.darul.geocitysuggest.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class City {
    private String name;
    private Double latitude;
    private Double longitude;
    private String Country;
    private String admin1;
}
