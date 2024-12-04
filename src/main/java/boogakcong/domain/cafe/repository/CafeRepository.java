package boogakcong.domain.cafe.repository;

import boogakcong.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    // 도로명 주소와 이름으로 단일 카페 검색
    Optional<Cafe> findByRoadAddressAndName(String roadAddress, String name);

    // 도로명 주소와 이름 목록으로 여러 카페 검색
    @Query("SELECT c FROM Cafe c WHERE c.roadAddress IN :roadAddresses AND c.name IN :names")
    List<Cafe> findAllByRoadAddressAndName(@Param("roadAddresses") List<String> roadAddresses,
                                           @Param("names") List<String> names);
}
