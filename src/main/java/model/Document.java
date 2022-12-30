package model;

import java.awt.*;

public class Document extends Model {
    private Image passport;
    private long user_id;

    public static Document createDocument(Image passport, long user_id) {
        Document document = new Document();
        document.setPassport(passport);
        document.setId(user_id);
        return document;
    }

    public Document() {
        super();
    }

    public Document(long id) {
        super(id);
    }

    public Document(Image passport, long user_id) {
        this.passport = passport;
        this.user_id = user_id;
    }

    public void setPassport(Image passport) {
        this.passport = passport;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Image getPassport() {
        return passport;
    }

    public long getUser_id() {
        return user_id;
    }
}
