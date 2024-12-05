package boogakcong.domain.cafe._notification.service;

import boogakcong.domain.cafe._notification.dto.request.NotificationRequest;
import boogakcong.domain.cafe._notification.entity.CafeNotification;
import boogakcong.domain.cafe._notification.repository.CafeNotificationRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeNotificationService {
    private final CafeNotificationRepository cafeNotificationRepository;

    @Transactional
    public void updateNotification(NotificationRequest request) {
        cafeNotificationRepository.save(CafeNotification.builder()
                .cafeId(request.cafeId())
                .content(request.content())
                .build());
    }

    public boolean isNotificationExist(Long cafeId, Long notificationId) {
        return cafeNotificationRepository.existsByCafeIdAndId(cafeId, notificationId);
    }

    public CafeNotification getNotification(Long cafeId, Long notificationId) {
        return cafeNotificationRepository.findByCafeIdAndId(cafeId, notificationId)
                .orElseThrow(() -> new BusinessException(BusinessError.NOTIFICATION_NOT_FOUND));
    }
}
