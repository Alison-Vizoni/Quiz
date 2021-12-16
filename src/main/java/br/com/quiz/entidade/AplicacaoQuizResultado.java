/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.quiz.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author alison
 */
@Entity
@Table(name = "quiz_resultado")
public class AplicacaoQuizResultado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private AplicacaoQuizResultadoPK id = new AplicacaoQuizResultadoPK();
    
    @Column(name = "data_resolucao")
    private Date dataResolucao;

    public AplicacaoQuizResultado() {
    }

    public AplicacaoQuizResultado(Alternativa alternativa, Usuario usuario, 
            AplicacaoQuiz aplicacaoQuiz,Date dataResolucao) {
        id.setAlternativa(alternativa);
        id.setUsuario(usuario);
        id.setAplicacaoQuiz(aplicacaoQuiz);
        this.dataResolucao = dataResolucao;
    }
    
    public AplicacaoQuizResultadoPK getId() {
        return id;
    }

    public void setId(AplicacaoQuizResultadoPK id) {
        this.id = id;
    }
    
    public Alternativa getAlternativa(){
        return id.getAlternativa();
    }
    
    public void set(Alternativa alternativa){
        id.setAlternativa(alternativa);
    }
    
    public Usuario getUsuario(){
        return id.getUsuario();
    }
    
    public void set(Usuario usuario){
        id.setUsuario(usuario);
    }
    
    public AplicacaoQuiz getAplicacaoQuiz(){
        return id.getAplicacaoQuiz();
    }
    
    public void set(AplicacaoQuiz aplicacaoQuiz){
        id.setAplicacaoQuiz(aplicacaoQuiz);
    }

    public Date getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(Date dataResolucao) {
        this.dataResolucao = dataResolucao;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (this == object) return false;
        
        if (object == null || getClass() != object.getClass()) return false;
        AplicacaoQuizResultado quizResultado = (AplicacaoQuizResultado) object;
        return Objects.equals(id, quizResultado.id);
    }
}