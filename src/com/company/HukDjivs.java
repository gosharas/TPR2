package com.company;

import java.util.ArrayList;
import java.util.List;

public class HukDjivs {
    static class Toch{
        private int id;
        private double x1;
        private double x2;
        private double func;

        public Toch() {

        }

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

        public void setX1(double x1) {
            this.x1 = x1;
        }

        public void setX2(double x2) {
            this.x2 = x2;
        }

        public void setFunc(double func) {
            this.func = func;
        }


        public Toch(int id, double x1, double x2) {
            this.id = id;
            this.x1 = x1;
            this.x2 = x2;
            this.func = funcRez(x1, x2);
        }

        public Toch(String lol, double x1, double x2){
            this.x1 = x1;
            this.x2 = x2;
            this.func = funcRez(x1, x2);
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
    static int d = 2;//уменьшение шага
    static double m = 2;
    static double h = 0.2;//шаг
    static double eps = 0.1;//точность
    static double [] start = {1.0, 1.0};
    static double [] xobr = new double[2];
    static List<Toch> res = new ArrayList<Toch>();
    static Toch basic1 = new Toch();
    static Toch basic = new Toch();
    static Toch prob = new Toch();

    public static void main(String[] args) {
        res.add(new Toch(k, start[0], start[1]));
        System.out.println(k);
        basic = new Toch( "",res.get(k).getX1(), res.get(k).getX2());
        iter1();

    }
    public static void iter1(){
        prob = new Toch("",basic.getX1(), basic.getX2());
        prob.setX1(prob.getX1() + h);
        prob.setFunc(funcRez(prob.getX1(), prob.getX2()));
        if (prob.getFunc() < res.get(k).getFunc()){

            res.add(new Toch(prob.getX1(), prob.getX2()));
            writeMap();
            iter3();
        } else {
            prob.setX1(prob.getX1() - h);
            iter2();
        }
    }

    public static void iter2(){
        prob.setX1(prob.getX1() - h);
        prob.setFunc(funcRez(prob.getX1(), prob.getX2()));
        if (prob.getFunc() < res.get(k).getFunc()){

            res.add(new Toch(prob.getX1(), prob.getX2()));

            writeMap();
            iter3();
        } else {
            prob.setX1(prob.getX1() + h);
            iter3();
        }

    }
    public static void iter3(){
        prob.setX2(prob.getX2() + h);
        prob.setFunc(funcRez(prob.getX1(), prob.getX2()));
        if (prob.getFunc() < res.get(k).getFunc()){

            res.add(new Toch(prob.getX1(), prob.getX2()));

            writeMap();
            iter5();
        }else {
            prob.setX2(prob.getX2() - h);
            iter4();
        }
    }
    public static void iter4(){
        prob.setX2(prob.getX2() - h);
        prob.setFunc(funcRez(prob.getX1(), prob.getX2()));
        if (prob.getFunc() < res.get(k).getFunc()){

            res.add(new Toch(prob.getX1(), prob.getX2()));

            writeMap();
            iter5();
        } else {
            prob.setX2(prob.getX2() + h);
            iter5();
        }
    }
    public static void iter5(){
        if (prob.getX1() == basic.getX1() & prob.getX2() == basic.getX2()){
            h = h / d;
            System.out.println("Уменьшение шага");
            iter1();
        } else {
            xobr[0] = prob.getX1() + m*(prob.getX1() - basic.getX1());
            xobr[1] = prob.getX2() + m*(prob.getX2() - basic.getX2());
            if (funcRez(xobr[0], xobr[1]) < res.get(k).getFunc()){
                res.add(new Toch(xobr[0], xobr[1]));
                basic = new Toch("",res.get(k).getX1(), res.get(k).getX2());
                iter6();
            } else {
                basic = new Toch("",prob.getX1(), prob.getX2());
                iter6();
            }
        }

    }

    public static void iter6(){
        if (h <= eps){
            System.out.println("Поиск завершен, требуемая точность достигнута");
            writeMap();
        }
        else {
            System.out.println("Поиск не завершен, треюуется повторный поиск");
            iter1();
        }
    }
    public static double funcRez(double x1, double x2){
        double res = 0;
        res = (x1*x1) - (x1*x2) + (3.0*x2*x2) - 2*x1;
        return res;
    }
    public static void writeMap(){
        int k2 = 0;
        while (k2 <= k){
            System.out.printf("%d   %.3f   %.3f   %.3f",res.get(k2).getId(), res.get(k2).getX1(),
                    res.get(k2).getX2(), res.get(k2).getFunc());
            System.out.println();
            k2++;
        }
        System.out.println("\n");
    }
}
