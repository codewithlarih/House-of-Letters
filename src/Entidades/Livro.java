package Entidades;
 
public class Livro {
	private String id;
	private String titulo;
	private String autor;
	private int ano;
	private int quanttotal;
	private int quantdisponivel;
	private String genero;
 
	public Livro(String id, String titulo, String autor, int ano,int quanttotal, int quantdisponivel, String genero) {
        this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.ano = ano;
		this.quanttotal = quanttotal;
		this.quantdisponivel = quantdisponivel;
		this.genero = genero;
	}
 
	public String getId() {
		return id;
	}
 
	public void setId(String id) {
		this.id = id;
	}
 
	public String getTitulo() {
		return titulo;
	}
 
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
 
	public String getAutor() {
		return autor;
	}
 
	public void setAutor(String autor) {
		this.autor = autor;
	}
 
	public int getAno() {
		return ano;
	}
 
	public void setAno(int ano) {
		this.ano = ano;
	}
 
	public int getQuanttotal() {
		return quanttotal;
	}
 
	public void setQuanttotal(int quanttotal) {
		this.quanttotal = quanttotal;
	}
 
	public int getQuantdisponivel() {
		return quantdisponivel;
	}
 
	public void setQuantdisponivel(int quantdisponivel) {
		this.quantdisponivel = quantdisponivel;
	}
 
	public String getGenero() {
		return genero;
	}
 
	public void setGenero(String genero) {
		this.genero = genero;
	}
 
	public boolean emprestarExemplar() {
		if (quantdisponivel > 0) {
			quantdisponivel--;
			return true;
		}
		return false;
	}
 
	// Devolve um exemplar ao estoque
	// return true se a devolução foi aceita e false se já estavam todos devolvidos
 
	public boolean devolverExemplar() {
		if (quantdisponivel < quanttotal) {
			quantdisponivel++;
			return true;
		}
		return false; 
	}
 
	public boolean estaDisponivel() {
		return quantdisponivel > 0;
	}
 
	@Override
	public String toString() {
		return "[" + id + "] " + titulo + " - " + autor + " (" + ano + ") | "
				+ genero + " Disponíveis: " + quantdisponivel + "/" + quanttotal;
	}
}
