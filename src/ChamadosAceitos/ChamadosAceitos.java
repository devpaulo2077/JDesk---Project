package ChamadosAceitos;

import classes.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import telaChamadoTec.telaChamadoTec;

public class ChamadosAceitos extends JFrame {

    public ChamadosAceitos() {
        setTitle("Chamados Aceitos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        JTable tabelaChamados = criarTabelaChamadosAceitos();
        JScrollPane scrollPane = new JScrollPane(tabelaChamados);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            telaChamadoTec mainpage = new telaChamadoTec();
            mainpage.abrirTela();
            dispose();
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVoltar, BorderLayout.SOUTH);
        add(panel);
        
        setVisible(true);
    }

    private JTable criarTabelaChamadosAceitos() {
        String[] colunas = {"Patrimônio", "Problema", "Ação"};
        String[][] dados = obterDadosChamadosAceitos();
        DefaultTableModel model = new DefaultTableModel(dados, colunas);
        return new JTable(model);
    }

    private String[][] obterDadosChamadosAceitos() {
        Conexao banco = new Conexao();
        ArrayList<String[]> dadosList = new ArrayList<>();
        try {
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();
            ResultSet resultSet = banco.stmt.executeQuery("SELECT patri_equipamento, desc_problema, desc_acao FROM chamados WHERE estatus = 'aceito'");
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
        new ChamadosAceitos();
    }
}
