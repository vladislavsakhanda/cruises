package entity;

import java.awt.*;

public class Document {
    private long id;
    private Image passport;
    private long user_id;

    public static Document createDocument(Image passport, long user_id) {
        Document document = new Document();
        document.setPassport(passport);
        document.setId(user_id);
        return document;
    }

    public Document() {
    }

    public Document(Image passport, long user_id) {
        this.passport = passport;
        this.user_id = user_id;
    }

    public Document(long id, Image passport, long user_id) {
        this.id = id;
        this.passport = passport;
        this.user_id = user_id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setPassport(Image passport) {
        this.passport = passport;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public Image getPassport() {
        return passport;
    }

    public long getUser_id() {
        return user_id;
    }
}
