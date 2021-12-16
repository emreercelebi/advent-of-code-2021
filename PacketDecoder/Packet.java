package PacketDecoder;

public class Packet {
  long value;
  int length;

  public Packet(long v, int l) {
    this.value = v;
    this.length = l;
  }
}
