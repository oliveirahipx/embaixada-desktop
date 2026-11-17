package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexão com o MySQL. Os valores padrão batem com o docker-compose.yml do projeto;
 * para outro servidor, defina EMBAIXADA_DB_URL, EMBAIXADA_DB_USER e EMBAIXADA_DB_PASSWORD.
 */
public class Conexao {
    private static final String URL =
            config("EMBAIXADA_DB_URL", "jdbc:mysql://localhost:3306/embaixadaBrasil");
    private static final String USER = config("EMBAIXADA_DB_USER", "embaixada");
    private static final String PASSWORD = config("EMBAIXADA_DB_PASSWORD", "embaixada");

    private static String config(String nome, String padrao) {
        String valor = System.getenv(nome);
        return valor == null || valor.isEmpty() ? padrao : valor;
    }

    public static String getUrl() {
        return URL;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }
}
