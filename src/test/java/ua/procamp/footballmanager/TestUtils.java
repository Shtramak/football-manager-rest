package ua.procamp.footballmanager;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Position;

import java.time.LocalDate;

public class TestUtils {
    public static Player generatePlayerWithId(Long id) {
        Player player = new Player();
        player.setId(id);
        player.setFirstName("FirstName-" + id);
        player.setLastName("LastName-" + id);
        player.setBirthday(LocalDate.now());
        player.setPosition(Position.DEFENDER);
        return player;
    }
}
