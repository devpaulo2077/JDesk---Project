package MeusChamadosFuncionario;
import classes.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import telaChamadoTec.telaChamadoTec;
import classes.Usuario;
import telaChamado.telaChamado;

public class MeusChamadosF extends JFrame {

    public MeusChamadosF(){
        setTitle("Chamados Aceitos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        JTable tabelaChamados = criarTabelaMeusChamados();
        JScrollPane scrollPane = new JScrollPane(tabelaChamados);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            telaChamado telaChamado = new telaChamado();
            telaChamado.abrirTela();
            dispose();
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVoltar, BorderLayout.SOUTH);
        add(panel);
        
        setVisible(true);
    }
    public JTable criarTabelaMeusChamados() {
        String[] colunas = {"Patrimônio", "Problema", "Ação"};
        String[][] dados = obterDadosMeusChamados();
        DefaultTableModel model = new DefaultTableModel(dados, colunas);
        return new JTable(model);
    }
     private String[][] obterDadosMeusChamados() {
        Conexao banco = new Conexao();
        ArrayList<String[]> dadosList = new ArrayList<>();
        try {
            int id = Usuario.idTecnico;
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();
            ResultSet resultSet = banco.stmt.executeQuery("SELECT patri_equipamento, desc_problema, desc_acao FROM chamados WHERE id_usuario = '" + id + "'");
            while (resultSet.next()) {
                String patrimonio = resultSet.getString("patri_equipamento");
                String problema = resultSet.getString("desc_problema");
                String acao = resultSet.getString("desc_acao");
                dadosList.add(new String[]{patrimonio, problema, acao});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar chamados aceitos: " + e.getMessage());
        } finally {
            banco.FecharConexao();
        }
        String[][] dados = new String[dadosList.size()][3];
        dadosList.toArray(dados);
        return dados;
    }

    public static void abrirTela() {
        new MeusChamadosF();
    }
}

