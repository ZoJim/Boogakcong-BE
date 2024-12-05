package boogakcong.domain.cafe._notification.repository;

import boogakcong.domain.cafe._notification.entity.CafeNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeNotificationRepository extends JpaRepository<CafeNotification, Long> {
    // 가장 최근에 등록된 알림을 조회하는 메소드
    Optional<CafeNotification> findFirstByCafeIdOrderByCreatedAtDesc(Long cafeId);

    Optional<CafeNotification> findByCafeIdAndId(Long cafeId, Long id);

    void deleteByCafeIdAndId(Long cafeId, Long notificationId);

    boolean existsByCafeIdAndId(Long cafeId, Long notificationId);
}
