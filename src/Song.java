/**
 *  Class which represents a song, with author and title.
 *
 *  @date   17052016
 *  @author ajfc
 */

public class Song {
  private String author;
  private String title;

  public Song(String author, String title) {
    this.author = author;
    this.title  = title;
  }

  // Getters.

  public String getAuthor() {
    return this.author;
  }

  public String getTitle() {
    return this.title;
  }

  // Setters.

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setTitle(String title) {
    this.title  = title;
  }

  // equals.

  public boolean equals(Song s) {
    boolean a = s.getAuthor().toLowerCase().replaceAll("\\s+","").equals(
        this.author.toLowerCase().replaceAll("\\s+",""));
    boolean b = s.getTitle().toLowerCase().replaceAll("\\s+","").equals(
        this.title.toLowerCase().replaceAll("\\s+",""));
    return a && b;
  }
}
