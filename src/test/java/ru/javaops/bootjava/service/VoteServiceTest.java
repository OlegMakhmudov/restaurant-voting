package ru.javaops.bootjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.exception.IllegalVoteException;
import ru.javaops.bootjava.model.*;
import ru.javaops.bootjava.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    private User testUser;
    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(new User(null, "Test User", "test@example.com", "password", true, Set.of(Role.USER)));
        testRestaurant = restaurantRepository.save(new Restaurant(null, "Test Restaurant", "Test Address"));

        Menu menu = new Menu();
        menu.setRestaurant(testRestaurant);
        menu.setDate(LocalDate.now());
        menu.setDishName("Test Dish");
        menu.setPrice(BigDecimal.valueOf(100));
        menuRepository.save(menu);
    }

    @Test
    void vote_shouldCreateNewVote_whenUserNotVotedToday() {
        Vote vote = voteService.vote(testUser.getId(), testRestaurant.getId());
        assertThat(vote).isNotNull();
        assertThat(vote.getUser().getId()).isEqualTo(testUser.getId());
        assertThat(vote.getRestaurant().getId()).isEqualTo(testRestaurant.getId());
        assertThat(vote.getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void vote_shouldThrowException_whenRestaurantHasNoMenuToday() {
        Restaurant restaurantWithoutMenu = restaurantRepository.save(
                new Restaurant(null, "No Menu Restaurant", "Some Address")
        );
        assertThrows(IllegalVoteException.class, () -> voteService.vote(testUser.getId(), restaurantWithoutMenu.getId()));
    }

    @Test
    void hasUserVotedToday_shouldReturnTrue_whenUserHasVote() {
        voteService.vote(testUser.getId(), testRestaurant.getId());
        boolean hasVoted = voteService.hasUserVotedToday(testUser.getId());
        assertThat(hasVoted).isTrue();
    }

    @Test
    void hasUserVotedToday_shouldReturnFalse_whenUserHasNoVote() {
        boolean hasVoted = voteService.hasUserVotedToday(testUser.getId());
        assertThat(hasVoted).isFalse();
    }
}