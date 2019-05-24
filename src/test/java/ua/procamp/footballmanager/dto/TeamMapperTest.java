package ua.procamp.footballmanager.dto;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ua.procamp.footballmanager.TestUtils;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static ua.procamp.footballmanager.TestUtils.generatePlayerWithIdAndNoTeam;
import static ua.procamp.footballmanager.TestUtils.generateTeamWithIdAndNoPlayers;
import static ua.procamp.footballmanager.TestUtils.mapTeamFieldsOnTeamDtoFields;

class TeamMapperTest {
    @Test
    void teamToTeamDtoWithoutCaptainReturnsTeamDtoWithNullCaptain() {
        Team team = generateTeamWithIdAndNoPlayers(1);
        TeamDto teamDto = new TeamDto();
        mapTeamFieldsOnTeamDtoFields(team, teamDto);
        TeamDto actual = TeamMapper.teamToTeamDto(team);
        assertThat(actual, samePropertyValuesAs(teamDto));
    }

    @Test
    void teamToTeamDtoWithCaptainReturnsTeamDtoWithCaptainDto() {
        Team team = generateTeamWithIdAndNoPlayers(1);
        Player captain = generatePlayerWithIdAndNoTeam(1);
        team.setCaptain(captain);
        TeamDto teamDto = new TeamDto();
        mapTeamFieldsOnTeamDtoFields(team, teamDto);
        TeamDto actual = TeamMapper.teamToTeamDto(team);
        assertThat(actual.getCaptain(), Matchers.samePropertyValuesAs(teamDto.getCaptain()));
    }

    @Test
    void teamDtoToTeamWithoutCaptainReturnsTeamWithNullCaptain() {
        Team team = generateTeamWithIdAndNoPlayers(1);
        TeamDto teamDto = new TeamDto();
        mapTeamFieldsOnTeamDtoFields(team, teamDto);
        Team actual = TeamMapper.teamDtoToTeam(teamDto);
        assertThat(actual, Matchers.samePropertyValuesAs(team));
    }

    @Test
    void teamDtoToTeamWithCaptainReturnsTeamWithCaptain() {
        Team team = generateTeamWithIdAndNoPlayers(1);
        Player captain = generatePlayerWithIdAndNoTeam(1);
        team.setCaptain(captain);
        TeamDto teamDto = new TeamDto();
        mapTeamFieldsOnTeamDtoFields(team, teamDto);
        Team actual = TeamMapper.teamDtoToTeam(teamDto);
        assertThat(actual.getCaptain(), samePropertyValuesAs(captain));
    }

    @Test
    void listTeamToListTeamDto() {
        Team team1 = generateTeamWithIdAndNoPlayers(1);
        Team team2 = generateTeamWithIdAndNoPlayers(2);
        Team team3 = generateTeamWithIdAndNoPlayers(3);
        List<Team> teams = List.of(team1, team2, team3);
        List<TeamDto> teamsDto = TeamMapper.listTeamToListTeamDto(teams);
        assertThat(teams.size(), equalTo(teamsDto.size()));
        for (int i = 0; i < teams.size(); i++) {
            TeamDto teamDto = new TeamDto();
            TestUtils.mapTeamFieldsOnTeamDtoFields(teams.get(i), teamDto);
            TeamDto actual = teamsDto.get(i);
            assertThat(actual, samePropertyValuesAs(teamDto));
        }
    }
}