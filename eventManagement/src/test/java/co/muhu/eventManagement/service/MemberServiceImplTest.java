package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MemberServiceImplTest {
    private MemberServiceImpl memberServiceTest;
    @Mock
    private MemberRepository memberRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        memberServiceTest=new MemberServiceImpl(memberRepositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllMembers() {
        memberServiceTest.getAllMembers();
        verify(memberRepositoryMock).findAll();
    }

    @Test
    void getMemberById() {
        long memberId=1;
        memberServiceTest.getMemberById(memberId);
        verify(memberRepositoryMock).findById(memberId);
    }

    @Test
    void createMember() {
        Member newMember=new Member();
        memberServiceTest.createMember(newMember);
        verify(memberRepositoryMock).save(newMember);
    }

    @Test
    void updateMemberById() {
        Member exitingMember = Member.builder().email("exitingMember@gmail.com").id((long)1).build();
        Member updateMember = Member.builder().email("updatedMember@gmail.com").id(exitingMember.getId()).build();

        when(memberRepositoryMock.findById(exitingMember.getId())).thenReturn(Optional.of(exitingMember));
        when(memberRepositoryMock.save(updateMember)).thenReturn(updateMember);

        Optional<Member> result = memberServiceTest.updateMemberById(exitingMember.getId(), updateMember);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(member -> {
            assertThat(member.getId()).isEqualTo(exitingMember.getId());
            assertThat(member.getEmail()).isEqualTo(updateMember.getEmail());

        });
        verify(memberRepositoryMock).findById(exitingMember.getId());
        verify(memberRepositoryMock).save(any(Member.class));
    }
    @Test
    void updateMemberByIdWhenMemberNotPresent() {
        Member exitingMember = Member.builder().email("exitingMember@gmail.com").id((long)1).build();
        Member updateMember = Member.builder().email("updatedMember@gmail.com").id(exitingMember.getId()).build();

        when(memberRepositoryMock.findById(exitingMember.getId())).thenReturn(Optional.empty());

        Optional<Member> result = memberServiceTest.updateMemberById(exitingMember.getId(), updateMember);

        assertThat(result).isNotPresent();

        verify(memberRepositoryMock).findById(exitingMember.getId());
    }

    @Test
    void deleteMemberById() {
        long memberId =1 ;

        when(memberRepositoryMock.existsById(memberId)).thenReturn(true);

        boolean result = memberServiceTest.deleteMemberById(memberId);

        assertThat(result).isTrue();

        verify(memberRepositoryMock).deleteById(memberId);
    }
    @Test
    void deleteMemberByIdWhenMemberNotPresent() {
        long memberId =1 ;

        when(memberRepositoryMock.existsById(memberId)).thenReturn(false);

        boolean result = memberServiceTest.deleteMemberById(memberId);

        assertThat(result).isFalse();
    }

    @Test
    void getMemberByEmail() {
        String memberEmail="memberEmail@gmail.com";

        memberServiceTest.getMemberByEmail(memberEmail);

        verify(memberRepositoryMock).findByEmail(memberEmail);
    }

    @Test
    void deleteMemberByEmail() {
        String memberEmail ="memberEmail@gmail.com";

        when(memberRepositoryMock.existsByEmail(memberEmail)).thenReturn(true);

        boolean result = memberServiceTest.deleteMemberByEmail(memberEmail);

        assertThat(result).isTrue();

        verify(memberRepositoryMock).deleteByEmail(memberEmail);
    }
    @Test
    void deleteMemberByEmailWhenMemberNotPresent() {
        String memberEmail ="memberEmail@gmail.com";

        when(memberRepositoryMock.existsByEmail(memberEmail)).thenReturn(false);

        boolean result = memberServiceTest.deleteMemberByEmail(memberEmail);

        assertThat(result).isFalse();
    }
    @Test
    void updateMemberByEmail() {
        Member exitingMember = Member.builder().email("exitingMember@gmail.com").id((long)1).firstName("Old one").build();
        Member updateMember = Member.builder().email(exitingMember.getEmail()).id(exitingMember.getId()).firstName("New one").build();

        when(memberRepositoryMock.findByEmail(exitingMember.getEmail())).thenReturn(Optional.of(exitingMember));
        when(memberRepositoryMock.save(updateMember)).thenReturn(updateMember);

        Optional<Member> result = memberServiceTest.updateMemberByEmail(exitingMember.getEmail(), updateMember);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(member -> {
            assertThat(member.getId()).isEqualTo(exitingMember.getId());
            assertThat(member.getEmail()).isEqualTo(exitingMember.getEmail());
            assertThat(member.getFirstName()).isEqualTo(updateMember.getFirstName());
        });
        verify(memberRepositoryMock).findByEmail(exitingMember.getEmail());
        verify(memberRepositoryMock).save(any(Member.class));
    }
    @Test
    void updateMemberByEmailWhenMemberNotPresent() {
        Member exitingMember = Member.builder().email("exitingMember@gmail.com").id((long)1).firstName("Old one").build();
        Member updateMember = Member.builder().email(exitingMember.getEmail()).id(exitingMember.getId()).firstName("New one").build();

        when(memberRepositoryMock.findByEmail(exitingMember.getEmail())).thenReturn(Optional.empty());

        Optional<Member> result = memberServiceTest.updateMemberByEmail(exitingMember.getEmail(), updateMember);

        assertThat(result).isNotPresent();

        verify(memberRepositoryMock).findByEmail(exitingMember.getEmail());
    }
}