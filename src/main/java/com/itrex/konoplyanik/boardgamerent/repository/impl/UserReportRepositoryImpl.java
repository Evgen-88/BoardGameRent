package com.itrex.konoplyanik.boardgamerent.repository.impl;

import com.itrex.konoplyanik.boardgamerent.entity.report.RequestParameters;
import com.itrex.konoplyanik.boardgamerent.entity.report.UserReport;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.UserReportRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserReportRepositoryImpl implements UserReportRepository {

    private static final String USER_REPORT_QUERY = ""
            + "SELECT    u.id, "
            + "          u.login, "
            + "          Count(o.id)        AS quantity, "
            + "          Sum(o.total_price) AS sum, "
            + "          Count(r.id)        AS rent_quantity, "
            + "          Sum(r.price)       AS rent_sum, "
            + "          Count(p.id)        AS purchase_quantity, "
            + "          Sum(p.price)       AS purchase_sum "
            + "FROM      users u "
            + "JOIN      orders o           ON u.id = o.user_id "
            + "LEFT JOIN rent r             ON o.id = r.order_id "
            + "LEFT JOIN purchase p         ON o.id = p.order_id "
            + "WHERE     o.status = 'confirmed' "
            + "AND       o.order_date BETWEEN :start AND :end "
            + "GROUP BY  u.id, "
            + "          o.id "
            + "ORDER BY  %s %s, u.login ASC "
            + "LIMIT     :pageNumber, :pageSize";

    private final SessionFactory sessionFactory;

    public UserReportRepositoryImpl(EntityManagerFactory entityManager) {
        this.sessionFactory = entityManager.unwrap(SessionFactory.class);
    }

    @Override
    public List<UserReport> getUserReport(RequestParameters requestParameters) throws RepositoryException {
        String query = USER_REPORT_QUERY;
        query = String.format(query, requestParameters.getParameter(), requestParameters.getDirection());
        try(Session session = sessionFactory.openSession()) {
            List<Object[]> rows = session.createSQLQuery(query)
                    .setParameter("start", requestParameters.getStart())
                    .setParameter("end", requestParameters.getEnd())
                    .setParameter("pageNumber", requestParameters.getPageNumber())
                    .setParameter("pageSize", requestParameters.getPageSize())
                    .list();
            return parseReport(rows);
        } catch (Exception ex) {
            throw new RepositoryException("Error: Report failed", ex);
        }
    }

    private List<UserReport> parseReport(List<Object[]> rows) {
        return rows.stream()
                .map(row -> UserReport.builder()
                        .id(Long.parseLong(String.valueOf(row[0])))
                        .login(String.valueOf(row[1]))
                        .orderQuantity(Long.parseLong(String.valueOf(row[2])))
                        .orderSum(Long.parseLong(String.valueOf(row[3])))
                        .purchaseQuantity(Long.parseLong(String.valueOf(row[4])))
                        .purchaseSum(Long.parseLong(String.valueOf(row[4])) == 0 ? 0 : Long.parseLong(String.valueOf(row[5])))
                        .rentQuantity(Long.parseLong(String.valueOf(row[6])))
                        .rentSum(Long.parseLong(String.valueOf(row[6])) == 0 ? 0 : Long.parseLong(String.valueOf(row[7])))
                        .build()).collect(Collectors.toList());
    }
}
