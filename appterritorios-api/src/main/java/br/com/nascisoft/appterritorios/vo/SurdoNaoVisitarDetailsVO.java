package br.com.nascisoft.appterritorios.vo;

import java.io.Serializable;
import java.util.Comparator;

import br.com.nascisoft.appterritorios.entities.Cidade;
import br.com.nascisoft.appterritorios.entities.Regiao;
import br.com.nascisoft.appterritorios.entities.Surdo;

public class SurdoNaoVisitarDetailsVO implements Serializable {
	
	public SurdoNaoVisitarDetailsVO() {
		
	}
	
	public SurdoNaoVisitarDetailsVO(Surdo surdo, Regiao regiao, Cidade cidade) {
		this.setId(surdo.getId());
		this.setNome(surdo.getNome());
		this.setRegiao(regiao.getNomeRegiaoCompleta());
		if (surdo.isMudouSe()) {
			this.setMotivo("Mudou-se");
		} else if (surdo.isVisitarSomentePorAnciaos()) {
			this.setMotivo("Visitar somente por anciãos");
		} 
		this.setObservacao(surdo.getObservacao());
		this.setNomeCidade(cidade.getNome());
	}

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String regiao;
	private String motivo;
	private String observacao;
	private String nomeCidade;
	
	public static final Comparator<SurdoNaoVisitarDetailsVO> COMPARATOR_NOME = new Comparator<SurdoNaoVisitarDetailsVO>() {
		@Override
		public int compare(SurdoNaoVisitarDetailsVO o1, SurdoNaoVisitarDetailsVO o2) {
			return o1.getNome().compareTo(o2.getNome());
		}
	};
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	
}
