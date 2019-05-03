package ua.procamp.footballmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TeamDto {
    private Long teamId;
    private String teamName;
    private PlayerDto captain;
}