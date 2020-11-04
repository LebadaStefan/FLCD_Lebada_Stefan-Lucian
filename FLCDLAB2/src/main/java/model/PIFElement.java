package model;


public class PIFElement {

    String token;
    Integer hashcode;
    Integer position;

    public PIFElement() {
        this.token = null;
        this.hashcode = -1;
        this.position = -1;
    }

    public PIFElement(String token, Integer hashcode, Integer position) {
        this.token = token;
        this.hashcode = hashcode;
        this.position = position;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getHashcode() {
        return hashcode;
    }

    public void setHashcode(Integer hashcode) {
        this.hashcode = hashcode;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
