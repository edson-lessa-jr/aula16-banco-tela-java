package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import view.MensagensException;

public class AlunoDAO {

    private ArrayList<Aluno> listaAluno
            = new ArrayList<>();

    public Connection getConnection() {
        Connection conn = null;

        try {
            // Carregamento do JDBC Driver
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            // Configurar a conexão
            String server = "127.0.0.1"; //caminho do MySQL
            String database = "db_alunos";
            String url = "jdbc:mysql://" + server
                    + ":3306/" + database
                    + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "root";

            conn = DriverManager.getConnection(url,
                    user, password);
        } catch (SQLException erro) {
            erro.printStackTrace();
        } catch (ClassNotFoundException erro) {
            erro.printStackTrace();
        }
        return conn;
    }

    public ArrayList<Aluno> getMinhaLista() throws MensagensException, SQLException {

        listaAluno.clear(); // Limpa nosso ArrayList

        Statement stmt = this.getConnection().createStatement();
        ResultSet res
                = stmt.executeQuery("SELECT id, nome, "
                        + "idade, curso, fase "
                        + "FROM tb_alunos");
        while (res.next()) {

            String curso = res.getString("curso");
            int fase = res.getInt("fase");
            int id = res.getInt("id");
            String nome = res.getString("nome");
            int idade = res.getInt("idade");
            Aluno objeto = new Aluno(curso, fase, id, nome, idade);

            listaAluno.add(objeto);
        }

        stmt.close();
        return listaAluno;

    }

    public boolean InsertAlunoBD(String curso, int fase, String nome, int idade) throws MensagensException, SQLException {
        int id = this.maiorID()+1;
        Aluno objeto = new Aluno(curso, fase, id, nome, idade);
        String sql = "INSERT INTO "
                + "tb_alunos(id,nome,idade,curso,fase) "
                + "VALUES(?,?,?,?,?)";

        PreparedStatement stmt = this.getConnection().prepareStatement(sql);

        stmt.setInt(1, objeto.getId());
        stmt.setString(2, objeto.getNome());
        stmt.setInt(3, objeto.getIdade());
        stmt.setString(4, objeto.getCurso());
        stmt.setInt(5, objeto.getFase());

        stmt.execute();
        stmt.close();
        return true;

    }

    public boolean DeleteAlunoBD(int id) throws SQLException {
        Statement stmt = this.getConnection().createStatement();
        stmt.executeUpdate("DELETE FROM tb_alunos WHERE id = " + id);
        stmt.close();
        return true;
    }

    public boolean UpdateAlunoBD(String curso, int fase, int id, String nome, int idade) throws MensagensException, SQLException {

        Aluno objeto = new Aluno(curso, fase, id, nome, idade);

        String sql = "UPDATE tb_alunos set nome = ? ,"
                + "idade = ? ,curso = ? ,fase = ? WHERE id = ?";

        PreparedStatement stmt = this.getConnection().prepareStatement(sql);

        stmt.setString(1, objeto.getNome());
        stmt.setInt(2, objeto.getIdade());
        stmt.setString(3, objeto.getCurso());
        stmt.setInt(4, objeto.getFase());
        stmt.setInt(5, objeto.getId());

        stmt.execute();
        stmt.close();
        return true;
    }

    // procura o INDICE de objeto da MinhaLista que contem o ID enviado.
  /*  private int procuraIndice(int id) {
        int indice = -1;
        for (int i = 0; i < AlunoDAO.listaAluno.size(); i++) {
            if (AlunoDAO.listaAluno.get(i).getId() == id) {
                indice = i;
            }
        }
        return indice;
    }*/

    public Aluno carregaAluno(int id) throws SQLException, MensagensException {
        Aluno objeto = new Aluno();
        objeto.setId(id);

        Statement stmt = this.getConnection().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * "
                + "FROM tb_alunos WHERE id = " + id);
        res.next();

        objeto.setNome(res.getString("nome"));
        objeto.setIdade(res.getInt("idade"));
        objeto.setCurso(res.getString("curso"));
        objeto.setFase(res.getInt("fase"));

        stmt.close();

        return objeto;
    }

    private int maiorID() throws SQLException {
        int maiorID = 0;
        Statement stmt = this.getConnection().createStatement();

        ResultSet res
                = stmt.executeQuery("SELECT MAX(id) id FROM tb_alunos");
        res.next();
        maiorID = res.getInt("id");
        stmt.close();

        return maiorID;
    }
}
