//classe Peca jogo damas
public class Peca {
    private int x; // Coordenada x da peça
    private int y; // Coordenada y da peça
    private String cor; // Cor da peça
    protected boolean isDama; // Indica se a peça é uma dama

    // Construtor da classe Peca, define a posição e a cor da peça
    public Peca(int x, int y, String cor) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.isDama = false;
    }

    // Metodo para mover uma peça normal
    public void mover(int xDestino, int yDestino) {
        // Implementação do movimento da peça normal
    }

    // Metodo para capturar uma peça adversária
    public void capturar(int xDestino, int yDestino) {
        // Implementação da captura de uma peça adversária
    }

    // Metodo para promover uma peça a dama
    public void promoverADama() {
        isDama = true;
    }
}