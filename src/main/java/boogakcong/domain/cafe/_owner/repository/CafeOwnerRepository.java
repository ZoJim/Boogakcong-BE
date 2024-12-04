package boogakcong.domain.cafe._owner.repository;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeOwnerRepository extends JpaRepository<CafeOwner, Long> {
    boolean existsByCafeId(Long cafeId);

    List<CafeOwner> findAllByAllocationStatusOrderByCreatedAtDesc(CafeOwner.AllocationStatus allocationStatus);
}

