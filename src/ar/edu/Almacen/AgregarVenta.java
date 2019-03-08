package ar.edu.Almacen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;


public class AgregarVenta extends JPanel 
{
	private JTable table;
	private ArrayList<Producto> productosTempo = new ArrayList<Producto>();
	private ArrayList<Producto> productosSelec = new ArrayList<Producto>();
	private ArrayList<Integer> cantidades = new ArrayList<Integer>();

	private JTextField textFieldCantidad;
	private int id_proveedor_pd=0;
	private float costo=0;
	public AgregarVenta(JFrame ventanaUnica)
	{
		setBackground(new Color(128, 128, 128));
		this.setBounds(0, 0, 800,500);
		setLayout(null);
		ventanaUnica.setContentPane(this);
		setLayout(null);
		
		Vector columnas = new Vector();
		columnas.add("Nombre");
		columnas.add("Cantidad");
		columnas.add("Precio");
		DefaultTableModel modelo_de_tabla_productos = new DefaultTableModel(new Vector<Object>(),columnas );// guardo las columnas	
		
		JComboBox comboProductos = new JComboBox();
		comboProductos.setBounds(35, 408, 421, 20);
		add(comboProductos);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(532, 407, 95, 23);
		add(btnIngresar);
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBounds(684, 383, 95, 56);
		add(btnFinalizar);
		
		JScrollPane tablaProductos = new JScrollPane();
		tablaProductos.setBounds(35, 26, 485, 354);
		add(tablaProductos);
		
		table = new JTable(modelo_de_tabla_productos);
		tablaProductos.setViewportView(table);
		
		mostrarProductos(comboProductos);
		
		//coso calendar
		JDateChooser calen=new JDateChooser();
		calen.setBounds(623, 92, 104, 24);
		add(calen);
		
		JLabel lblFecha = new JLabel("Fecha :");
		lblFecha.setForeground(Color.WHITE);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFecha.setBounds(645, 46, 69, 36);
		add(lblFecha);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setBounds(466, 408, 42, 20);
		add(textFieldCantidad);
		textFieldCantidad.setColumns(10);

		
		btnIngresar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(Producto p:productosTempo)
				{
					if(p.getNombre()==comboProductos.getSelectedItem())
					{
						
						if(Integer.parseInt(textFieldCantidad.getText()) <= p.getStock())
						{
							
						p.setStock(p.getStock() - Integer.parseInt(textFieldCantidad.getText()));	
						
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
							datos.add(p.getPrecio_venta() * ( Integer.parseInt(textFieldCantidad.getText())/100) );
							costo = costo + p.getPrecio_venta() * ( Integer.parseInt(textFieldCantidad.getText())/100);
							cantidades.add(Integer.parseInt(textFieldCantidad.getText()));
							
							
							productosSelec.add(p);
							 id_proveedor_pd=p.getProveedor();
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
								datos.add(p.getPrecio_venta()* Integer.parseInt(textFieldCantidad.getText()));
								costo = costo + p.getPrecio_venta() * ( Integer.parseInt(textFieldCantidad.getText()));
								cantidades.add(Integer.parseInt(textFieldCantidad.getText()));
								productosSelec.add(p);
								 id_proveedor_pd=p.getProveedor();
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
						
						} // condicion stok
						
						else
						{
							JOptionPane.showMessageDialog(getParent(), "La cantidad excede al stock", "Error",  JOptionPane.ERROR_MESSAGE);
						}
						
					}
					
					
				}
				
			}
		});
		
		btnFinalizar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				Venta venta = null;
				
				//la fecha
				try
				{
					String fecha = LocalDateTime.ofInstant(calen.getDate().toInstant(),TimeZone.getDefault().toZoneId()).toLocalDate().toString();
				
				if(productosSelec.size()>0)
				{
				//ingreso compra
				try
				{
					
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
					String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
					Connection conexion =  DriverManager.getConnection(direccionURL); ;
					
					CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [ingresarVenta] (?,?)}");				
					llamadaDeProcedimiento.setString(1, fecha);
					llamadaDeProcedimiento.setFloat(2, costo);
					llamadaDeProcedimiento.execute();


					CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [ultimaVentaId]}");				
					llamadaDeProcedimiento2.execute();
					ResultSet resultados = llamadaDeProcedimiento2.getResultSet();

					resultados.next();
					venta=new Venta(resultados.getInt(1),resultados.getString(2),new ArrayList<>(),resultados.getFloat(3));
					conexion.close();
				}
				
				catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
					
				} catch (SQLException e2)
				{
					e2.printStackTrace();
				}
				
				int contador=0;
				//ingreso productos por compra
				for(Producto p:productosSelec)
				{

					try 
					{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//driver 
						String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
						Connection conexion2 =  DriverManager.getConnection(direccionURL); 
						
						CallableStatement llamadaDeProcedimiento1 = conexion2.prepareCall("{call [ingresarProductosXVenta] (?,?,?,?)}"); // llamo procedimiento
						llamadaDeProcedimiento1.setInt(1,venta.getId_venta());
						llamadaDeProcedimiento1.setInt(2,p.getId_producto());
						llamadaDeProcedimiento1.setInt(3,cantidades.get(contador));
						llamadaDeProcedimiento1.setFloat(4,p.getPrecio_venta());
						llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado	
				 
						
							conexion2.close();
						venta.getProductos().add(p);
					} 
					catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
						
					} catch (SQLException e2)
					{
						e2.printStackTrace();
					}
					contador++;
				}
				
				int contador2=0;
				for(Producto p: productosSelec)
				{
					try 
					{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//llamada del driver
						String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
						Connection conexion2 =  DriverManager.getConnection(direccionURL); 
						
						CallableStatement llamada = conexion2.prepareCall("{call [disminuirStockProductos] (?,?)}");
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
			}
				
				else
				{
					JOptionPane.showMessageDialog(getParent(), "Falta ingresar prodcutos.", "Error",  JOptionPane.ERROR_MESSAGE);
				}
				
			
				}// try fecha
				
				catch(java.lang.NullPointerException e3)
				{
					JOptionPane.showMessageDialog(getParent(), "Falta establecer la fecha.", "Error",  JOptionPane.ERROR_MESSAGE);
				
				}
				
			new InterfazVentas(ventanaUnica);
				
			}// action listener
			
		});// action performed
		
	}// funcion
	
	public void mostrarProductos(JComboBox comboProductos)
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
				if(resultadosDeProcedimiento.getInt(5)>0)

				{
					if(resultadosDeProcedimiento.getString(6).contains("g"))
					{
						
						CallableStatement llamadaDeProcedimiento2 = conexion2.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
						llamadaDeProcedimiento2.setInt(1, resultadosDeProcedimiento.getInt(7));
						llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
						ResultSet resultadosDeProcedimiento2 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
						resultadosDeProcedimiento2.next();
						
						ProductoPorPeso productoP = new ProductoPorPeso(resultadosDeProcedimiento.getInt(1),resultadosDeProcedimiento.getString(2),
								resultadosDeProcedimiento.getFloat(3),resultadosDeProcedimiento.getFloat(4),resultadosDeProcedimiento.getInt(7),
								resultadosDeProcedimiento.getInt(5));

						comboProductos.addItem(productoP.getNombre());  // en el combo

						productosTempo.add(productoP); // en el array
					}
					
					if((resultadosDeProcedimiento.getString(6).contains("un")))
					{
						CallableStatement llamadaDeProcedimiento2 = conexion2.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
						llamadaDeProcedimiento2.setInt(1, resultadosDeProcedimiento.getInt(7));
						llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
						ResultSet resultadosDeProcedimiento3 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
						resultadosDeProcedimiento3.next();
						
						ProductoPorUnidad productoU = new ProductoPorUnidad(resultadosDeProcedimiento.getInt(1),resultadosDeProcedimiento.getString(2),
								resultadosDeProcedimiento.getFloat(3),resultadosDeProcedimiento.getFloat(4),resultadosDeProcedimiento.getInt(7),
								resultadosDeProcedimiento.getInt(5));
						
						comboProductos.addItem(productoU.getNombre());  // en el combo

						productosTempo.add(productoU); // en el array
					
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
	
		
	}//super condicion
}
