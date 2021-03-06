package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.client.common.ImageButtonCell;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.MelhorDia;
import br.com.nascisoft.apoioterritoriols.login.entities.MelhorDia.Dia;
import br.com.nascisoft.apoioterritoriols.login.entities.MelhorPeriodo;
import br.com.nascisoft.apoioterritoriols.login.entities.MelhorPeriodo.Periodo;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;
import br.com.nascisoft.apoioterritoriols.resources.client.CellTableCustomResources;
import br.com.nascisoft.apoioterritoriols.resources.client.Resources;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.maps.client.HasMapOptions;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlay.HasMarker;
import com.google.gwt.maps.client.overlay.HasMarkerOptions;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.googlecode.objectify.Key;

public class CadastroSurdoViewImpl extends AbstractCadastroViewImpl implements CadastroSurdoView {

	private static CadastroSurdoViewUiBinderUiBinder uiBinder = GWT
			.create(CadastroSurdoViewUiBinderUiBinder.class);
	@UiField(provided=true) CellTable<SurdoDetailsVO> pesquisaResultadoCellTable;
	@UiField Label pesquisaResultadoLabel;
	@UiField HTML manterWarningHTML;
	@UiField VerticalPanel manterSurdoPanel;
	@UiField TextBox manterNomeTextBox;
	@UiField TextBox manterLogradouroTextBox;
	@UiField TextBox manterNumeroTextBox;
	@UiField TextBox manterComplementoTextBox;
	@UiField ListBox manterCidadeListBox;
	@UiField ListBox manterRegiaoListBox;
	@UiField(provided=true) SuggestBox manterBairroSuggestBox;
	@UiField TextBox manterCEPTextBox;
	@UiField TextArea manterObservacaoTextArea;
	@UiField TextBox manterTelefoneTextBox;
	@UiField ListBox manterLibrasListBox;
	@UiField TextBox manterPublicacoesTextBox;
	@UiField ListBox manterDVDListBox;
	@UiField TextBox manterInstrutorTextBox;
	@UiField IntegerBox manterAnoNascimentoIntegerBox;
	@UiField ListBox manterSexoListBox;
	@UiField TextBox manterHorarioTextBox;
	@UiField TextBox manterMelhorDiaTextBox;
	@UiField TextBox manterOnibusTextBox;
	@UiField TextBox manterMSNTextBox;
	@UiField PushButton manterSalvarButton;
	@UiField SimplePager pesquisaResultadoSimplePager;
	@UiField PopupPanel manterMapaPopupPanel;
	@UiField LayoutPanel manterMapaLayoutPanel;
	@UiField FlowPanel manterWarningManualFlowPanel;
	@UiField PushButton manterMapaConfirmarEnderecoButton;
	@UiField PushButton manterMapaVoltarEnderecoButton;
	@UiField CheckBox manterMudouSe;
	@UiField CheckBox manterVisitarSomentePorAnciaos;
	@UiField CheckBox manterMapaSateliteCheckBox;
	@UiField TextBox manterQtdePessoasTextBox;
	@UiField TextBox pesquisaFiltrarTextBox;
	@UiField PushButton adicionarPessoaButton;
	@UiField PushButton manterVoltarButton;
	@UiField PopupPanel visualizarPopUpPanel;
	@UiField CheckBox pesquisarSemMapaCheckBox;
	@UiField FlowPanel manterHorarioSelectionFlowPanel;
	@UiField FlowPanel manterMelhorDiaSelectionFlowPanel;
	@UiField PopupPanel manterHorarioSelectionPopupPanel;
	@UiField PopupPanel manterMelhorDiaSelectionPopupPanel;
	@UiField FlowPanel manterNacionalidadePanel;
	@UiField FlowPanel manterLibrasPanel;
	@UiField FlowPanel manterDVDPanel;
	@UiField TextBox manterNacionalidadeTextBox;
	@UiField TextBox manterMapaLatLngTextBox;
	@UiField PushButton manterMapaLatLngButton;
	
	MultiWordSuggestOracle bairroOracle;
	
	Long manterId;
	Key<Mapa> manterMapa;
	Double manterLongitude;
	Double manterLatitude;
	HasMarker marker;
	String manterRegiao;
	String manterCidade;
	boolean buscaEndereco = true;
	
	List<CheckBox> listaMelhoresDias = new ArrayList<CheckBox>();
	List<CheckBox> listaMelhoresPeriodos = new ArrayList<CheckBox>();
	
	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder extends
			UiBinder<Widget, CadastroSurdoViewImpl> {
	}
	
	private CadastroSurdoView.Presenter presenter;
	
	private ListDataProvider<SurdoDetailsVO> resultadoPesquisa;
	private List<SurdoDetailsVO> listaResultadoPesquisa;

