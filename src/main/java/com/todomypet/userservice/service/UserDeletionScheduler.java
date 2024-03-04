package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDeletionScheduler {

    private final UserRepository userRepository;


    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 0시에 실행
    public void deleteUsers() {
        log.info(">>> 회원 삭제 스케줄러 실행");
        List<User> userToDelete = userRepository.findByDeletedTrue();
        for (User user : userToDelete) {
            log.info(">>> 회원 삭제 처리:" + user.getId());
            // todo: 카테고리 삭제
            // todo: 투두 삭제
            // todo: 게시글 삭제
            // todo: 댓글 삭제
            userRepository.deleteByUserId(user.getId());
        }
    }
}
