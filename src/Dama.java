//classe Dama jogo damas
public class Dama extends Peca {
    // Construtor da classe Dama, define a posição e a cor da peça, e marca como dama
    public Dama(int x, int y, String cor) {
        super(x, y, cor);
        this.isDama = true;
    }

    // Metodo para a dama poder mover-se livremente
    public void moverLivre(int xDestino, int yDestino) {
        // Implementação do movimento livre da dama
    }

    // Metodo para a dama capturar múltiplas peças
    public void capturarLivre(int xDestino, int yDestino) {
        // Implementação da captura múltipla
    }
}