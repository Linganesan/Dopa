package com.example.linganesan.dopa.Db;


public class Loci {
    // private variables
    int _id;
    String _name;
    byte[] _image;

    // Empty constructor
    public Loci() {

    }

    // constructor
    public Loci(int keyId, String name, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._image = image;

    }
    public Loci(String name, byte[] image) {
        this._name = name;
        this._image = image;

    }
    public Loci(int keyId) {
        this._id = keyId;

    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    // setting phone number
    public void setImage(byte[] image) {
        this._image = image;
    }

}
