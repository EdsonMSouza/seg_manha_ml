/**
 *
 * @author Edson Melo de Souza
 */
import java.util.Random;

public class Individuo {

    private String genes = "";
    private int aptidao = 0;

    //gera um indivíduo aleatório
    public Individuo(int numGenes) {
        genes = "";
        Random r = new Random();

        String caracteres = AG.getCaracteres();

        for (int i = 0; i < numGenes; i++) {
            genes += caracteres.charAt(r.nextInt(caracteres.length()));
        }

        geraAptidao();
    }

    //cria um indivíduo com os genes definidos
    public Individuo(String genes) {
        this.genes = genes;

        Random r = new Random();
        //se for mutar, cria um gene aleatório
        if (r.nextDouble() <= AG.getTaxaDeMutacao()) {
            String caracteres = AG.getCaracteres();
            String geneNovo = "";
            int posAleatoria = r.nextInt(genes.length());
            for (int i = 0; i < genes.length(); i++) {
                if (i == posAleatoria) {
                    geneNovo += caracteres.charAt(r.nextInt(caracteres.length()));
                } else {
                    geneNovo += genes.charAt(i);
                }

            }
            this.genes = geneNovo;
        }
        geraAptidao();
    }

    //gera o valor de aptidão, será calculada pelo número de bits do gene iguais ao da solução
    private void geraAptidao() {
        String solucao = AG.getSolucao();
        for (int i = 0; i < solucao.length(); i++) {
            if (solucao.charAt(i) == genes.charAt(i)) {
                aptidao++;
            }
        }
    }

    public int getAptidao() {
        return aptidao;
    }

    public String getGenes() {
        return genes;
    }
}
