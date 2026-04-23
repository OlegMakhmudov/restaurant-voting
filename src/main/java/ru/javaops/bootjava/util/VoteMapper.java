package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.web.dto.VoteTo;

@UtilityClass
public class VoteMapper {

    public static VoteTo toTo(Vote vote) {
        return new VoteTo(
                vote.getId(),
                vote.getRestaurant().getId(),
                vote.getRestaurant().getName(),
                vote.getDate(),
                vote.getTime()
        );
    }
}

