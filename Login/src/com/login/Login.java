package com.login;

import java.awt.Color;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        icono = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        userTxt = new javax.swing.JTextField();
        passLabel = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        passTxt = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        btnEntrar = new javax.swing.JPanel();
        btnEntrarTxt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagen/ticket.png"))); // NOI18N
        icono.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icono, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icono, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(307, Short.MAX_VALUE))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 500));

        userLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        userLabel.setText("Usuario");
        userLabel.setToolTipText("");
        bg.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 238, 39));

        userTxt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        userTxt.setForeground(new java.awt.Color(204, 204, 204));
        userTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        userTxt.setText("Ingrese su nombre de usuario");
        userTxt.setBorder(null);
        userTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userTxtMousePressed(evt);
            }
        });
        bg.add(userTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 238, 30));

        passLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passLabel.setText("Contraseña");
        bg.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 110, 30));

        title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        title.setText("Inicio de Sesion");
        bg.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 229, 57));
        bg.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 170, -1));

        passTxt.setForeground(new java.awt.Color(204, 204, 204));
        passTxt.setText("********");
        passTxt.setBorder(null);
        passTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                passTxtMousePressed(evt);
            }
        });
        bg.add(passTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, 170, 20));
        bg.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, 170, -1));

        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));

        btnEntrarTxt.setBackground(new java.awt.Color(255, 255, 255));
        btnEntrarTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEntrarTxt.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrarTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEntrarTxt.setText("ENTRAR");
        btnEntrarTxt.setToolTipText("");
        btnEntrarTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrarTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEntrarTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrarTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnEntrarLayout = new javax.swing.GroupLayout(btnEntrar);
        btnEntrar.setLayout(btnEntrarLayout);
        btnEntrarLayout.setHorizontalGroup(
            btnEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEntrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEntrarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnEntrarLayout.setVerticalGroup(
            btnEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEntrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEntrarTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        bg.add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 130, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //btn de entrar, presionado y sin presionar
    private void btnEntrarTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarTxtMouseEntered
        btnEntrar.setBackground(new Color(0,156,223));
    }//GEN-LAST:event_btnEntrarTxtMouseEntered

    private void btnEntrarTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarTxtMouseExited
        btnEntrar.setBackground(new Color(0,153,153));
    }//GEN-LAST:event_btnEntrarTxtMouseExited

    private void userTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTxtMousePressed
        if(userTxt.getText().trim().equalsIgnoreCase("Ingrese su nombre de usuario")){
        userTxt.setText("");
        userTxt.setForeground(Color.black);
        }
        
        if (passTxt.getPassword().length == 0){
        passTxt.setText("*******");
        passTxt.setForeground(Color.gray);
        }
    }//GEN-LAST:event_userTxtMousePressed

    private void passTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passTxtMousePressed
        if (new String(passTxt.getPassword()).trim().equals("********")){
            passTxt.setText("");
            passTxt.setForeground(Color.black);
        }
        
        if(userTxt.getText().trim().isEmpty()){
        userTxt.setText("Ingrese su nombre de usuario");
        userTxt.setForeground(Color.gray);
        }
    }//GEN-LAST:event_passTxtMousePressed

    private void btnEntrarTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarTxtMouseClicked
    javax.swing.JOptionPane.showMessageDialog(this,"Intento de login con los datos: \nUsuario: " + userTxt.getText() + "\nContraseña: " + String.valueOf(passTxt.getPassword()), "LOGIN", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnEntrarTxtMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JPanel btnEntrar;
    private javax.swing.JLabel btnEntrarTxt;
    private javax.swing.JLabel icono;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passTxt;
    private javax.swing.JLabel title;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField userTxt;
    // End of variables declaration//GEN-END:variables
}
