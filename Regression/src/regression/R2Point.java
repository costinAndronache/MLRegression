/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression;

/**
 *
 * @author Costin
 */
    public class R2Point {
        public Double x, y;
        public R2Point(Double x, Double y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "(" + x + ")" +  " = " + y;
        }
    }