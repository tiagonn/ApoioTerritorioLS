package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImpressaoViewImpl extends Composite implements
		ImpressaoView {
	
	private static CadastroSurdoViewUiBinderUiBinder uiBinder = 
		GWT.create(CadastroSurdoViewUiBinderUiBinder.class);
	private Presenter presenter;
	
	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
	@UiField ListBox pesquisaImpressaoRegiaoListBox;
	@UiField ListBox pesquisaImpressaoMapaListBox;	
	@UiField DecoratedPopupPanel impressaoDecoratedPopupPanel;
	@UiField FlexTable impressaoSurdoFlexTable;
	@UiField LayoutPanel impressaoMapaLayoutPanel;
	@UiField Button impressaoVoltarButton;
	
	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder 
		extends UiBinder<Widget, ImpressaoViewImpl> {
	}

	public ImpressaoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void initView() {
		this.cadastroSurdoTabLayoutPanel.selectTab(2);	
		this.limparPesquisa();
		this.limparImpressao();
	}
	
	private void limparPesquisa() {
		this.pesquisaImpressaoRegiaoListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.setEnabled(false);
	}
	
	private void limparImpressao() {
		this.impressaoDecoratedPopupPanel.hide();
		this.impressaoSurdoFlexTable.removeAllRows();
	}

	@Override
	public void setRegiaoList(List<String> regioes) {
		this.pesquisaImpressaoRegiaoListBox.clear();
		this.pesquisaImpressaoRegiaoListBox.addItem("-- Escolha uma regiao --", "");

		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		
		for (String regiao : regioes) {
			this.pesquisaImpressaoRegiaoListBox.addItem(regiao);
		}
	}

	@Override
	public void setMapaList(List<Mapa> mapas) {
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		for (Mapa mapa : mapas) {
			this.pesquisaImpressaoMapaListBox.addItem(mapa.getNome(), mapa.getId().toString());
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.cadastroSurdoTabLayoutPanel.addSelectionHandler(handler);
	}

	@UiHandler("pesquisaImpressaoRegiaoListBox")
	void onPesquisaMapaRegiaoListBoxChange(ChangeEvent event) {
		if (presenter != null) {
			if (!this.pesquisaImpressaoRegiaoListBox.getValue(this.pesquisaImpressaoRegiaoListBox.getSelectedIndex()).isEmpty()) {
				this.pesquisaImpressaoMapaListBox.setEnabled(true);
				this.presenter.onPesquisaRegiaoListBoxChange(pesquisaImpressaoRegiaoListBox.getValue(pesquisaImpressaoRegiaoListBox.getSelectedIndex()));
			} else {
				this.pesquisaImpressaoMapaListBox.setEnabled(false);
				this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
			}
		}
		limparImpressao();
	}
	
	@UiHandler("pesquisaImpressaoMapaListBox")
	void pesquisaImpressaoMapaListBox(ChangeEvent event) {
		if (presenter != null) {
			String mapa = this.pesquisaImpressaoMapaListBox.getValue(this.pesquisaImpressaoMapaListBox.getSelectedIndex());
			if (!"".equals(mapa)) {
				this.presenter.abrirImpressao(Long.valueOf(mapa));
			}
			else {
				limparImpressao();
			}
		}
	}

	@Override
	public void onAbrirImpressao(List<SurdoVO> surdos) {
		String classe = " class=\"impressao-celula\"";
		String classe1= " class=\"impressao-celula-titulo\"";
		
		for (int i = 0; i < surdos.size();i++) {
			if (i == 0) {
				this.impressaoSurdoFlexTable.setHTML(0, 0, surdos.get(i).getMapa());
				this.impressaoSurdoFlexTable.getCellFormatter().addStyleName(0,0,"impressao-tabela-centro");
			}
			SurdoVO surdo = surdos.get(i);
			StringBuilder html = new StringBuilder();
			html.append("<table width=\"100%\" cellspacing=0>")
					.append("<tr>")
						.append("<td width=\"40px\"").append(classe1).append(">Nome:</td>")
						.append("<td width=\"495px\"").append(classe).append(">").append(StringUtils.toCamelCase(surdo.getNome())).append("</td>")
						.append("<td width=\"150px\" ").append(classe1).append(">Ultima Visita:______</td>")
						.append("<td width=\"60px\"").append(classe1).append(">Libras:</td>")
						.append("<td width=\"15px\" ").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getLibras())).append("</td>")
						.append("<td width=\"60px\"").append(classe1).append(">Idade:</td>")
						.append("<td width=\"30px\" ").append(classe).append(">").append(StringUtils.duasLetras(surdo.getIdade())).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">End:</td>")
						.append("<td").append(classe).append(">")
							.append(surdo.getLogradouro()).append(" ")
							.append(surdo.getNumero()).append(" ")
							.append(surdo.getComplemento())
						.append("</td>")
						.append("<td").append(classe1).append(">Ultima Visita:______</td>")
						.append("<td").append(classe1).append(">Crianca:</td>")
						.append("<td").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getCrianca())).append("</td>")
						.append("<td").append(classe1).append(">Sexo:</td>")
						.append("<td").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getSexo())).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">Bairro:</td>")
						.append("<td").append(classe).append(">").append(surdo.getBairro()).append("</td>")
						.append("<td").append(classe1).append(">Ultima Visita:______</td>")
						.append("<td").append(classe1).append(">DVD:</td>")
						.append("<td").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getDvd())).append("</td>")
						.append("<td").append(classe1).append(">Onibus:</td>")
						.append("<td").append(classe).append(">").append(surdo.getOnibus()).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">Obs:</td>")
						.append("<td colspan=\"6\" ").append(classe).append(">").append(surdo.getObservacao()).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td colspan=\"7\"><table width=\"100%\" cellspacing=0><tr>")
						.append("<td width=\"130px\"").append(classe).append("><strong>Tel:</strong> ").append(surdo.getTelefone()).append("</td>")
						.append("<td width=\"240px\"").append(classe).append("><strong>Horario:</strong> ").append(surdo.getHorario()).append("</td>")
						.append("<td width=\"240px\"").append(classe).append("><strong>Instrutor:</strong> ").append(surdo.getInstrutor()).append("</td>")
						.append("<td width=\"240px\"").append(classe).append("><strong>Melhor dia:</strong> ").append(surdo.getMelhorDia()).append("</td>")
					.append("</tr></table></td></tr>")
				.append("</table>");
						
			this.impressaoSurdoFlexTable.setHTML(i+1, 0, html.toString());
			this.impressaoSurdoFlexTable.setBorderWidth(1);
			this.impressaoSurdoFlexTable.setCellSpacing(0);
		}
		
		//TODO: Terminar de criar usando o layoutpanel o mapa (vai precisar de scroll na tela, usar um scrool panel
		
		this.impressaoDecoratedPopupPanel.setAnimationEnabled(true);
		this.impressaoDecoratedPopupPanel.setAutoHideEnabled(true);
		this.impressaoDecoratedPopupPanel.setGlassEnabled(true);
		this.impressaoDecoratedPopupPanel.setTitle("Impressao de mapas");
		this.impressaoDecoratedPopupPanel.setVisible(true);
		this.impressaoDecoratedPopupPanel.show();		
	}
	
	@UiHandler("impressaoVoltarButton")
	public void onImpressaovoltarButtonClick(ClickEvent event) {
		this.presenter.onVoltar();
	}
}
