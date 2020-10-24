package ar.edu.Almacen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazProveedores extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTable tablaProveedores;
	private ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
	private boolean buscaAhora=false;
	
	public InterfazProveedores(JFrame ventanaUnica)
	{
		ventanaUnica.setContentPane(this);
		this.setBounds(0, 0, 800,500);
		ventanaUnica.validate();
		
		Vector<String> columnas= new Vector<String>();// vector de nombres de columnas
		columnas.add("Nombre");
		columnas.add("Teléfono");
		columnas.add("Calle Domicilio");
		columnas.add("Altura Domicilio");
		columnas.add("Piso");
		columnas.add("Departamento");
		columnas.add("Especialidad");

		DefaultTableModel modelo_de_tabla_proveedores = new DefaultTableModel(new Vector<>(),columnas);// guardo las columnas	
		
		//tabla
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 141, 780, 298);
		add(scrollPane);

		traerDatos(modelo_de_tabla_proveedores);
		
		tablaProveedores = new JTable(modelo_de_tabla_proveedores);
		scrollPane.setViewportView(tablaProveedores);
		
		JButton botonAgregarProveedor = new JButton("Agregar");
		botonAgregarProveedor.setBounds(10, 66, 96, 33);
		add(botonAgregarProveedor);
		
		JButton botonModificar = new JButton("Modificar");
		botonModificar.setBounds(138, 66, 111, 33);
		add(botonModificar);
		
		JButton buttonBorrar = new JButton("Borrar");
		buttonBorrar.setBounds(276, 66, 71, 33);
		add(buttonBorrar);
		
		JButton btnOrdenar= new JButton("Ordenar Alfabeticamente");
		btnOrdenar.setBounds(90, 110, 198, 20);
		add(btnOrdenar);
		
		JTextField textFieldBuscar = new JTextField();
		textFieldBuscar.setBounds(531, 103, 244, 27);
		add(textFieldBuscar);
		textFieldBuscar.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBuscar.setBounds(531, 68, 79, 24);
		add(lblBuscar);		
		
		
		botonAgregarProveedor.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ventanaUnica.setContentPane(new AgregarProveedor(ventanaUnica,proveedores));
			}

		});
		
		botonModificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				if(tablaProveedores.getSelectedRow()>-1)
					ventanaUnica.setContentPane(new ModificarProveedor(ventanaUnica,proveedores.get(tablaProveedores.getSelectedRow())));
				
			}
		});
		
		buttonBorrar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(tablaProveedores.getSelectedRow()>-1)
				{
					int nro_columna_selec= tablaProveedores.getSelectedRow();
					
					borrarDato(nro_columna_selec,modelo_de_tabla_proveedores);
					
				}
			}

		});
		
		btnOrdenar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				for(int x=modelo_de_tabla_proveedores.getRowCount()-1; x>=0; x--)
				{
					modelo_de_tabla_proveedores.removeRow(x); 
				}
				
				proveedores.clear();
				
				Connection conexion;
				final ResultSet resultadosDeProcedimiento1;

				try 
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
					String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
					conexion =  DriverManager.getConnection(direccionURL); ;
					
					CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [mostrarProveedoresAlfabeticamente]}"); // llamo procedimiento
					llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado
					resultadosDeProcedimiento1 = llamadaDeProcedimiento1.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
				 
					while(resultadosDeProcedimiento1.next()) 
					{			
						Vector datos_proveedores = new Vector(); // vector para el modelo
						
						Proveedor proveedor = new Proveedor(resultadosDeProcedimiento1.getInt(1),resultadosDeProcedimiento1.getString(2), resultadosDeProcedimiento1.getString(3),
								  resultadosDeProcedimiento1.getString(4), resultadosDeProcedimiento1.getInt(5), resultadosDeProcedimiento1.getInt(6),
								  resultadosDeProcedimiento1.getString(7),resultadosDeProcedimiento1.getString(8));
						
						// agrego lo que quiero mostrar
						datos_proveedores.add(proveedor.getNombre()); 
						datos_proveedores.add(proveedor.getTelefono()); 
						datos_proveedores.add(proveedor.getCalle_domicilio()); 
						datos_proveedores.add( proveedor.getAltura_domicilio()); 
						datos_proveedores.add(proveedor.getPiso_domicilio()); 
						datos_proveedores.add( proveedor.getDepartamento_domicilio()); 
						datos_proveedores.add(proveedor.getEspecialidad()); 

						modelo_de_tabla_proveedores.addRow(datos_proveedores);
						proveedores.add(proveedor);
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
			
		});
		
		//buscar
		
		 textFieldBuscar.getDocument().addDocumentListener(
			        new javax.swing.event.DocumentListener() 
			        {
						@Override
						public void insertUpdate(DocumentEvent e) 
						{
							String textoBuscado= textFieldBuscar.getText();

							if(textoBuscado.length()>2)
							{
							
								for(int x=modelo_de_tabla_proveedores.getRowCount()-1; x>=0; x--)
								{
									modelo_de_tabla_proveedores.removeRow(x); 
								}
								
								
								for(Proveedor p:proveedores)
								{
										Vector datos_proveedores = new Vector();

											//condicion
											if( p.getNombre().contains(textoBuscado) || p.getEspecialidad().contains(textoBuscado) || p.getTelefono().contains(textoBuscado) ||
												p.getCalle_domicilio().contains(textoBuscado) || Integer.toString(p.getAltura_domicilio()).contains(textoBuscado) || Integer.toString(p.getPiso_domicilio()).contains(textoBuscado)
												|| p.getDepartamento_domicilio().contains(textoBuscado)
			 								)
			 								{
			 		
			 								// agregar al vector
			 								datos_proveedores.add(p.getNombre());
			 								datos_proveedores.add(p.getTelefono());
			 								datos_proveedores.add(p.getCalle_domicilio());
			 								datos_proveedores.add(p.getAltura_domicilio());
			 								datos_proveedores.add(p.getPiso_domicilio());
			 								datos_proveedores.add(p.getDepartamento_domicilio());
			 								datos_proveedores.add(p.getEspecialidad());

			 								}

										modelo_de_tabla_proveedores.addRow(datos_proveedores);
									
								}
								buscaAhora=true;
						  }
						}

						@Override
						public void removeUpdate(DocumentEvent e) 
						{
							String textoBuscado= textFieldBuscar.getText();

							if(textoBuscado.length()<1)
							{
							
								proveedores.clear();
								
								for(int x=modelo_de_tabla_proveedores.getRowCount()-1; x>=0; x--)
								{
									modelo_de_tabla_proveedores.removeRow(x); 
								}
								
								traerDatos(modelo_de_tabla_proveedores); 
								
								 
							}
							else
							insertUpdate(e);
	
						}

						@Override
						public void changedUpdate(DocumentEvent e) 
						{
							
						}

			        });

		
	}

	public void traerDatos(DefaultTableModel modelo_de_tabla_proveedores) 
	{
			Connection conexion;
			final ResultSet resultadosDeProcedimiento1;
			
			try 
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
				String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
				conexion =  DriverManager.getConnection(direccionURL); ;
				
				CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [mostrarProveedores]}"); // llamo procedimiento
				llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado
				resultadosDeProcedimiento1 = llamadaDeProcedimiento1.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
			 
				while(resultadosDeProcedimiento1.next()) 
				{			
					Vector datos_proveedores = new Vector(); // vector para el modelo
					
					// objeto proveedor
					Proveedor proveedor = new Proveedor(resultadosDeProcedimiento1.getInt(1),resultadosDeProcedimiento1.getString(2), resultadosDeProcedimiento1.getString(3),
							  resultadosDeProcedimiento1.getString(4), resultadosDeProcedimiento1.getInt(5), resultadosDeProcedimiento1.getInt(6),
							  resultadosDeProcedimiento1.getString(7),resultadosDeProcedimiento1.getString(8));
					
					// agrego lo que quiero mostrar
					datos_proveedores.add(proveedor.getNombre()); 
					datos_proveedores.add(proveedor.getTelefono()); 
					datos_proveedores.add(proveedor.getCalle_domicilio()); 
					datos_proveedores.add(proveedor.getAltura_domicilio()); 
					datos_proveedores.add(proveedor.getPiso_domicilio()); 
					datos_proveedores.add(proveedor.getDepartamento_domicilio()); 
					
					datos_proveedores.add(proveedor.getEspecialidad()); 
					// agrego el proveedor al Array
					proveedores.add(proveedor);

					modelo_de_tabla_proveedores.addRow(datos_proveedores);
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
	
	private void borrarDato(int nro_columna_selec,DefaultTableModel modelo_de_tabla_proveedores) 
	{

		modelo_de_tabla_proveedores.removeRow(nro_columna_selec);//saca visualmente
		
		//lo saca de la base de datos
		
		Connection conexion;
		final ResultSet resultadosDeProcedimiento1;

		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			conexion =  DriverManager.getConnection(direccionURL); ;
			
			CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [borrarProveedor] (?)}"); // llamo procedimiento
			llamadaDeProcedimiento1.setInt(1,proveedores.get(nro_columna_selec).getId_proveedor());
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
		
		
		proveedores.remove(nro_columna_selec);// saca el objeto
		
	}

}
