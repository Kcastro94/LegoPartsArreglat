package com.kev.legoparts;

import android.graphics.Bitmap;


/**
 * Created by DAM on 23/1/17.
 */

public class LegoPiece {
    private long id;
    private String piece_id;
    private String name;
    private Bitmap image;
    private int quantity;

    public LegoPiece(){}

    public LegoPiece(long id, String piece_id, String name, Bitmap image, int quantity) {
        this.id = id;
        this.piece_id = piece_id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPiece_id() {
        return piece_id;
    }

    public void setPiece_id(String piece_id) {
        this.piece_id = piece_id;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegoPiece legoPiece = (LegoPiece) o;

        if (id != legoPiece.id) return false;
        if (piece_id != null ? !piece_id.equals(legoPiece.piece_id) : legoPiece.piece_id != null)
            return false;
        return name != null ? name.equals(legoPiece.name) : legoPiece.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (piece_id != null ? piece_id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LegoPiece{" +
                "id=" + id +
                ", piece_id='" + piece_id + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", quantity=" + quantity +
                '}';
    }
}
