package com.nivekaa.ecommerce.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class TransVM implements Serializable,Parcelable {
    private double prix;
    private String date;
    private long  id;
    private boolean status;
    private String numero;
    private String numeroAffichable;
    private String message;
    private String typeCarte;
    private String carte;

    public TransVM() {
    }

    public TransVM(double prix, String date, boolean status, String numero, String numeroAffichable,String message, String carte) {
        this.prix = prix;
        this.date = date;
        this.status = status;
        this.numero = numero;
        this.numeroAffichable = numeroAffichable;
        this.carte = carte;
        this.message = message;
    }

    public TransVM(double prix, String date, boolean status, String numero, String numeroAffichable,String message) {
        this.prix = prix;
        this.date = date;
        this.status = status;
        this.numero = numero;
        this.numeroAffichable = numeroAffichable;
        this.message = message;
    }

    protected TransVM(Parcel in) {
        prix = in.readFloat();
        date = in.readString();
        status = in.readByte() != 0;
        numero = in.readString();
        numeroAffichable = in.readString();
        message = in.readString();
        typeCarte = in.readString();
        id = in.readLong();
    }

    public static final Creator<TransVM> CREATOR = new Creator<TransVM>() {
        @Override
        public TransVM createFromParcel(Parcel in) {
            return new TransVM(in);
        }

        @Override
        public TransVM[] newArray(int size) {
            return new TransVM[size];
        }
    };

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String  getCarte() {
        return carte;
    }

    public void setCarte(String  carte) {
        this.carte = carte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumeroAffichable() {
        return numeroAffichable;
    }

    public void setNumeroAffichable(String numeroAffichable) {
        this.numeroAffichable = numeroAffichable;
    }

    public String getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(String typeCarte) {
        this.typeCarte = typeCarte;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(prix);
        parcel.writeString(date);
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(numero);
        parcel.writeString(numeroAffichable);
        parcel.writeString(message);
        parcel.writeString(typeCarte);
        parcel.writeLong(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}