package com.company;

import java.util.*;

import static java.lang.Math.*;

public class Simplex {
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
    static int n = 2;//размерность
    static int iteration = 0;
    static double m = 0.25;//ребро
    static double e = 0.1;//точность
    static double [] start = {0.0, 0.0};
    static double [] toch = new double[3];
    static List<Toch> res = new ArrayList<Toch>();



    public static void main(String[] args) {
        res.add(new Toch(k, start[0], start[1]));
        System.out.println(k);
        res.add(new Toch(res.get(0).getX1() + increment1() , res.get(0).getX2() + increment2()));
        System.out.println(k);
        res.add(new Toch(res.get(0).getX1() + increment2() , res.get(0).getX2() + increment1()));
        System.out.println(k);

        writeMap();
        iter(new ArrayList<Toch>(Arrays.asList(res.get(0), res.get(1), res.get(2))));

        writeMap();


    }

    public static void iter(List<Toch> lol){
        System.out.println("\n\n"+"Итерация k="+iteration);
        System.out.println("Точки:");
        writeList(lol);
        iteration++;
        List<Toch> toches = lol;
        int indexOfMax = 0;
        double [] xc = new double[2];
        double [] xcsimp = new double[2];

        for (int i = 1; i < toches.size(); i++) {
            if (toches.get(i).getFunc() > toches.get(indexOfMax).getFunc()) {
                indexOfMax = i;
            }
        }

        System.out.printf("Максимальное значение функции %d: %.3f\n", toches.get(indexOfMax).getId(),
                toches.get(indexOfMax).getFunc());

       double sumx1 = toches.get(0).getX1() + toches.get(1).getX1() + toches.get(2).getX1();
       double sumx2 = toches.get(0).getX2() + toches.get(1).getX2() + toches.get(2).getX2();

       xc[0] = (sumx1 - toches.get(indexOfMax).getX1()) / n;
       xc[1] = (sumx2 - toches.get(indexOfMax).getX2()) / n;

       System.out.printf("Центр тяжести: %.3f   %.3f\n", xc[0], xc[1]);
       res.add(new Toch((xc[0]*2)-toches.get(indexOfMax).getX1(), (xc[1]*2) - toches.get(indexOfMax).getX2()));

       //System.out.println(k);

       toches.set(indexOfMax, res.get(k));

       sumx1 = toches.get(0).getX1() + toches.get(1).getX1() + toches.get(2).getX1();
       sumx2 = toches.get(0).getX2() + toches.get(1).getX2() + toches.get(2).getX2();

       xcsimp[0] = sumx1 / 3;
       xcsimp[1] = sumx2 / 3;

       if ((toches.get(0).getFunc() - funcRez(xcsimp[0], xcsimp[1])) > e |
               (toches.get(1).getFunc() - funcRez(xcsimp[0], xcsimp[1])) > e |
               (toches.get(2).getFunc() - funcRez(xcsimp[0], xcsimp[1])) > e){
           iter(toches);
       }


    }

    public static double funcRez(double x1, double x2){
        double res;
        res = x1*x1 - x1*x2 + 3*x2*x2 - 2*x1;
        return res;
    }

    public static void writeList(List<Toch> lol){
        for (Toch rs: lol) {
            System.out.printf("%d   %.3f   %.3f   %.3f",rs.getId(), rs.getX1(), rs.getX2(), rs.getFunc());
            System.out.println();
        }
        
    }
    public static void writeMap(){
        int k2 = 0;
        while (k2 <= k){
            System.out.printf("%d   %.3f   %.3f   %.3f",k2, res.get(k2).x1, res.get(k2).x2, res.get(k2).func);
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
}
