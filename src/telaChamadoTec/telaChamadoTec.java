/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telaChamadoTec;

import TeladeLogin.TelaLogin;
import TelaConsultaChamadosT.ConsultarChamados;
import classes.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ChamadosAceitos.ChamadosAceitos;

/**
 *
 * @author renan.losantos
 */
public class telaChamadoTec extends javax.swing.JFrame {

    /**
     * Creates new form telaChamado
     */
   
    
    public telaChamadoTec() {
        initComponents();
        txtUsuario.setText(Usuario.usuarioSistema);
        btnVoltar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                TelaLogin telaDeLogin = new TelaLogin();
                telaDeLogin.abrirTela();
                dispose();
            }
            });
        btnChamado.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ChamadosAceitos tela = new ChamadosAceitos();
                tela.abrirTela(); // Chama o método para abrir a tela
                dispose(); // Fecha a tela atual
            }
            });
    }
    
    public void abrirTela()
    {
        new telaChamadoTec().setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        javaLogo = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JLabel();
        btnChamado = new javax.swing.JButton();
        txtBemVindo = new javax.swing.JLabel();
        btnSeusChamados = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(490, 420));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        btnVoltar.setBackground(new java.awt.Color(255, 153, 153));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVoltar.setText("VOLTAR");
        jPanel1.add(btnVoltar);
        btnVoltar.setBounds(190, 330, 110, 20);

        javaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icons8-logo-java-coffee-cup-144.png"))); // NOI18N
        jPanel1.add(javaLogo);
        javaLogo.setBounds(180, 10, 130, 120);

        txtUsuario.setBackground(new java.awt.Color(255, 0, 0));
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 0, 0));
        txtUsuario.setText("Úsuario");
        jPanel1.add(txtUsuario);
        txtUsuario.setBounds(280, 140, 66, 25);

        btnChamado.setBackground(new java.awt.Color(204, 255, 255));
        btnChamado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChamado.setText("CONSULTAR CHAMADOS");
        jPanel1.add(btnChamado);
        btnChamado.setBounds(150, 210, 190, 23);

        txtBemVindo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBemVindo.setText("Bem vindo(a)");
        jPanel1.add(txtBemVindo);
        txtBemVindo.setBounds(150, 140, 114, 25);

        btnSeusChamados.setBackground(new java.awt.Color(255, 255, 204));
        btnSeusChamados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSeusChamados.setText("SEUS CHAMADOS");
        jPanel1.add(btnSeusChamados);
        btnSeusChamados.setBounds(150, 260, 190, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 480, 400);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(telaChamadoTec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaChamadoTec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaChamadoTec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaChamadoTec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telaChamadoTec().setVisible(true);
            }
        });
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChamado;
    private javax.swing.JButton btnSeusChamados;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel javaLogo;
    private javax.swing.JLabel txtBemVindo;
    private javax.swing.JLabel txtUsuario;
    // End of variables declaration//GEN-END:variables
}
