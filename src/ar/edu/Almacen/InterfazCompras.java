package ar.edu.Almacen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazCompras extends JPanel
{
	private ArrayList<Compra> compras = new ArrayList<Compra>();
	JTable tablaCompras;
	
public InterfazCompras(JFrame ventanaUnica) 
	{

		ventanaUnica.setContentPane(this);
		this.setBounds(0, 0, 800,500);
		setLayout(null);
		ventanaUnica.validate();
		
		Vector<String> columnas= new Vector<String>();// vector de nombres de columnas
		
		columnas.add("Fecha");
		columnas.add("Productos");
		columnas.add("Gasto");
		columnas.add("Proveedor");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 130, 751, 294);
		add(scrollPane);
		
		DefaultTableModel modelo_tabla_compras = new DefaultTableModel(new Vector<>(),columnas);// guardo las columnas
		
		tablaCompras = new JTable(modelo_tabla_compras);
		scrollPane.setViewportView(tablaCompras);
		
		traerDatos(modelo_tabla_compras, tablaCompras);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(35, 64, 106, 23);
		add(btnAgregar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (tablaCompras.getSelectedRow()>-1)
				{
					int rowSelec = tablaCompras.getSelectedRow();
					
					borrarCompra(rowSelec, modelo_tabla_compras);
				}
			}

		});
		btnBorrar.setBounds(33, 98, 108, 23);
		add(btnBorrar);
		
		JButton btnBorrarregistro = new JButton("BorrarRegistro");
		btnBorrarregistro.setBounds(171, 96, 124, 23);
		add(btnBorrarregistro);
		
		JButton buttonModificar = new JButton("Modificar");
		buttonModificar.setBounds(171, 64, 118, 23);
		add(buttonModificar);
		
		btnAgregar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				new AgregarCompra(ventanaUnica);
			}
		});
		
		buttonModificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (tablaCompras.getSelectedRow()>-1)
				{
					int rowSelec = tablaCompras.getSelectedRow();
					
					ventanaUnica.setContentPane(new ModificarCompra(ventanaUnica,compras.get(rowSelec)));
					
				}
				
			}
		});
		
		
		btnBorrarregistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (tablaCompras.getSelectedRow()>-1)
				{
					int rowSelec = tablaCompras.getSelectedRow();
					
					borrarCompraRegistro(rowSelec, modelo_tabla_compras);
				}
			}
		});
	}

private void traerDatos(DefaultTableModel modelo_tabla_compras, JTable tablaCompras)
	{
		
		Connection conexion;
		final ResultSet resultadosDeProcedimiento1;
		int id_proveedor=0;
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			conexion =  DriverManager.getConnection(direccionURL); ;
			
			CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [mostrarCompras]}"); // llamo procedimiento
			llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
			ResultSet resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
		 
			CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [mostrarProductosXCompraConId] (?)}"); // llamo procedimiento

			CallableStatement llamada = conexion.prepareCall("{call  [nombreProveedorConId] (?) }");
			
			
			while(resultadosDeProcedimiento.next()) 
			{			
			
				Vector datos_compras = new Vector(); // vector para el modelo
				
				// objeto Compra
				Compra compra = new Compra(resultadosDeProcedimiento.getInt(1) , resultadosDeProcedimiento.getString(2), 
						new ArrayList<Producto>(), resultadosDeProcedimiento.getFloat(3));
				
				// agrego lo que quiero mostrar
				datos_compras.add(compra.getFecha()); 

				llamadaDeProcedimiento2.setInt(1, compra.getId_compra());
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
						
					compra.getProductos().add(ppp);
					
					productos=productos+ ppp.getNombre() + "  (" + resultadosDeProcedimiento2.getFloat(8) +"$)  X " + 
							resultadosDeProcedimiento2.getInt(9) + ",   ";
					
					id_proveedor = ppp.getProveedor();
					}
					
					if(resultadosDeProcedimiento2.getString(6).contains("un"))
					{

						ppu=new ProductoPorUnidad(resultadosDeProcedimiento2.getInt(1), resultadosDeProcedimiento2.getString(2),
								resultadosDeProcedimiento2.getFloat(3),resultadosDeProcedimiento2.getFloat(4),resultadosDeProcedimiento2.getInt(7),
								resultadosDeProcedimiento2.getFloat(5));
						
					compra.getProductos().add(ppu);
					
					productos = productos + ppu.getNombre() + "  ( " + resultadosDeProcedimiento2.getFloat(8) +" $)  X " + 
							resultadosDeProcedimiento2.getInt(9) + ",   ";
					
					id_proveedor = ppu.getProveedor();
					}
					
				}

				
				llamada.setInt(1, id_proveedor);
				llamada.execute();
				ResultSet resultados = llamada.getResultSet();
				resultados.next(); 
				
				datos_compras.add(productos);
				datos_compras.add(compra.getGasto()); 
				datos_compras.add(resultados.getString(1));
				
				// agrego el proveedor al Array
				compras.add(compra);
				modelo_tabla_compras.addRow(datos_compras);
				
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
private void borrarCompra(int rowSelec,DefaultTableModel modelo_tabla_compras) 
	{
		modelo_tabla_compras.removeRow(rowSelec); // saca visualmente
		Compra c = compras.get(rowSelec);
		
		//sacar de la bd
		
		Connection conexion;
		final ResultSet resultadosDeProcedimiento1;

		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			conexion =  DriverManager.getConnection(direccionURL); ;
			
			CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [borrarCompra] (?)}"); // llamo procedimiento
			llamadaDeProcedimiento1.setInt(1,c.getId_compra());
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
		
		compras.remove(rowSelec); //saco el objeto
	}
private void borrarCompraRegistro(int rowSelec,DefaultTableModel modelo_tabla_compras)
{
	modelo_tabla_compras.removeRow(rowSelec); // saca visualmente
	Compra c = compras.get(rowSelec);
	
	//sacar de la bd
	
	Connection conexion;
	final ResultSet resultadosDeProcedimiento1;

	try 
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
		String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
		conexion =  DriverManager.getConnection(direccionURL); ;
		
		CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [borrarCompraRegistro] (?)}"); // llamo procedimiento
		llamadaDeProcedimiento1.setInt(1,c.getId_compra());
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
	
	compras.remove(rowSelec); //saco el objeto

}
}
