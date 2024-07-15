package com.github.balazs60.decline.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

}
