package model;

import view.MensagensException;

public abstract class Pessoa {
    private int id;
    private String nome;
    private int idade;

    public Pessoa() {
    }

    public Pessoa(int id, String nome, int idade) throws MensagensException {
        this.id = id;
        this.setNome(nome);
        this.setIdade(idade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws MensagensException {
        if (nome.length()<2)
            throw new MensagensException("O nome deve ter no mínimo 2 caractéres");
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) throws MensagensException {
        if (idade <=0)
            throw  new MensagensException("Idade deve ser maior que 0");
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome=" + nome + ", idade=" + idade + '}';
    }
    
    
    
    
    
}
