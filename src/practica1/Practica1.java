package practica1;

import java.util.*;

public class Practica1 {

    //Calcula la diferencia simétrica de s1 y s2 en s1. s2 contiene los elementos que están en ambos conjuntos
    public static <T> void difSimetrica (Set<T> s1, Set<T> s2) {
        Set<T> aux = new HashSet<>();
        Iterator<T> it = s1.iterator();
        while (it.hasNext()) {
            T elem = it.next();
            if (s2.contains(elem)) {
                aux.add(elem);
                it.remove();
            }
        }
        it = s2.iterator();
        while (it.hasNext()) {
            T elem = it.next();
            if (!aux.contains(elem)) {
                s1.add(elem);
                it.remove();
            }
        }
    }

    //Calcula y devuelve la diferencia simétrica de una colección de conjuntos
    public static <T> Set<T> difSimetricaCol (Collection<Set<T>> col) {
        Set<T> resul = new HashSet<>();
        Iterator<Set<T>> it = col.iterator();
        if (it.hasNext()) {
            Set<T> s=it.next();
            resul.addAll(s);
            while (it.hasNext()) {
                difSimetrica(resul,it.next());
            }
        }
        return resul;
    }

    //Dado un iterador a una colleción de elementos, devolver un conjunto con los elementos que no se repiten
    //en la colección inicial deben quedar solo los elementos repetidos
    public static <T> Set<T> unicos (Iterator<T> it) {
        Set<T> aux=new HashSet<>();
        Set<T> resul = new HashSet<>();
        while (it.hasNext()) {
            T elem = it.next();
            if (!aux.contains(elem)) {
                resul.add(elem); //no repetido
                aux.add(elem);
            }
            else {
                resul.remove(elem);
            }
        }
        return resul;
    }

    //Dada una colección y un conjunto de elementos del mismo tipo, devuelve cuántas veces ocurre el conjunto
    //en la colección (teniendo en cuenta que cada elemento de la colección solo puede ser usado una vez)
    public static <T> int  numOcurrecias  (Collection<T> col, Set<T> s) {
        int n = 0;
        if (s.isEmpty())
            return n;
        boolean stop = false;
        //System.out.println("numOcurrencias "+col.size());
        while (col.size()>0 && !stop) {
            Iterator<T> it = s.iterator();
            while (it.hasNext() && !stop) {
                T elem = it.next();
                //System.out.println("Elem del conjunto "+elem);
                if (col.contains(elem)) {
                    //System.out.println("esta en la colección");
                    col.remove(elem);
                }
                else {
                    stop=true;
                }
            }
            if (!stop) {
                n++;
                //System.out.println("incrementa n");
            }
        }
        return n;
    }

    //Dividir una colección de elementos en conjuntos, según el orden de los elementos en la colección
    public static <T> Collection<Set<T>>  split  (Collection<T> col) {
        Collection<Set<T>> resul = new LinkedList<Set<T>>();
        Set<T> s = new HashSet<T>();
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            T elem = it.next();
            if (!s.contains(elem)) {
                s.add(elem);
            }
            else {
                resul.add(s);
                s=new HashSet<>();
                s.add(elem);
            }
        }
        resul.add(s);
        return resul;
    }
}
