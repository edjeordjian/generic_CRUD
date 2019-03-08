package ar.edu.Almacen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;


public class Main {

	private JFrame ventanaUnica= new JFrame();

	public Main() 
	{
	ventanaUnica.setTitle("Almacen");
	ventanaUnica.setResizable(false);
	ventanaUnica.setBounds(300, 100, 800,500);
	ventanaUnica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JMenuBar menuPrincipal=new JMenuBar();
	ventanaUnica.setJMenuBar(menuPrincipal);
	JMenu menuArchivo=new JMenu("Archivo");
	menuPrincipal.add(menuArchivo);
	JMenuItem menuSalir=new JMenuItem("Salir");
	menuSalir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			System.exit(0);
		}
	});
	menuArchivo.add(menuSalir);
	
	JMenu menuCompra=new JMenu("Compras");
	JMenuItem menuAgregarCompra=new JMenuItem("Principal");
	menuCompra.setHorizontalAlignment(SwingConstants.CENTER);
	menuAgregarCompra.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0)
		{
			new InterfazCompras(ventanaUnica);
		}
	});
	menuCompra.add(menuAgregarCompra);
	menuPrincipal.add(menuCompra);
	
	JMenu menuVenta=new JMenu("Ventas");
	JMenuItem menuAgregarVenta=new JMenuItem("Principal");
	menuVenta.setHorizontalAlignment(SwingConstants.CENTER);
	menuAgregarVenta.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0)
		{
			new InterfazVentas(ventanaUnica);
		}
	});
	menuVenta.add(menuAgregarVenta);
	menuPrincipal.add(menuVenta);
	
	JMenu menuProveedores=new JMenu("Proveedores");
	JMenuItem menuAgregarProveedor=new JMenuItem("Principal");
	menuProveedores.setHorizontalAlignment(SwingConstants.CENTER);
	menuAgregarProveedor.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent arg0)
	{
		new InterfazProveedores(ventanaUnica);
	}
	});
	menuProveedores.add(menuAgregarProveedor);
	menuPrincipal.add(menuProveedores);
	
	JMenu menuProductos=new JMenu("Productos");
	JMenuItem menuAgregarProducto=new JMenuItem("Principal");
	menuAgregarProducto.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent arg0)
	{
		new InterfazProductos(ventanaUnica);
	}
	});
	menuProductos.add(menuAgregarProducto);
	menuPrincipal.add(menuProductos);
	
	JMenu menuAyuda=new JMenu("Ayuda");
	JMenuItem menuayudar=new JMenuItem("Ver");
	menuayudar.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent arg0)
	{
		new Ayuda(ventanaUnica);
	}
	});
	menuAyuda.add(menuayudar);
	menuPrincipal.add(menuAyuda);
	}
	
	public static void main(String[] args)
	{
		Main window = new Main();
		window.ventanaUnica.setVisible(true);
		
	}


}
