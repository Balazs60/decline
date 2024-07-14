package com.github.balazs60.decline.model.members;

import com.github.balazs60.decline.model.Task;
import com.github.balazs60.decline.model.UnSuccessfulTask;
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
    @OneToMany
    @JoinTable(name = "member_wrong_task",
            joinColumns = @JoinColumn(name ="un_successful_task_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<UnSuccessfulTask> unSuccessfulTasks = new ArrayList<>();

    public Role getRole() {
        return role;
    }
}
