package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class AdminBairroViewImpl extends Composite implements AdminBairroView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private Presenter presenter;
	@UiField TabLayoutPanel adminTabLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField ListBox bairroCidadeListBox;
	@UiField TextBox bairroNomeTextBox;
	@UiField Button bairroAdicionarButton;
	@UiField HTML bairrosWarningHTML;
	@UiField CellTable<Bairro> pesquisaBairroResultadoCellTable;
	@UiField Label pesquisaBairroResultadoLabel;
	@UiField SimplePager pesquisaBairroResultadoSimplePager;
	private ListDataProvider<Bairro> resultadoPesquisaBairro;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminBairroViewImpl> {
	}

	public AdminBairroViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaBairro = new ListDataProvider<Bairro>();
		this.resultadoPesquisaBairro.addDataDisplay(this.pesquisaBairroResultadoCellTable);
		this.pesquisaBairroResultadoSimplePager.setDisplay(this.pesquisaBairroResultadoCellTable);
	}

	@Override
	public void showWaitingPanel() {
		waitingPopUpPanel.setVisible(true);
		waitingPopUpPanel.show();		
	}

	@Override
	public void hideWaitingPanel() {
		waitingPopUpPanel.hide();
		waitingPopUpPanel.setVisible(false);
	}

	@Override
	public void initView() {
		this.selectThisTab();
		this.limparFormularios();
		this.limparResultadoPesquisa();
		this.presenter.buscarCidades();
		this.presenter.buscarBairros();
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(4, false);
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("bairroAdicionarButton")
	void onBairroAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.bairrosWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarBairro(populaBairro(), this.bairroCidadeListBox.getValue(this.bairroCidadeListBox.getSelectedIndex()));
		}
	}

	@Override
	public void setBairros(List<Bairro> bairros) {
		this.limparResultadoPesquisa();
		this.pesquisaBairroResultadoCellTable.setRowCount(bairros.size());
		this.resultadoPesquisaBairro.setList(bairros);
		this.mostrarResultadoPesquisa();		
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.bairroCidadeListBox.clear();
		this.bairroCidadeListBox.addItem("-- Escolha uma opção --", "");
		for (Cidade cidade : cidades) {
			this.bairroCidadeListBox.addItem(cidade.getNome());
		}
	}
	
	private Bairro populaBairro() {
		Bairro bairro = new Bairro();
		
		bairro.setNome(this.bairroNomeTextBox.getText());
		
		return bairro;
	}

	private void limparFormularios() {
		this.bairroNomeTextBox.setText("");
		this.bairrosWarningHTML.setHTML("");
		this.bairroCidadeListBox.setSelectedIndex(0);
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaBairroResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaBairroResultadoCellTable.removeColumn(0);
		}
		this.pesquisaBairroResultadoLabel.setText("");
		this.pesquisaBairroResultadoCellTable.setVisible(false);
		this.pesquisaBairroResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaBairroResultadoCellTable.getRowCount() == 0) {
			this.pesquisaBairroResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaBairroResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaBairroResultadoLabel.setText("Foram encontrado(s) " + this.pesquisaBairroResultadoCellTable.getRowCount() + " resultado(s).");
			this.pesquisaBairroResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaBairroResultadoCellTable.setVisible(true);
			
			TextColumn<Bairro> nome = new TextColumn<Bairro>() {
				@Override
				public String getValue(Bairro object) {
					return object.getNome();
				}
			};				
			
			TextColumn<Bairro> cidade = new TextColumn<Bairro>() {
				@Override
				public String getValue(Bairro object) {
					return object.getCidade().getName();
				}
			};			
			
			Delegate<String> deletarDelegate = new Delegate<String>() {
				@Override
				public void execute(String object) {
					if (Window.confirm("Deseja realmente apagar esta bairro?")) {
						presenter.apagarBairro(object);
					}
				}				
			};
			ActionCell<String> deletarCell = new ActionCell<String>("Apagar", deletarDelegate);
			Column<Bairro, String> deletarColumn = new Column<Bairro, String>(deletarCell) {
				@Override
				public String getValue(Bairro object) {
					return object.getNome();
				}
			};
			
			Delegate<Bairro> editarDelegate = new Delegate<Bairro>() {
				@Override
				public void execute(Bairro object) {
					popularManterBairro(object);
				}				
			};
			ActionCell<Bairro> editarCell = new ActionCell<Bairro>("Editar", editarDelegate);
			Column<Bairro, Bairro> editarColumn = new Column<Bairro, Bairro>(editarCell) {
				@Override
				public Bairro getValue(Bairro object) {
					return object;
				}
			};
			
			this.pesquisaBairroResultadoCellTable.addColumn(cidade, "Cidade");
			this.pesquisaBairroResultadoCellTable.addColumn(nome, "Nome");
			this.pesquisaBairroResultadoCellTable.addColumn(editarColumn, "");
			this.pesquisaBairroResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaBairroResultadoSimplePager.setVisible(true);
		}
		this.bairrosWarningHTML.setHTML("");
	}

	private void popularManterBairro(Bairro bairro) {
		this.bairroNomeTextBox.setText(bairro.getNome());
		this.bairroCidadeListBox.setSelectedIndex(
				obterIndice(this.bairroCidadeListBox, bairro.getCidade().getName()));

	}
	
	private int obterIndice(ListBox list, String valor) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(valor)) {
				return i;
			}
		}
		return 0;
	}
	
	private Validacoes validarAdicionarUsuario() {
		Validacoes validacoes = new Validacoes();
		

		if (this.bairroCidadeListBox.getSelectedIndex() == 0) {
			validacoes.add("Uma cidade precisa ser selecionada.");
		}
		if (bairroNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}		
		
		return validacoes;
	}
}
