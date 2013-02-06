package laboratory.util.functional;

import laboratory.util.functional.Functor1;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Util {

    public static <A, B> ArrayList<B> map(List<A> as, Functor1<A, B> f) {
        final ArrayList<B> bs = new ArrayList<B>(as.size());
        for (A a: as) {
            bs.add(f.apply(a));
        }
        return bs;
    }

    public static <A> List<A> filter(List<A> as, Functor1<A, Boolean> f) {
        final List<A> res = new LinkedList<A>();
        for (A a: as) {
            if (f.apply(a)) {
                res.add(a);
            }
        }
        return res;
    }    

    public static <A> ArrayList<A> listFromFunctor(Functor0<A> f, int size) {
        ArrayList<A> res = new ArrayList<A>(size);
        for (int i = 0; i < size; i++) {
            res.add(f.apply());
        }
        return res;
    }

    public static int[] getIntArray(List<Integer> list) {
        int[] res = new int[list.size()];
        int j = 0;
        for (int i: list) {
            res[j] = i;
            j++;
        }
        return res;
    }
}

