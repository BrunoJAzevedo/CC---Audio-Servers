import pdu.PDUType;
import pdu.PDU;

public class PDUTest {
  public static void main(String args[]) {
    int options[] = {0, 0, 0, 0};
    PDU pdu = new PDU(1, 0, PDUType.REGISTER, options);

    System.out.println(pdu.version);
  }
}

