package ChamadosAceitos;

import classes.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import telaChamadoTec.telaChamadoTec;

public class ChamadosAceitos extends JFrame {

    private JTable currentTable;

    public ChamadosAceitos() {
        setTitle("Chamados Aceitos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JTable tabelaChamados = criarTabelaChamadosAceitos();
        JScrollPane scrollPane = new JScrollPane(tabelaChamados);

        // Botão "Voltar" com estilização
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14)); // Texto em negrito
        btnVoltar.setBackground(new Color(173, 216, 230)); // Azul claro suave
        btnVoltar.setForeground(Color.BLACK);

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

        currentTable = new JTable(model); // Atribua a tabela à variável da classe
        currentTable.setBackground(Color.WHITE); // Fundo da tabela
        currentTable.setGridColor(Color.LIGHT_GRAY); // Cor das linhas
        currentTable.setShowGrid(true); // Mostra as linhas

        // Configura a cor do cabeçalho da tabela
        currentTable.getTableHeader().setBackground(Color.WHITE);
        currentTable.getTableHeader().setForeground(Color.BLACK);

        currentTable.getColumnModel().getColumn(2).setCellRenderer(new ActionButtonRenderer());
        currentTable.getColumnModel().getColumn(2).setCellEditor(new ActionButtonEditor(currentTable)); // Passe a referência da tabela

        return currentTable; // Retorne a tabela

    }

    private String[][] obterDadosChamadosAceitos() {
        Conexao banco = new Conexao();
        ArrayList<String[]> dadosList = new ArrayList<>();
        try {
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();
            ResultSet resultSet = banco.stmt.executeQuery(
                    "SELECT patri_equipamento, desc_problema, desc_acao FROM chamados WHERE estatus = 'aceito'"
            );
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

    // Renderer para exibir os botões "Concluir" e "Cancelar"
    private class ActionButtonRenderer extends JPanel implements TableCellRenderer {

        private final JButton btnConcluir = new JButton("Concluir");
        private final JButton btnCancelar = new JButton("Cancelar");

        public ActionButtonRenderer() {
            setLayout(new GridLayout(1, 2, 5, 5));
            configurarBotoes();
        }

        private void configurarBotoes() {
            Font fonteNegrito = new Font("Arial", Font.BOLD, 12);

            // Estilização dos botões
            btnConcluir.setFont(fonteNegrito);
            btnConcluir.setBackground(Color.GREEN);
            btnConcluir.setForeground(Color.WHITE);

            btnCancelar.setFont(fonteNegrito);
            btnCancelar.setBackground(Color.RED);
            btnCancelar.setForeground(Color.WHITE);

            add(btnConcluir);
            add(btnCancelar);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor para adicionar funcionalidade aos botões "Concluir" e "Cancelar"
    private class ActionButtonEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {

        private final JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        private final JButton btnConcluir = new JButton("Concluir");
        private final JButton btnCancelar = new JButton("Cancelar");

        // Adiciona uma variável para armazenar a tabela atual
        private JTable currentTable;

        // Modifica o construtor para receber a tabela
        public ActionButtonEditor(JTable currentTable) {
            this.currentTable = currentTable; // Armazena a referência da tabela
            configurarBotoes();
        }

        private void configurarBotoes() {
            Font fonteNegrito = new Font("Arial", Font.BOLD, 12);

            // Estilização dos botões
            btnConcluir.setFont(fonteNegrito);
            btnConcluir.setBackground(Color.GREEN);
            getContentPane().setBackground(Color.WHITE);

            panel.setBackground(Color.WHITE);

            btnCancelar.setFont(fonteNegrito);
            btnCancelar.setBackground(Color.RED);
            btnCancelar.setForeground(Color.WHITE);

            btnConcluir.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Chamado Concluído!");
                fireEditingStopped();
            });

            btnCancelar.addActionListener(e -> {
                int currentRow = currentTable.getSelectedRow(); // Obtém a linha selecionada da tabela atual
                if (currentRow != -1) { // Verifica se alguma linha está selecionada
                    String patrimonio = currentTable.getValueAt(currentRow, 0).toString(); // Captura o patrimônio da coluna correta
                    Conexao banco = new Conexao();

                    try {
                        banco.AbrirConexao();
                        banco.stmt = banco.con.createStatement();
                        int resultado = banco.stmt.executeUpdate(
                                "UPDATE chamados SET estatus = 'aberto' WHERE patri_equipamento = '" + patrimonio + "'"
                        );
                        if (resultado > 0) {
                            JOptionPane.showMessageDialog(null, "Chamado cancelado com sucesso!");
                                dispose();
                                abrirTela();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao cancelar o chamado.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao cancelar chamado: " + ex.getMessage());
                    } finally {
                        banco.FecharConexao();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um chamado para cancelar.");
                }

                fireEditingStopped(); // Para parar a edição da célula
            });

            panel.add(btnConcluir);
            panel.add(btnCancelar);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}
