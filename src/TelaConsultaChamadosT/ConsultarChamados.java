package TelaConsultaChamadosT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import classes.Conexao;

public class ConsultarChamados {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConsultarChamados::abrirTela);
    }

    private static void abrirTela() {
        Conexao banco = new Conexao();

        try {
            // Abrir conexão
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();

            // Executar consulta
            banco.resultset = banco.stmt.executeQuery("SELECT chamados.estatus, chamados.patri_equipamento, chamados.desc_problema, chamados.desc_acao FROM chamados WHERE chamados.estatus = 'aberto';");

            // Criar uma lista para armazenar os dados
            ArrayList<String[]> dadosList = new ArrayList<>();

            // Processar os resultados da consulta
            while (banco.resultset.next()) {
                String patrimonio = banco.resultset.getString("patri_equipamento");
                String descricao = banco.resultset.getString("desc_problema");
                String acao = banco.resultset.getString("desc_acao");
                dadosList.add(new String[]{patrimonio, descricao, acao});
            }

            // Converter a lista para um array
            String[][] dados = new String[dadosList.size()][3];
            dadosList.toArray(dados);

            // Cabeçalhos das colunas
            String[] colunas = {"Patrimonio", "Descrição", "Ação"};

            // Criar a tabela
            JTable tabela = new JTable(dados, colunas);

            // Colocar a tabela dentro de um JScrollPane (barra de rolagem)
            JScrollPane scrollPane = new JScrollPane(tabela);

            // Criar um botão de Voltar
            JButton btnVoltar = new JButton("Voltar");
            btnVoltar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Fecha a janela atual
                    frame.dispose();
                }
            });

            // Criar um painel para adicionar a tabela e o botão
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout()); // Usar layout de borda
            panel.add(scrollPane, BorderLayout.CENTER); // Adiciona a tabela ao centro
            panel.add(btnVoltar, BorderLayout.SOUTH); // Adiciona o botão ao fundo

            // Criar uma janela para exibir a tabela
            JFrame frame = new JFrame("Consultar Chamados");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel); // Adiciona o painel à janela
            frame.setSize(480, 400);
            frame.setResizable(false); // Bloqueia o redimensionamento da janela
            frame.setVisible(true);

            // Fechar a conexão
            banco.FecharConexao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
