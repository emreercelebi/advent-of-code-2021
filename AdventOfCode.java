import BinaryDiagnostic.BinaryDiagnostic;
import Dive.Dive;
import DumboOctopus.DumboOctopus;
import GiantSquid.GiantSquid;
import HydrothermalVenture.HydrothermalVenture;
import Lanternfish.Lanternfish;
import PassagePathing.PassagePathing;
import SevenSegmentSearch.SevenSegmentSearch;
import SmokeBasin.SmokeBasin;
import SonarSweep.SonarSweep;
import SyntaxScoring.SyntaxScoring;
import TreacheryOfWhales.Whales;

class AdventOfCode {
  public static void main(String[] args) throws Exception{
    new SonarSweep();
    new Dive();
    new BinaryDiagnostic();
    new GiantSquid();
    new HydrothermalVenture();
    new Lanternfish();
    new Whales();
    new SevenSegmentSearch();
    new SmokeBasin();
    new SyntaxScoring();
    new DumboOctopus();
    new PassagePathing();
  }
}