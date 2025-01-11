package fr.esaip.barduino.bottle;

import java.util.ArrayList;

public class BottleList{
    private ArrayList<Bottle> bottleList;

    public BottleList(ArrayList<Bottle> bottleList) {
        this.bottleList = bottleList;
    }

    public ArrayList<Bottle> getBottleList() {
        return bottleList;
    }

    public void setBottleList(ArrayList<Bottle> bottleList) {
        this.bottleList = bottleList;
    }

    public void addBottle(Bottle bottle){
        this.bottleList.add(bottle);
    }

    public int size(){
        return this.bottleList.size();
    }
    public Bottle get(int position){
        return this.bottleList.get(position);
    }

    public void set(int position, Bottle bottle) {
        this.bottleList.set(position,bottle);
    }
}
