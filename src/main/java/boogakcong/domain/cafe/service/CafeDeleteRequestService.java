package boogakcong.domain.cafe.service;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.domain.cafe.entity.CafeDeleteRequest;
import boogakcong.domain.cafe.repository.CafeDeleteRequestRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeDeleteRequestService {
    private final CafeDeleteRequestRepository cafeDeleteRequestRepository;
    private final CafeOwnerService cafeOwnerService;
    private final CafeService cafeService;

    @Transactional
    public void requestCafeDelete(Long ownerId, Long reasonId) {
        CafeOwner owner = cafeOwnerService.findByOwnerId(ownerId);

        cafeDeleteRequestRepository.findByCafeIdAndRequestStatus(
                owner.getCafeId(),
                CafeDeleteRequest.RequestStatus.REQUESTED
        ).ifPresent(request -> {
            throw new BusinessException(BusinessError.CAFE_DELETE_REQUEST_ALREADY_EXISTS);
        });

        cafeDeleteRequestRepository.save(
                CafeDeleteRequest
                        .builder()
                        .cafeId(owner.getCafeId())
                        .deleteReason(CafeDeleteRequest.DeleteReason.fromId(reasonId))
                        .requestStatus(CafeDeleteRequest.RequestStatus.REQUESTED)
                        .build()
        );
    }

    @Transactional
    public void acceptCafeDelete(Long requestId, boolean accept) {
        // request 가져오기
        CafeDeleteRequest cafeDeleteRequest = this.findById(requestId);

        // 카페 삭제 요청 상태인가?
        if (cafeDeleteRequest.getRequestStatus() != CafeDeleteRequest.RequestStatus.REQUESTED) {
            throw new BusinessException(BusinessError.CAFE_DELETE_REQUEST_NOT_FOUND);
        }

        // 카페 삭제 요청 승인
        if (accept) {
            cafeService.deleteCafe(cafeDeleteRequest.getCafeId());
            cafeDeleteRequest.setRequestStatus(CafeDeleteRequest.RequestStatus.ACCEPTED);
            cafeOwnerService.deleteCafeOwner(cafeDeleteRequest.getCafeId());
        } else {
            cafeDeleteRequest.setRequestStatus(CafeDeleteRequest.RequestStatus.REJECTED);
        }
    }

    private CafeDeleteRequest findById(Long requestId) {
        return cafeDeleteRequestRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(BusinessError.CAFE_DELETE_REQUEST_NOT_FOUND));
    }

    public List<CafeDeleteRequestResponse> getCafeDeleteRequests() {
        List<CafeDeleteRequest> all = cafeDeleteRequestRepository.findAll();
        return all.stream()
                .map(CafeDeleteRequestResponse::from)
                .toList();
    }

    public record CafeDeleteRequestResponse(
            Long id,
            Long cafeId,
            CafeDeleteRequest.RequestStatus requestStatus,
            CafeDeleteRequest.DeleteReason deleteReason
    ) {
        public static CafeDeleteRequestResponse from(CafeDeleteRequest request) {
            return new CafeDeleteRequestResponse(
                    request.getId(),
                    request.getCafeId(),
                    request.getRequestStatus(),
                    request.getDeleteReason()
            );
        }
    }
}
