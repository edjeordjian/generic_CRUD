package ar.edu.Almacen;

import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

public class AgregarProducto extends JPanel 
{
	private JTextField textFieldNombre;
	private JTextField textFieldPrecioCompra;
	private JTextField textFieldPrecioVenta;

	public AgregarProducto(JFrame ventanaUnica, ArrayList<Producto> productos) 
	{
		setBackground(Color.ORANGE);
		this.setBounds(0, 0, 800,500);
		ventanaUnica.setContentPane(this);
		setLayout(null);
		
		JLabel labelNombre = new JLabel("Nombre :");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelNombre.setBounds(35, 37, 70, 19);
		add(labelNombre);
		
		JLabel labelPrecioCompra = new JLabel("Precio Compra:");
		labelPrecioCompra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelPrecioCompra.setBounds(35, 167, 99, 19);
		add(labelPrecioCompra);
		
		JLabel lblPrecioVenta = new JLabel("Precio Venta:");
		lblPrecioVenta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrecioVenta.setBounds(35, 292, 99, 19);
		add(lblPrecioVenta);
		
		JLabel labelVendidoPor = new JLabel("Vendido por:");
		labelVendidoPor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelVendidoPor.setBounds(437, 41, 93, 19);
		add(labelVendidoPor);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblProveedor.setBounds(437, 156, 70, 19);
		add(lblProveedor);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnIngresar.setBounds(518, 293, 164, 41);
		add(btnIngresar);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldNombre.setBounds(161, 37, 136, 20);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldPrecioCompra = new JTextField();
		textFieldPrecioCompra.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldPrecioCompra.setColumns(10);
		textFieldPrecioCompra.setBounds(161, 168, 136, 20);
		add(textFieldPrecioCompra);
		
		textFieldPrecioVenta = new JTextField();
		textFieldPrecioVenta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldPrecioVenta.setColumns(10);
		textFieldPrecioVenta.setBounds(161, 293, 136, 20);
		add(textFieldPrecioVenta);
		
		String[] opcionesDeUnidad = {"Gramos","Unidades"};
		Vector nombresProveedores = new Vector();
		
		JComboBox comboBoxProveedor = new JComboBox(nombresProveedores);
		comboBoxProveedor.setBounds(561, 158, 156, 19);
		add(comboBoxProveedor);
		
		JComboBox comboBoxVendidoPor = new JComboBox(opcionesDeUnidad);
		comboBoxVendidoPor.setBounds(561, 38, 156, 19);
		add(comboBoxVendidoPor);
		
		try
		{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
		String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
		Connection conexion =  DriverManager.getConnection(direccionURL); 

		CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [mostrarNombresProveedores]}"); // llamo procedimiento
		llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
		ResultSet resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
		
		while(resultadosDeProcedimiento.next())
		{
			nombresProveedores.add(resultadosDeProcedimiento.getString(1));
		}
		
		}
		
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
			
		} catch (SQLException e2)
		{
			e2.printStackTrace();
		}
		
		
		comboBoxProveedor.setSelectedIndex(0);
		
		btnIngresar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
						Connection conexion;
						Proveedor proveedor=null;
						boolean huboError=true;
							
						try
						{
							
							try
							{
							//conexion
							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
							String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
							conexion =  DriverManager.getConnection(direccionURL); ;
							
							
							// el id del proveedor que eligio
							CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [idProveedorConNombre](?)}"); // llamo procedimiento
							llamadaDeProcedimiento.setString(1, comboBoxProveedor.getSelectedItem().toString());
							llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
							ResultSet resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
							resultadosDeProcedimiento.next();
							
							// si es por peso
							if(comboBoxVendidoPor.getSelectedItem()=="Gramos")
							{
								

								// el objeto
								ProductoPorPeso productoPr = new ProductoPorPeso(0,textFieldNombre.getText(),
										Float.parseFloat(textFieldPrecioCompra.getText()) ,Float.parseFloat(textFieldPrecioVenta.getText()) ,resultadosDeProcedimiento.getInt(1) ,
										0);
								
								// en la bd
								CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [agregarProducto] (?,?,?,?,?)}"); // llamo procedimiento
								llamadaDeProcedimiento2.setString(1, productoPr.getNombre());
								llamadaDeProcedimiento2.setFloat(2, productoPr.getPrecio_compra());
								llamadaDeProcedimiento2.setFloat(3, productoPr.getPrecio_venta());
								llamadaDeProcedimiento2.setString(4,"g");
								llamadaDeProcedimiento2.setInt(5,productoPr.getProveedor());
								llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
							
								
								// agrego datos a la tabla
								Vector<Comparable> datos_productos = new Vector();
								
								datos_productos.add(productoPr.getNombre()); 
								datos_productos.add(productoPr.getPrecio_compra());
								datos_productos.add(productoPr.getPrecio_venta());
								datos_productos.add(productoPr.getStock());
								datos_productos.add("Gramos");
								datos_productos.add(comboBoxProveedor.getSelectedItem().toString());
								
								// agrego el proveedor al Array
								productos.add(productoPr);

							}
							
							if(comboBoxVendidoPor.getSelectedItem()=="Unidades")
							{
							
								
								// el objeto
								ProductoPorPeso productoUn = new ProductoPorPeso(0,textFieldNombre.getText(),
										Float.parseFloat(textFieldPrecioCompra.getText()) ,Float.parseFloat(textFieldPrecioVenta.getText()) ,resultadosDeProcedimiento.getInt(1) ,
										0);
								
								// en la bd
								CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [agregarProducto] (?,?,?,?,?)}"); // llamo procedimiento
								llamadaDeProcedimiento2.setString(1, productoUn.getNombre());
								llamadaDeProcedimiento2.setFloat(2, productoUn.getPrecio_compra());
								llamadaDeProcedimiento2.setFloat(3, productoUn.getPrecio_venta());
								llamadaDeProcedimiento2.setString(4,"un");
								llamadaDeProcedimiento2.setInt(5,productoUn.getProveedor());
								llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
							
								
								// agrego datos a la tabla
								Vector<Comparable> datos_productos = new Vector();
								
								datos_productos.add(productoUn.getNombre()); 
								datos_productos.add(productoUn.getPrecio_compra());
								datos_productos.add(productoUn.getPrecio_venta());
								datos_productos.add(productoUn.getStock());
								datos_productos.add("Unidades");
								datos_productos.add(comboBoxProveedor.getSelectedItem().toString());
								
								// agrego el proveedor al Array
								productos.add(productoUn);
								
							}
							
								conexion.close();
								huboError=false;
							}
							
							catch (ClassNotFoundException e1)
							{
								e1.printStackTrace();
								
							} catch (SQLException e2)
							{
								e2.printStackTrace();
							}
							
							new InterfazProductos(ventanaUnica);
						}
						
						
						catch(java.lang.NumberFormatException e3)
						{
							 JOptionPane.showMessageDialog(getParent(), "Error de formato en algún campo.", "Error",  JOptionPane.ERROR_MESSAGE);
						}

				
			}
	
		});
		
	}
}
