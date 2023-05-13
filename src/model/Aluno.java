package model;

import view.MensagensException;

public class Aluno extends Pessoa{
    private String curso;
    private int fase;

    public Aluno() {
        super();
    }

    public Aluno(String curso, int fase) throws MensagensException {
         this.setCurso(curso);
        this.setFase(fase);
    }

    public Aluno(String curso, int fase, int id, String nome, int idade) throws MensagensException {
        super(id, nome, idade);
        this.setCurso(curso);
        this.setFase(fase);
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) throws MensagensException {
        if (curso.length()<2)
            throw new MensagensException("Curso deve conter no mínimo 2 caracteres");
        this.curso = curso;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) throws MensagensException {
        if (fase <=0)
            throw new MensagensException("Fase deve ser maior que 0");
        this.fase = fase;
    }

    @Override
    public String toString() {
        return "Aluno{" +super.toString()+ "curso=" + curso + ", fase=" + fase + '}';
    }
    
    
    
    
}
