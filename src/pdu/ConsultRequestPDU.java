/**
 *  PDU used to search for a music file.
 *
 *  @author ajfc
 *  @date   21032016
 */

package pdu;

import java.util.Arrays;

public class ConsultRequestPDU extends PDU {
  private String band;
  private String song;
  private String extension;

  /**
   *  Parameterized constructor.
   *
   *  @param  band      The band's name.
   *  @param  song      The song's title.
   *  @param  extension The audio file extension, e.g., "mp3".
   */
  public ConsultRequestPDU (String band, String song, String extension) {
    super(PDUType.CONSULT_REQUEST, new int[] {0,0,0,0});
    this.band       = band;
    this.song       = song;
    this.extension  = extension;
  }

  /**
   *  Parameterized constructor.
   *
   *  @param  version   The PDU version.
   *  @param  security  If set to 1 security will be used to transport the PDU. If set to
   *                    0 it won't.
   *  @param  options   PDU Option values, array with 4 ints.
   *  @param  band      The band's name.
   *  @param  song      The song's title.
   *  @param  extension The audio file extension, e.g., "mp3".
   */
  public ConsultRequestPDU (int version, int security, int[] options, String band,
      String song, String extension) {
    super(version, security, PDUType.CONSULT_REQUEST, options);
    this.band       = band;
    this.song       = song;
    this.extension  = extension;
  }

  // Getters.

  /** @return The band's name. */
  public String getBand() { return this.band.toString(); }

  /** @return The song's title. */
  public String getSong() { return this.song.toString(); }

  /** @return The music file extension. */
  public String getExtension() { return this.extension.toString(); }

  // Setters.

  /**
   *  Change the band's name.
   *
   *  @param  band  The band's name.
   */
  public void setBand(String band) { this.band = band.toString(); }

  /**
   *  Change the song's title.
   *
   *  @param  band  The song's title.
   */
  public void setSong(String song) { this.song = song.toString(); }

  /**
   *  Change the music file extension.
   *
   *  @param  extension The file's extension.
   */
  public void setExtension(String extension) { this.extension = extension.toString(); }

  @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getVersion() + "\n");
      sb.append(this.getSecurity() + "\n");
      sb.append(this.getType() + "\n");
      sb.append(Arrays.toString(this.getOptions()) + "\n");
      sb.append(this.getBand() + "\n");
      sb.append(this.getSong() + "\n");
      sb.append(this.getExtension());

      return sb.toString();
    }
}
