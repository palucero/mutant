package ar.com.mercadolibre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroMutante extends Thread {

	private int esMutante;

	private long cantidadMutante;
	private long cantidadHumano;
	private float indice;
	
	public RegistroMutante() {
		cantidadMutante=0;
		cantidadHumano=0;
		indice=0;
	}

	public void run() {
		try {

			String instanceConnectionName = "complete-sector-222611:southamerica-east1:mutantdb";
			String databaseName = "registros";
			String username = "root";
			String password = "root";

			String uriJDBC = "jdbc:mysql://google/" + databaseName + "?cloudSqlInstance=" + instanceConnectionName
					+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=" + username + "&password="
					+ password + "&useSSL=false";

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				connection = DriverManager.getConnection(uriJDBC);
				preparedStatement = connection.prepareStatement("INSERT INTO Cantidades (EsMutante) values (?)");
				preparedStatement.setInt(1, esMutante);
				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void Estadisticas() {

		try {

			String instanceConnectionName = "complete-sector-222611:southamerica-east1:mutantdb";
			String databaseName = "registros";
			String username = "root";
			String password = "root";

			String uriJDBC = "jdbc:mysql://google/" + databaseName + "?cloudSqlInstance=" + instanceConnectionName
					+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=" + username + "&password="
					+ password + "&useSSL=false";

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				connection = DriverManager.getConnection(uriJDBC);
				preparedStatement = connection.prepareStatement("select sum(EsMutante) as mutantes,count(*) as total from Cantidades;");
				ResultSet resultado = preparedStatement.executeQuery();
				resultado.next();
				cantidadMutante = resultado.getLong("mutantes");
				long total = 0;
				total = resultado.getLong("total");
				cantidadHumano = total - cantidadMutante;
				if (total > 0) {
					indice = ((float)cantidadMutante / total);
				} else {
					indice = 0;
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	

	public void setEsMutante(int esMutante) {
		this.esMutante = esMutante;
	}

	public long getCantidadMutante() {
		return cantidadMutante;
	}

	public long getCantidadHumano() {
		return cantidadHumano;
	}

	public float getIndice() {
		return indice;
	}

}
