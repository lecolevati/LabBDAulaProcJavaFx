package application.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import application.model.Cliente;

public class ClienteDao implements IClienteDao {

	private Connection c;
	
	public ClienteDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}

	@Override
	public String insereCliente(Cliente cli) throws SQLException {
		// O insert está baseado em uma procedure que valida o CPF antes de inserir
		// {CALL nome_sp (param)}
		String sql = "{CALL sp_valida_insert_cadastro(?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, cli.getCpf());
		cs.setString(2, cli.getNome());
		cs.setString(3, cli.getLogradouro());
		cs.setInt(4, cli.getNumero());
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(5);
		cs.close();
		
		return saida;
	}

	@Override
	public String atualizaCliente(Cliente cli) throws SQLException {
		String sql = "UPDATE cadastro SET nome = ?, logradouro = ?, numero = ? WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getNome());
		ps.setString(2, cli.getLogradouro());
		ps.setInt(3, cli.getNumero());
		ps.setString(4, cli.getCpf());
		
		ps.execute();
		ps.close();
		return "Atualizado com sucesso !"; 
	}

	@Override
	public String excluiCliente(Cliente cli) throws SQLException {
		String sql = "DELETE cadastro WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		
		ps.execute();
		ps.close();
		return "Excluido com sucesso !"; 
	}

	@Override
	public Cliente consultaCliente(Cliente cli) throws SQLException {
		String sql = "SELECT cpf, nome, logradouro, numero FROM cadastro WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			cli.setNome(rs.getString("nome"));
			cli.setLogradouro(rs.getString("logradouro"));
			cli.setNumero(rs.getInt("numero"));
		}
		rs.close();
		ps.close();
		
		return cli;
	}

}
