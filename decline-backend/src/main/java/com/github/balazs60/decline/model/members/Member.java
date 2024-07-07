package com.github.balazs60.decline.model.members;

import com.github.balazs60.decline.model.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private int numberOfGoodAnswers;
    private int numberOfWrongAnswers;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "unsuccessful_questions", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "question")
    private List<String> unsuccessfulQuestions = new ArrayList<>();

    public Role getRole() {
        return role;
    }
}
