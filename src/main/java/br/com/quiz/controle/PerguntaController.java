package br.com.quiz.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.primefaces.event.SelectEvent;

import br.com.quiz.model.dao.AlternativaDao;
import br.com.quiz.model.dao.AlternativaDaoImpl;
import br.com.quiz.model.dao.HibernateUtil;
import br.com.quiz.model.dao.PerguntaDao;
import br.com.quiz.model.dao.PerguntaDaoImpl;
import br.com.quiz.model.dao.SubCategoriaDao;
import br.com.quiz.model.dao.SubCategoriaDaoImpl;
import br.com.quiz.model.entidade.Alternativa;
import br.com.quiz.model.entidade.Categoria;
import br.com.quiz.model.entidade.Pergunta;
import br.com.quiz.model.entidade.SubCategoria;
import br.com.quiz.model.entidade.Usuario;
import java.io.IOException;
import java.util.Map;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author alf_a
 */
@ManagedBean(name = "perguntaC")
@ViewScoped
public class PerguntaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.logger(getClass());

    private List<Alternativa> listaAlternativas = new ArrayList<>();
    private DataModel<Alternativa> modelAlternativas;
    private Alternativa alternativaCorreta;
    private AlternativaDao alternativaDao;
    private Alternativa alternativa;
    private Alternativa alter;

    private List<Pergunta> perguntas = new ArrayList<>();
    private DataModel<Pergunta> modelPerguntas;
    private Pergunta perguntaSelecionada;
    private SubCategoria subCategoria;
    private PerguntaDao perguntaDao;
    private Categoria categoria;
    private String refinaBusca;
    private Pergunta pergunta;
    private Pergunta perguntaModal;
    private Pergunta perguntaEdit;
    private Long contadorId = 0L;
    private Session sessao;

    public PerguntaController() {
        logger.info("entrou na PerguntaController");
        perguntaDao = new PerguntaDaoImpl();
//                buscarPerguntasElaboradasPeloUsuario();
    }

    public void vinculaSubcategoriaComPergunta(SubCategoria subCategoria) {
        logger.info("método - vinculaSubcategoriaComPergunta()");
        this.subCategoria = subCategoria;
    }

    public void visualizaQuestaoSelecionada(Pergunta perguntaSele) {
        logger.info("entrou no método visualizaQuestaoSelecionada()");
        perguntaSelecionada = perguntaSele;
    }

    /**
     * ALTERNATIVA *
     */
    /**
     * Insere alternativas na tabela
     */
    public void populaListaAlternativa() {
        if (alternativa.getTexto() != null && alternativa.getTexto().trim().length() != 0) {
            alternativa.setId(contadorId++);
            listaAlternativas.add(alternativa);
            apresentaTabela();
            alternativa = null;
        } else {
            Mensagem.sucesso("Texto da alternativa não pode ser vazio!");
        }
    }

    /**
     * Apresenta na tela as alternativas cadastradas
     */
    public void apresentaTabela() {
        try {
            modelAlternativas = new ListDataModel<>(listaAlternativas);
        } catch (HibernateException e) {
            logger.error("Método apresentaTabela() - " + e.getMessage());
        }
    }

    /**
     * Exclui alternativa da tabela
     */
    public void excluir() {
        alter = new Alternativa();
        alter = modelAlternativas.getRowData();
        listaAlternativas.removeIf(alt -> alt.equals(alter));
    }

    // CRUD
    public void salvarPergunta(Categoria categoria) {
        logger.info("método - salvar()");

        try {

            sessao = HibernateUtil.abrirSessao();
            subCategoria.setCategoria(categoria);
            pergunta.setSubCategoria(subCategoria);
            Date criacao = new Date(System.currentTimeMillis());
            pergunta.setDataCriacao(criacao);
            pergunta.setUsuarioProprietario(LoginController.usuarioSessao());

            if (validaDados(categoria)) {
                perguntaDao.salvarOuAlterar(pergunta, sessao);
                selecionaAlternativaCorreta(pergunta.getId());
                Mensagem.sucesso("Questão cadastrada com sucesso!");
                this.listaAlternativas.removeAll(listaAlternativas);
                this.alternativaCorreta = null;
                this.modelAlternativas = null;
                this.pergunta = null;
            } else {
                throw new Exception("Dados obrigatórios não preenchidos");
            }

        } catch (Exception e) {
            logger.error("Erro ao salvar - " + e.getMessage());

        } finally {
            sessao.close();
        }

    }

    private boolean validaDados(Categoria categoria) {
        logger.info("método - validaDados()");

        boolean camposValidos = true;

        if (null == this.pergunta.getTexto() ||  this.pergunta.getTexto().isEmpty()) {
			camposValidos = false;
			Mensagem.erro("Campo 'PERGUNTA' deve ser preenchido!");
		} else if (null == categoria.getId()){
			camposValidos = false;
			Mensagem.erro("Campo 'CATEGORIA' deve ser preenchido!");
		} else if (null == this.pergunta.getSubCategoria().getId()){
			camposValidos = false;
			Mensagem.erro("Campo 'SUBCATEGORIA' deve ser preenchido!");
		} else if (null == this.listaAlternativas || this.listaAlternativas.isEmpty()){
			camposValidos = false;
			Mensagem.erro("Campo 'ALTERNATIVA' deve ser preenchido!");
		} else if (this.listaAlternativas.size() < 2){
			camposValidos = false;
			Mensagem.erro("Campo 'ALTERNATIVA' deve conter no mínimo 2 alternativas!");
		} else if (null == alternativaCorreta.getId()){
			camposValidos = false;
			Mensagem.erro("Selecione a 'ALTERNATIVA CORRETA'!");
		}
        return camposValidos;
    }

    private void selecionaAlternativaCorreta(Long id) {
        alternativaDao = new AlternativaDaoImpl();
        for (Alternativa altern : listaAlternativas) {
            if (altern.getId() == alternativaCorreta.getId()) {
                altern.setStatusCorreta(true);
            } else {
                altern.setStatusCorreta(false);
            }
            altern.setPergunta(pergunta);
            altern.setId(null);
            alternativaDao.salvarOuAlterar(altern, sessao);
        }

    }

    public void buscaPerguntasPorCategoria() {
        logger.info("método - buscaPerguntaPorCategoria()");
        try {
            sessao = HibernateUtil.abrirSessao();
            perguntas = perguntaDao.buscaPerguntasPorCategoria(categoria, sessao);
            modelPerguntas = new ListDataModel<>(perguntas);
        } catch (HibernateException e) {
            logger.error("erro na busca de perguntas por categoria " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void buscaPerguntasPorSubCategoria() {
        logger.info("método - buscaPerguntasPorSubCategoria()");
        try {
            sessao = HibernateUtil.abrirSessao();
            perguntas = perguntaDao.buscaPerguntasPorSubCategoria(subCategoria.getId(), sessao);
            modelPerguntas = new ListDataModel<>(perguntas);
        } catch (HibernateException e) {
            logger.error("erro na busca de perguntas por subCategoria " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

	public void buscarPerguntasElaboradasPeloUsuario() {
		logger.info("método - buscarPerguntasElaboradasPeloUsuario()");

		Usuario usuarioLogado = LoginController.usuarioSessao();

		try {
			sessao = HibernateUtil.abrirSessao();
			perguntas = perguntaDao.buscarPerguntasElaboradosPeloUsuario(usuarioLogado.getId(), sessao);
			modelPerguntas = new ListDataModel<>(perguntas);
		} catch (HibernateException e) {
			logger.error("erro na busca de perguntas por usuario " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void buscaPerguntasComFiltro(Long id_categoria, Long id_sub_categoria) {
		logger.info("método - buscaPerguntasComFiltro()");
		
		if (id_categoria != null && id_sub_categoria != null) {
			SubCategoriaDao subCategoriaDao = new SubCategoriaDaoImpl();
			List<SubCategoria> subCategoriaValida = null;
			try {
				sessao = HibernateUtil.abrirSessao();
				subCategoriaValida = subCategoriaDao.pesquisarPorIdCategoria(id_categoria, sessao);
			} catch (HibernateException e) {
				logger.error("erro na busca de perguntas com filtro " + e.getMessage());
			} finally {
				sessao.close();
			}
			
			boolean flag = false;
			if (subCategoriaValida != null) {
				for (SubCategoria subCategoria : subCategoriaValida) {
					if (subCategoria.getId() == id_sub_categoria) {
						flag = false;
						break;
					} else {
						flag = true;
					}
				}
			}
			
			if (flag) {
				id_sub_categoria = null;
			}
		}

		if (id_categoria != null) {
			try {
				sessao = HibernateUtil.abrirSessao();
				List<Pergunta> perguntasBuscadas = perguntaDao.buscaPerguntasComFiltro(id_categoria, id_sub_categoria, refinaBusca, sessao);
				this.filtrarPerguntas(perguntasBuscadas);
				modelPerguntas = new ListDataModel<>(perguntas);
			} catch (HibernateException e) {
				logger.error("erro na busca de perguntas com filtro " + e.getMessage());
			} finally {
				sessao.close();
			}
		} else {
			perguntas = null;
			modelPerguntas = new ListDataModel<>(perguntas);
		}
	}

    private void filtrarPerguntas(List<Pergunta> perguntasBuscadas) {
    	perguntas = new ArrayList<Pergunta>();
    	Long usuarioLogado = LoginController.usuarioSessao().getId();
		if(usuarioLogado != null) {
			for (Pergunta perguntaBuscada : perguntasBuscadas) {
				if(perguntaBuscada.isVisibilidadePrivada()) {
					if (perguntaBuscada.getUsuarioProprietario().getId() == usuarioLogado) {
						perguntas.add(perguntaBuscada);
					}
				} else {
					perguntas.add(perguntaBuscada);
				}
			}
		} else {
			for (Pergunta perguntaBuscada : perguntasBuscadas) {
				if(!perguntaBuscada.isVisibilidadePrivada()) {
					perguntas.add(perguntaBuscada);
				}
			}
		}
	}

	public void buscaPerguntaPorId(Long idPergunta){
        logger.info("método - buscaPerguntaPorId()");
        try {
        	sessao = HibernateUtil.abrirSessao();
        	pergunta = perguntaDao.buscaPerguntaPorId(idPergunta, sessao);
        	perguntaModal = pergunta;
        } catch (HibernateException e) {
			logger.error("erro na busca de perguntas por id: " + e.getMessage());
		} finally {
			sessao.close();
		}
    }
	
	// GETTERS AND SETTERS
    public Pergunta getPergunta() {
        if (pergunta == null) {
            pergunta = new Pergunta();
        }
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public Categoria getCategoria() {
        if (categoria == null) {
            categoria = new Categoria();
        }
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria() {
        if (subCategoria == null) {
            subCategoria = new SubCategoria();
        }
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Alternativa getAlternativaCorreta() {
        if (null == alternativaCorreta) {
            alternativaCorreta = new Alternativa();
        }
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(Alternativa alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public Alternativa getAlternativa() {
        if (alternativa == null) {
            alternativa = new Alternativa();
        }
        return alternativa;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public DataModel<Pergunta> getModelPerguntas() {
        return modelPerguntas;
    }

    public void setModelPerguntas(DataModel<Pergunta> modelPerguntas) {
        this.modelPerguntas = modelPerguntas;
    }

    public List<Alternativa> getListaAlternativas() {
        return listaAlternativas;
    }

    public void setListaAlternativas(List<Alternativa> listaAlternativas) {
        this.listaAlternativas = listaAlternativas;
    }

    public DataModel<Alternativa> getModelAlternativas() {
        return modelAlternativas;
    }

    public void setModelAlternativas(DataModel<Alternativa> modelAlternativas) {
        this.modelAlternativas = modelAlternativas;
    }

    public Alternativa getAlter() {
        return alter;
    }

    public void setAlter(Alternativa alter) {
        this.alter = alter;
    }

    public String getRefinaBusca() {
        return refinaBusca;
    }

    public void setRefinaBusca(String refinaBusca) {
        this.refinaBusca = refinaBusca;
    }

    public Pergunta getPerguntaSelecionada() {
        if (perguntaSelecionada == null) {
            perguntaSelecionada = new Pergunta();
        }
        return perguntaSelecionada;
    }

    public void setPerguntaSelecionada(Pergunta perguntaSelecionada) {
        this.perguntaSelecionada = perguntaSelecionada;
    }

    public Pergunta getPerguntaEdit() {
        if (perguntaEdit == null) {
            perguntaEdit = new Pergunta();
        }
        return perguntaEdit;
    }

    public void setPerguntaEdit(Pergunta perguntaEdit) {
        this.perguntaEdit = perguntaEdit;
    }

    public Pergunta getPerguntaModal() {
        return perguntaModal;
    }

    public void setPerguntaModal(Pergunta perguntaModal) {
        this.perguntaModal = perguntaModal;
    }
    
    

}
