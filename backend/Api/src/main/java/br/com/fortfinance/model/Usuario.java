package br.com.fortfinance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome_completo", length = 200, nullable = true)
	private String nome_completo;
	
	@Column(name = "username", length = 100, nullable = true)
	private String username;

	@Column(name = "email", length = 50, nullable = true)
	private String email;

	@Column(name = "senha", columnDefinition = "TEXT", nullable = true)
	private String senha;

	@Column(name = "telefone", length = 15, nullable = true)
	private String telefone;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Despesas> despesas;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Receitas> receitas;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome_completo;
	}
	public void setNome(String nome) {
		this.nome_completo = nome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public List<Despesas> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<Despesas> despesas) {
		this.despesas = despesas;
	}
	public void setReceitas(List<Receitas> receitas) {
		this.receitas = receitas;
	}
}