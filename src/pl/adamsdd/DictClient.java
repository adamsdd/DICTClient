/**
 * @author Dawid Adamczyk #adamsdd
 */

package pl.adamsdd;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

import java.awt.*;
import java.awt.event.*;

public class DictClient extends JFrame{
    
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private String SERVER = "dict.org";
	private int PORT = 2628;
    
    public DictClient()
    {
        conn = new Connection();
        conn.connectTo(SERVER, PORT);
        
        JTextArea txt = new JTextArea(30, 20);
        JScrollPane txtScrollPane = new JScrollPane(txt);
        JPanel southPanel = new JPanel();
        JTextField query = new JTextField(20);
        JButton sendButt = new JButton("Wyslij");
        txt.setEditable(false);
        sendButt.setMnemonic(KeyEvent.VK_W);
        
        addWindowListener(new WindowAdapter(){
            
        	@Override
            public void windowClosing(WindowEvent e)
            {
                conn.close();
                System.exit(1);
            }
        });
        
        sendButt.addActionListener(new ActionListener(){
            
                public void actionPerformed(ActionEvent e)
                {
                    String response = conn.search(query.getText());
                    txt.setText(response);
                    txt.setCaretPosition(0);
                }
            });
        
        JPopupMenu popup = new JPopupMenu();
        JMenuItem copy = new JMenuItem("Copy");
        popup.add(copy);
        txt.setComponentPopupMenu(popup);
        DefaultEditorKit.CopyAction copyAction = new DefaultEditorKit.CopyAction();
        copy.addActionListener(copyAction);
        
        southPanel.add(query);
        southPanel.add(sendButt);
        
        add(txtScrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Dict Client");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
     public static void main(String []args)
     {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new DictClient();
            }
        });
     }
}