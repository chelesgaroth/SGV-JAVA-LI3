package View;

import java.util.List;

public interface INavegador {
    void divide(List<String> resposta, List<Integer> resposta2, String mensagem, int q);
    void proxima(List<String> resposta, List<Integer> resposta2, String mensagem, int q);
    void anterior(List<String> resposta, List<Integer> resposta2, String mensagem, int q);
    void total(List<String> resposta);
    void escolha(List<String> resposta, List<Integer> resposta2, String mensagem, int q, int num);
    void menu();
}
