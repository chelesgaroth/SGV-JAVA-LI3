package Model;

import java.io.Serializable;
import java.util.Comparator;
import java.lang.*;

/**
 * Classe utilizada para comparar duas IInfoFatur, necessária para o TreeSet de IInfoFatur definido na classe Faturacao.
 * Organiza, então, as IInfoFatur por mês e por filial.
 */
public class CompareFat implements Comparator<IInfoFatur>, Serializable {

    public int compare(IInfoFatur i1, IInfoFatur i2) {
        int comparison = 0;
        comparison = compareFilial(i1,i2);
        if (comparison == 0){
            comparison = compareMes(i1,i2);
        }
        return comparison;
    }

    public int compareFilial(IInfoFatur i1, IInfoFatur i2){
        int fil1 = i1.get_fil();
        int fil2 = i2.get_fil();
        int res;
        if (fil1 == fil2)
            res = 0;
        if (fil1 > fil2)
            res = 1;
        else res = -1;
        return res;
    }
    public int compareMes (IInfoFatur i1, IInfoFatur i2){
        int mes1 = i1.get_mes();
        int mes2 = i1.get_mes();
        int res;
        if (mes1 == mes2)
            res = 0;
        if (mes1 > mes2)
            res = 1;
        else res = -1;
        return res;
    }

}