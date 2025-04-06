//classe Jogo jogo damas
public class Jogo {
    private Tabuleiro tabuleiro; // Instância do tabuleiro do jogo
    private Jogador jogador1; // Jogador 1
    private Jogador jogador2; // Jogador 2
    private int turno; // Indica o turno atual (1 ou 2)

    // Metodo para iniciar o jogo
    public void iniciarJogo() {
        tabuleiro = new Tabuleiro();
        jogador1 = new Jogador("branco");
        jogador2 = new Jogador("preto");
        turno = 1;

        tabuleiro.criarTabuleiro();
        jogar();
    }

    // Metodo que contém o ciclo do jogo
    public void jogar() {
        while (!verificarFimDeJogo()) {
            if (turno == 1) {
                jogador1.realizarJogada(tabuleiro);
            } else {
                jogador2.realizarJogada(tabuleiro);
            }
            mudarTurno();
        }
        System.out.println("Fim de Jogo!");
    }

    // Metodo para verificar se o jogo terminou
    public boolean verificarFimDeJogo() {
        // Implementação da verificação do fim do jogo
        return false;
    }

    // Metodo para mudar de turno
    public void mudarTurno() {
        turno = (turno == 1) ? 2 : 1;
    }
}