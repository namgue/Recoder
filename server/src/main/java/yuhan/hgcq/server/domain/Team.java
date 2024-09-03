package yuhan.hgcq.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void validatedOwner() {
        if (this.owner == null) {
            throw new IllegalStateException("Owner is null");
        }
    }

    public Team(Member owner, String name) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        this.owner = owner;
        this.name = name;
    }
}
