package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsById(@NonNull Long id);
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);

}
