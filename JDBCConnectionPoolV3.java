package edu.ezip.ing1.pds.connectionPoolV3;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class JDBCConnectionPoolV3 {

    private DBConfigV3 config;
    private Queue<Connection> connections;

    public JDBCConnectionPoolV3() {
        this.connections = new LinkedList<>();
        this.config = new DBConfigV3();


        try {
            Class.forName(config.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("driver JDBC introuvable", e);
        }

        alimente(config.getPoolSize());
    }

    public void alimente(int nombre) {
        for (int i = 0; i < nombre; i++) {
            try {
                Connection conn = DriverManager.getConnection(
                        config.getUrl(), config.getLogin(), config.getPassword());
                connections.add(conn);
            } catch (SQLException e) {
                System.err.println("impossible de créer une connexion : " + e.getMessage());
            }
        }
    }

    public synchronized Connection prendreCo() throws InterruptedException {
        while (connections.isEmpty()) {
            wait();
        }
        return connections.poll();
    }

    public synchronized void remettreConn(Connection conn) {
        connections.add(conn);
        notifyAll();
    }

    public synchronized void fermerTout() throws SQLException {
        for (Connection c : connections) {
            try {
                c.close();
            } catch (SQLException e) {
                System.err.println("erreur lors de la fermeture " + e.getMessage());
            }
        }
        connections.clear();
        System.out.println("toutes les connexions ont été fermées");
    }
}
