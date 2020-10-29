package Model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe utilizada para comparar duas IInfoVendas pelo mês, necessária para o TreeSet de IInfoVendas definido
 * na filial. Organiza , então, os meses de forma crescente.
 */
public class Comparable implements Comparator<IInfoVenda>, Serializable {

    public int compare(IInfoVenda infoVenda1, IInfoVenda infoVenda2) {
        int mes1 = infoVenda1.getMes();
        int mes2 = infoVenda2.getMes();
        int res;
        if (mes1 == mes2)
            res = 0;
        if (mes1 > mes2)
            res = 1;
        else res = -1;
        return res;
    }

}
