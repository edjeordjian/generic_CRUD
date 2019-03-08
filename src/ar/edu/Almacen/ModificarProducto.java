package ar.edu.Almacen;

import java.awt.Color;
import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarProducto extends JPanel 
{
	private JTextField textFieldStock;

	public ModificarProducto(JFrame ventanaUnica, Producto producto, ArrayList<Producto> productos) 
	{
		
		setBackground(Color.ORANGE);
		this.setBounds(0, 0, 800,500);
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
		
		JLabel labelStock = new JLabel("Stock:");
		labelStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelStock.setBounds(446, 292, 99, 19);
		add(labelStock);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnModificar.setBounds(381, 361, 164, 41);
		add(btnModificar);
		
		JTextField textFieldNombre = new JTextField(producto.getNombre());
		textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldNombre.setBounds(161, 37, 136, 20);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JTextField textFieldPrecioCompra = new JTextField(Float.toString( producto.getPrecio_compra()));
		textFieldPrecioCompra.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldPrecioCompra.setColumns(10);
		textFieldPrecioCompra.setBounds(161, 168, 136, 20);
		add(textFieldPrecioCompra);
		
		JTextField textFieldPrecioVenta = new JTextField(Float.toString(producto.getPrecio_venta()));
		textFieldPrecioVenta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldPrecioVenta.setColumns(10);
		textFieldPrecioVenta.setBounds(161, 293, 136, 20);
		add(textFieldPrecioVenta);
		
		textFieldStock = new JTextField(Float.toString(producto.getStock()));
		textFieldStock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(579, 292, 136, 20);
		add(textFieldStock);
		
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
		
		
		CallableStatement llamada = conexion.prepareCall("{call [nombreProveedorConId] (?)}");
		llamada.setInt(1, producto.getProveedor());
		llamada.execute();
		ResultSet resultadosLlamada = llamada.getResultSet();
		resultadosLlamada.next();
		
		comboBoxProveedor.setSelectedItem(resultadosLlamada.getString(1));
		conexion.close();
		
		}
		
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
			
		} catch (SQLException e2)
		{
			e2.printStackTrace();
		}
		
		
		ProductoPorPeso productoP = new ProductoPorPeso(0,"",0,0,0,0);
		ProductoPorUnidad productoU = new ProductoPorUnidad(0,"",0,0,0,0);
		
		if(producto.getClass()== productoP.getClass())
			comboBoxVendidoPor.setSelectedIndex(0);
		else
			comboBoxVendidoPor.setSelectedIndex(1);

		btnModificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Connection conexion;
				
				try 
				{
					
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;IntegratedSecurity=true"; 
				conexion =  DriverManager.getConnection(direccionURL); 
						
				CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [modificarProducto] (?,?,?,?,?,?,?)}"); // llamo procedimiento
		
				CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [idProveedorConNombre](?)}"); // llamo procedimiento
				llamadaDeProcedimiento.setString(1, comboBoxProveedor.getSelectedItem().toString());
				llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
				ResultSet resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
				resultadosDeProcedimiento.next();
				
				
				if(comboBoxVendidoPor.getSelectedIndex()==0)// producto por peso
				{
					
					productoP.setId_producto(producto.getId_producto());
					productoP.setNombre(textFieldNombre.getText());
					productoP.setPrecio_compra(Float.parseFloat(textFieldPrecioCompra.getText()));
					productoP.setPrecio_venta(Float.parseFloat(textFieldPrecioVenta.getText()));
					productoP.setProveedor(resultadosDeProcedimiento.getInt(1));
					productoP.setStock(Float.parseFloat(textFieldStock.getText()));
					
					llamadaDeProcedimiento1.setInt(1,productoP.getId_producto());
					llamadaDeProcedimiento1.setString(2,productoP.getNombre());
					llamadaDeProcedimiento1.setFloat(3,productoP.getPrecio_compra());
					llamadaDeProcedimiento1.setFloat(4,productoP.getPrecio_venta());
					llamadaDeProcedimiento1.setString(5,"g");
					llamadaDeProcedimiento1.setInt(6,productoP.getProveedor());
					llamadaDeProcedimiento1.setFloat(7,productoP.getStock());
					
					llamadaDeProcedimiento1.execute();
					
					productos.remove(producto);
					productos.add(productoP);
				}
				
				else // producto por unidad
				{
					productoU.setId_producto(producto.getId_producto());
					productoU.setNombre(textFieldNombre.getText());
					productoU.setPrecio_compra(Float.parseFloat(textFieldPrecioCompra.getText()));
					productoU.setPrecio_venta(Float.parseFloat(textFieldPrecioVenta.getText()));
					productoU.setProveedor(resultadosDeProcedimiento.getInt(1));
					productoU.setStock(Float.parseFloat(textFieldStock.getText()));
					
					llamadaDeProcedimiento1.setInt(1,productoU.getId_producto());
					llamadaDeProcedimiento1.setString(2,productoU.getNombre());
					llamadaDeProcedimiento1.setFloat(3,productoU.getPrecio_compra());
					llamadaDeProcedimiento1.setFloat(4,productoU.getPrecio_venta());
					llamadaDeProcedimiento1.setString(5,"un");
					llamadaDeProcedimiento1.setInt(6,productoU.getProveedor());
					llamadaDeProcedimiento1.setFloat(7,productoU.getStock());
					
					llamadaDeProcedimiento1.execute();
					
					productos.remove(producto);
					productos.add(productoU);
				}
				
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
		});
		

	}
}
