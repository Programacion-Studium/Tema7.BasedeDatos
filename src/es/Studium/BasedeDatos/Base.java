package es.Studium.BasedeDatos;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Base extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	TextField nombreCliente = new TextField(20);
	Button insertar = new Button("Insertar");
	Button borrar = new Button("Borrar");
	Dialog d = new Dialog(this, "Operación Inserción", true);
	Label e = new Label ("Operación realizada correctamente!");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Tema7.Empresa?useSSL=false";
	String login = "root";
	String password = "Studium.2019;";
	Connection connection = null;
	Statement statement = null;
	public Base()
	{
		setLayout(new FlowLayout());
		setSize(200,200);
		setResizable(false);
		add(nombreCliente);
		add(insertar);
		add(borrar);
		insertar.addActionListener(this);
		borrar.addActionListener(this);
		addWindowListener(this);
		// Diálogo
		d.setLayout(new FlowLayout());
		d.add(e);
		d.setSize(250,150);
		//Para poder cerrar el Diálogo
		d.addWindowListener(this);
		//Cargar el Driver
		try
		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
		}
		//Establecer la conexión con la base de datos
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
		//Preparar el statement
		try
		{
			statement =
					connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new Base();
	}
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{
		// Si es el Cerrar del diálogo
		if(d.hasFocus())
		{
			d.setVisible(false);
		}
		else
		{
			//Cerrar los elementos de la base de datos
			try
			{
				statement.close();
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error al cerrar "+e.toString());
			}
			System.exit(0);
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void actionPerformed(ActionEvent actionEvent)
	{
		// Hemos pulsado Insertar
		if(insertar.equals(actionEvent.getSource()))
		{
			try
			{
				statement.executeUpdate("INSERT INTO Empleados(nombreEmpleado)VALUES ('"+nombreCliente.getText()+"')");
						nombreCliente.setText("");
						d.setVisible(true);
			}
			catch(SQLException se)
			{
				System.out.println("Error en la sentencia SQL"+se.toString());
			}
		}
		else
		{
			nombreCliente.getText();
			nombreCliente.setText("");
		}
	}
}	
	