package ar.edu.Almacen;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgregarProveedor extends JPanel 
{

	public AgregarProveedor(JFrame ventanaUnica, ArrayList<Proveedor> proveedores)
	{
		setBackground(Color.ORANGE);
		this.setBounds(0, 0, 800,500);
		setLayout(null);
		
		JButton boton_ingreso_proveedor = new JButton("Ingresar Proveedor");
		boton_ingreso_proveedor.setBounds(316, 330, 219, 58);
		add(boton_ingreso_proveedor);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTelefono.setBounds(77, 118, 68, 23);
		add(lblTelefono);
		
		JLabel label_Especialidad_proveedor = new JLabel("Especialidad");
		label_Especialidad_proveedor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_Especialidad_proveedor.setBounds(77, 204, 86, 23);
		add(label_Especialidad_proveedor);
		
		JLabel lblCalleDomicilio = new JLabel("Calle Domicilio");
		lblCalleDomicilio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCalleDomicilio.setBounds(400, 39, 108, 23);
		add(lblCalleDomicilio);
		
		JLabel label_AlturaDomicilio_proveedor = new JLabel("Altura Domicilio");
		label_AlturaDomicilio_proveedor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_AlturaDomicilio_proveedor.setBounds(400, 118, 108, 23);
		add(label_AlturaDomicilio_proveedor);
		
		JLabel label_piso_proveedor = new JLabel("Piso");
		label_piso_proveedor.setHorizontalAlignment(SwingConstants.CENTER);
		label_piso_proveedor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_piso_proveedor.setBounds(400, 204, 68, 23);
		add(label_piso_proveedor);
		
		JLabel label_4 = new JLabel("Nombre");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_4.setBounds(77, 39, 68, 23);
		add(label_4);
		
		JLabel Altura = new JLabel("Departamento");
		Altura.setHorizontalAlignment(SwingConstants.CENTER);
		Altura.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Altura.setBounds(551, 204, 108, 23);
		add(Altura);
		
		JTextField textField_nombre_proveedor = new JTextField();
		textField_nombre_proveedor.setBounds(155, 42, 137, 20);
		add(textField_nombre_proveedor);
		textField_nombre_proveedor.setColumns(10);
		
		JTextField textField_telefono_proveedor = new JTextField();
		textField_telefono_proveedor.setColumns(10);
		textField_telefono_proveedor.setBounds(155, 121, 137, 20);
		add(textField_telefono_proveedor);
		
		JTextField textField_especialidad_proveedor = new JTextField();
		textField_especialidad_proveedor.setColumns(10);
		textField_especialidad_proveedor.setBounds(173, 207, 119, 20);
		add(textField_especialidad_proveedor);

		JTextField textField_calle_proveedor = new JTextField();
		textField_calle_proveedor.setColumns(10);
		textField_calle_proveedor.setBounds(537, 42, 119, 20);
		add(textField_calle_proveedor);
		
		JTextField textField_altura_proveedor = new JTextField();
		textField_altura_proveedor.setColumns(10);
		textField_altura_proveedor.setBounds(537, 121, 119, 20);
		add(textField_altura_proveedor);
		
		JTextField textField_piso_proveedor = new JTextField();
		textField_piso_proveedor.setColumns(10);
		textField_piso_proveedor.setBounds(478, 207, 38, 20);
		add(textField_piso_proveedor);
		
		JTextField textField_departamento_proveedor = new JTextField();
		textField_departamento_proveedor.setColumns(10);
		textField_departamento_proveedor.setBounds(686, 207, 86, 20);
		add(textField_departamento_proveedor);
		
		JLabel labelError = new JLabel("");
		labelError.setForeground(Color.RED);
		labelError.setBounds(231, 274, 345, 23);
		add(labelError);

		
		boton_ingreso_proveedor.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				Connection conexion;
				Proveedor proveedor=null;
				boolean huboError=true;
					
				try
				{
						proveedor = new Proveedor(0, textField_nombre_proveedor.getText(),textField_telefono_proveedor.getText(),
						textField_calle_proveedor.getText(),
						Integer.parseInt(textField_altura_proveedor.getText()) ,  Integer.parseInt(textField_piso_proveedor.getText()),
						textField_departamento_proveedor.getText() , 
						textField_especialidad_proveedor.getText() );
						
						try 
						{
							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
							String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
							conexion =  DriverManager.getConnection(direccionURL); ;
							
							CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call agregarProveedor (?,?,?,?,?,?,?)}"); // llamo procedimiento
							llamadaDeProcedimiento1.setString(1, proveedor.getNombre());
							llamadaDeProcedimiento1.setString(2, proveedor.getTelefono()); 
							llamadaDeProcedimiento1.setString(3, proveedor.getCalle_domicilio()); 
							llamadaDeProcedimiento1.setInt(4, proveedor.getAltura_domicilio()); 
							llamadaDeProcedimiento1.setInt(5, proveedor.getPiso_domicilio()); 
							llamadaDeProcedimiento1.setString(6, proveedor.getDepartamento_domicilio()); 
							llamadaDeProcedimiento1.setString(7, proveedor.getEspecialidad()); 
					
							llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado

							CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call devolverIdUltimoProveedor}");
							llamadaDeProcedimiento2.execute();
							ResultSet resultadosDeProcedimiento2 =llamadaDeProcedimiento2.getResultSet();
							resultadosDeProcedimiento2.next();
							proveedor.setId_proveedor(resultadosDeProcedimiento2.getInt(1));

							conexion.close();
						}
							
							catch (ClassNotFoundException e1)
							{
								e1.printStackTrace();
								
							} catch (SQLException e2)
							{
								e2.printStackTrace();
							}
						
						new InterfazProveedores(ventanaUnica);
				}
				
				
				catch(java.lang.NumberFormatException e3)
				{
					 JOptionPane.showMessageDialog(getParent(), "Error de formato en algún campo.", "Error",  JOptionPane.ERROR_MESSAGE);
				}
		
			
			}
		});
	}
}
