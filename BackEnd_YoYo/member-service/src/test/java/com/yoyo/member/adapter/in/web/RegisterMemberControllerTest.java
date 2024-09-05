//package com.yoyo.member.adapter.in.web;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yoyo.member.domain.Member;
//import java.time.LocalDate;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class RegisterMemberControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Test
//    public void registerMember() throws Exception {
//        RegisterMemberRequest request = new RegisterMemberRequest("name", "01054513115", "ea2d31dz",
//                                                                  LocalDate.now());
//
//        Member member = Member.generateMember(
//                new Member.MemberId(1L),
//                new Member.MemberName("name"),
//                new Member.MemberPassword("ea2d31dz"),
//                new Member.MemberPhoneNumber("01054513115"),
//                new Member.MemberBirthDay(LocalDate.now())
//        );
//
//        mockMvc.perform(
//                       MockMvcRequestBuilders.post("/member/register")
//                                             .contentType(MediaType.APPLICATION_JSON)
//                                             .content(mapper.writeValueAsString(request))
//               )
//               .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//}