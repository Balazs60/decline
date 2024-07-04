package com.github.balazs60.decline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.balazs60.decline.model.members.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.name = :name")
    Member findMemberByName(@Param("name") String name);
}
