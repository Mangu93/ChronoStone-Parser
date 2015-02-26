/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronostone.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Adrian
 */
public class Character {
    public String name, position;
    public String gp, tp, kick, dribbling, block, catch_keeper, technique, speed, stamina, lucky;
    public List<Hissatsu> hissatsu;
    Character() {
        name="Matsuzake Tenma";
        hissatsu = new ArrayList<>();
    }
    Character(String n) {
        name=n;
        hissatsu = new ArrayList<>();
    } 
    
    public String print_stats() {
        String ret = "Character{" + "name=" + name + ", position=" + position + ", gp=" + gp + ", tp=" + tp + ", kick=" + kick + ", dribbling=" + dribbling + ", block=" + block + ", catch_keeper=" + catch_keeper + ", technique=" + technique + ", speed=" + speed + ", stamina=" + stamina + ", lucky=" + lucky  + '}';
        return ret;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Character other = (Character) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    public void addHissatsu(String hissatsu) {
        Hissatsu h = new Hissatsu(hissatsu);
        this.hissatsu.add(h);
    }
    public void addHissatsu(String hissatsu, int power) {
        Hissatsu h = new Hissatsu(hissatsu, power);
        this.hissatsu.add(h);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public void setKick(String kick) {
        this.kick = kick;
    }

    public void setDribbling(String dribbling) {
        this.dribbling = dribbling;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setCatch_keeper(String catch_keeper) {
        this.catch_keeper = catch_keeper;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setStamina(String stamina) {
        this.stamina = stamina;
    }

    public void setLucky(String lucky) {
        this.lucky = lucky;
    }

    public void setHissatsu(List<Hissatsu> hissatsu) {
        this.hissatsu = hissatsu;
    }
    
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getGp() {
        return gp;
    }

    public String getTp() {
        return tp;
    }

    public String getKick() {
        return kick;
    }

    public String getDribbling() {
        return dribbling;
    }

    public String getBlock() {
        return block;
    }

    public String getCatch_keeper() {
        return catch_keeper;
    }

    public String getTechnique() {
        return technique;
    }

    public String getSpeed() {
        return speed;
    }

    public String getStamina() {
        return stamina;
    }

    public String getLucky() {
        return lucky;
    }

    public List<Hissatsu> getHissatsu() {
        return hissatsu;
    }
    
}
