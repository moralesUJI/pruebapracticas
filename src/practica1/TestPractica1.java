package practica1;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static practica1_2122.Practica1.difSimetrica;

public class TestPractica1 {
    private String[] conjuntoTest1 = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private String[] conjuntoTest2 = {"CERO", "UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"};
    private String[] conjuntoPeq1 = {"uno", "dos", "tres"};
    private String[] conjuntoPeq2 = {"cuatro", "cinco", "seis", "siete"};

    private LinkedList<String> generarColAleatoria (int size, int limInf, int limSup) {
        LinkedList<String> col= new LinkedList<>();
        Random rand=new Random();
        for (int i=0; i<size; i++) {
            int n = rand.nextInt();
            if (n<0)
                n=n*(-1);
            n=n%limSup+limInf;
            col.add(String.valueOf(n));
        }
        return col;
    }

    private Set<String> generarConjAleatorio (int size, int limInf, int limSup) {
        Set<String> s= new HashSet<>();
        Random rand=new Random();
        for (int i=0; i<size; i++) {
            int n = rand.nextInt() * (limSup - limInf + 1) + limInf;
            if (n<0)
                n=n*(-1);
            n=n%limSup+limInf;
            String str = String.valueOf(n);
            if (!s.contains(str)) {
                s.add(str);
            }
        }
        return s;
    }

    private <E> void difSimetricaParaTest(Set<E> s1, Set<E> s2) {
        Set<E> aux = new HashSet<>();
        aux.addAll(s2);
        s2.retainAll(s1); //intersección
        //System.out.println("interseccion "+s2.toString());
        s1.addAll(aux);
        s1.removeAll(s2);
    }

    private <E> Set<E> difSimetricaConjParaTest(Collection<Set<E>> col) {
        Map<E, Integer> mapa = new HashMap<>();
        Set<E> result = new HashSet<>();
        for (Set<E> s : col) {
            for (E elem : s) {
                if (mapa.containsKey(elem)) {
                    int n = mapa.get(elem) + 1;
                    mapa.put(elem, n);
                } else
                    mapa.put(elem, 1);
            }
        }
        for (E elem : mapa.keySet()) {
            if (mapa.get(elem) % 2 != 0) // es impar
                result.add(elem);
        }
        return result;
    }

    private static <E> Set<E> unicosParaTest(Iterator<E> it) {
        Map<E, Integer> mapa = new HashMap<>();
        while (it.hasNext()) {
            E e = it.next();
            if (mapa.containsKey(e)) {
                int n = mapa.get(e) + 1;
                mapa.put(e, n);
            } else
                mapa.put(e, 1);
        }
        Set<E> s = new HashSet<>();
        for (E e : mapa.keySet()) {
            if (mapa.get(e) == 1)
                s.add(e);
        }
        return s;
    }

    private static <T> int numOcurreciasParaTest(Collection<T> col, Set<T> s) {
        int n = 0;
        if (s.size() ==0)
            return n;
        if (s.size()<=col.size()) {
            boolean seguir=true;
            while (col.size()>0 && seguir) {
                for (T e:s) {
                    if (col.contains(e))
                        col.remove(e);
                    else
                        seguir=false;
                }
                if (seguir)
                    n++;
            }

        }
        return n;
    }

    private void splitParaTest (Collection<String> linicial, Collection<Set<String>> esperado, int size) {
        //linicial=new LinkedList<>();
        //esperado=new LinkedList<>();
        Set<String> sesperado = this.generarConjAleatorio(3,1,10);
        //System.out.println("conjunto generado "+sesperado.toString());
        linicial.addAll(sesperado);
        for (int i=0; i<5; i++) {
            Set<String> s = this.generarConjAleatorio(size,1,10);
            linicial.addAll(s);
            Iterator<String> it = s.iterator();
            while (it.hasNext()) {
                String e=it.next();
                if (sesperado.contains(e)) {
                    esperado.add(sesperado);
                    sesperado = new HashSet<>();
                    sesperado.add(e);
                }
                else {
                    sesperado.add(e);
                }
            }
        }
        esperado.add(sesperado);
    }

