package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("팀A");

            em.persist(team);

            // 조회시
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            Member member2 = new Member();
            member.setAge(20);

            em.persist(member);
            em.persist(member2);

            member.addTeam(team);

            System.out.println("===================");

            String query = "select m from Member m" +
                    "where m.age > (select avg(m2.age) from Member m2)";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("resultList.get(0).toString() = " + resultList.get(0).getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }
}
