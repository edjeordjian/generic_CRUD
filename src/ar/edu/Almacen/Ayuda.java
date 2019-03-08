package ar.edu.Almacen;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class Ayuda extends JPanel
{
 public Ayuda(JFrame ventanaunica)
 {
	 setLayout(null);
	 this.setBounds(0, 0, 800,500);
	 ventanaunica.setContentPane(this);
	 ventanaunica.validate();
	 
	 JLabel lblNewLabel = new JLabel("");
	 lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
	 lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	 lblNewLabel.setBounds(47, 122, 693, 78);
	 lblNewLabel.setText("El precio indicado por los productos por peso es por 100 gramos, la m\u00EDnima cantidad para");
	 add(lblNewLabel);
	 
	 JLabel lblNewLabel_1 = new JLabel("AYUDA");
	 lblNewLabel_1.setForeground(Color.BLUE);
	 lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
	 lblNewLabel_1.setBounds(65, 42, 219, 54);
	 add(lblNewLabel_1);
	 
	 JLabel label = new JLabel("  comprar o vender");
	 label.setHorizontalAlignment(SwingConstants.CENTER);
	 label.setFont(new Font("Tahoma", Font.PLAIN, 16));
	 label.setBounds(57, 156, 144, 78);
	 add(label);
 }
}
