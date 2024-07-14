package com.github.balazs60.decline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.balazs60.decline.model.members.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unsuccessful_task" )
public class UnSuccessfulTask {
    @Id
    @GeneratedValue
    private long id;
    private String question;
    private String inflectedAdjective;
    private String inflectedArticle;
    @ElementCollection
    private List<String> articleAnswerOptions = new ArrayList<>();
    @ElementCollection
    private List<String> adjectiveAnswerOptions = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @JsonIgnore
    private Member member;

}
