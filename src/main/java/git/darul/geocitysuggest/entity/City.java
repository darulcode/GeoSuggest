package git.darul.geocitysuggest.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class City {
    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String Country;
    private String admin1;
}
