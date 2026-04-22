package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id = :userId AND v.date = :date")
    Optional<Vote> findByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.id = :userId AND v.date = :date")
    int deleteByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(v) > 0 FROM Vote v WHERE v.user.id = :userId AND v.date = :date")
    boolean existsByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Query("SELECT v.time FROM Vote v WHERE v.user.id = :userId AND v.date = :date")
    Optional<LocalTime> findTimeByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);
}