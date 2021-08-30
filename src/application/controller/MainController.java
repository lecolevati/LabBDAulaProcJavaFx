package application.controller;

import java.sql.SQLException;

import application.model.Cliente;
import application.persistence.ClienteDao;
import application.persistence.IClienteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MainController {
	@FXML
	private TextField tfCpf;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfLogradouro;
	@FXML
	private TextField tfNumero;
	@FXML
	private Button btnBusca;
	@FXML
	private Button btnCadatrar;
	@FXML
	private Button btnAtualizar;
	@FXML
	private Button btnExcluir;

	// Event Listener on Button[#btnBusca].onAction
	@FXML
	public void acaoCadastro(ActionEvent event) {
		String cmd = event.getSource().toString();
		Cliente cli = new Cliente();
		try {
			IClienteDao cDao = new ClienteDao();
			cli = validaCampos(cmd, tfCpf, tfNome, tfLogradouro, tfNumero);
			if (cmd.contains("Cadastrar")) {
				if (cli != null) {
					String saida = cDao.insereCliente(cli);
					alertInfo(saida);
					
					limparCampos();
				} else {
					alertErro("Preencha os campos !");
				}
			}
			if (cmd.contains("Atualizar")) {
				if (cli != null) {
					String saida = cDao.atualizaCliente(cli);
					alertInfo(saida);
					
					limparCampos(); 
				} else {
					alertErro("Preencha os campos !");
				}
			}
			if (cmd.contains("Excluir")) {
				if (cli != null) {
					String saida = cDao.excluiCliente(cli);
					alertInfo(saida);
					
					limparCampos();
				} else {
					alertErro("Preencha os campos !");
				}
			}
			if (cmd.contains("Buscar")) {
				if (cli != null) {
					cli = cDao.consultaCliente(cli);
					tfNome.setText(cli.getNome());
					tfLogradouro.setText(cli.getLogradouro());
					tfNumero.setText(String.valueOf(cli.getNumero()));
				} else {
					alertErro("Preencha os campos !");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			alertErro(e.getMessage());
		}
	}

	private void limparCampos() {
		tfCpf.setText("");
		tfNome.setText("");
		tfLogradouro.setText("");
		tfNumero.setText("");		
	}

	private void alertErro(String erro) {
		Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
		alert.setHeaderText(erro);
		alert.setTitle("ERRO");
		alert.showAndWait();		
	}

	private void alertInfo(String saida) {
		Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
		alert.setHeaderText(saida);
		alert.setTitle("Sucesso");
		alert.showAndWait();
	}

	private Cliente validaCampos(String cmd, TextField tfCpf, TextField tfNome,
			TextField tfLogradouro, TextField tfNumero) {
		if (cmd.contains("Cadastrar") || cmd.contains("Atualizar")) {
			if(tfCpf.getText().trim().isEmpty() ||
					tfNome.getText().trim().isEmpty() ||
					tfLogradouro.getText().trim().isEmpty() ||
					tfNumero.getText().trim().isEmpty()) {
				return null;
			} else {
				Cliente cli = new Cliente();
				cli.setCpf(tfCpf.getText());
				cli.setNome(tfNome.getText());
				cli.setLogradouro(tfLogradouro.getText());
				cli.setNumero(Integer.parseInt(tfNumero.getText()));
				
				return cli;
			}
		}
		if (cmd.contains("Excluir") || cmd.contains("Busca")) {
			if(tfCpf.getText().trim().isEmpty()) {
				return null;
			} else {
				Cliente cli = new Cliente();
				cli.setCpf(tfCpf.getText());
				
				return cli;
			}
		}
		return null;
	}
}







