package com.company;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class NelMid {
    static class Toch{
        private int id;
        private double x1;
        private double x2;
        private double func;

        public int getId() {
            return id;
        }

        public double getX1() {
            return x1;
        }

        public double getX2() {
            return x2;
        }

        public double getFunc() {
            return func;
        }

        public Toch(int id, double x1, double x2) {
            this.id = id;
            this.x1 = x1;
            this.x2 = x2;
            this.func = func;
        }

        public Toch(double x1, double x2) {
            this.id = ++k;
            this.x1 = x1;
            this.x2 = x2;
            this.func = funcRez(x1, x2);
        }
    }

    static int k = 0;//итерации
    static int iteration = 0;
    static int n = 2;//размерность
    static double m = 0.75;//длина ребра
    static double betta = 2.8;//параметр растяжения
    static double gamma = 0.4;//параметр сжатия
    static double eps = 0.01;//точность
    static double [] start = {0.0, 0.0};
    static double [] xc = new double[2];//центр тяжести
    static double [] xotr = new double[2];//отраженная точка(-и)
    static double [] xred = new double[2];
    static double fH;//наибольшее
    static double fL;//наименьшее
    static double fS;//середина
    static List<Toch> res = new ArrayList<Toch>();

    public static void main(String[] args) {
        res.add(new Toch(k, start[0], start[1]));
        res.add(new Toch(res.get(0).getX1() + increment1() , res.get(0).getX2() + increment2()));
        res.add(new Toch(res.get(0).getX1() + increment2() , res.get(0).getX2() + increment1()));
        //System.out.println(k);
        writeMap();
        //Collections.sort(res, Comparator.comparing(Toch::getFunc));

        way3_6(new ArrayList<Toch>(Arrays.asList(res.get(0), res.get(1), res.get(2))));
        writeMap();
        System.out.println();
        Collections.sort(res, Comparator.comparing(Toch::getFunc));
        System.out.printf("В качестве приблеженного решения выберается %d   %.3f   %.3f   %.3f",
                res.get(0).getId(), res.get(0).getX1(), res.get(0).getX2(), res.get(0).getFunc());

    }

    public static void way3_6(List<Toch> lol){
        System.out.println("\n\n\nИтерация k = "+iteration);
        iteration++;
        int indexOfMax = 0;


        List<Toch> sortFuncLol = lol;
        Collections.sort(sortFuncLol, Comparator.comparing(Toch::getFunc));
        fL = sortFuncLol.get(0).getFunc();
        fS = sortFuncLol.get(1).getFunc();
        fH = sortFuncLol.get(2).getFunc();

        for (int i = 1; i < sortFuncLol.size(); i++) {
            if (sortFuncLol.get(i).getFunc() > sortFuncLol.get(indexOfMax).getFunc()) {
                indexOfMax = i;
            }
        }

        System.out.printf("Максимальное значение функции %d: %.3f\n", sortFuncLol.get(indexOfMax).getId(),
                sortFuncLol.get(indexOfMax).getFunc());

        double sumx1 = sortFuncLol.get(0).getX1() + sortFuncLol.get(1).getX1() + sortFuncLol.get(2).getX1();
        double sumx2 = sortFuncLol.get(0).getX2() + sortFuncLol.get(1).getX2() + sortFuncLol.get(2).getX2();

        xc[0] = (sumx1 - sortFuncLol.get(indexOfMax).getX1()) / n;
        xc[1] = (sumx2 - sortFuncLol.get(indexOfMax).getX2()) / n;
        System.out.printf("Центр тяжести: %.3f   %.3f\n", xc[0], xc[1]);

        xotr[0] = (xc[0]*2)-sortFuncLol.get(indexOfMax).getX1();
        xotr[1] = (xc[1]*2) - sortFuncLol.get(indexOfMax).getX2();

        res.add(new Toch(xotr[0], xotr[1]));
        System.out.printf("Отраженная точка %d: %.3f   %.3f   %.3f\n", k, xotr[0], xotr[1],
                funcRez(xotr[0], xotr[1]));

        writeMap();

        if (funcRez(xotr[0], xotr[1]) < sortFuncLol.get(indexOfMax).getFunc()){
            sortFuncLol.set(indexOfMax, res.get(k));
            way7(indexOfMax, sortFuncLol);
        }else {
            way9(indexOfMax, sortFuncLol);
        }






    }

    public static void way7(int k0, List<Toch> sortFuncLol) {
        System.out.println("Пункт 7:");
        if (sortFuncLol.get(k0).getFunc() < fL){
            xotr[0] = xc[0] + betta*(sortFuncLol.get(k0).getX1() - xc[0]);
            xotr[1] = xc[1] + betta*(sortFuncLol.get(k0).getX2() - xc[1]);
            res.add(new Toch(xotr[0], xotr[1]));
            System.out.printf("Отраженная точка %d: %.3f   %.3f   %.3f\n", k, xotr[0], xotr[1],
                    funcRez(xotr[0], xotr[1]));

            way8(k0, sortFuncLol);
        }else {
            way9(k0, sortFuncLol);
        }
    }

    public static void way9(int k0, List<Toch> sortFuncLol) {
        System.out.println("Пункт 9:");
        if (fS < sortFuncLol.get(k0).getFunc() && sortFuncLol.get(k0).getFunc() < fH){
            xotr[0] = xc[0] + gamma*(sortFuncLol.get(k0).getX1() - xc[0]);
            xotr[1] = xc[1] + gamma*(sortFuncLol.get(k0).getX2() - xc[1]);
            res.add(new Toch(xotr[0], xotr[1]));
            System.out.printf("Отраженная точка %d: %.3f   %.3f   %.3f\n", k, xotr[0], xotr[1],
                    funcRez(xotr[0], xotr[1]));

            way10(k0, sortFuncLol);
        }else {
            way11(k0, sortFuncLol);
        }
    }

    public static void way10(int k0, List<Toch> sortFuncLol) {
        System.out.println("Пункт 10:");
        if (funcRez(xotr[0], xotr[1]) < sortFuncLol.get(k0).getFunc()){
            sortFuncLol.set(k0, res.get(k));
            way12(k0, sortFuncLol);
        } else {
            way11(k0, sortFuncLol);
        }
    }

    public static void way11(int k0, List<Toch> sortFuncLol) {
       xred[0] = sortFuncLol.get(0).getX1() + 0.5*(sortFuncLol.get(2).getX1() - sortFuncLol.get(0).getX1());
       xred[1] = sortFuncLol.get(0).getX2() + 0.5*(sortFuncLol.get(2).getX2() - sortFuncLol.get(0).getX2());
       res.add(new Toch(xred[0], xred[1]));
       sortFuncLol.set(2, res.get(k));

       xred[0] = sortFuncLol.get(0).getX1() + 0.5*(sortFuncLol.get(1).getX1() - sortFuncLol.get(0).getX1());
       xred[1] = sortFuncLol.get(0).getX2() + 0.5*(sortFuncLol.get(1).getX2() - sortFuncLol.get(0).getX2());
       res.add(new Toch(xred[0], xred[1]));
       sortFuncLol.set(1, res.get(k));
       way12(k0, sortFuncLol);
    }

    public static void way8(int k0, List<Toch> sortFuncLol) {
        System.out.println("Пункт 8:");
        if (funcRez(xotr[0], xotr[1]) < sortFuncLol.get(k0).getFunc()){
            sortFuncLol.set(k0, res.get(k));
            way12(k0, sortFuncLol);
        } else {
           way9(k0, sortFuncLol);
        }

    }

    public static void way12(int k0, List<Toch> sortFuncLol) {
        System.out.println("Пункт 12:");
        double xcsimp [] = new double[2];
        double sigm;
        double sumx1 = sortFuncLol.get(0).getX1() + sortFuncLol.get(1).getX1() + sortFuncLol.get(2).getX1();
        double sumx2 = sortFuncLol.get(0).getX2() + sortFuncLol.get(1).getX2() + sortFuncLol.get(2).getX2();

        xcsimp[0] = sumx1 / 3;
        xcsimp[1] = sumx2 / 3;

        System.out.printf("Центр тяжести симплекса: %.3f   %.3f   %.3f \n", xcsimp[0], xcsimp[1],
                funcRez(xcsimp[0], xcsimp[1]));
        double funr = funcRez(xcsimp[0], xcsimp[1]);
        sigm = pow((pow(sortFuncLol.get(0).getFunc() - funr, 2) + pow(sortFuncLol.get(1).getFunc() - funr, 2) +
                pow(sortFuncLol.get(2).getFunc() - funr, 2))/3, 0.5);
        System.out.printf("Сигма: %.3f\n", sigm);
        if (!(sigm < eps)){
            way3_6(sortFuncLol);
        }
    }

    public static void writeMap(){
        int k2 = 0;
        while (k2 <= k){
            System.out.printf("%d   %.3f   %.3f   %.3f",res.get(k2).getId(), res.get(k2).getX1(),
                    res.get(k2).getX2(), res.get(k2).getFunc());
            System.out.println();
            k2++;
        }
    }
    public static double increment1(){
        return ((sqrt(n + 1) - 1)/(n * sqrt(2))) * m ;
    }
    public static double increment2(){
        return ((sqrt(n + 1) + n - 1)/(n * sqrt(2))) * m ;
    }


    public static double funcRez(double x1, double x2){
        double res = 0;
        res = (x1*x1) - (x1*x2) + (3.0*x2*x2) - 2*x1;
        return res;
    }
}
