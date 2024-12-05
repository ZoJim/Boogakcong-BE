package boogakcong.domain.cafe.repository;

import boogakcong.domain.cafe.entity.CafeDeleteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeDeleteRequestRepository extends JpaRepository<CafeDeleteRequest, Long> {
    Page<CafeDeleteRequest> findAll(Pageable pageable);

    Optional<Object> findByCafeIdAndRequestStatus(Long cafeId, CafeDeleteRequest.RequestStatus requestStatus);
}
