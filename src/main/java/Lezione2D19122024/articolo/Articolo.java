package Lezione2D19122024.articolo;

public class Articolo {

    private String title, subtitle, author, link;

    public Articolo(String title, String subtitle, String author, String link) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.link = link;
    }

    //getter & setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //toString
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [title=" + title + ", subtitle=" + subtitle + ", author= " + author + ", link= " + link + "]";
    }
}
