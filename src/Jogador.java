//classe jogador jogo damas
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String cor;
    private List<Peca> pecas;

    public Jogador(String cor) {
        this.cor = cor;
        pecas = new ArrayList<>();
    }

    public void realizarJogada(Tabuleiro tabuleiro) {
        // Logica para o jogador realizar uma jogada
    }

    public void verificarMovimentosValidos(Tabuleiro tabuleiro) {
        // Verifica os movimentos válidos para o jogador
    }

    public String getCor() {
        return cor;
    }

    public List<Peca> getPecas() {
        return pecas;
    }
}
