package ar.edu.Almacen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ModificarProveedor extends JPanel 
{

	public ModificarProveedor(JFrame ventanaUnica, Proveedor proveedor)
	{
		setBackground(Color.ORANGE);
		this.setBounds(0, 0, 800,500);
		setLayout(null);
		
		JLabel Nombre = new JLabel("Nombre");
		Nombre.setForeground(Color.WHITE);
		Nombre.setFont(new Font("Arial", Font.BOLD, 15));
		Nombre.setBounds(59, 11, 60, 35);
		this.add(Nombre);
		
		JLabel Precio_Compra = new JLabel("Telefono");
		Precio_Compra.setForeground(Color.WHITE);
		Precio_Compra.setFont(new Font("Arial", Font.BOLD, 15));
		Precio_Compra.setBounds(56, 118, 110, 35);
		this.add(Precio_Compra);
		
		JLabel labelEspecialidad = new JLabel("Especialidad");
		labelEspecialidad.setForeground(Color.WHITE);
		labelEspecialidad.setFont(new Font("Arial", Font.BOLD, 15));
		labelEspecialidad.setBounds(327, 22, 118, 35);
		this.add(labelEspecialidad);
		
		JLabel labelCalle = new JLabel("Calle");
		labelCalle.setHorizontalAlignment(SwingConstants.CENTER);
		labelCalle.setForeground(Color.WHITE);
		labelCalle.setFont(new Font("Arial", Font.BOLD, 15));
		labelCalle.setBounds(41, 214, 110, 35);
		this.add(labelCalle);
		
		JLabel lblAltura = new JLabel("Altura");
		lblAltura.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltura.setForeground(Color.WHITE);
		lblAltura.setFont(new Font("Arial", Font.BOLD, 15));
		lblAltura.setBounds(151, 214, 110, 35);
		this.add(lblAltura);
		
		JLabel labelPiso = new JLabel("Piso");
		labelPiso.setHorizontalAlignment(SwingConstants.CENTER);
		labelPiso.setForeground(Color.WHITE);
		labelPiso.setFont(new Font("Arial", Font.BOLD, 15));
		labelPiso.setBounds(41, 292, 110, 35);
		this.add(labelPiso);
		
		JLabel lblDepto = new JLabel("Depto");
		lblDepto.setHorizontalAlignment(SwingConstants.CENTER);
		lblDepto.setForeground(Color.WHITE);
		lblDepto.setFont(new Font("Arial", Font.BOLD, 15));
		lblDepto.setBounds(151, 292, 110, 35);
		this.add(lblDepto);
		
		JTextField textField_nombre_proveedor = new JTextField(proveedor.getNombre());
		textField_nombre_proveedor.setBounds(59, 59, 137, 20);
		this.add(textField_nombre_proveedor);
		textField_nombre_proveedor.setColumns(10);
		
		JTextField textField_telefono_proveedor = new JTextField(proveedor.getTelefono());
		textField_telefono_proveedor.setColumns(10);
		textField_telefono_proveedor.setBounds(56, 164, 137, 20);
		this.add(textField_telefono_proveedor);
		
		JTextField textField_especialidad_proveedor = new JTextField(proveedor.getEspecialidad());
		textField_especialidad_proveedor.setColumns(10);
		textField_especialidad_proveedor.setBounds(313, 79, 230, 65);
		this.add(textField_especialidad_proveedor);

		JTextField textField_calle_proveedor = new JTextField(proveedor.getCalle_domicilio());
		textField_calle_proveedor.setColumns(10);
		textField_calle_proveedor.setBounds(59, 261, 91, 20);
		this.add(textField_calle_proveedor);
		
		JTextField textField_altura_proveedor = new JTextField(Integer.toString(proveedor.getAltura_domicilio()));
		textField_altura_proveedor.setColumns(10);
		textField_altura_proveedor.setBounds(175, 261, 183, 20);
		this.add(textField_altura_proveedor);
		
		JTextField textField_piso_proveedor = new JTextField(Integer.toString(proveedor.getPiso_domicilio()));
		textField_piso_proveedor.setColumns(10);
		textField_piso_proveedor.setBounds(59, 334, 71, 20);
		this.add(textField_piso_proveedor);

		JTextField textField_departamento = new JTextField(proveedor.getDepartamento_domicilio());
		textField_departamento.setColumns(10);
		textField_departamento.setBounds(175, 334, 86, 20);
		this.add(textField_departamento);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(562, 292, 118, 43);
		add(btnGuardar);

		btnGuardar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				Connection conexion;
				try 
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;IntegratedSecurity=true"; 
					conexion =  DriverManager.getConnection(direccionURL); 
					
					proveedor.setNombre(textField_nombre_proveedor.getText());
					proveedor.setTelefono(textField_telefono_proveedor.getText());
					proveedor.setCalle_domicilio(textField_calle_proveedor.getText());
					proveedor.setAltura_domicilio(Integer.parseInt(textField_altura_proveedor.getText()));
					proveedor.setPiso_domicilio(Integer.parseInt(textField_piso_proveedor.getText()));
					proveedor.setDepartamento_domicilio(textField_departamento.getText());
					proveedor.setEspecialidad(textField_especialidad_proveedor.getText());
					
					CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [modificarProveedor] (?,?,?,?,?,?,?,?)}"); // llamo procedimiento
			
					llamadaDeProcedimiento1.setInt(1, proveedor.getId_proveedor());
					llamadaDeProcedimiento1.setString(2,proveedor.getNombre());
					llamadaDeProcedimiento1.setString(3,proveedor.getTelefono());
					llamadaDeProcedimiento1.setString(4,proveedor.getCalle_domicilio());
					llamadaDeProcedimiento1.setInt(5, proveedor.getAltura_domicilio());
					llamadaDeProcedimiento1.setInt(6,proveedor.getPiso_domicilio());
					llamadaDeProcedimiento1.setString(7,proveedor.getDepartamento_domicilio());
					llamadaDeProcedimiento1.setString(8,proveedor.getEspecialidad());
					llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado
					
					conexion.close();
					
					new InterfazProveedores(ventanaUnica);
				   
				} 
				catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
					
				} catch (SQLException e2)
				{
					e2.printStackTrace();
				}
			}
		});
		
		
	}
}
