package dompetmu;

import javax.swing.*;

public class ControllerDompet {
    private ModelDompet modelDompet;
    
    public void setModelDompet(ModelDompet modelDompet) {
       this.modelDompet = modelDompet;
    }
   
    public void topUpForm(ViewTopUp viewTopUp) {
        if (viewTopUp.getJumlahTopUp().getText().equals("")) {
            JOptionPane.showMessageDialog(null,"Field masih kosong!","Message",JOptionPane.ERROR_MESSAGE);
            
            viewTopUp.dispose();
            new ViewTopUp(viewTopUp.modelDompet).show();
        } else {
            try {
                long jumlahTopup = Long.parseLong(viewTopUp.getJumlahTopUp().getText());
            
                if (jumlahTopup <= 0) {
                    JOptionPane.showMessageDialog(null,"Jumlah saldo tidak valid!","Message",JOptionPane.ERROR_MESSAGE);

                    viewTopUp.dispose();
                    new ViewTopUp(viewTopUp.modelDompet).show();
                } else {
                    modelDompet.TopUp(viewTopUp);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null,"Jumlah saldo tidak valid!","Message",JOptionPane.ERROR_MESSAGE);
                
                viewTopUp.dispose();
                new ViewTopUp(viewTopUp.modelDompet).show();
            }
        }
    }
    
    public void transferForm(ViewTransfer viewTransfer) {
        String no_telp = viewTransfer.getNoTelp().getText();
        
        try {
            Long cekNoTelp = Long.parseLong(no_telp);
            
            if (no_telp.equals("") || viewTransfer.getJumlahTransfer().getText().equals("")) {
                JOptionPane.showMessageDialog(null,"Field masih kosong!","Message",JOptionPane.ERROR_MESSAGE);

                viewTransfer.dispose();
                new ViewTransfer(viewTransfer.modelDompet).show();
            } else {
                try {
                    long jumlahTransfer = Long.parseLong(viewTransfer.getJumlahTransfer().getText());

                    if (jumlahTransfer <= 0) {
                        JOptionPane.showMessageDialog(null,"Jumlah saldo tidak valid!","Message",JOptionPane.ERROR_MESSAGE);

                        viewTransfer.dispose();
                        new ViewTransfer(viewTransfer.modelDompet).show();
                    } else {
                        modelDompet.Transfer(viewTransfer);
                    }
                } catch (RuntimeException ex){
                    JOptionPane.showMessageDialog(null,"Jumlah saldo tidak valid!","Message",JOptionPane.ERROR_MESSAGE);

                    viewTransfer.dispose();
                    new ViewTransfer(viewTransfer.modelDompet).show();
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null,"No.Telp tidak valid!","Message",JOptionPane.ERROR_MESSAGE);

            viewTransfer.dispose();
            new ViewTransfer(viewTransfer.modelDompet).show();
        }
    }
    
    public void submitRegisterForm(ViewRegister viewRegister) {
        String username = viewRegister.getUsername().getText();
        String password = viewRegister.getPassword().getText();
        String no_telp = viewRegister.getNoTelp().getText();
        
        try {
            Long cekNoTelp = Long.parseLong(no_telp);
            
            if (username.equals("") || password.equals("") || no_telp.equals("")) {
                JOptionPane.showMessageDialog(null,"Field masih kosong!","Message",JOptionPane.ERROR_MESSAGE);
            } else {
                modelDompet.submitRegisterForm(viewRegister);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null,"No.Telp tidak valid!","Message",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void submitLoginForm(ViewLogin viewLogin) {
        String username = viewLogin.getUsername().getText();
        String password = viewLogin.getPassword().getText();
        
        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null,"Field masih kosong!","Message",JOptionPane.ERROR_MESSAGE);
        } else {
            modelDompet.submitLoginForm(viewLogin);
        }
    }
}