import Database.Conexao;
import Telas.LoginTela;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Testa a conexão antes de abrir o login, para avisar se o MySQL não estiver rodando
            try (Connection ignored = Conexao.getConnection()) {
                new LoginTela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível conectar ao MySQL em " + Conexao.getUrl() + ".\n"
                                + "Suba o banco com \"docker compose up -d\" ou configure as variáveis\n"
                                + "EMBAIXADA_DB_URL, EMBAIXADA_DB_USER e EMBAIXADA_DB_PASSWORD.",
                        "Embaixada", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
