package fr.esaip.barduino.bottle;


public class Bottle {
    private String bottlename;
    private int bottleposition;
    private Integer img;

    public Bottle(String bottlename, int bottleposition, Integer img) {
        this.bottlename = bottlename;
        this.bottleposition = bottleposition;
        this.img = img;
    }

    public String getBottlename() {
        return bottlename;
    }

    public void setBottlename(String bottlename) {
        this.bottlename = bottlename;
    }

    public int getBottleposition() {
        return bottleposition;
    }

    public void setBottleposition(int bottleposition) {
        this.bottleposition = bottleposition;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String toString(){
        return "Bottlename: "+bottlename+"Bottleposition: "+bottleposition;
    }
}
