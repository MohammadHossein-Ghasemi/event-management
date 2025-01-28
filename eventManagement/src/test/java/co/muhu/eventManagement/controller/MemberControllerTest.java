package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.model.MemberDto;
import co.muhu.eventManagement.model.MemberRegistrationDto;
import co.muhu.eventManagement.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockitoBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllMembers() throws Exception {

        given(memberService.getAllMembers()).willReturn(List.of());

        mockMvc.perform(get(MemberController.MEMBER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));

    }

    @Test
    void getMemberById() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        given(memberService.getMemberById(any(Long.class))).willReturn(Optional.of(memberDto));

        mockMvc.perform(get(MemberController.MEMBER_PATH_ID,memberDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(memberDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(memberDto.getEmail())));
    }

    @Test
    void getMemberByIdWhenMemberNotFound() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        given(memberService.getMemberById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(MemberController.MEMBER_PATH_ID,memberDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void saveMember() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        MemberRegistrationDto memberRDto = MemberRegistrationDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Controller")
                .build();

        given(memberService.createMember(memberRDto)).willReturn(memberDto);

        mockMvc.perform(post(MemberController.MEMBER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(memberDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(memberDto.getEmail())));
    }

    @Test
    void updateMemberById() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        MemberRegistrationDto memberRDto = MemberRegistrationDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Controller")
                .build();

        given(memberService.updateMemberById(any(Long.class),any(Member.class))).willReturn(Optional.of(memberDto));

        mockMvc.perform(put(MemberController.MEMBER_PATH_ID,memberDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRDto)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(memberDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(memberDto.getEmail())));
    }

    @Test
    void updateMemberByIdWhenMemberNotFound() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        MemberRegistrationDto memberRDto = MemberRegistrationDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Controller")
                .build();

        given(memberService.updateMemberById(any(Long.class),any(Member.class))).willReturn(Optional.empty());

        mockMvc.perform(put(MemberController.MEMBER_PATH_ID,memberDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRDto)))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteMemberById() throws Exception {

        given(memberService.deleteMemberById(any(Long.class))).willReturn(true);

        mockMvc.perform(delete(MemberController.MEMBER_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteMemberByIdWhenMemberNotFound() throws Exception {

        given(memberService.deleteMemberById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete(MemberController.MEMBER_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getMemberByEmail() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        given(memberService.getMemberByEmail(any(String.class))).willReturn(Optional.of(memberDto));

        mockMvc.perform(get(MemberController.MEMBER_PATH_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberEmail",memberDto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(memberDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(memberDto.getEmail())));
    }

    @Test
    void getMemberByEmailWhenMemberNotFound() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        given(memberService.getMemberByEmail(any(String.class))).willReturn(Optional.empty());

        mockMvc.perform(get(MemberController.MEMBER_PATH_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberEmail",memberDto.getEmail()))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteMemberByEmail() throws Exception {

        given(memberService.deleteMemberByEmail(any(String.class))).willReturn(true);

        mockMvc.perform(delete(MemberController.MEMBER_PATH_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberEmail"," "))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteMemberByEmailWhenMemberNotFound() throws Exception {

        given(memberService.deleteMemberByEmail(any(String.class))).willReturn(false);

        mockMvc.perform(delete(MemberController.MEMBER_PATH_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberEmail"," "))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }


    @Test
    void updateMemberByEmail() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        MemberRegistrationDto memberRDto = MemberRegistrationDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Controller")
                .build();

        given(memberService.updateMemberByEmail(any(String.class),any(Member.class))).willReturn(Optional.of(memberDto));

        mockMvc.perform(put(MemberController.MEMBER_PATH_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRDto))
                .param("memberEmail"," "))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(memberDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(memberDto.getEmail())));
    }

    @Test
    void updateMemberByEmailWhenMemberNotFound() throws Exception {

        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        MemberRegistrationDto memberRDto = MemberRegistrationDto.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Controller")
                .build();

        given(memberService.updateMemberByEmail(any(String.class),any(Member.class))).willReturn(Optional.empty());

        mockMvc.perform(put(MemberController.MEMBER_PATH_EMAIL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRDto))
                .param("memberEmail"," "))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}