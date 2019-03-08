package ar.edu.Almacen;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class AjustarPreciosProductos extends JPanel 
{
	private JTextField textField;

	public AjustarPreciosProductos(JFrame ventanaUnica) 
	{
		setBackground(Color.ORANGE);
		ventanaUnica.setContentPane(this);
		this.setBounds(0, 0, 800,500);
		setLayout(null);
		
		JLabel labelMensaje = new JLabel("Ajuste el porcentaje por el cual se modificaran los precios. ");
		labelMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		labelMensaje.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelMensaje.setBounds(118, 106, 574, 34);
		add(labelMensaje);
		
		JLabel labelMensaje2 = new JLabel("(Se recomienda utilizar el porcentaje de inflaci\u00F3n)");
		labelMensaje2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelMensaje2.setHorizontalAlignment(SwingConstants.CENTER);
		labelMensaje2.setBounds(253, 192, 295, 14);
		add(labelMensaje2);
		
		textField = new JTextField();
		textField.setBounds(351, 264, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton botonAjustar = new JButton("Ajustar Precios");
		botonAjustar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonAjustar.setBounds(315, 357, 171, 34);
		add(botonAjustar);
		
		botonAjustar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{

					Connection conexion;
					try 
					{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;IntegratedSecurity=true"; 
						conexion =  DriverManager.getConnection(direccionURL); 
						
						float porcentaje= Float.parseFloat(textField.getText());
						
						CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [ajustarPreciosProductosA] (?)}"); // llamo procedimiento
						llamadaDeProcedimiento1.setFloat(1, porcentaje);
						llamadaDeProcedimiento1.execute();
						conexion.close();
						
						new InterfazProductos(ventanaUnica);
					   
					} 
					catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
						
					} catch (SQLException e2)
					{
						e2.printStackTrace();
					}
				}

				
				catch(java.lang.NumberFormatException e3)
				{
					 JOptionPane.showMessageDialog(getParent(), "Debe ingresar un número.", "Error",  JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}

}
