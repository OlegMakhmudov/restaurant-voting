package ru.javaops.bootjava.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.service.MenuService;
import ru.javaops.bootjava.service.RestaurantService;
import ru.javaops.bootjava.service.VoteService;
import ru.javaops.bootjava.util.MenuMapper;
import ru.javaops.bootjava.util.VoteMapper;
import ru.javaops.bootjava.web.dto.MenuTo;
import ru.javaops.bootjava.web.dto.RestaurantTo;
import ru.javaops.bootjava.web.dto.VoteTo;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserVoteController {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final VoteService voteService;

    @GetMapping("/restaurants")
    public Page<RestaurantTo> getAll(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return restaurantService.getAll(pageable);
    }

    @GetMapping("/restaurants/today")
    public List<MenuTo> getTodayMenus() {
        return menuService.getTodayMenu().stream()
                .map(MenuMapper::toTo)
                .toList();
    }

    @PostMapping("/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteTo vote(@RequestParam int userId, @RequestParam int restaurantId) {
        Vote vote = voteService.vote(userId, restaurantId);
        return VoteMapper.toTo(vote);
    }

    @GetMapping("/votes/today")
    public VoteTo getTodayVote(@RequestParam int userId) {
        Vote vote = voteService.getTodayVote(userId);
        return vote != null ? VoteMapper.toTo(vote) : null;
    }
}