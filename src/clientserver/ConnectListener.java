package clientserver;

import chessview.DeskView;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;


public class ConnectListener implements ActionListener {
    private JDialog chooseConnectionDialog;

    public ConnectListener(JDialog chooseConnectionDialog){
        this.chooseConnectionDialog = chooseConnectionDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooseConnectionDialog.setVisible(false);
        JFrame mainWindow = new JFrame("Chess");
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize ();
        mainWindow.setSize(sSize);
        mainWindow.setVisible(true);
        mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        String nameHost;
        boolean flag = false;
        while (!flag){
            try {
                nameHost = JOptionPane.showInputDialog("Enter name of host");
                Socket socket = new Socket(nameHost, 4569);
                DeskView deskView = new DeskView(socket, "black");
                mainWindow.add(deskView);
                flag = true;
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(mainWindow,"No connected to host");
                exception.printStackTrace();
            }
        }
    }
}
