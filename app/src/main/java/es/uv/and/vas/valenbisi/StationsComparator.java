package es.uv.and.vas.valenbisi;

import java.util.Comparator;

public class StationsComparator implements Comparator<Station> {
    @Override
    public int compare(Station s1, Station s2) {
        int n1 = s1.properties.number;
        int n2 = s2.properties.number;
        if(n1 > n2){
            return 1;
        }else if(n2 > n1){
            return -1;
        }
        return 0;
    }
}