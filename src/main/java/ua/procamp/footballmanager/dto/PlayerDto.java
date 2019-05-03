package ua.procamp.footballmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerDto {
    private Long playerId;
    private String firstName;
    private String lastName;
    private String position;
    private String birthday;
    private String teamName;
}
