package ar.edu.Almacen;

import java.util.ArrayList;

public class Venta
{
	private int id_venta;
	private String fecha;
	private ArrayList<Producto> productos = new  ArrayList<Producto>();
	private float ingreso;
	
	public Venta(int id_venta,String fecha, ArrayList<Producto> productos,float ingreso) 
	{
		this.id_venta= id_venta;
		this.fecha = fecha;
		this.productos = productos;
		this.ingreso=ingreso;
	}

	public float getIngreso() 
	{
		return ingreso;
	}

	public void setGasto(float gasto)
	{
		this.ingreso = gasto;
	}

	public int getId_venta()
	{
		return id_venta;
	}

	public void setId_compra(int Id_compra) 
	{
		this.id_venta= id_venta;
	}

	public ArrayList<Producto> getProductos() 
	{
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) 
	{
		this.productos = productos;
	}

	public String getFecha() 
	{
		return fecha;
	}

	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}

}
