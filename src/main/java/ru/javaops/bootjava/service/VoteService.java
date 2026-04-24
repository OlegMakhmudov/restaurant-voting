package ru.javaops.bootjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.exception.IllegalVoteException;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.MenuRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.repository.UserRepository;
import ru.javaops.bootjava.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private static final LocalTime DEADLINE = LocalTime.of(11, 0);

    public Vote getTodayVote(int userId) {
        return voteRepository.findByUserIdAndDate(userId, LocalDate.now()).orElse(null);
    }

    public boolean hasUserVotedToday(int userId) {
        return voteRepository.existsByUserIdAndDate(userId, LocalDate.now());
    }

    public Vote vote(int userId, int restaurantId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        boolean hasTodayMenu = menuRepository.findAllByRestaurantIdAndDate(restaurantId, today).isEmpty();
        if (hasTodayMenu) {
            throw new IllegalVoteException("Restaurant has no menu for today");
        }

        return voteRepository.findByUserIdAndDate(userId, today)
                .map(existingVote -> {
                    if (existingVote.getTime().isBefore(DEADLINE)) {
                        existingVote.setRestaurant(restaurant);
                        existingVote.setTime(now);
                        return voteRepository.save(existingVote);
                    } else {
                        throw new IllegalVoteException("Cannot change vote after 11:00");
                    }
                })
                .orElseGet(() -> voteRepository.save(new Vote(null, user, restaurant, today, now)));
    }
}

