package ar.edu.Almacen;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.toedter.calendar.JDateChooser;

import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class ModificarCompra extends JPanel
{
	private JTable table;
	private JTextField textFieldCantidad;
	private ArrayList<Producto> productosTempo = new ArrayList<Producto>();
	private ArrayList<Producto> productosSelec = new ArrayList<Producto>();
	private ArrayList<Integer> cantidades = new ArrayList<Integer>();
	private float costo=0;
	
	public ModificarCompra(JFrame ventanaUnica, Compra compra) 
	{
		
		costo=compra.getGasto();
		setBackground(Color.ORANGE);
		this.setBounds(0, 0, 800,500);
		setLayout(null);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setForeground(Color.WHITE);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFecha.setBounds(614, 47, 71, 32);
		add(lblFecha);
	
		JDateChooser calen=new JDateChooser();
		calen.setBounds(613, 90, 141, 24);
		add(calen);
		
		java.util.Date date = null;
		
		 try 
		 {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(compra.getFecha());
		 } 
		 catch (ParseException e) 
		 {
			e.printStackTrace();
		  }

		calen.setDate(date);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 62, 485, 354);
		add(scrollPane);
		
		Vector columnas = new Vector();
		columnas.add("Nombre");
		columnas.add("Cantidad");
		columnas.add("Precio");
		
		DefaultTableModel modelo_de_tabla_productos = new DefaultTableModel(new Vector<>(),columnas );
		
		table = new JTable(modelo_de_tabla_productos);
		scrollPane.setViewportView(table);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(636, 403, 141, 44);
		add(btnModificar);
		
		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setForeground(Color.WHITE);
		lblProductos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProductos.setBounds(35, 28, 91, 32);
		add(lblProductos);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(35, 427, 421, 20);
		add(comboBox);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(518, 426, 95, 23);
		add(btnIngresar);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setColumns(10);
		textFieldCantidad.setBounds(466, 427, 42, 20);
		add(textFieldCantidad);
		
		traerProductos(modelo_de_tabla_productos,compra, comboBox);
		mostrarProductos(comboBox, compra);
		
		btnModificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				compra.setFecha(LocalDateTime.ofInstant(calen.getDate().toInstant(),
						
						TimeZone.getDefault().toZoneId()).toLocalDate().toString());
				
				Connection conexion;
				final ResultSet resultadosDeProcedimiento1;
				try 
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
					String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
					conexion =  DriverManager.getConnection(direccionURL); ;
					
					CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [modificarCompra] (?,?,?)}"); // llamo procedimiento
					llamadaDeProcedimiento1.setInt(1, compra.getId_compra());
					llamadaDeProcedimiento1.setString(2, compra.getFecha());
					llamadaDeProcedimiento1.setFloat(3,costo);
					llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado	
		
					int contador=0;
					//ingreso productos por compra
					for(Producto p:productosSelec)
					{
					CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [ingresarProductosXCompra] (?,?,?,?) }"); // llamo procedimiento
					llamadaDeProcedimiento2.setInt(1,compra.getId_compra());
					llamadaDeProcedimiento2.setInt(2,p.getId_producto());
					llamadaDeProcedimiento2.setInt(3,cantidades.get(contador));
					llamadaDeProcedimiento2.setFloat(4,p.getPrecio_compra());
					llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado	
					contador++;
					}
					
					conexion.close();
				   
				} 
				catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
					
				} catch (SQLException e2)
				{
					e2.printStackTrace();
				}
				
				int contador2=0;
				for(Producto p: productosSelec)
				{
					try 
					{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//llamada del driver
						String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
						Connection conexion2 =  DriverManager.getConnection(direccionURL); 
						
						CallableStatement llamada = conexion2.prepareCall("{call [aumentarStockProductos] (?,?)}");
						llamada.setInt(1,p.getId_producto());
						llamada.setInt(2, cantidades.get(contador2));
						contador2++;
						llamada.execute();
						
						conexion2.close();
					}
					
					catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
						
					} catch (SQLException e2)
					{
						e2.printStackTrace();
					}
				}
				
				new InterfazCompras(ventanaUnica);
			}
			
			
		});
		
		
		btnIngresar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(Producto p:productosTempo)
				{
					if(p.getNombre()==comboBox.getSelectedItem())
					{
						ProductoPorPeso ppp = new ProductoPorPeso(0,"",0,0,0,0);
						
						try
						{
							
						if(p.getClass()==ppp.getClass())
						{
							Vector datos= new Vector();
					
							if(Integer.parseInt(textFieldCantidad.getText())%100==0 && Integer.parseInt(textFieldCantidad.getText())>0)
							{
							datos.add(p.getNombre());
							datos.add(textFieldCantidad.getText());
							datos.add(p.getPrecio_compra() * ( Integer.parseInt(textFieldCantidad.getText())/100) );
							costo = costo + p.getPrecio_compra() * ( Integer.parseInt(textFieldCantidad.getText())/100);
							cantidades.add(Integer.parseInt(textFieldCantidad.getText()));
							productosSelec.add(p);
							modelo_de_tabla_productos.addRow(datos);
							}
							
							else
							{
							JOptionPane.showMessageDialog(getParent(), "La cantidad de productos por peso "
									+ "debe ser de 100g o un múltiplo del mismo.", "Error",  JOptionPane.ERROR_MESSAGE);
							}
						
						}
						
						else
						{
							Vector datos= new Vector();
							
							if(Integer.parseInt(textFieldCantidad.getText())>0)
							{
								datos.add(p.getNombre());
								datos.add(textFieldCantidad.getText());
								datos.add(p.getPrecio_compra()* Integer.parseInt(textFieldCantidad.getText()));
								costo = costo + p.getPrecio_compra() * ( Integer.parseInt(textFieldCantidad.getText()));
								cantidades.add(Integer.parseInt(textFieldCantidad.getText()));
								productosSelec.add(p);
								modelo_de_tabla_productos.addRow(datos);
							}
							
							else
							{
							JOptionPane.showMessageDialog(getParent(), "La cantidad debe ser positiva.", "Error",  JOptionPane.ERROR_MESSAGE);
							}
					
						}
						
						
						}
						
						catch(java.lang.NumberFormatException e2)
						{
							JOptionPane.showMessageDialog(getParent(), "Debe ingresar una cantidad.", "Error",  JOptionPane.ERROR_MESSAGE);
						}
						
				
					}
				}

				
			}
		});
		
	}

	private void traerProductos(DefaultTableModel modelo_tabla_productos, Compra compra, JComboBox comboBox)
	{
		
		Connection conexion;
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			conexion =  DriverManager.getConnection(direccionURL); ;
				
		CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [mostrarProductosXCompraConId] (?)}"); // llamo procedimiento
		
			llamadaDeProcedimiento2.setInt(1, compra.getId_compra());
			llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
			ResultSet resultadosDeProcedimiento2 = llamadaDeProcedimiento2.getResultSet();  
			ProductoPorPeso ppp;
			ProductoPorUnidad ppu;
			String productos="";

			while(resultadosDeProcedimiento2.next())
			{

				Vector datos = new Vector();

				if(resultadosDeProcedimiento2.getString(6).contains("g"))
				{
				
					ppp=new ProductoPorPeso(resultadosDeProcedimiento2.getInt(1), resultadosDeProcedimiento2.getString(2),
							resultadosDeProcedimiento2.getFloat(3),resultadosDeProcedimiento2.getFloat(4),resultadosDeProcedimiento2.getInt(7),
							resultadosDeProcedimiento2.getFloat(5));
					
				
					productos=productos+ ppp.getNombre() + "  (" + resultadosDeProcedimiento2.getFloat(8) +"$)  X " + 
					resultadosDeProcedimiento2.getInt(9) + ",   ";
					datos.add(ppp.getNombre());
					datos.add(resultadosDeProcedimiento2.getInt(9));
					datos.add(ppp.getPrecio_compra()*(resultadosDeProcedimiento2.getInt(9)/100));
				
				}
				
				if(resultadosDeProcedimiento2.getString(6).contains("un"))
				{

					ppu=new ProductoPorUnidad(resultadosDeProcedimiento2.getInt(1), resultadosDeProcedimiento2.getString(2),
							resultadosDeProcedimiento2.getFloat(3),resultadosDeProcedimiento2.getFloat(4),resultadosDeProcedimiento2.getInt(7),
							resultadosDeProcedimiento2.getFloat(5));
									
					productos = productos + ppu.getNombre() + "  ( " + resultadosDeProcedimiento2.getFloat(8) +" $)  X " + 
							resultadosDeProcedimiento2.getInt(9) + ",   ";
				
					datos.add(ppu.getNombre());
					datos.add(resultadosDeProcedimiento2.getInt(9));
					datos.add(ppu.getPrecio_compra()*resultadosDeProcedimiento2.getInt(9));
				
				}
				
				modelo_tabla_productos.addRow(datos); 
			}// while
		}//try 
		
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
			
		} catch (SQLException e2)
		{
			e2.printStackTrace();
		}
	}
	
	public void mostrarProductos(JComboBox comboProductos,Compra compra)
	{
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			 Connection conexion2 =  DriverManager.getConnection(direccionURL); 
			
			CallableStatement llamadaDeProcedimiento = conexion2.prepareCall("{call [mostrarProductos]}"); // llamo procedimiento
			llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
			ResultSet resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
		 
			while(resultadosDeProcedimiento.next())
			{
			
					if(resultadosDeProcedimiento.getString(6).contains("g"))
					{
						
						ProductoPorPeso productoP = new ProductoPorPeso(resultadosDeProcedimiento.getInt(1),resultadosDeProcedimiento.getString(2),
								resultadosDeProcedimiento.getFloat(3),resultadosDeProcedimiento.getFloat(4),resultadosDeProcedimiento.getInt(7),
								resultadosDeProcedimiento.getInt(5));

						
						if(compra.getProductos().get(0).getProveedor()==productoP.getProveedor())
						{comboProductos.addItem(productoP.getNombre());
						productosTempo.add(productoP);
						}

					}
					
					if((resultadosDeProcedimiento.getString(6).contains("un")))
					{
						
						ProductoPorUnidad productoU = new ProductoPorUnidad(resultadosDeProcedimiento.getInt(1),resultadosDeProcedimiento.getString(2),
								resultadosDeProcedimiento.getFloat(3),resultadosDeProcedimiento.getFloat(4),resultadosDeProcedimiento.getInt(7),
								resultadosDeProcedimiento.getInt(5));
						
						if(compra.getProductos().get(0).getProveedor()==productoU.getProveedor())
						{	comboProductos.addItem(productoU.getNombre());  
							productosTempo.add(productoU);
						}

					
					}
				
			}
		
			conexion2.close();
		}
		
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
			
		} catch (SQLException e2)
		{
			e2.printStackTrace();
		}
	}
}