    @org.junit.Test
    public void difSimetricaTest() {
        Set<String> conj1 = new HashSet<>();
        Set<String> conj2 = new HashSet<>();
        Set<String> conj1Inicial = new HashSet<>(conj1);
        Set<String> conj2Inicial = new HashSet<>(conj2);

        //Prueba1: los dos conjuntos vacios
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        Set<String> conj11Inicial = new HashSet<>(conj1);
        Set<String> conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");

        //Prueba2: primer conjunto vacío
        conj1 = new HashSet<String>();
        conj2 = new HashSet<String>();
        for (int i = 0; i < conjuntoTest1.length; i++)
            conj2.add(conjuntoTest1[i]);
        conj1Inicial = new HashSet<>(conj1);
        conj2Inicial = new HashSet<>(conj2);
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        conj11Inicial = new HashSet<>(conj1);
        conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");

        //Prueba3: segundo conjunto vacío
        conj2 = new HashSet<>();
        conj1 = new HashSet<String>();
        for (int i = 0; i < conjuntoTest1.length; i++)
            conj1.add(conjuntoTest1[i]);
        conj1Inicial = new HashSet<>(conj1);
        conj2Inicial = new HashSet<>(conj2);
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        conj11Inicial = new HashSet<>(conj1);
        conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");


        //Prueba5: los dos conjuntos distintos
        conj1 = new HashSet<String>();
        conj2 = new HashSet<String>();
        for (int i = 0; i < conjuntoPeq1.length; i++)
            conj1.add(conjuntoPeq1[i]);
        for (int i = 0; i < conjuntoPeq2.length; i++)
            conj2.add(conjuntoPeq2[i]);
        conj1Inicial = new HashSet<>(conj1);
        conj2Inicial = new HashSet<>(conj2);
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        conj11Inicial = new HashSet<>(conj1);
        conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");

        //Prueba6: primer conjunto incluido en el segundo
        for (int i = 0; i < conjuntoPeq1.length; i++)
            conj1.add(conjuntoPeq1[i]);
        for (int i = 0; i < conjuntoPeq2.length; i++)
            conj2.add(conjuntoPeq2[i]);
        conj2.addAll(conj1);
        conj1Inicial = new HashSet<>(conj1);
        conj2Inicial = new HashSet<>(conj2);
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        conj11Inicial = new HashSet<>(conj1);
        conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");

        //Prueba7: segundo conjunto incluido en el primero

        conj1 = new HashSet<String>();
        conj2 = new HashSet<String>();
        for (int i = 0; i < conjuntoPeq1.length; i++)
            conj1.add(conjuntoPeq1[i]);
        for (int i = 0; i < conjuntoPeq2.length; i++)
            conj2.add(conjuntoPeq2[i]);
        conj1.addAll(conj2);
        conj1Inicial = new HashSet<>(conj1);
        conj2Inicial = new HashSet<>(conj2);
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        conj11Inicial = new HashSet<>(conj1);
        conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");

        //Prueba8: caso general
        conj1 = new HashSet<String>();
        conj2 = new HashSet<String>();
        for (int i = 0; i < conjuntoPeq1.length; i++)
            conj1.add(conjuntoPeq1[i]);
        for (int i = 0; i < conjuntoPeq2.length; i++)
            conj2.add(conjuntoPeq2[i]);
        conj1.add("siete");
        conj2.add("dos");
        conj2.add("tres");
        conj1Inicial = new HashSet<>(conj1);
        conj2Inicial = new HashSet<>(conj2);
        System.out.println("Entrada: ");
        System.out.println(" Conjunto1 : " + conj1Inicial.toString());
        System.out.println(" Conjunto2 : " + conj2Inicial.toString());
        difSimetricaParaTest(conj1Inicial, conj2Inicial);
        System.out.println("Salida esperada: ");
        System.out.println("  conj1: " + conj1Inicial.toString() + "  conj2: " + conj2Inicial.toString());

        conj11Inicial = new HashSet<>(conj1);
        conj22Inicial = new HashSet<>(conj2);
        Practica1.difSimetrica(conj11Inicial, conj22Inicial);

        System.out.println("Salida");
        System.out.println("  conj1: " + conj11Inicial.toString() + "  conj2: " + conj22Inicial.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", conj1Inicial, conj11Inicial);
        assertEquals("filtra: RESPUESTA INCORRECTA", conj2Inicial, conj22Inicial);
        System.out.println("--------------------");
    }

    @Test
    public void difSimetricaTestConj() {
        //Prueba 0: Lista Vacía de conjuntos
        List<Set<String>> linicial = new LinkedList<>();
        List<Set<String>> l1 = new LinkedList<>(linicial);
        System.out.println("Entrada: ");
        System.out.println(" Coleccion : " + l1.toString());
        Set<String> esperado = difSimetricaConjParaTest(l1);
        System.out.println("Salida esperada: " + esperado.toString());

        List<Set<String>> l2 = new LinkedList<>(linicial);
        Set<String> obtenido = Practica1.difSimetricaCol(l2);

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //Prueba 2: Lista con un solo conjunto
        linicial = new LinkedList<>();
        Set<String> s = new HashSet<>();
        for (int i = 0; i < conjuntoTest1.length; i++)
            s.add(conjuntoTest1[i]);
        linicial.add(s);
        l1 = new LinkedList<>(linicial);
        System.out.println("Entrada: ");
        System.out.println(" Coleccion : " + l1.toString());
        esperado = difSimetricaConjParaTest(l1);
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.difSimetricaCol(l2);

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //Prueba 3: Lista tamaño impar
        linicial = new LinkedList<>();
        s = new HashSet<>();
        for (int i = 0; i < conjuntoTest1.length; i++)
            s.add(conjuntoTest1[i]);
        linicial.add(s);
        s = new HashSet<>();
        for (int i = 0; i < conjuntoTest2.length; i++)
            s.add(conjuntoTest2[i]);
        linicial.add(s);
        s = new HashSet<>();
        for (int i = 0; i < conjuntoPeq1.length; i++)
            s.add(conjuntoPeq1[i]);
        linicial.add(s);
        l1 = new LinkedList<>(linicial);
        System.out.println("Entrada: ");
        for (int i = 0; i < l1.size(); i++) {
            System.out.println("    " + l1.get(i).toString());
        }
        esperado = difSimetricaConjParaTest(l1);
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.difSimetricaCol(l2);

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //Prueba 4: Lista tamaño par
        s = new HashSet<>();
        for (int i = 0; i < conjuntoPeq2.length; i++)
            s.add(conjuntoPeq2[i]);
        linicial.add(s);
        l1 = new LinkedList<>(linicial);
        System.out.println("Entrada: ");
        for (int i = 0; i < l1.size(); i++) {
            System.out.println("    " + l1.get(i).toString());
        }
        esperado = difSimetricaConjParaTest(l1);
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.difSimetricaCol(l2);

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //Prueba 5: con un conjunto vacío
        s = new HashSet<>();
        linicial.add(s);
        l1 = new LinkedList<>(linicial);
        System.out.println("Entrada: ");
        for (int i = 0; i < l1.size(); i++) {
            System.out.println("    " + l1.get(i).toString());
        }
        esperado = difSimetricaConjParaTest(l1);
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.difSimetricaCol(l2);

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

    }

    @org.junit.Test
    public void unicosTest() {
        //prueba1: coleccion vacia
        List<String> linicial = new LinkedList<>();
        List<String> l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        Set<String> esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        List<String> l2 = new LinkedList<>(linicial);
        Set<String> obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba2: un solo elemento
        linicial = new LinkedList<>();
        linicial.add("uno");
        l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba3: varias veces el mismo elemento
        linicial = new LinkedList<>();
        for (int i = 0; i < 20; i++)
            linicial.add("uno");
        l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba4: todos los elementos distintos
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            linicial.add(conjuntoTest1[i]);
        l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba5: diferentes elementos repetidos
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            linicial.add(conjuntoTest1[i]);
        linicial.addAll(linicial);
        l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba6: con elementos repetidos
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            linicial.add(conjuntoTest1[i]);
        for (int i = 0; i < this.conjuntoPeq1.length; i++)
            linicial.add(conjuntoPeq1[i]);
        l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba7: con elementos repetidos
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            linicial.add(conjuntoTest1[i]);
        for (int i = 0; i < this.conjuntoPeq2.length; i++)
            linicial.add(conjuntoPeq2[i]);
        l1 = new LinkedList<>(linicial);
        System.out.println(" Entrada: " + l1.toString());
        esperado = unicosParaTest(l1.iterator());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.unicos(l2.iterator());

        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");
    }

    @org.junit.Test
    public void nocurrenciasTest() {
        //prueba1: coleccion y conjuntos vacíos
        List<String> linicial = new LinkedList<>();
        List<String> l1 = new LinkedList<>(linicial);
        Set<String> s1 = new HashSet<String>();

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        int esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        List<String> l2 = new LinkedList<>(linicial);
        int obtenido = Practica1.numOcurrecias(l2, s1);

        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba2: coleccion vacía, conjunto no vacío
        linicial = new LinkedList<>();
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoPeq2.length; i++)
            s1.add(conjuntoPeq2[i]);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba3: coleccion no vacía, conjunto vacío
        linicial = new LinkedList<>();
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            linicial.add(conjuntoTest1[i]);
        l1 = new LinkedList<>(linicial);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba4: conjunto mayor que colección, mismos elementos
        linicial = new LinkedList<>();
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoTest1.length / 2; i++)
            linicial.add(conjuntoTest1[i]);
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            s1.add(conjuntoTest1[i]);
        l1 = new LinkedList<>(linicial);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba5: conjunto tamaño 1, colección tamaño 1 mismos elementos
        linicial = new LinkedList<>();
        s1 = new HashSet<String>();
        linicial.add("uno");
        s1.add("uno");
        l1 = new LinkedList<>(linicial);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba6: coleccion con el mismo elemento, conjunto con un elemento igual
        linicial = new LinkedList<>();
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoTest1.length / 2; i++)
            linicial.add("uno");
        l1 = new LinkedList<>(linicial);
        s1.add("uno");

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba7: conjunto mayor que coleccion, distintos elementos
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoPeq1.length; i++)
            linicial.add(conjuntoPeq1[i]);
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoTest1.length; i++)
            s1.add(conjuntoTest1[i]);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");


        //prueba8: conjunto y coleccion iguales
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest2.length; i++)
            linicial.add(conjuntoTest2[i]);
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoTest2.length; i++)
            s1.add(conjuntoTest2[i]);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba9: coleccion y conjunto diferentes
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest2.length; i++)
            linicial.add(conjuntoTest2[i]);
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoPeq1.length; i++)
            s1.add(conjuntoPeq1[i]);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");


        //prueba10
        linicial = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linicial.add("uno");
            linicial.add("dos");
        }
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        s1.add("dos");
        s1.add("uno");

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba11
        linicial = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            linicial.add("uno");
            linicial.add("dos");
        }
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        s1.add("dos");
        s1.add("uno");

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba12
        linicial = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            linicial.add("uno");
            linicial.add("dos");
            linicial.add("tres");
        }
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        s1.add("dos");

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba13
        linicial = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linicial.add("uno");
            linicial.add("dos");
            linicial.add("tres");
        }
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        s1.add("cuatro");
        s1.add("cinco");

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba14
        linicial = new LinkedList<>();
        for (int i = 0; i < this.conjuntoTest1.length; i++) {
            linicial.add(conjuntoTest1[i]);
            linicial.add(this.conjuntoPeq1[i % this.conjuntoPeq1.length]);
        }
        l1 = new LinkedList<>(linicial);
        s1 = new HashSet<String>();
        for (int i = 0; i < this.conjuntoPeq1.length; i++) {
            s1.add(conjuntoPeq1[i]);
        }

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba15
        linicial = generarColAleatoria(100, 0, 30);
        l1 = new LinkedList<>(linicial);
        s1 = generarConjAleatorio(10, 0, 30);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + l1.toString());
        System.out.println("    Conjunto= " + s1.toString());
        esperado = numOcurreciasParaTest(l1, s1);
        System.out.println("Salida esperada: " + esperado);

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.numOcurrecias(l2, s1);
        System.out.println("Salida: " + obtenido);

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba16
        for (int i = 0; i < 5; i++) {
            linicial = generarColAleatoria(100, 0, 20);
            l1 = new LinkedList<>(linicial);
            s1 = generarConjAleatorio(3, 0, 20);

            System.out.println(" Entrada: ");
            System.out.println("    Coleccion= " + l1.toString());
            System.out.println("    Conjunto= " + s1.toString());
            esperado = numOcurreciasParaTest(l1, s1);
            System.out.println("Salida esperada: " + esperado);

            l2 = new LinkedList<>(linicial);
            obtenido = Practica1.numOcurrecias(l2, s1);
            System.out.println("Salida: " + obtenido);

            assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
            System.out.println("--------------------");
        }
    }

    @org.junit.Test
    public void spliTest() {
        //prueba1: vacía
        Collection<String> linicial=new LinkedList<>();
        List<Set<String>> esperado=new LinkedList<>();
        esperado.add(new HashSet<String>());
        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        Collection<String> l2 = new LinkedList<>(linicial);
        Collection<Set<String>> obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());

        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 2: un elemento
        linicial=new LinkedList<>();
        linicial.add("uno");
        esperado=new LinkedList<>();
        Set<String> s=new HashSet<>();
        s.add("uno");
        esperado.add(s);
        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());

        //prueba 3: un elemento repetido
        linicial=new LinkedList<>();
        for (int i=0; i<11; i++)
            linicial.add("uno");
        esperado=new LinkedList<>();
        s=new HashSet<>();
        s.add("uno");
        for (int i=0; i<11; i++)
            esperado.add(s);
        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 3: un elemento repetido
        linicial.add("uno");
        esperado.add(s);
        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 4:
        linicial=new LinkedList<>();
        for (int i=0; i<this.conjuntoPeq1.length; i++)
            linicial.add(conjuntoPeq1[i]);
        esperado=new LinkedList<>();
        s=new HashSet<>();
        for (int i=0; i<this.conjuntoPeq1.length; i++)
            s.add(conjuntoPeq1[i]);
        esperado.add(s);
        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 5:
        linicial=new LinkedList<>();
        for (int j=0; j<5; j++) {
            for (int i = 0; i < this.conjuntoPeq1.length; i++)
                linicial.add(conjuntoPeq1[i]);
        }
        esperado=new LinkedList<>();
        s=new HashSet<>();
        for (int i=0; i<this.conjuntoPeq1.length; i++)
            s.add(conjuntoPeq1[i]);
        for (int j=0; j<5; j++) {
            esperado.add(s);
        }
        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 6:
        linicial=new LinkedList<>();

        linicial.add("uno");
        linicial.add("uno");
        linicial.add("dos");
        linicial.add("dos");
        linicial.add("tres");
        linicial.add("tres");

        esperado=new LinkedList<>();
        s=new HashSet<>();
        s.add("uno");
        esperado.add(s);
        s=new HashSet<>();
        s.add("uno");
        s.add("dos");
        esperado.add(s);
        s=new HashSet<>();
        s.add("dos");
        s.add("tres");
        esperado.add(s);
        s=new HashSet<>();
        s.add("tres");
        esperado.add(s);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 7:
        linicial=new LinkedList<>();

        linicial.add("uno");
        linicial.add("uno");
        linicial.add("uno");
        linicial.add("dos");
        linicial.add("dos");
        linicial.add("dos");
        linicial.add("tres");
        linicial.add("tres");
        linicial.add("tres");

        esperado=new LinkedList<>();
        s=new HashSet<>();
        s.add("uno");
        esperado.add(s);
        s=new HashSet<>();
        s.add("uno");
        esperado.add(s);
        s=new HashSet<>();
        s.add("uno");
        s.add("dos");
        esperado.add(s);
        s=new HashSet<>();
        s.add("dos");
        esperado.add(s);
        s=new HashSet<>();
        s.add("dos");
        s.add("tres");
        esperado.add(s);
        s=new HashSet<>();
        s.add("tres");
        esperado.add(s);
        s=new HashSet<>();
        s.add("tres");
        esperado.add(s);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 8:
        linicial=new LinkedList<>();
        for (int j=0; j<23; j++) {
            linicial.add(String.valueOf(j));
        }
        esperado=new LinkedList<>();
        s=new HashSet<>();
        for (int i=0; i<23; i++)
            s.add(String.valueOf(i));
        esperado.add(s);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");

        //prueba 9:
        linicial=new LinkedList<>();
        for (int j=23; j>=0; j--) {
            linicial.add(String.valueOf(j));
        }
        esperado=new LinkedList<>();
        s=new HashSet<>();
        for (int i=23; i>=0; i--)
            s.add(String.valueOf(i));
        esperado.add(s);

        System.out.println(" Entrada: ");
        System.out.println("    Coleccion= " + linicial.toString());
        System.out.println("Salida esperada: " + esperado.toString());

        l2 = new LinkedList<>(linicial);
        obtenido = Practica1.split(l2);
        System.out.println("Salida: " + obtenido.toString());
        assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
        System.out.println("--------------------");


        //prueba 10:
        for (int i=0; i<10; i++) {
            linicial = new LinkedList<>();
            esperado = new LinkedList<>();
            splitParaTest(linicial, esperado, 3);
            System.out.println(" Entrada: ");
            System.out.println("    Coleccion= " + linicial.toString());
            System.out.println("Salida esperada: " + esperado.toString());

            l2 = new LinkedList<>(linicial);
            obtenido = Practica1.split(l2);
            System.out.println("Salida: " + obtenido.toString());
            assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
            System.out.println("--------------------");
        }

        //prueba 11:
        for (int i=0; i<10; i++) {
            linicial = new LinkedList<>();
            esperado = new LinkedList<>();
            splitParaTest(linicial, esperado, 2);
            System.out.println(" Entrada: ");
            System.out.println("    Coleccion= " + linicial.toString());
            System.out.println("Salida esperada: " + esperado.toString());

            l2 = new LinkedList<>(linicial);
            obtenido = Practica1.split(l2);
            System.out.println("Salida: " + obtenido.toString());
            assertEquals("filtra: RESPUESTA INCORRECTA", esperado, obtenido);
            System.out.println("--------------------");
        }
    }


}




