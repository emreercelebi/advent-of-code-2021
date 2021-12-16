package PacketDecoder;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.Helpers;

public class PacketDecoder {
  private static final String FILE_NAME = "input.txt";

  List<String> lines;
  Map<Character, String> hexToBinary;
  String binaryString;
  int versionTotal;

  public PacketDecoder() {
    URL path = PacketDecoder.class.getResource(FILE_NAME);

    this.lines = Helpers.getFileLines(path);

    this.init();
    int part1Result = this.runPart1();

    this.init();
    int part2Result = this.runPart2();

    System.out.println("Packet Decoder part 1: " + part1Result);
    System.out.println("Packet Decoder part 2: " + part2Result);
  }

  private void init() {
    this.versionTotal = 0;
    this.hexToBinary = new HashMap<>();
    char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    String[] bin = {
      "0000",
      "0001",
      "0010",
      "0011",
      "0100",
      "0101",
      "0110",
      "0111",
      "1000",
      "1001",
      "1010",
      "1011",
      "1100",
      "1101",
      "1110",
      "1111"
    };

    for (int i = 0; i < hex.length; i++) {
      this.hexToBinary.put(hex[i], bin[i]);
    }

    StringBuilder sb = new StringBuilder();
    for (char c : this.lines.get(0).toCharArray()) {
      sb.append(this.hexToBinary.get(c));
    }

    this.binaryString = sb.toString();
  }

  private int runPart1() {
    processPacket(this.binaryString);

    return this.versionTotal;
  }

  private int runPart2() {
    return 0;
  }

  private int processPacket(String bin) {
    int packetLength = 6;
    int version = binaryToInt(bin.substring(0, 3));
    int typeId = binaryToInt(bin.substring(3, 6));
    boolean isLiteral = typeId == 4;
    
    this.versionTotal += version;

    StringBuilder binaryResult = new StringBuilder();
    if (isLiteral) {
      int indexOffset = 6;
      int index = 0;
      char group;
      do {
        packetLength += 5;
        group = bin.charAt(index + indexOffset);
        binaryResult.append(bin.substring(index + indexOffset + 1, index + indexOffset + 5));
        index += 5;
      } while (group != '0');
    } else {
      char lengthTypeId = bin.charAt(6);
      packetLength++;
      if (lengthTypeId == '0') {
        int subPacketLength = binaryToInt(bin.substring(7, 22));
        String subPackets = bin.substring(22, 22 + subPacketLength);
        packetLength += subPacketLength + 15;
        int sbl = 0;
        while (sbl < subPacketLength) {
          sbl += processPacket(subPackets.substring(sbl));
        }
      } else {
        int numSubPackets = binaryToInt(bin.substring(7, 18));
        packetLength += 11;
        int index = 18;
        for (int i = 0; i < numSubPackets; i++) {
          int sbl = processPacket(bin.substring(index));
          index += sbl;
          packetLength += sbl; 
        }
      }
    }
    return packetLength;
  }



  private int binaryToInt(String bin) {
    int result = 0;
    for (int i = bin.length() - 1; i >= 0; i--) {
      if (bin.charAt(i) == '1') {
        int exp = bin.length() - 1 - i;
        result += Math.pow(2, exp);
      }
    }
    return result;
  }
}
