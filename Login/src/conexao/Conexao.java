package conexao;

	import java.sql.*;
	import java.util.Scanner;

	public class Conexao {
	    public static void main(String[] args) {
	        // URL de conexão com o Oracle (altere conforme necessário)
	        String jdbcUrl = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
	        String dbUser = "RM561274";
	        String dbPassword = "280507";

	        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
	            Scanner scanner = new Scanner(System.in);

	            System.out.print("E-mail: ");
	            String email = scanner.nextLine();

	            System.out.print("Senha: ");
	            String senha = scanner.nextLine();

	            String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setString(1, email);
	                stmt.setString(2, senha);

	                ResultSet rs = stmt.executeQuery();

	                if (rs.next()) {
	                    System.out.println("Login bem-sucedido. Bem-vindo, " + rs.getString("nome") + "!");
	                } else {
	                    System.out.println("Login falhou. Verifique suas credenciais.");
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Erro ao conectar ou consultar o banco de dados:");
	            e.printStackTrace();
	        }
	    }
	}
