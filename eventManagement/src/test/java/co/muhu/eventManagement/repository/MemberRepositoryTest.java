package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void existsById() {
        Member newMember = Member.builder()
                .email("newMember@gamil.com")
                .firstName("Test")
                .lastName("Member")
                .build();

        Member savedMember = memberRepository.save(newMember);

        assertThat(savedMember).satisfies(
                member -> {
                    assertThat(member).isNotNull();
                    assertThat(member.getId()).isNotNull();
                    assertThat(member.getEmail()).isEqualTo(newMember.getEmail());
                }
        );

        boolean result = memberRepository.existsById(savedMember.getId());

        assertThat(result).isTrue();
    }

    @Test
    void findByEmail() {
        Member newMember = Member.builder()
                .email("newMember@gamil.com")
                .firstName("Test")
                .lastName("Member")
                .build();

        Member savedMember = memberRepository.save(newMember);

        assertThat(savedMember).satisfies(
                member -> {
                    assertThat(member).isNotNull();
                    assertThat(member.getId()).isNotNull();
                    assertThat(member.getEmail()).isEqualTo(newMember.getEmail());
                }
        );

        Optional<Member> result = memberRepository.findByEmail(savedMember.getEmail());

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(
                member -> assertThat(member.getEmail()).isEqualTo(savedMember.getEmail()));
    }

    @Test
    void existsByEmail() {
        Member newMember = Member.builder()
                .email("newMember@gamil.com")
                .firstName("Test")
                .lastName("Member")
                .build();

        Member savedMember = memberRepository.save(newMember);

        assertThat(savedMember).satisfies(
                member -> {
                    assertThat(member).isNotNull();
                    assertThat(member.getId()).isNotNull();
                    assertThat(member.getEmail()).isEqualTo(newMember.getEmail());
                }
        );

        boolean result = memberRepository.existsByEmail(savedMember.getEmail());

        assertThat(result).isTrue();
    }

    @Test
    void deleteByEmail() {
        Member newMember = Member.builder()
                .email("newMember@gamil.com")
                .firstName("Test")
                .lastName("Member")
                .build();

        Member savedMember = memberRepository.save(newMember);

        assertThat(savedMember).satisfies(
                member -> {
                    assertThat(member).isNotNull();
                    assertThat(member.getId()).isNotNull();
                    assertThat(member.getEmail()).isEqualTo(newMember.getEmail());
                }
        );

        memberRepository.deleteByEmail(savedMember.getEmail());

        boolean result = memberRepository.existsById(savedMember.getId());

        assertThat(result).isFalse();
    }
}