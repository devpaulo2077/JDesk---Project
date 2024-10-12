package ChamadosAceitos;

import classes.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChamadosAceitos extends JFrame {

    public ChamadosAceitos() {
        // Configurações da janela principal
        setTitle("Chamados Aceitos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Criação do painel e layout
        JPanel panel = new JPanel(new BorderLayout());
        JTable tabelaChamados = criarTabelaChamadosAceitos();
        JScrollPane scrollPane = new JScrollPane(tabelaChamados);

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);

        // Tornar a janela visível
        setVisible(true);
    }

    // Método para criar a tabela com os chamados aceitos
    private JTable criarTabelaChamadosAceitos() {
        // Colunas da tabela
        String[] colunas = {"Patrimônio", "Problema", "Ação"};

        // Obter dados dos chamados aceitos
        String[][] dados = obterDadosChamadosAceitos();

        // Criar o modelo da tabela
        DefaultTableModel model = new DefaultTableModel(dados, colunas);

        // Criar a tabela com o modelo
        JTable tabela = new JTable(model);
        return tabela;
    }

    // Método para obter os dados dos chamados aceitos do banco de dados
    private String[][] obterDadosChamadosAceitos() {
        Conexao banco = new Conexao();
        ArrayList<String[]> dadosList = new ArrayList<>();
        try {
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();

            // Consulta para buscar os chamados com status 'aceito'
            ResultSet resultSet = banco.stmt.executeQuery("SELECT patri_equipamento, desc_problema, desc_acao FROM chamados WHERE estatus = 'aceito'");

            // Processar os resultados da consulta e adicionar à lista
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

        // Converter a lista para um array
        String[][] dados = new String[dadosList.size()][3];
        dadosList.toArray(dados);
        return dados;
    }

    // Método para abrir a tela
    public static void abrirTela() {
        new ChamadosAceitos();
    }
}
