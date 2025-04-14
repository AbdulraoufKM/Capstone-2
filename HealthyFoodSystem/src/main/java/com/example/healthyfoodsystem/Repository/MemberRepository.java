package com.example.healthyfoodsystem.Repository;

import com.example.healthyfoodsystem.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {

    Member findMemberById(Integer id);
}
