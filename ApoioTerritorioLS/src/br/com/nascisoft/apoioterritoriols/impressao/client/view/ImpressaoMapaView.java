package br.com.nascisoft.apoioterritoriols.impressao.client.view;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;



public interface ImpressaoMapaView {
	
	public interface IImpressaoPresenter {
		public void go(final HasWidgets container);
		public void setConfiguracoesImpressao(Boolean paisagem, Boolean imprimirCabecalho, Boolean imprimirMapa);
		public void abrirImpressaoMapa(Long identificadorMapa);
	}
	
	Widget asWidget();
	public void setConfiguracoesImpressao(Boolean paisagem, Boolean imprimirCabecalho, Boolean imprimirMapa);
	public void abrirImpressaoMapa(List<SurdoVO> surdos);

}