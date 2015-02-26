/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronostone.parser;

/**
 *
 * @author Adrian
 */
public class Hissatsu {
    public String name;
    public int power;
    Hissatsu(String n ) {
        this.name=n;
        power=0;
    }
    Hissatsu(String n, int p) {
        this.name=n;
        this.power=p;
    }

    @Override
    public String toString() {
        return "Hissatsu{" + "name=" + name + '}';
    }
    
}