	public CadastroSurdoViewImpl() {
		this.bairroOracle = new MultiWordSuggestOracle();
		this.manterBairroSuggestBox = new SuggestBox(this.bairroOracle);
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.pesquisaResultadoCellTable = new CellTable<SurdoDetailsVO>(10, CellTableCustomResources.INSTANCE);
		initWidget(uiBinder.createAndBindUi(this));
		this.iniciarSNListBox(this.manterLibrasListBox);
		this.iniciarSNListBox(this.manterDVDListBox);
		this.iniciarSexoListBox(this.manterSexoListBox); 
		this.resultadoPesquisa = new ListDataProvider<SurdoDetailsVO>();
		this.resultadoPesquisa.addDataDisplay(this.pesquisaResultadoCellTable);
		this.pesquisaResultadoSimplePager.setDisplay(this.pesquisaResultadoCellTable);		
		
		this.manterHorarioSelectionPopupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				manterHorarioSelectionPopupPanel.setVisible(Boolean.FALSE);
			}
		});
		
		this.manterMelhorDiaSelectionPopupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				manterMelhorDiaSelectionPopupPanel.setVisible(Boolean.FALSE);
			}
		});

		this.iniciaMelhoresDiasEPeriodoCheckboxes();
	}
	
	public void initView() {
		this.selectThisTab();
		this.pesquisaFiltrarTextBox.setText("");
		this.manterSurdoPanel.setVisible(false);
		this.manterWarningHTML.setHTML("");
		this.manterMapaPopupPanel.hide();
		this.limparResultadoPesquisa();
		this.limparManter();
	}

	@Override
	public void selectThisTab() {
		this.cadastroSurdoTabLayoutPanel.selectTab(0, false);
	}

	@Override
	public void setPresenter(CadastroSurdoView.Presenter presenter) {
		this.presenter = presenter;
		
	}	

	@Override
	public void setCidadeList(List<Cidade> cidades) {
		this.manterCidadeListBox.clear();
		for (Cidade cidade : cidades) {
			this.manterCidadeListBox.addItem(cidade.getNome(), cidade.getId().toString());
		}
		this.presenter.onManterCidadeListBoxChange(
				Long.valueOf(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex())));
	}	
	
	@Override
	public void setManterRegiaoList(List<Regiao> regioes) {
		GWT.log("entrando em setRegiaoList, regiões encontradas: " + regioes.size());
		this.manterRegiaoListBox.clear();
		this.manterRegiaoListBox.addItem("-- Escolha uma região --", "");
		for (Regiao regiao : regioes) {
			String nome = regiao.getNomeRegiaoCompleta();
			if (nome != null && nome.length() > 38) {
				nome = nome.substring(0, 39);
			}
			this.manterRegiaoListBox.addItem(nome, regiao.getId().toString());
		}
		if (this.manterRegiao != null) {
			this.manterRegiaoListBox.setSelectedIndex(obterIndice(this.manterRegiaoListBox, this.manterRegiao));
		}
 	}

	@Override
	public void setBairroList(List<String> bairros) {
		this.bairroOracle.clear();
		for (String bairro : bairros) {
			this.bairroOracle.add(bairro);
		}
	}
	
	@UiHandler("manterCidadeListBox")
	void onManterCidadeListBoxChange(ChangeEvent event) {
		if (!StringUtils.isEmpty(this.manterCidade) 
				&& !this.manterCidade.equals(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex()))
				&& this.manterMapa != null) {
			this.mostrarWarning("Ao alterar a cidade do cadastro ele perderá a associação que tem com o mapa atual.", ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
		}
		this.manterCidade = this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex());
		this.presenter.onManterCidadeListBoxChange(Long.valueOf(manterCidade));
	}
	
	@UiHandler("manterRegiaoListBox")
	void onManterRegiaoListBoxChange(ChangeEvent event) {
		if (!StringUtils.isEmpty(this.manterRegiao) 
				&& !this.manterRegiao.equals(this.manterRegiaoListBox.getValue(this.manterRegiaoListBox.getSelectedIndex()))
				&& this.manterMapa != null) {
			this.mostrarWarning("Ao alterar a região do cadastro ele perderá a associação que tem com o mapa atual.", ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
		}
	}
	
	@UiHandler("manterSexoListBox")
	void onManterSexoListBoxChange(ChangeEvent event) {
		switch (manterSexoListBox.getValue(manterSexoListBox.getSelectedIndex())) {
		case "Homem":
			this.manterQtdePessoasTextBox.setValue("1");
			break;
		case "Mulher":
			this.manterQtdePessoasTextBox.setValue("1");
			break;
		case "Casal":
			this.manterQtdePessoasTextBox.setValue("2");
			break;
		}
	}
		
	@Override
	public void setResultadoPesquisa(List<SurdoDetailsVO> resultadoPesquisa) {
		this.showWaitingPanel();
		if (resultadoPesquisa == null && this.listaResultadoPesquisa != null) {
			resultadoPesquisa = this.listaResultadoPesquisa;
		}
		
		this.listaResultadoPesquisa = resultadoPesquisa;
		this.filtrarPesquisa();	
		this.hideWaitingPanel();
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaResultadoCellTable.getRowCount() == 0) {
			this.pesquisaResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaResultadoLabel.setText("Foram encontrados " + this.pesquisaResultadoCellTable.getRowCount() + " resultados.");
			this.pesquisaResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaResultadoCellTable.setVisible(true);
			
			TextColumn<SurdoDetailsVO> nomeColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return StringUtils.toCamelCase(object.getNome());
				}
			};
			ListHandler<SurdoDetailsVO> nomeSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(nomeColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						return (o2 != null) ? o1.getNome()
								.compareTo(o2.getNome()) : 1;
					}
					return -1;
				}
			});
			nomeColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> cidadeColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getNomeCidade();
				}
			};
			ListHandler<SurdoDetailsVO> cidadeSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(cidadeColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						return (o2 != null) ? o1.getNomeCidade()
								.compareTo(o2.getNomeCidade()) : 1;
					}
					return -1;
				}
			});
			cidadeColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> regiaoColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getRegiao();
				}				
			};
			ListHandler<SurdoDetailsVO> regiaoSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(regiaoColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						return (o2 != null) ? o1.getRegiao()
								.compareTo(o2.getRegiao()) : 1;
					}
					return -1;
				}
			});
			regiaoColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> mapaColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					if (!StringUtils.isEmpty(object.getMapa())) {
						return object.getMapa().substring(5);
					} else {
						return "";
					}
				}
			};			
			ListHandler<SurdoDetailsVO> mapaSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(mapaColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						if (o2 != null) {
							String mapa1 = o1.getMapa().substring(5);
							String mapa2 = o2.getMapa().substring(5);
							if (mapa1.length() == 2) {
								mapa1 = mapa1.substring(0,1) + "0" + mapa1.substring(1);
							}
							if (mapa2.length() == 2) {
								mapa2 = mapa2.substring(0,1) + "0" + mapa2.substring(1);
							}
							return mapa1.compareTo(mapa2);
						} else {
							return 1;
						}
					}
					return -1;
				}
			});
			mapaColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> enderecoColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getEndereco();
				}
			};
			
			Column<SurdoDetailsVO, String> visualizarColumn = new Column<SurdoDetailsVO, String>(
					new ImageButtonCell("Visualizar", "Visualizar")) {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return Resources.INSTANCE.visualizar().getSafeUri().asString();
				}
			};
			visualizarColumn.setFieldUpdater(new FieldUpdater<SurdoDetailsVO, String>() {
				@Override
				public void update(int index, SurdoDetailsVO object, String value) {
					presenter.onVisualizar(object.getId());
				}
			});
			
			Column<SurdoDetailsVO, String> editColumn = new Column<SurdoDetailsVO, String>(
					new ImageButtonCell("Editar", "Editar")) {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return Resources.INSTANCE.editar().getSafeUri().asString();
				}
			};
			editColumn.setFieldUpdater(new FieldUpdater<SurdoDetailsVO, String>() {
				@Override
				public void update(int index, SurdoDetailsVO object, String value) {
					presenter.onEditarButtonClick(object.getId());
				}
			});
			
			Column<SurdoDetailsVO, String> deletarColumn = new Column<SurdoDetailsVO, String>(
					new ImageButtonCell("Apagar", "Apagar")) {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return Resources.INSTANCE.apagar().getSafeUri().asString();
				}
			};
			deletarColumn.setFieldUpdater(new FieldUpdater<SurdoDetailsVO, String>() {
				@Override
				public void update(int index, final SurdoDetailsVO object, String value) {
					mostrarConfirmacao("Deseja realmente apagar este cadastro?", new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							presenter.onApagar(object.getId());
							
						}
					});
				}
			});
			
			this.pesquisaResultadoCellTable.addColumnSortHandler(nomeSortHandler);
			this.pesquisaResultadoCellTable.addColumnSortHandler(cidadeSortHandler);
			this.pesquisaResultadoCellTable.addColumnSortHandler(regiaoSortHandler);
			this.pesquisaResultadoCellTable.addColumnSortHandler(mapaSortHandler);

			this.pesquisaResultadoCellTable.addColumn(cidadeColumn, "Cidade");
			this.pesquisaResultadoCellTable.addColumn(nomeColumn, "Nome");
			this.pesquisaResultadoCellTable.addColumn(regiaoColumn, "Região");
			this.pesquisaResultadoCellTable.addColumn(mapaColumn, "Mapa");
			if (Window.getClientWidth() > 1000) {
				this.pesquisaResultadoCellTable.addColumn(enderecoColumn, "Endereço");
			}
			this.pesquisaResultadoCellTable.addColumn(visualizarColumn, "");
			this.pesquisaResultadoCellTable.addColumn(editColumn, "");
			this.pesquisaResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaResultadoCellTable.getColumnSortList().push(nomeColumn);
			
			this.pesquisaResultadoSimplePager.setVisible(true);
		}
		this.manterWarningHTML.setHTML("");
		this.manterSurdoPanel.setVisible(false);
	}
	
	@UiHandler("manterSalvarButton")
	void onManterSalvarButtonClick(ClickEvent event) {
		Validacoes validacoes = validaManterSurdo();
		if (validacoes.size() > 0) {
			this.manterWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			if (this.buscaEndereco) {
				this.presenter.buscarEndereco(
						Long.valueOf(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex())),
						this.manterLogradouroTextBox.getValue(), 
						this.manterNumeroTextBox.getValue(),
						this.manterBairroSuggestBox.getValue(),
						this.manterCEPTextBox.getValue());
			} else {
				HasLatLng position = new LatLng(this.manterLatitude, this.manterLongitude);
				this.setPosition(position, true, false);
				onManterMapaConfirmarEnderecoButtonClick(event);
			}
		}		
	}
	
	public void setPosition(HasLatLng position, Boolean sucesso, Boolean mostraMapa) {
		
		int zoom = 17;
		this.manterWarningManualFlowPanel.setVisible(false);
		int heightMinus = 100;
		if (!sucesso) {
			this.manterWarningManualFlowPanel.setVisible(true);
			this.manterMapaLatLngTextBox.setText("");
			zoom = 12;
			heightMinus = 180;
		} 
		this.manterLatitude = position.getLatitude();
		this.manterLongitude = position.getLongitude();
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(true);
		markerOpt.setDraggable(true);
		markerOpt.setVisible(true);
		this.marker = new Marker(markerOpt);
		this.marker.setPosition(position);

		if (mostraMapa) {		
			HasMapOptions opt = new MapOptions();
			opt.setZoom(zoom);
			opt.setCenter(new LatLng(this.manterLatitude,this.manterLongitude));
			opt.setMapTypeId(new MapTypeId().getRoadmap());
			opt.setDraggable(true);
			opt.setNavigationControl(true);
			opt.setScrollwheel(true);
			MapWidget mapa = new MapWidget(opt);
			int width = Window.getClientWidth()-100;
			int left = (Window.getClientWidth() - width)/2;
			int height = Window.getClientHeight()-heightMinus;
			String widthString = width+"px";
			String heightString = height+"px";
			

			mapa.setSize(widthString, heightString);
			this.marker.setMap(mapa.getMap());
			manterMapaLayoutPanel.clear();
			manterMapaLayoutPanel.setSize(widthString, heightString);
			manterMapaLayoutPanel.add(mapa);		
			manterMapaPopupPanel.setPopupPosition(left, 0);
			manterMapaPopupPanel.setVisible(true);
			manterMapaPopupPanel.show();
		}
	}
	
	@UiHandler("manterMapaConfirmarEnderecoButton")
	void onManterMapaConfirmarEnderecoButtonClick(ClickEvent event) {
		this.manterLatitude = marker.getPosition().getLatitude();
		this.manterLongitude = marker.getPosition().getLongitude();
		this.manterMapaPopupPanel.hide();
		Surdo surdo = populaSurdo();
		this.presenter.adicionarOuAlterarSurdo(surdo, this.listaResultadoPesquisa);
	}
	
	@UiHandler("manterMapaVoltarEnderecoButton")
	void onManterMapaVoltarEnderecoButtonClick(ClickEvent event) {
		manterMapaPopupPanel.hide();
	}
	
	@UiHandler(value={"manterLogradouroTextBox", "manterNumeroTextBox", "manterCEPTextBox", "manterCidadeListBox"})
	void onEnderecoChange(ChangeEvent event) {
		this.buscaEndereco = true;
	}
	
	@UiHandler("manterBairroSuggestBox")
	void onManterBairroSuggestBoxKeyPress(KeyPressEvent event) {
		this.buscaEndereco = true;
	}
	
	@UiHandler(value={"manterMudouSe", "manterVisitarSomentePorAnciaos"})
	void onNaoVisitarChangeValue(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			mostrarWarning("Ao selecionar mudou-se ou visitar somente por anciãos, este cadastro será removido " +
					"da listagem e do mapa que ele está associado, se é que ele está associado a algum mapa, " + 
					"até que ele seja desmarcado como não visitar na aba própria para este fim.\n\n" +
					"Por favor, detalhe porque você está marcando este cadastro para não ser visitado no campo observação.", 
					ApoioTerritorioLSConstants.INSTANCE.warningLongTimeout());
			if ("manterMudouSe".equals(((CheckBox)event.getSource()).getName())) {
				this.manterVisitarSomentePorAnciaos.setValue(Boolean.FALSE);
			} else {
				this.manterMudouSe.setValue(Boolean.FALSE);
			}
		}
	}

	@Override
	public void onEditar(Surdo surdo) {
		this.manterSurdoPanel.setVisible(true);
		this.limparResultadoPesquisa();
		this.populaManter(surdo);
		this.presenter.onManterCidadeListBoxChange(Long.valueOf(this.manterCidade));
	}
	
	@Override
	public void onApagarSurdo(Long id) {
		for (int i = 0; i < this.resultadoPesquisa.getList().size();i++) {
			SurdoDetailsVO vo = this.resultadoPesquisa.getList().get(i);
			if (vo.getId().equals(id)) {
				this.resultadoPesquisa.getList().remove(i);
				break;
			}
		}
		for (int i = 0; i < this.listaResultadoPesquisa.size(); i++) {
			SurdoDetailsVO surdo = this.listaResultadoPesquisa.get(i);
			if (surdo.getId().equals(id)) {
				this.listaResultadoPesquisa.remove(i);
				break;
			}
		}
		this.pesquisaResultadoCellTable.setRowCount(this.resultadoPesquisa.getList().size());
		this.limparResultadoPesquisa();
		this.mostrarResultadoPesquisa();
	}

	private Validacoes validaManterSurdo() {
		Validacoes validacoes = new Validacoes();
		
		if (manterNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}
		if (manterLogradouroTextBox.getText().isEmpty()) {
			validacoes.add("Logradouro precisa ser preenchido");
		}
		if (manterNumeroTextBox.getText().isEmpty()) {
			validacoes.add("Número precisa ser preenchido");
		}
		if (manterRegiaoListBox.getSelectedIndex() == 0) {
			validacoes.add("Região precisa ser preenchida");
		}
		if (manterQtdePessoasTextBox.getValue() == null ||
				manterQtdePessoasTextBox.getValue().length() == 0) {
			validacoes.add("Quantidade de pessoas no endereço precisa ser preenchida");
		} else {
			try {
				Byte.valueOf(this.manterQtdePessoasTextBox.getValue());
			} catch (NumberFormatException ex) {
				validacoes.add("Quantidade de pessoas no endereço precisa ser um valor numérico");
			}
		}
		
		return validacoes;
	} 
	
	private void limparResultadoPesquisa() {	
		
		this.cadastroSurdoTabLayoutPanel.getTabWidget(4).getParent().setVisible(this.presenter.getLoginInformation().isAdmin());
		
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaResultadoCellTable.removeColumn(0);
		}
		this.pesquisaResultadoLabel.setText("");
		this.pesquisaResultadoCellTable.setVisible(false);
		this.pesquisaResultadoSimplePager.setVisible(false);
		
		String dropDownImageUrl = "url(" + Resources.INSTANCE.dropdown().getSafeUri().asString()+")";
		GWT.log("Setando imagem de fundo aos TextBoxes manterHorario e manterMelhorDia: " + dropDownImageUrl);
		this.manterHorarioTextBox.getElement().getStyle().setBackgroundImage(dropDownImageUrl);
		this.manterMelhorDiaTextBox.getElement().getStyle().setBackgroundImage(dropDownImageUrl);

	}
	
	private void iniciaMelhoresDiasEPeriodoCheckboxes() {
		for (int i = 0; i < Dia.values().length; i++) {
			final Dia dia = Dia.values()[i];
			CheckBox cb = new CheckBox(dia.getNome());
			cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					atualizarTextBox(event.getValue(), manterMelhorDiaTextBox, dia.getNomeAbreviado());
				}
			});
			cb.setStyleName("pessoas-manter-form-checkbox");
			this.manterMelhorDiaSelectionFlowPanel.add(cb);	
			this.listaMelhoresDias.add(cb);
		}
		
		for (int i = 0; i < Periodo.values().length; i++) {
			final Periodo periodo = Periodo.values()[i];
			CheckBox cb = new CheckBox(periodo.getNome());
			cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					atualizarTextBox(event.getValue(), manterHorarioTextBox, periodo.getNome());
				}
			});
			cb.setStyleName("pessoas-manter-form-checkbox");
			this.manterHorarioSelectionFlowPanel.add(cb);
			this.listaMelhoresPeriodos.add(cb);
		}		
	}
	

	private void limparManter() {
		GWT.log("Entrando em limparManter");
		this.buscaEndereco = true;
		this.manterId = null;
		this.manterMapa = null;
		this.manterLongitude = null;
		this.manterLatitude = null;
		this.manterRegiao = null;
		this.manterCidade = null;
		this.manterNomeTextBox.setText("");         
		this.manterLogradouroTextBox.setText("");   
		this.manterNumeroTextBox.setText("");       
		this.manterComplementoTextBox.setText("");  
		this.manterRegiaoListBox.setSelectedIndex(0);       
		this.manterCidadeListBox.setSelectedIndex(0);       
		this.manterBairroSuggestBox.setText("");       
		this.manterCEPTextBox.setText("");          
		this.manterObservacaoTextArea.setText("");  
		this.manterTelefoneTextBox.setText("");     
		this.manterLibrasListBox.setSelectedIndex(0);       
		this.manterDVDListBox.setSelectedIndex(0);          
		this.manterInstrutorTextBox.setText("");    
		this.manterPublicacoesTextBox.setText("");    
		this.manterAnoNascimentoIntegerBox.setText("");     
		this.manterSexoListBox.setSelectedIndex(0);         
		this.manterHorarioTextBox.setText("");      
		this.manterMelhorDiaTextBox.setText("");    
		this.manterOnibusTextBox.setText("");       
		this.manterMSNTextBox.setText("");
		this.manterMudouSe.setValue(Boolean.FALSE);
		this.manterVisitarSomentePorAnciaos.setValue(Boolean.FALSE);
		this.manterMapaSateliteCheckBox.setValue(false);
		this.manterQtdePessoasTextBox.setText("1");
		this.manterMelhorDiaSelectionPopupPanel.hide();
		this.manterHorarioSelectionPopupPanel.hide();
		for (CheckBox cb : this.listaMelhoresDias) {
			GWT.log("Limpando checkbox " + cb.getText());
			cb.setValue(Boolean.FALSE);
		}
		for (CheckBox cb : this.listaMelhoresPeriodos) {
			GWT.log("Limpando checkbox " + cb.getText());
			cb.setValue(Boolean.FALSE);
		}
		this.manterNacionalidadeTextBox.setText("");
		GWT.log("Sistema configurado para LIBRAS: " + ApoioTerritorioLSConstants.INSTANCE.isLibras());
		this.manterLibrasPanel.setVisible(ApoioTerritorioLSConstants.INSTANCE.isLibras());
		this.manterDVDPanel.setVisible(ApoioTerritorioLSConstants.INSTANCE.isLibras());
		this.manterNacionalidadePanel.setVisible(ApoioTerritorioLSConstants.INSTANCE.isMultiNacionalidade());
	}
	
	private Surdo populaSurdo() {
		Surdo surdo = new Surdo();
		surdo.setId(this.manterId);
		surdo.setNome(this.manterNomeTextBox.getText().toUpperCase());
		surdo.setLogradouro(this.manterLogradouroTextBox.getText());
		surdo.setNumero(this.manterNumeroTextBox.getText());
		surdo.setComplemento(this.manterComplementoTextBox.getText());
		surdo.setRegiaoId(Long.valueOf(this.manterRegiaoListBox.getValue(this.manterRegiaoListBox.getSelectedIndex())));
		surdo.setBairro(this.manterBairroSuggestBox.getText());
		surdo.setCep(this.manterCEPTextBox.getText());
		surdo.setObservacao(this.manterObservacaoTextArea.getText());
		surdo.setTelefone(this.manterTelefoneTextBox.getText());
		if (ApoioTerritorioLSConstants.INSTANCE.isLibras()) {
			surdo.setLibras(this.manterLibrasListBox.getValue(this.manterLibrasListBox.getSelectedIndex()));
			surdo.setDvd(this.manterDVDListBox.getValue(this.manterDVDListBox.getSelectedIndex()));
		} else if (ApoioTerritorioLSConstants.INSTANCE.isMultiNacionalidade()) {
			surdo.setLibras(this.manterNacionalidadeTextBox.getText());
		}
		surdo.setPublicacoesPossui(this.manterPublicacoesTextBox.getText());
		surdo.setInstrutor(this.manterInstrutorTextBox.getText());
		surdo.setAnoNascimento(this.manterAnoNascimentoIntegerBox.getValue());
		surdo.setSexo(this.manterSexoListBox.getValue(this.manterSexoListBox.getSelectedIndex()));
		surdo.setMelhorDia(new MelhorDia(this.manterMelhorDiaTextBox.getValue(), Boolean.TRUE));
		surdo.setMelhorPeriodo(new MelhorPeriodo(this.manterHorarioTextBox.getValue()));
		surdo.setOnibus(this.manterOnibusTextBox.getText());
		surdo.setMsn(this.manterMSNTextBox.getText());
		if (this.manterRegiao != null && this.manterRegiao.equals(surdo.getRegiaoId().toString())) {
			surdo.setMapa(this.manterMapa);
		} else if (this.manterMapa != null){
			surdo.setMapaAnterior(this.manterMapa.getId());
		}
		surdo.setLongitude(this.manterLongitude);
		surdo.setLatitude(this.manterLatitude);
		surdo.setMudouSe(this.manterMudouSe.getValue());
		surdo.setVisitarSomentePorAnciaos(this.manterVisitarSomentePorAnciaos.getValue());
		if ((surdo.isMudouSe() || surdo.isVisitarSomentePorAnciaos())
				&& this.manterMapa != null) {
			surdo.setMapaAnterior(this.manterMapa.getId());
		}
		surdo.setCidadeId(Long.valueOf(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex())));
		surdo.setQtdePessoasEndereco(
				this.manterQtdePessoasTextBox.getValue() != null && this.manterQtdePessoasTextBox.getValue().length()>0 
				? Byte.valueOf(this.manterQtdePessoasTextBox.getValue())
						:null);
		return surdo;
	}
	
	private void atualizarTextBox(Boolean isAdd, TextBox box, String valor) {
		StringBuilder valorAtual = new StringBuilder(box.getText());
		if (isAdd) {
			if (valorAtual.length() > 0) {
				valorAtual.append(",");
			}
			valorAtual.append(valor);
		} else {
			int i = valorAtual.indexOf(valor);
			if (i > 0) {
				i = i-1;
			}
			valorAtual.replace(i, i+valor.length()+1, "");
			if (valorAtual.length() < 3) {
				valorAtual = new StringBuilder();
			}
			
		}
		box.setText(valorAtual.toString());
	}
	
	private void marcarMelhoresPeriodos(MelhorPeriodo melhoresPeriodos) {
		for (CheckBox cb : this.listaMelhoresPeriodos) {
			GWT.log("Marcando checkboxes de melhores periodos: " + cb.getText());
			if (Periodo.manha.getNome().equals(cb.getText())) {
				GWT.log("Manhã: " + melhoresPeriodos.getManha());
				cb.setValue(melhoresPeriodos.getManha());
			} else if (Periodo.tarde.getNome().equals(cb.getText())) {
				GWT.log("Tarde: " + melhoresPeriodos.getTarde());
				cb.setValue(melhoresPeriodos.getTarde());
			} else if (Periodo.noite.getNome().equals(cb.getText())) {
				GWT.log("Noite: " + melhoresPeriodos.getNoite());
				cb.setValue(melhoresPeriodos.getNoite());
			}
		}
	}
	
	private void marcarMelhoresDias(MelhorDia melhoresDias) {
		for (CheckBox cb : this.listaMelhoresDias) {
			GWT.log("Marcando checkboxes de melhores dias: " + cb.getText());
			if (Dia.segunda.getNome().equals(cb.getText())) {
				GWT.log("Segunda: " + melhoresDias.getSegunda());
				cb.setValue(melhoresDias.getSegunda());
			} else if (Dia.terca.getNome().equals(cb.getText())) {
				GWT.log("Terça: " + melhoresDias.getTerca());
				cb.setValue(melhoresDias.getTerca());
			} else if (Dia.quarta.getNome().equals(cb.getText())) {
				GWT.log("Quarta: " + melhoresDias.getQuarta());
				cb.setValue(melhoresDias.getQuarta());
			} else if (Dia.quinta.getNome().equals(cb.getText())) {
				GWT.log("Quinta: " + melhoresDias.getQuinta());
				cb.setValue(melhoresDias.getQuinta());
			} else if (Dia.sexta.getNome().equals(cb.getText())) {
				GWT.log("Sexta: " + melhoresDias.getSexta());
				cb.setValue(melhoresDias.getSexta());
			} else if (Dia.sabado.getNome().equals(cb.getText())) {
				GWT.log("Sábado: " + melhoresDias.getSabado());
				cb.setValue(melhoresDias.getSabado());
			} else if (Dia.domingo.getNome().equals(cb.getText())) {
				GWT.log("Domingo: " + melhoresDias.getDomingo());
				cb.setValue(melhoresDias.getDomingo());
			}
		}				
	}
	
	private void populaManter(Surdo surdo) {
		this.buscaEndereco = false;
		this.manterId = surdo.getId();
		this.manterRegiao = String.valueOf(surdo.getRegiao().getId());
		this.manterCidade = String.valueOf(surdo.getCidade().getId());
		this.manterNomeTextBox.setText(StringUtils.toCamelCase(surdo.getNome()));
		this.manterLogradouroTextBox.setText(surdo.getLogradouro());
		this.manterNumeroTextBox.setText(surdo.getNumero());
		this.manterComplementoTextBox.setText(surdo.getComplemento());
		this.manterRegiaoListBox.setSelectedIndex(
				obterIndice(this.manterRegiaoListBox, this.manterRegiao));
		this.manterBairroSuggestBox.setText(surdo.getBairro());
		this.manterCEPTextBox.setText(surdo.getCep());
		this.manterObservacaoTextArea.setText(surdo.getObservacao());
		this.manterTelefoneTextBox.setText(surdo.getTelefone());
		this.manterLibrasListBox.setSelectedIndex(
				obterIndice(this.manterLibrasListBox, surdo.getLibras()));
		this.manterNacionalidadeTextBox.setText(surdo.getLibras());
		this.manterPublicacoesTextBox.setText(surdo.getPublicacoesPossui());
		this.manterDVDListBox.setSelectedIndex(
				obterIndice(this.manterDVDListBox, surdo.getDvd()));
		this.manterInstrutorTextBox.setText(surdo.getInstrutor());
		this.manterAnoNascimentoIntegerBox.setValue(surdo.getAnoNascimento());
		this.manterSexoListBox.setSelectedIndex(
				obterIndice(this.manterSexoListBox, surdo.getSexo()));
		this.manterMelhorDiaTextBox.setText(surdo.getMelhoresDiasCsv(Boolean.TRUE));
		marcarMelhoresDias(surdo.getMelhorDia());
		this.manterHorarioTextBox.setText(surdo.getMelhoresPeriodosCsv());
		marcarMelhoresPeriodos(surdo.getMelhorPeriodo());
		this.manterOnibusTextBox.setText(surdo.getOnibus());
		this.manterMSNTextBox.setText(surdo.getMsn());
		this.manterMapa = surdo.getMapa();
		this.manterLongitude = surdo.getLongitude();
		this.manterLatitude = surdo.getLatitude();
		this.manterMudouSe.setValue(surdo.isMudouSe());
		this.manterVisitarSomentePorAnciaos.setValue(surdo.isVisitarSomentePorAnciaos());
		this.manterCidadeListBox.setSelectedIndex(obterIndice(this.manterCidadeListBox, this.manterCidade));
		this.manterQtdePessoasTextBox.setValue(surdo.getQtdePessoasEndereco()!=null?surdo.getQtdePessoasEndereco().toString():"1");
	}
	
	private void iniciarSNListBox(ListBox snListBox) {
		snListBox.addItem("-- Escolha uma opção --", "");
		snListBox.addItem("Sim");
		snListBox.addItem("Não");
	}
	
	private void iniciarSexoListBox(ListBox sexoListBox){
		sexoListBox.addItem("-- Escolha uma opção --", "");
		sexoListBox.addItem("Homem");
		sexoListBox.addItem("Mulher");
		sexoListBox.addItem("Casal");
		sexoListBox.addItem("Família");
		sexoListBox.addItem("Amigos/Grupo");
	}
	
	private int obterIndice(ListBox list, String valor) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(valor)) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public void onAdicionar() {
		this.manterSurdoPanel.setVisible(true);
		this.limparResultadoPesquisa();
		this.limparManter();
		this.presenter.populaCidades();
	}
	
	@UiHandler("manterMapaSateliteCheckBox")
	void onImprimirMapaSateliteCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			((MapWidget)this.manterMapaLayoutPanel.getWidget(0)).getMap().setMapTypeId(new MapTypeId().getHybrid());
		} else {
			((MapWidget)this.manterMapaLayoutPanel.getWidget(0)).getMap().setMapTypeId(new MapTypeId().getRoadmap());
		}
	}
	
	@Override
	public void onVisualizar(SurdoVO surdo) {
		int clientWidth = Window.getClientWidth();
		int clientHeight = Window.getClientHeight();
		int width = 400;
		int top = 100;
		int left = (clientWidth-width)/2;
		if (clientWidth < width) {
			width = 200;
			left = 0;
		}
		if (clientHeight < 400) {
			top = 0;
		}
		String sWidth = width+"px";

		visualizarPopUpPanel.setWidth(sWidth);
		visualizarPopUpPanel.setPopupPosition(left, top);
		visualizarPopUpPanel.clear();
		visualizarPopUpPanel.add(new HTML(surdo.getSafeHTMLDetails("pessoas-visualizar-lista")));
		visualizarPopUpPanel.setVisible(true);
		visualizarPopUpPanel.show();
		
	}
	
	@UiHandler("adicionarPessoaButton")
	void onAdicionarPessoaButtonClick(ClickEvent e) {
		History.newItem("surdos!adicionar");
	}
	
	@UiHandler("manterVoltarButton")
	void onManterVoltarButtonClick(ClickEvent e) {
		this.presenter.onManterVoltarClick();
	}
	
	@UiHandler("pesquisaFiltrarTextBox")
	void onPesquisaFiltrarTextBoxKeyUpEvent(KeyUpEvent event) {
		this.filtrarPesquisa();
	}
	
	@UiHandler("pesquisarSemMapaCheckBox")
	void onPesquisarSemMapaCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		this.filtrarPesquisa();
	}
	
	private void filtrarPesquisa() {
		this.limparResultadoPesquisa();
		List<SurdoDetailsVO> listaNova = new ArrayList<SurdoDetailsVO>(this.listaResultadoPesquisa);
		if (this.pesquisarSemMapaCheckBox.getValue()) {
			listaNova = filtrarResultadoPesquisaSemMapa(listaNova);
		}
		if (this.pesquisaFiltrarTextBox.getValue().length()>0) {
			listaNova = filtrarResultadoPesquisa(this.pesquisaFiltrarTextBox.getValue(), listaNova);
		} 
		
		this.resultadoPesquisa.setList(listaNova);
		this.pesquisaResultadoCellTable.setRowCount(listaNova.size());
		this.mostrarResultadoPesquisa();
	}
	
	private List<SurdoDetailsVO> filtrarResultadoPesquisaSemMapa(List<SurdoDetailsVO> lista) {
		List<SurdoDetailsVO> result = new ArrayList<SurdoDetailsVO>();
		for (SurdoDetailsVO vo : lista) {
			if (StringUtils.isEmpty(vo.getMapa())) {
				result.add(vo);
			}
		}
		return result;		
	}
	
	private List<SurdoDetailsVO> filtrarResultadoPesquisa(String query, List<SurdoDetailsVO> lista) {
		List<SurdoDetailsVO> result = new ArrayList<SurdoDetailsVO>();
		for (SurdoDetailsVO vo : lista) {
			if ( (vo.getNome().toUpperCase().indexOf(query.toUpperCase()) != -1)
					|| (vo.getNomeCidade().toUpperCase().indexOf(query.toUpperCase()) != -1)
					|| (vo.getRegiao().toUpperCase().indexOf(query.toUpperCase()) != -1) 
					|| (vo.getMapa().toUpperCase().indexOf(query.toUpperCase()) != -1)
					|| (vo.getEndereco().toUpperCase().indexOf(query.toUpperCase()) != -1)
					) {
				result.add(vo);
			}
		}
		return result;
	}
	
	@UiHandler("manterHorarioTextBox")
	void onManterHorarioTextBoxClick(ClickEvent event) {
		if (!this.manterHorarioSelectionPopupPanel.isVisible()) {
			this.manterHorarioSelectionPopupPanel.show();
			this.manterHorarioSelectionPopupPanel.setVisible(Boolean.TRUE);
			this.manterHorarioSelectionPopupPanel.getElement().getStyle().setLeft(
					this.manterHorarioTextBox.getAbsoluteLeft(), Unit.PX);
			this.manterHorarioSelectionPopupPanel.getElement().getStyle().setTop(
					this.manterHorarioTextBox.getAbsoluteTop()+
					this.manterHorarioTextBox.getOffsetHeight(), Unit.PX);
			this.manterHorarioSelectionPopupPanel.getElement().getStyle().setWidth(
					this.manterHorarioTextBox.getOffsetWidth()-2, Unit.PX);
		} else {
			this.manterHorarioSelectionPopupPanel.hide();
		}
	}
	
	@UiHandler("manterMelhorDiaTextBox")
	void onManterMelhorDiaTextBoxClick(ClickEvent event) {
		if (!this.manterMelhorDiaSelectionPopupPanel.isVisible()) {
			this.manterMelhorDiaSelectionPopupPanel.show();
			this.manterMelhorDiaSelectionPopupPanel.setVisible(Boolean.TRUE);
			this.manterMelhorDiaSelectionPopupPanel.getElement().getStyle().setLeft(
					this.manterMelhorDiaTextBox.getAbsoluteLeft(), Unit.PX);
			this.manterMelhorDiaSelectionPopupPanel.getElement().getStyle().setTop(
					this.manterMelhorDiaTextBox.getAbsoluteTop()+
					this.manterMelhorDiaTextBox.getOffsetHeight(), Unit.PX);
			this.manterMelhorDiaSelectionPopupPanel.getElement().getStyle().setWidth(
					this.manterMelhorDiaTextBox.getOffsetWidth()-2, Unit.PX);
		} else {
			this.manterMelhorDiaSelectionPopupPanel.hide();
		}
		
	}
	
	@UiHandler("manterMapaLatLngButton")
	void onManterMapaLatLngButtonClick(ClickEvent event) {
		try {
			String[] coord = this.manterMapaLatLngTextBox.getText().replaceAll("\\s+","").split(",");
			Double lat = Double.valueOf(coord[0]);
			Double lng = Double.valueOf(coord[1]);
			HasLatLng newPosition = new LatLng(lat,  lng);
			this.marker.setPosition(newPosition);			
			this.marker.getMap().setCenter(newPosition);
			this.marker.getMap().setZoom(17);
		} catch (Exception ex) {
			this.mostrarWarning("As coordenadas geográficas inseridas não estão no formato apropriado. Por favor utilize o formato lat,long como em -22.9099384,-47.0626332", 
					ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
		}
	}
	
}
