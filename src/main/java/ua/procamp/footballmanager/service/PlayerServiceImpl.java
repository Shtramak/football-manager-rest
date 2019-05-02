package ua.procamp.footballmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dao.PlayerRepository;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Player> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Player save(Player player) {
        return repository.save(player);
    }

    @Override
    public void update(Player player) {
        Player managedPlayer = repository.findById(player.getId()).orElseThrow(NoSuchElementException::new);
        managedPlayer.setFirstName(player.getFirstName());
        managedPlayer.setLastName(player.getLastName());
        managedPlayer.setPosition(player.getPosition());
        managedPlayer.setBirthday(player.getBirthday());
        managedPlayer.setTeam(player.getTeam());
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> findPlayersByTeam(Team team) {
        return repository.findByTeam(team.getId());
    }
}
