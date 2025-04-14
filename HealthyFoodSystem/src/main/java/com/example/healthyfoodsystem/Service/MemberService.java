package com.example.healthyfoodsystem.Service;

import com.example.healthyfoodsystem.Model.Member;
import com.example.healthyfoodsystem.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public void addMember(Member member){
        memberRepository.save(member);
    }

    public Boolean update(Integer id, Member member){
        Member oldMember = memberRepository.findMemberById(id);

        if (oldMember == null){
            return false;
        }

        oldMember.setName(member.getName());
        oldMember.setEmail(member.getEmail());
        oldMember.setPhone(member.getPhone());
        oldMember.setCity(member.getCity());
        oldMember.setAge(member.getAge());
        oldMember.setGender(member.getGender());
        oldMember.setHeight(member.getHeight());
        oldMember.setWeight(member.getWeight());

        memberRepository.save(oldMember);

        return true;
    }

    public Boolean delete(Integer id){
        Member member = memberRepository.findMemberById(id);

        if (member == null){
            return false;
        }

        memberRepository.delete(member);
        return true;
    }


}
