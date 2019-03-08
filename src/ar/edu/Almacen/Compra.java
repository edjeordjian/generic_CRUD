package ar.edu.Almacen;

import java.util.ArrayList;

public class Compra
{
	private int id_compra;
	private String fecha;
	private ArrayList<Producto> productos = new  ArrayList<Producto>();
	private float gasto;
	
	public Compra(int id_compra,String fecha, ArrayList<Producto> productos,float gasto) 
	{
		this.id_compra = id_compra;
		this.fecha = fecha;
		this.productos = productos;
		this.gasto=gasto;
	}

	public float getGasto() 
	{
		return gasto;
	}

	public void setGasto(float gasto)
	{
		this.gasto = gasto;
	}

	public int getId_compra()
	{
		return id_compra;
	}

	public void setId_compra(int Id_compra) 
	{
		this.id_compra= id_compra;
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
