package ar.edu.Almacen;

import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfazProductos extends JPanel
{
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	private static final long serialVersionUID = 1L;
	private JTable tablaProductos;
	private JTextField textFieldBuscar;

	public InterfazProductos(JFrame ventanaUnica) 
	{
		ventanaUnica.setContentPane(this);
		this.setBounds(0, 0, 800,500);
		ventanaUnica.validate();
		
		Vector<String> columnas= new Vector<String>();// vector de nombres de columnas
		columnas.add("Nombre");
		columnas.add("Precio Compra");
		columnas.add("Precio Venta");
		columnas.add("Stock");
		columnas.add("Tipo Stock");
		columnas.add("Proveedor");

		DefaultTableModel modelo_de_tabla_productos = new DefaultTableModel(new Vector<>(),columnas);// guardo las columnas	
		
		//tabla
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 141, 780, 298);
		add(scrollPane);

		traerDatos(modelo_de_tabla_productos);
		
		tablaProductos = new JTable(modelo_de_tabla_productos);
		scrollPane.setViewportView(tablaProductos);
		
		JButton botonAgregarProducto = new JButton("Agregar");
		botonAgregarProducto.setBounds(21, 66, 89, 33);
		add(botonAgregarProducto);
		
		JButton botonModificar = new JButton("Modificar");
		botonModificar.setBounds(134, 66, 102, 33);
		add(botonModificar);
		
		JButton botonBorrar = new JButton("Borrar");
		botonBorrar.setBounds(270, 66, 102, 33);
		add(botonBorrar);
		
		JButton btnOrdenar = new JButton("Ordenar alfabeticamente");
		btnOrdenar.setBounds(10, 110, 179, 23);
		add(btnOrdenar);
		
		textFieldBuscar = new JTextField();
		textFieldBuscar.setBounds(531, 103, 244, 27);
		add(textFieldBuscar);
		textFieldBuscar.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBuscar.setBounds(531, 68, 79, 24);
		add(lblBuscar);		
		
		JButton btnAjustarPrecios = new JButton("Ajustar Precios");
		btnAjustarPrecios.setBounds(204, 110, 179, 25);
		add(btnAjustarPrecios);
		
		 textFieldBuscar.getDocument().addDocumentListener(
			        new javax.swing.event.DocumentListener() 
			        {
						@Override
						public void insertUpdate(DocumentEvent e) 
						{
							String textoBuscado= textFieldBuscar.getText();
							ResultSet resultadosDeProcedimiento2;
							Connection conexion = null;
							ProductoPorPeso ppp = new ProductoPorPeso(0,"",0,0,0,0);
							ProductoPorUnidad ppu = new ProductoPorUnidad(0,"",0,0,0,0);
							String gr= "Gramos";
							String un="Unidades";
							
							if(textoBuscado.length()>2)
							{
							
								for(int x=modelo_de_tabla_productos.getRowCount()-1; x>=0; x--)
								{
									modelo_de_tabla_productos.removeRow(x); 
								}
								
								for(Producto p: productos)
								{
								
									try
									{
										
										Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
										String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
										conexion =  DriverManager.getConnection(direccionURL); 
										
										CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
										llamadaDeProcedimiento2.setInt(1,p.getProveedor());
										llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
										resultadosDeProcedimiento2 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
										resultadosDeProcedimiento2.next();
										
										Vector datos_productos = new Vector();

										if(p.getClass()==ppp.getClass())// por peso
			 							{
											
											if( p.getNombre().contains(textoBuscado)  || gr.contains(textoBuscado) ||
			 								Float.toString(p.getPrecio_compra()).contains(textoBuscado) || Float.toString(p.getPrecio_venta()).contains(textoBuscado)
			 								|| resultadosDeProcedimiento2.getString(2).contains(textoBuscado) || Float.toString(p.getStock()).contains(textoBuscado)
			 								)
			 								{
			 		
			 								datos_productos.add(p.getNombre());
			 								datos_productos.add(p.getPrecio_compra());
			 								datos_productos.add(p.getPrecio_venta());
			 								datos_productos.add(p.getStock());
			 								datos_productos.add("Gramos");
			 								datos_productos.add(resultadosDeProcedimiento2.getString(2));

			 								}
			 						
			 							}
			 							
			 							else //por cantidad
			 							{	
			 								if( p.getNombre().contains(textoBuscado)  || un.contains(textoBuscado) ||
					 								Float.toString(p.getPrecio_compra()).contains(textoBuscado) || Float.toString(p.getPrecio_venta()).contains(textoBuscado)
					 								|| resultadosDeProcedimiento2.getString(2).contains(textoBuscado) || Float.toString(p.getStock()).contains(textoBuscado)
					 								)
					 						{
					 		
			 								
				 							datos_productos.add(p.getNombre());
				 							datos_productos.add(p.getPrecio_compra());
											datos_productos.add(p.getPrecio_venta());
											datos_productos.add(p.getStock());
											datos_productos.add("Unidades");
											datos_productos.add(resultadosDeProcedimiento2.getString(2));

				 						
					 						}
			 								
			 							}
										
										modelo_de_tabla_productos.addRow(datos_productos);
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
							}
						}

						@Override
						public void removeUpdate(DocumentEvent e) 
						{
							String textoBuscado= textFieldBuscar.getText();
							
							if(textoBuscado.length()<1)
							{
								
								productos.clear();
								
								for(int x=modelo_de_tabla_productos.getRowCount()-1; x>=0; x--)
								{
									modelo_de_tabla_productos.removeRow(x); 
								}
						
								
								traerDatos(modelo_de_tabla_productos); 
							}
							
							else
							insertUpdate(e); 
						}

						@Override
						public void changedUpdate(DocumentEvent e) 
						{
							
						}

			        });

			botonAgregarProducto.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					ventanaUnica.setContentPane(new AgregarProducto(ventanaUnica, productos));
				}
			});
		
		botonModificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(tablaProductos.getSelectedRow()>-1)
				{
					ventanaUnica.setContentPane( new ModificarProducto(ventanaUnica, productos.get(tablaProductos.getSelectedRow()) ,productos) );
				}
			}
		});
		
		botonBorrar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(tablaProductos.getSelectedRow()>-1)
				{
					int nro_columna_selec= tablaProductos.getSelectedRow();
					
					borrarDato(nro_columna_selec,modelo_de_tabla_productos);
				}
			}

		});
		
		btnOrdenar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(int x=modelo_de_tabla_productos.getRowCount()-1; x>=0; x--)
				{
					modelo_de_tabla_productos.removeRow(x); 
					
				}
				productos.clear();
				DecimalFormat formato = new DecimalFormat("0.00");
				Connection conexion;
				final ResultSet resultadosDeProcedimiento1;

				try 
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
					String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
					conexion =  DriverManager.getConnection(direccionURL); 
					
					CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [mostrarProductosAlfabeticamente]}"); // llamo procedimiento
					llamadaDeProcedimiento1.execute();// ejecutar procedimiento almacenado
					resultadosDeProcedimiento1 = llamadaDeProcedimiento1.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
					while(resultadosDeProcedimiento1.next()) 
					{			
						Vector datos_productos = new Vector(); // vector para el modelo
						
						// objeto producto
						if(resultadosDeProcedimiento1.getString(6).contains("g"))
						{
							
							CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
							llamadaDeProcedimiento2.setInt(1, resultadosDeProcedimiento1.getInt(7));
							llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
							ResultSet resultadosDeProcedimiento2 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
							resultadosDeProcedimiento2.next();
							
							ProductoPorPeso productoP = new ProductoPorPeso(resultadosDeProcedimiento1.getInt(1),resultadosDeProcedimiento1.getString(2),
									resultadosDeProcedimiento1.getFloat(3),resultadosDeProcedimiento1.getFloat(4),resultadosDeProcedimiento1.getInt(7),
									resultadosDeProcedimiento1.getInt(5));
							
						
							datos_productos.add(productoP.getNombre()); 
							datos_productos.add(formato.format(productoP.getPrecio_compra()));
							datos_productos.add(formato.format(productoP.getPrecio_venta()));
							datos_productos.add(formato.format(productoP.getStock()));
							datos_productos.add("Gramos");
							datos_productos.add(resultadosDeProcedimiento2.getString(2));
							
							
							// agrego el proveedor al Array
							productos.add(productoP);
							modelo_de_tabla_productos.addRow(datos_productos);
						}
						
						if((resultadosDeProcedimiento1.getString(6).contains("un")))
						{
							CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
							llamadaDeProcedimiento2.setInt(1, resultadosDeProcedimiento1.getInt(7));
							llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
							ResultSet resultadosDeProcedimiento3 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
							resultadosDeProcedimiento3.next();
							
							ProductoPorUnidad productoU = new ProductoPorUnidad(resultadosDeProcedimiento1.getInt(1),resultadosDeProcedimiento1.getString(2),
									resultadosDeProcedimiento1.getFloat(3),resultadosDeProcedimiento1.getFloat(4),resultadosDeProcedimiento1.getInt(7),
									resultadosDeProcedimiento1.getInt(5));
							
							
							datos_productos.add(productoU.getNombre()); 
							datos_productos.add(formato.format(productoU.getPrecio_compra()));
							datos_productos.add(formato.format(productoU.getPrecio_venta()));
							datos_productos.add(formato.format(productoU.getStock()));
							datos_productos.add("Unidades");
							datos_productos.add(resultadosDeProcedimiento3.getString(2));
							
							// agrego el proveedor al Array
							productos.add(productoU);
							modelo_de_tabla_productos.addRow(datos_productos);
						}
			
				   
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
		
		btnAjustarPrecios.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new AjustarPreciosProductos(ventanaUnica);
			}
		});
		
	}

	private void traerDatos(DefaultTableModel modelo_de_tabla_productos) 
	{
		DecimalFormat formato = new DecimalFormat("0.00");
		Connection conexion;
		final ResultSet resultadosDeProcedimiento;
		ResultSet resultadosDeProcedimiento2;
		ResultSet resultadosDeProcedimiento3;

		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			conexion =  DriverManager.getConnection(direccionURL); ;
			
			CallableStatement llamadaDeProcedimiento = conexion.prepareCall("{call [mostrarProductos]}"); // llamo procedimiento
			llamadaDeProcedimiento.execute();// ejecutar procedimiento almacenado
			resultadosDeProcedimiento = llamadaDeProcedimiento.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
			
			
			while(resultadosDeProcedimiento.next()) 
			{			
				Vector datos_productos = new Vector(); // vector para el modelo
				
				// objeto producto
				if(resultadosDeProcedimiento.getString(6).contains("g"))
				{
					
					CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
					llamadaDeProcedimiento2.setInt(1, resultadosDeProcedimiento.getInt(7));
					llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
					resultadosDeProcedimiento2 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
					resultadosDeProcedimiento2.next();
					
					ProductoPorPeso productoP = new ProductoPorPeso(resultadosDeProcedimiento.getInt(1),resultadosDeProcedimiento.getString(2),
							resultadosDeProcedimiento.getFloat(3),resultadosDeProcedimiento.getFloat(4),resultadosDeProcedimiento.getInt(7),
							resultadosDeProcedimiento.getInt(5));
					
					datos_productos.add(productoP.getNombre()); 
					datos_productos.add(formato.format(productoP.getPrecio_compra()));
					datos_productos.add(formato.format(productoP.getPrecio_venta()));
					datos_productos.add(formato.format(productoP.getStock()));
					datos_productos.add("Gramos");
					datos_productos.add(resultadosDeProcedimiento2.getString(2));
					
					
					// agrego el proveedor al Array
					productos.add(productoP);

				}
				
				if((resultadosDeProcedimiento.getString(6).contains("un")))
				{
					CallableStatement llamadaDeProcedimiento2 = conexion.prepareCall("{call [proveedorConId](?)}"); // llamo procedimiento
					llamadaDeProcedimiento2.setInt(1, resultadosDeProcedimiento.getInt(7));
					llamadaDeProcedimiento2.execute();// ejecutar procedimiento almacenado
					resultadosDeProcedimiento3 = llamadaDeProcedimiento2.getResultSet();  // guardo en una variable lo que devuelve el procedimiento almacenado
					resultadosDeProcedimiento3.next();
					
					ProductoPorUnidad productoU = new ProductoPorUnidad(resultadosDeProcedimiento.getInt(1),resultadosDeProcedimiento.getString(2),
							resultadosDeProcedimiento.getFloat(3),resultadosDeProcedimiento.getFloat(4),resultadosDeProcedimiento.getInt(7),
							resultadosDeProcedimiento.getInt(5));
					
					datos_productos.add(productoU.getNombre()); 
					datos_productos.add(formato.format(productoU.getPrecio_compra()));
					datos_productos.add(formato.format(productoU.getPrecio_venta()));
					datos_productos.add(formato.format(productoU.getStock()));
					datos_productos.add("Unidades");
					datos_productos.add(resultadosDeProcedimiento3.getString(2));
					
					// agrego el proveedor al Array
					productos.add(productoU);
					
				}
			
				modelo_de_tabla_productos.addRow(datos_productos);
			
				
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
	
	private void borrarDato(int nro_columna_selec,DefaultTableModel modelo_de_tabla_productos) 
	{
		modelo_de_tabla_productos.removeRow(nro_columna_selec);//saca visualmente
		
		//lo saca de la base de datos
		
		Connection conexion;
		final ResultSet resultadosDeProcedimiento1;

		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//motor de sql
			String direccionURL = "jdbc:sqlserver://localhost:1433;dataBaseName=Almacen;integratedSecurity=true"; //direccion de la base de datos
			conexion =  DriverManager.getConnection(direccionURL); ;
			
			CallableStatement llamadaDeProcedimiento1 = conexion.prepareCall("{call [borrarProducto] (?)}"); // llamo procedimiento
			llamadaDeProcedimiento1.setInt(1,productos.get(nro_columna_selec).getId_producto());
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
		
		productos.remove(nro_columna_selec);// saca el objeto
		
	}
}
