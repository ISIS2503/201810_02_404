package lambda.batch.persistence;

import com.impetus.client.cassandra.common.CassandraConstants;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConnection {

    public static final String CASSANDRA = "cassandra_db";
    public static final String DERBY = "derby_db";

    public static final String DB = CASSANDRA;

    private EntityManager entityManager;
    public static final JPAConnection CONNECTION = new JPAConnection();

    public JPAConnection() {
        if (entityManager == null) {
            EntityManagerFactory emf;
            if (DB.equals(DERBY)) {
                emf = Persistence.createEntityManagerFactory(DERBY);
            } else {
                Map<String, String> propertyMap = new HashMap<>();
                propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
                emf = Persistence.createEntityManagerFactory(CASSANDRA, propertyMap);
            }
            entityManager = emf.createEntityManager();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
