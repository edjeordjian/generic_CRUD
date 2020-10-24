package ar.edu.Almacen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InterfazVentas extends JPanel
{

	private ArrayList<Venta> ventas = new ArrayList<Venta>();
	
	private JTable tablaVentas;
	
	public InterfazVentas(JFrame ventanaUnica) 
	{	
			ventanaUnica.setContentPane(this);
			this.setBounds(0, 0, 800,500);
			setLayout(null);
			ventanaUnica.validate();
			
			Vector<String> columnas= new Vector<String>();// vector de nombres de columnas
			
			columnas.add("Fecha");
			columnas.add("Productos");
			columnas.add("Ingreso");

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(26, 130, 751, 294);
			add(scrollPane);
			
			DefaultTableModel modelo_tabla_ventas = new DefaultTableModel(new Vector<>(),columnas);// guardo las columnas
			
			tablaVentas = new JTable(modelo_tabla_ventas);
			scrollPane.setViewportView(tablaVentas);
			
			traerDatos(modelo_tabla_ventas, tablaVentas);
			
			JButton btnAgregar = new JButton("Agregar");
			btnAgregar.setBounds(35, 61, 106, 23);
			add(btnAgregar);
			
			JButton buttonBorrar = new JButton("Borrar");
			buttonBorrar.setBounds(35, 95, 106, 23);
			add(buttonBorrar);
			
			JButton btnBorrarr = new JButton("BorrarRegistro");
			btnBorrarr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					int rowSelec = tablaVentas.getSelectedRow();
					
					borrarRegistroVenta(rowSelec, modelo_tabla_ventas);
				}
			});
			btnBorrarr.setBounds(166, 96, 138, 23);
			add(btnBorrarr);
			
			JButton botonModificar = new JButton("Modificar");
			botonModificar.setBounds(166, 61, 138, 23);
			add(botonModificar);
			
			btnAgregar.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					new AgregarVenta(ventanaUnica);
				}
			});
			
			buttonBorrar.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					int rowSelec = tablaVentas.getSelectedRow();
					
					borrarVenta(rowSelec, modelo_tabla_ventas);
				}

			});
			
			botonModificar.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if (tablaVentas.getSelectedRow()>-1)
					{
						int rowSelec = tablaVentas.getSelectedRow();
						
						ventanaUnica.setContentPane(new ModificarVenta(ventanaUnica,ventas.get(rowSelec)));
						
					}
				}
			});
			
		}

private void traerDatos(DefaultTableModel modelo_tabla_compras, JTable tablaVentas)
		{
			
			Connection conexion;
			final ResultSet resultadosDeProcedimiento1;
			
			try 
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
				String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
				conexion =  DriverManager.getConnection(direccionURL); ;
				
				CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [mostrarVentas]}"); // llamo procedimiento
				llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
				ResultSet resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
			 
				CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [mostrarProductosXVentaConId] (?)}"); // llamo procedimiento

				while(resultadosDeProcedimiento.next()) 
				{			
				
					Vector datos_ventas = new Vector(); // vector para el modelo
					
					// objeto Compra
					Venta venta = new Venta(resultadosDeProcedimiento.getInt(1) , resultadosDeProcedimiento.getString(2), 
							new ArrayList<Producto>(), resultadosDeProcedimiento.getFloat(3));
					
					// agrego lo que quiero mostrar
					datos_ventas.add(venta.getFecha()); 
					llamadaDeProcedimiento2.setInt(1, venta.getId_venta());
					llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
					ResultSet resultadosDeProcedimiento2 = llamadaDeProcedimiento2.getResultSet();  
					ProductoPorPeso ppp;
					ProductoPorUnidad ppu;
					String productos="";

					while(resultadosDeProcedimiento2.next())
					{
											
						if(resultadosDeProcedimiento2.getString(6).contains("g"))
						{
						
							ppp=new ProductoPorPeso(resultadosDeProcedimiento2.getInt(1), resultadosDeProcedimiento2.getString(2),
									resultadosDeProcedimiento2.getFloat(3),resultadosDeProcedimiento2.getFloat(4),resultadosDeProcedimiento2.getInt(7),
									resultadosDeProcedimiento2.getFloat(5));
							
						venta.getProductos().add(ppp);
						
						productos=productos+ ppp.getNombre() + "  ( " + resultadosDeProcedimiento2.getFloat(8) +" $),     "
								+ " X " + 
						resultadosDeProcedimiento2.getInt(9) + ",          ";
						}
						
						if(resultadosDeProcedimiento2.getString(6).contains("un"))
						{

							ppu=new ProductoPorUnidad(resultadosDeProcedimiento2.getInt(1), resultadosDeProcedimiento2.getString(2),
									resultadosDeProcedimiento2.getFloat(3),resultadosDeProcedimiento2.getFloat(4),resultadosDeProcedimiento2.getInt(7),
									resultadosDeProcedimiento2.getFloat(5));
							
						venta.getProductos().add(ppu);
						
						productos = productos + ppu.getNombre() + "  ( " + resultadosDeProcedimiento2.getFloat(8) +" $)  X " + 
						resultadosDeProcedimiento2.getInt(9) + ",          ";
						
						}
						
					}

					 
					datos_ventas.add(productos);
					datos_ventas.add(venta.getIngreso()); 
					
					// agrego el proveedor al Array
					ventas.add(venta);
					modelo_tabla_compras.addRow(datos_ventas);
					
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
			
		}
private void borrarVenta(int rowSelec,	DefaultTableModel modelo_tabla_ventas) 
		{
			modelo_tabla_ventas.removeRow(rowSelec); // saca visualmente
			Venta d = ventas.get(rowSelec);
			
			//sacar de la bd
			
			Connection conexion;
			final ResultSet resultadosDeProcedimiento1;

			try 
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
				String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
				conexion =  DriverManager.getConnection(direccionURL); ;
				
				CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [borrarVenta] (?)}"); // llamo procedimiento
				llamadaDeProcedimiento1.setInt(1,d.getId_venta());
				llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado	
				conexion.close();
			   
			} 
			catch (ClassNotFoundException e1)
			{
				e1.printStackTrace();
				
			} catch (SQLException e2)
			{
				e2.printStackTrace();
			}
			
			
			ventas.remove(rowSelec); //saco el objet
		}
private void borrarRegistroVenta(int rowSelec,	DefaultTableModel modelo_tabla_ventas)
{
	modelo_tabla_ventas.removeRow(rowSelec); // saca visualmente
	Venta d = ventas.get(rowSelec);
	
	//sacar de la bd
	
	Connection conexion;
	final ResultSet resultadosDeProcedimiento1;

	try 
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
		String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
		conexion =  DriverManager.getConnection(direccionURL); ;
		
		CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [borrarVentaRegistro] (?)}"); // llamo procedimiento
		llamadaDeProcedimiento1.setInt(1,d.getId_venta());
		llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado	
		conexion.close();
	   
	} 
	catch (ClassNotFoundException e1)
	{
		e1.printStackTrace();
		
	} catch (SQLException e2)
	{
		e2.printStackTrace();
	}
	ventas.remove(rowSelec); //saco el objet
}
}
