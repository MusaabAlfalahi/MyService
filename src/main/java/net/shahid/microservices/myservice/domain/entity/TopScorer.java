package net.shahid.microservices.myservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopScorer {
    String name;

    String number;

    String image;

    String team;

    Integer goal;

    Integer assist;

    Integer play;

    Integer totalShots;

    Integer shotsAccuracy;
}
