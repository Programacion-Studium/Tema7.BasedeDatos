package es.Studium.BasedeDatos;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
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
public class BasedeDatos extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	TextField idCliente = new TextField(20);
	TextField nombreCliente = new TextField(20);
	Button next = new Button("Próximo");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Tema7.Empresa?useSSL=false";
	String login = "root";
	String password = "Studium.2019;";
	String sentencia = "SELECT * FROM Empleados";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public BasedeDatos()
	{
		setLayout(new FlowLayout());
		setSize(200,200);
		setResizable(false);
		add(idCliente);
		add(nombreCliente);
		add(next);
		next.addActionListener(this);
		addWindowListener(this);
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
			statement=connection.createStatement();
			rs=statement.executeQuery(sentencia);
			rs.next();
			//Poner en los TextField los valores obtenidos del 1º
			idCliente.setText(Integer.toString(rs.getInt("idEmpleado")));
			nombreCliente.setText(rs.getString("nombreEmpleado"));
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new BasedeDatos();
	}
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{
		//cerrar los elementos de la base de datos
		try
		{
			rs.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e)
		{
			System.out.println("error al cerrar "+e.toString());
		}
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void actionPerformed(ActionEvent actionEvent)
	{
		try
		{
			//Si no hemos llegado al final
			if(rs.next())
			{
				//Poner en los TextField los valores obtenidos
				idCliente.setText(Integer.toString(rs.getInt("idEmpleado")));
				nombreCliente.setText(rs.getString("nombreEmpleado"));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL" + e.getMessage());
		}
	}
}