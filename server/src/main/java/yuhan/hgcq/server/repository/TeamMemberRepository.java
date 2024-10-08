package yuhan.hgcq.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yuhan.hgcq.server.domain.Member;
import yuhan.hgcq.server.domain.Team;
import yuhan.hgcq.server.domain.TeamMember;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepository {
    @PersistenceContext
    private final EntityManager em;

    /**
     * 그룹에 회원 저장
     *
     * @param teamMember 그룹 회원
     */
    public void save(TeamMember teamMember) {
        em.persist(teamMember);
    }

    /**
     * 그룹에 회원 삭제
     *
     * @param teamMember 그룹 회원
     */
    public void delete(TeamMember teamMember) {
        em.remove(teamMember);
    }

    /**
     * 그룹 회원 관계 조회
     *
     * @param member 회원
     * @param team   그룹
     * @return 그룹 회원
     */
    public TeamMember findOne(Member member, Team team) {
        try {
            return em.createQuery("select tm from TeamMember tm where tm.member = :member and tm.team = :team", TeamMember.class)
                    .setParameter("member", member)
                    .setParameter("team", team)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 그룹에 회원 정보 수정
     *
     * @param teamMember 그룹 회원
     */
    public void update(TeamMember teamMember) {
        em.merge(teamMember);
    }

    /**
     * 그룹 삭제
     *
     * @param team 그룹
     */
    public void deleteAll(Team team) {
        em.createQuery("delete from TeamMember tm where tm.team = :team")
                .setParameter("team", team)
                .executeUpdate();
    }

    /**
     * 회원이 가진 그룹 리스트 조회
     *
     * @param member 회원
     * @return 그룹 리스트
     */
    public List<Team> findAll(Member member) {
        return em.createQuery("select tm.team from TeamMember tm where tm.member = :member order by tm.team.name asc", Team.class)
                .setParameter("member", member)
                .getResultList();
    }

    /**
     * 회원이 가진 그룹 리스트 이름으로 조회
     *
     * @param member 회원
     * @param name   그룹 이름
     * @return 그룹 리스트
     */
    public List<Team> findByName(Member member, String name) {
        return em.createQuery("select tm.team from TeamMember tm where tm.member = :member and tm.team.name like :name order by tm.team.name asc", Team.class)
                .setParameter("member", member)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    /**
     * 그룹에 속한 회원 리스트 조회
     *
     * @param team 그룹
     * @return 회원 리스트
     */
    public List<Member> findByTeam(Team team) {
        return em.createQuery("select tm.member from TeamMember tm where tm.team = :team order by tm.isAdmin desc, tm.member.name asc", Member.class)
                .setParameter("team", team)
                .getResultList();
    }

    /**
     * 그룹에 속한 관리자 리스트 조회
     *
     * @param team 그룹
     * @return 관리자 리스트
     */
    public List<Member> findAdminByTeam(Team team) {
        return em.createQuery("select tm.member from TeamMember tm where tm.team = :team and tm.isAdmin = true", Member.class)
                .setParameter("team", team)
                .getResultList();
    }
}
