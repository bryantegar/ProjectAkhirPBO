package dompetmu;

import javax.swing.*;
import java.sql.*;

public class ModelDompet {
    private int id_user;
    private String username;
    private String password;
    private String no_telp;
    private long saldo;
    
    private String DBurl = "jdbc:mysql://localhost/dompetmu";
    private String DBusername = "root";
    private String DBpassword = "";
    Connection koneksi;
    Statement statement;
    
    public int getIdUser() {
        return this.id_user;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getNoTelp() {
        return this.no_telp;
    }
    
    public long getSaldo() {
        return this.saldo;
    }
    
    public void Transfer(ViewTransfer viewTransfer) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(DBurl, DBusername, DBpassword);
            statement = koneksi.createStatement();

            String sql = "SELECT * FROM users WHERE no_telp='" + viewTransfer.getNoTelp().getText() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
             
            if (resultSet.next()) {
               long newSaldo = resultSet.getLong("saldo") + Long.parseLong(viewTransfer.getJumlahTransfer().getText());
               
               sql = "UPDATE users SET saldo='" + newSaldo + "' WHERE no_telp='" + viewTransfer.getNoTelp().getText() + "'";
               statement.executeUpdate(sql);

               newSaldo = viewTransfer.modelDompet.saldo - Long.parseLong(viewTransfer.getJumlahTransfer().getText());
               viewTransfer.modelDompet.saldo = newSaldo;
               
               sql = "UPDATE users SET saldo='" + newSaldo + "' WHERE id_user='" + viewTransfer.modelDompet.id_user + "'";
               statement.executeUpdate(sql);

               JOptionPane.showMessageDialog(null, "Transfer berhasil!", "Message",JOptionPane.INFORMATION_MESSAGE);
               
               new ViewTransfer(viewTransfer.modelDompet).show();
            } else {
                JOptionPane.showMessageDialog(null, "No.Telp penerima belum terdaftar!", "Message",JOptionPane.ERROR_MESSAGE);
            }
            
            statement.close();
            koneksi.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Transfer gagal!", "Message", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan!", "Message", JOptionPane.ERROR_MESSAGE);
         }
    }
    
    public void TopUp(ViewTopUp viewTopUp) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(DBurl, DBusername, DBpassword);
            statement = koneksi.createStatement();
            
            long newSaldo = Long.parseLong(viewTopUp.getJumlahTopUp().getText()) + viewTopUp.modelDompet.saldo;
            viewTopUp.modelDompet.saldo = newSaldo;
            
            String sql = "UPDATE users SET saldo='" + newSaldo + "' WHERE id_user='" + viewTopUp.modelDompet.id_user + "'";
            statement.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(null, "Top Up berhasil!", "Message",JOptionPane.INFORMATION_MESSAGE);
            
            statement.close();
            koneksi.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Top Up gagal!", "Message", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan!", "Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void submitRegisterForm(ViewRegister viewRegister) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(DBurl, DBusername, DBpassword);
            statement = koneksi.createStatement();
            
            String sql = "SELECT * FROM users WHERE no_telp='" + viewRegister.getNoTelp().getText() +"'";;
            ResultSet resultSet = statement.executeQuery(sql);
            
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "No.Telp telah digunakan!", "Message",JOptionPane.ERROR_MESSAGE);
            } else {    
                sql = "INSERT INTO users (username,password,no_telp,saldo) VALUES('" + viewRegister.getUsername().getText() + "','" + viewRegister.getPassword().getText() + "','" + viewRegister.getNoTelp().getText() + "',0)";
                statement.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Registrasi berhasil!", "Message",JOptionPane.INFORMATION_MESSAGE);
            }
            
            statement.close();
            koneksi.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Register gagal!", "Message", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan!", "Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void submitLoginForm(ViewLogin viewLogin) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(DBurl, DBusername, DBpassword);
            statement = koneksi.createStatement();
            
            String sql = "SELECT * FROM users WHERE username='" + viewLogin.getUsername().getText() + "' OR no_telp='" + viewLogin.getUsername().getText() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            
            if (resultSet.next()) {
                if (viewLogin.getPassword().getText().equals(resultSet.getString("password"))) {
                    this.id_user = resultSet.getInt("id_user");
                    this.username = resultSet.getString("username");
                    this.password = resultSet.getString("password");
                    this.no_telp = resultSet.getString("no_telp");
                    this.saldo = resultSet.getLong("saldo");
                    
                    new ViewDompet(this).show();
                } else{
                    JOptionPane.showMessageDialog(null, "Username/No.Telp atau Password salah!", "Message",JOptionPane.ERROR_MESSAGE);
                    
                    new ViewLogin().show();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username/No.Telp atau Password salah!", "Message",JOptionPane.ERROR_MESSAGE);
                
                new ViewLogin().show();
            }
            
            statement.close();
            koneksi.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Login gagal!", "Message", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan!", "Message", JOptionPane.ERROR_MESSAGE);
        }
    }
}