package TelaConsultaChamadosT;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import classes.Conexao;

public class ConsultarChamados {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConsultarChamados::abrirTela);
    }

    public static void abrirTela() {
        Conexao banco = new Conexao();

        try {
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();

            banco.resultset = banco.stmt.executeQuery("SELECT chamados.estatus, chamados.patri_equipamento, chamados.desc_problema FROM chamados WHERE chamados.estatus = 'aberto';");

            ArrayList<String[]> dadosList = new ArrayList<>();

            while (banco.resultset.next()) {
                String patrimonio = banco.resultset.getString("patri_equipamento");
                String descricao = banco.resultset.getString("desc_problema");
                dadosList.add(new String[]{patrimonio, descricao});
            }

            String[][] dados = new String[dadosList.size()][2];
            dadosList.toArray(dados);

            String[] colunas = {"Patrimônio", "Descrição", "Ação"};

            DefaultTableModel model = new DefaultTableModel(dados, colunas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 2;
                }
            };

            JTable tabela = new JTable(model);

            tabela.getColumn("Ação").setCellRenderer(new ButtonRenderer());
            tabela.getColumn("Ação").setCellEditor(new ButtonEditor(new JCheckBox(), tabela));

            JScrollPane scrollPane = new JScrollPane(tabela);

            JButton btnVoltar = new JButton("Voltar");
            btnVoltar.addActionListener(e -> ((JFrame) SwingUtilities.getWindowAncestor(btnVoltar)).dispose());

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(btnVoltar, BorderLayout.SOUTH);

            JFrame frame = new JFrame("Consultar Chamados");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setSize(480, 400);
            frame.setResizable(false);
            frame.setVisible(true);

            banco.FecharConexao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setText("Aceitar");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private boolean isPushed;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setText("Aceitar");
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int selectedRow = table.getSelectedRow();
            String patrimonio = table.getValueAt(selectedRow, 0).toString();
            
            // Atualizar o status no banco de dados
            aceitarChamado(patrimonio);
            
            JOptionPane.showMessageDialog(button, "Chamado de patrimônio " + patrimonio + " foi aceito!");

            // Fechar a tela atual
            SwingUtilities.getWindowAncestor(button).dispose();

            // Reabrir a tela de todos os chamados
            ConsultarChamados.abrirTela();
        }
        isPushed = false;
        return "Aceitar";
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    // Método para atualizar o status do chamado no banco de dados
    private void aceitarChamado(String patrimonio) {
        Conexao banco = new Conexao();
        try {
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();

            // Query para atualizar o status para 'aceito'
            String sql = "UPDATE chamados SET estatus = 'Aceito' WHERE patri_equipamento = '" + patrimonio + "'";
            banco.stmt.executeUpdate(sql);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(button, "Erro ao aceitar o chamado: " + e.getMessage());
        } finally {
            banco.FecharConexao();
        }
    }
}
