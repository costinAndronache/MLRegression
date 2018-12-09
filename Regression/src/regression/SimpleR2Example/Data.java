/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.SimpleR2Example;
import regression.*;
/**
 *
 * @author Costin
 */
public class Data {
    
    public static R2Point[] parse() {
        return new R2Point[]{
          new R2Point(0., 2.),
          new R2Point(1., 3.),
          new R2Point(2., 5.),
          new R2Point(3.0, 4.0),
          new R2Point(4.0, 6.0)
        };
    }
    // http://www.analyzemath.com/statistics/linear_regression.html
    public static R2Point[] parse2() {
        return new R2Point[]{
          new R2Point(-1., 0.),
          new R2Point(0., 2.),
          new R2Point(1., 4.),
          new R2Point(2., 5.)
        };
    }
}
