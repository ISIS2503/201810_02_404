package lambda;

public class Batch {

	public Batch(String data) {
		String serverIP = "127.0.0.1";
		String keyspace = "system";

		Cluster cluster = Cluster.builder()
		  .addContactPoints(serverIP)
		  .build();

		Session session = cluster.connect(keyspace);
		
		// Sql del insert
		String cqlStatementC = "INSERT INTO exampkeyspace.users (username, password) " + 
                "VALUES ('Serenity', 'fa3dfQefx')";
		
		session.execute(cqlStatementC);
	}
	
}
