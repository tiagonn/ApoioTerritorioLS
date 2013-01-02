package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.enumeration.QuantidadeSurdosMapaEnum;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class AdminCidadeViewImpl extends Composite implements AdminCidadeView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private Presenter presenter;
	@UiField TabLayoutPanel adminTabLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField TextBox cidadeNomeTextBox;
	@UiField TextBox cidadeUFTextBox;
	@UiField TextBox cidadePaisTextBox;
	@UiField ListBox cidadeQuantidadeSurdosMapaListBox;
	@UiField TextBox cidadeLatitudeTextBox;
	@UiField TextBox cidadeLongitudeTextBox;
	@UiField CheckBox cidadeUtilizarBairroBuscaEnderecoCheckBox;
	@UiField Button cidadeAdicionarButton;
	@UiField HTML cidadesWarningHTML;
	@UiField CellTable<Cidade> pesquisaCidadeResultadoCellTable;
	@UiField Label pesquisaCidadeResultadoLabel;
	@UiField SimplePager pesquisaCidadeResultadoSimplePager;
	private ListDataProvider<Cidade> resultadoPesquisaCidade;
	private Long idSelecionado;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminCidadeViewImpl> {
	}

	public AdminCidadeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaCidade = new ListDataProvider<Cidade>();
		this.resultadoPesquisaCidade.addDataDisplay(this.pesquisaCidadeResultadoCellTable);
		this.pesquisaCidadeResultadoSimplePager.setDisplay(this.pesquisaCidadeResultadoCellTable);
		this.iniciarQuantidadeSurdosMapaListBox();
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
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(2, false);
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("cidadeAdicionarButton")
	void onCidadeAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.cidadesWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarCidade(populaCidade());
		}
	}

	@Override
	public void setCidades(List<Cidade> cidades) {
		this.limparResultadoPesquisa();
		this.pesquisaCidadeResultadoCellTable.setRowCount(cidades.size());
		this.resultadoPesquisaCidade.setList(cidades);
		this.mostrarResultadoPesquisa();		
	}
	
	private Cidade populaCidade() {
		Cidade cidade = new Cidade();
		
		cidade.setId(idSelecionado);
		cidade.setNome(this.cidadeNomeTextBox.getText());
		cidade.setUF(this.cidadeUFTextBox.getText());
		cidade.setPais(this.cidadePaisTextBox.getText());
		cidade.setLatitudeCentro(Double.valueOf(this.cidadeLatitudeTextBox.getText()));
		cidade.setLongitudeCentro(Double.valueOf(this.cidadeLongitudeTextBox.getText()));
		cidade.setUtilizarBairroBuscaEndereco(this.cidadeUtilizarBairroBuscaEnderecoCheckBox.getValue());
		cidade.setQuantidadeSurdosMapa(
				Integer.valueOf(this.cidadeQuantidadeSurdosMapaListBox.getValue(
						this.cidadeQuantidadeSurdosMapaListBox.getSelectedIndex())));
		
		
		return cidade;
	}

	private void limparFormularios() {
		this.idSelecionado = null;
		this.cidadeUtilizarBairroBuscaEnderecoCheckBox.setValue(false);
		this.cidadeNomeTextBox.setText("");
		this.cidadesWarningHTML.setHTML("");
		this.cidadeLatitudeTextBox.setText("");
		this.cidadeLongitudeTextBox.setText("");
		this.cidadeUFTextBox.setText("");
		this.cidadePaisTextBox.setText("");
		this.cidadeQuantidadeSurdosMapaListBox.setSelectedIndex(0);
	}
	
	private void iniciarQuantidadeSurdosMapaListBox() {
		this.cidadeQuantidadeSurdosMapaListBox.addItem("-- Escolha uma opção --", "");
		this.cidadeQuantidadeSurdosMapaListBox.addItem(String.valueOf(QuantidadeSurdosMapaEnum.UM.getQtde()));
		this.cidadeQuantidadeSurdosMapaListBox.addItem(String.valueOf(QuantidadeSurdosMapaEnum.QUATRO.getQtde()));
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaCidadeResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaCidadeResultadoCellTable.removeColumn(0);
		}
		this.pesquisaCidadeResultadoLabel.setText("");
		this.pesquisaCidadeResultadoCellTable.setVisible(false);
		this.pesquisaCidadeResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaCidadeResultadoCellTable.getRowCount() == 0) {
			this.pesquisaCidadeResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaCidadeResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaCidadeResultadoLabel.setText("Foram encontrado(s) " + this.pesquisaCidadeResultadoCellTable.getRowCount() + " resultado(s).");
			this.pesquisaCidadeResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaCidadeResultadoCellTable.setVisible(true);
			
			TextColumn<Cidade> nome = new TextColumn<Cidade>() {
				@Override
				public String getValue(Cidade object) {
					return object.getNome();
				}
			};			
			
			Delegate<Long> deletarDelegate = new Delegate<Long>() {
				@Override
				public void execute(Long object) {
					if (Window.confirm("Deseja realmente apagar esta cidade?")) {
						presenter.apagarCidade(object);
					}
				}				
			};
			ActionCell<Long> deletarCell = new ActionCell<Long>("Apagar", deletarDelegate);
			Column<Cidade, Long> deletarColumn = new Column<Cidade, Long>(deletarCell) {
				@Override
				public Long getValue(Cidade object) {
					return object.getId();
				}
			};
			
			Delegate<Cidade> editarDelegate = new Delegate<Cidade>() {
				@Override
				public void execute(Cidade object) {
					popularManterCidade(object);
				}				
			};
			ActionCell<Cidade> editarCell = new ActionCell<Cidade>("Editar", editarDelegate);
			Column<Cidade, Cidade> editarColumn = new Column<Cidade, Cidade>(editarCell) {
				@Override
				public Cidade getValue(Cidade object) {
					return object;
				}
			};

			this.pesquisaCidadeResultadoCellTable.addColumn(nome, "Nome");
			this.pesquisaCidadeResultadoCellTable.addColumn(editarColumn, "");
			this.pesquisaCidadeResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaCidadeResultadoSimplePager.setVisible(true);
		}
		this.cidadesWarningHTML.setHTML("");
	}

	private void popularManterCidade(Cidade cidade) {
		this.idSelecionado = cidade.getId();
		this.cidadeNomeTextBox.setText(cidade.getNome());
		this.cidadeLatitudeTextBox.setText(cidade.getLatitudeCentro().toString());
		this.cidadeLongitudeTextBox.setText(cidade.getLongitudeCentro().toString());
		this.cidadeUFTextBox.setText(cidade.getUF());
		this.cidadePaisTextBox.setText(cidade.getPais());
		this.cidadeUtilizarBairroBuscaEnderecoCheckBox.setValue(cidade.getUtilizarBairroBuscaEndereco());
		this.cidadeQuantidadeSurdosMapaListBox.setSelectedIndex(
				obterIndice(this.cidadeQuantidadeSurdosMapaListBox, cidade.getQuantidadeSurdosMapa().toString()));

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
		
		if (cidadeNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}		
		if (cidadeUFTextBox.getText().isEmpty()) {
			validacoes.add("UF precisa ser preenchido");
		}		
		if (cidadePaisTextBox.getText().isEmpty()) {
			validacoes.add("País precisa ser preenchido");
		}		
		if (cidadeLatitudeTextBox.getText().isEmpty()) {
			validacoes.add("Latitude precisa ser preenchido");
		} else {
			try {
				Double.valueOf(this.cidadeLatitudeTextBox.getText());
			} catch (NumberFormatException ex) {
				validacoes.add("Latitude precisa ser numérico");
			}
		}
		if (cidadeLongitudeTextBox.getText().isEmpty()) {
			validacoes.add("Longitude precisa ser preenchido");
		} else {
			try {
				Double.valueOf(this.cidadeLongitudeTextBox.getText());
			} catch (NumberFormatException ex) {
				validacoes.add("Longitude precisa ser numérico");
			}
		}
		if (this.cidadeQuantidadeSurdosMapaListBox.getSelectedIndex() == 0) {
			validacoes.add("Quantidade de surdos por mapa precisa ser selecionado.");
		}

		
		return validacoes;
	}
}