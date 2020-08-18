#ifdef TEST_ITERATIONS
#define TRANSITS
#endif /* TEST_ITERATIONS */
#ifdef EXTPRECISION
#define TRANSITS
#endif /* EXTPRECISION */
#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef TRANSITS
/*
   This is an extension to the Java port of the Swiss Ephemeris package
   of Astrodienst AG, Zuerich (Switzerland).

   Thomas Mack, mack@ifis.cs.tu-bs.de, 3rd of December, 2001

*/

package swisseph;


class Extensions
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  SwissEph sw;

  Extensions(SwissEph sw) {
    this.sw = sw;
  }



#ifdef TEST_ITERATIONS
  private long iterateCount = 0;
#endif /* TEST_ITERATIONS */

  // getTransit() will return the current date and time, when the
  // transit ist occuring on that date. If you really want the next
  // transit AFTER that date, add at least calcTimePrecision(...) to
  // your jdET, as this is the minimum time difference, for which the
  // available precision allows.
  // You can NOT rely on the assumption that you will get realistically
  // differentiable transit values with a time difference of
  // calcTimePrecision(...), but at least it does not make ANY sense
  // to recalculate a transit with a time difference SMALLER than the
  // value returned by calcTimePrecision().
  //
  // A problem:
  // When a transit takes a long time, this means, when the planet
  // stays a long time very near to the transit point, the program
  // may appear to be abitrary in its results. The reason is, that
  // it does not look for the EXACT transit point, but for an area
  // around the exact transit point that is defined by the maximum
  // available precision for the position calculation.

  // You may get many transits for just one planetary transit, as we
  // cannot differentiate transits, when they are in an area of values
  // which is beyond the maximum available precision. E.g., when the
  // sun is in the latitudinal area of 0.0019 to 0.0021 for maybe two
  // days, there is no chance to differentiate between any dates in
  // this area of time: You will get the input date returned as the
  // transit date always, when the input date is in the range of these
  // two days.
  double getTransit(TransitCalculator tc, double jdET, boolean back,
                    double jdMax)
         throws IllegalArgumentException, SwissephException {
    jdET = tc.preprocessDate(jdET, back);
    double max = tc.getMaxSpeed();
    double min = tc.getMinSpeed();

    double jdPlus, jdMinus;
    double lastJD = jdET;

    boolean found = false;
    boolean above;
    double lastVal;
    double val;
    double offset = tc.getOffset();
#ifdef EXTPRECISION
    double extPrecision = tc.getPrecisionFactor();
#endif /* EXTPRECISION */



    boolean xneg = (max < 0);
    boolean mneg = (min < 0);
    if (!xneg && !mneg) { min = max; }
    if (xneg && mneg) { max = min; }

    double degPrec = tc.getDegreePrecision(jdET)/2.; // Divided by two to have a range of +-degPrec
    double timePrec = tc.getTimePrecision(degPrec);

#ifdef EXTPRECISION
    timePrec /= extPrecision;
#endif /* EXTPRECISION */

#ifdef TEST_ITERATIONS
    iterateCount = 1;
#endif /* TEST_ITERATIONS */
    val = tc.calc(jdET);
    if (tc.checkIdenticalResult(offset, val)) { // If not 0.0 but "very small", then
                            // interpolate after another calculation
                            // in the calculation loop below
      return val;
    }


    if (max == 0. && min == 0.) { // No possible change in position or speed
      throw new SwissephException(jdET, SwissephException.OUT_OF_TIME_RANGE,
          "No transit possible due to lack of variation of speed or position.");
    }

    while (true) {
      if (tc.rollover) {
        while (val >= tc.rolloverVal) { val -= tc.rolloverVal; }
        while (val < 0.) { val += tc.rolloverVal; }
      }
      above = (val >= offset);

      lastJD = jdET;
      lastVal = val;

      //while (tc.rollover && val<offset) { val += tc.rolloverVal; }
      if (tc.rollover && !above) { val += tc.rolloverVal; }

      jdET = tc.getNextJD(jdET, val, offset, min, max, back);


      // Add at least "timePrec" time to the last time:
      if (SMath.abs(jdET - lastJD) < timePrec) {
        jdET = lastJD + (back?-timePrec:+timePrec);
      }
      if (jdET == lastJD) {
        return jdET;
      }
#ifdef TEST_ITERATIONS
      iterateCount++;
#endif /* TEST_ITERATIONS */
      val = tc.calc(jdET);
      if (val == Double.POSITIVE_INFINITY) {
        return jdET;
      }
      if (tc.rollover && val >= tc.rolloverVal) { val %= tc.rolloverVal; }
      while (tc.rollover && val < 0.) { val += tc.rolloverVal; }

      // Hits the transiting point exactly...:
      if(tc.checkIdenticalResult(offset, val)) {
        return val;
      }

      // The planet may have moved forward or backward, in one of these
      // directions it would have crossed the transit point.
      //
      // Whatever distance could have been reached in lesser time (forward or
      // backward move), we take it to be the direction of movement.
      boolean pxway = true;
      if (tc.rollover) {
        double deltadeg1 = val-lastVal;
        if (deltadeg1<0) { deltadeg1+=tc.rolloverVal; }
        double deltadeg2 = lastVal-val;
        if (deltadeg2<0) { deltadeg2+=tc.rolloverVal; }
        pxway = SMath.abs(deltadeg1/max)<SMath.abs(deltadeg2/min);
      } else {
        pxway = lastVal<=val;
      }

      found = tc.checkResult(offset, lastVal, val, above, pxway);

      if (found) { // Return an interpolated value, but not prior to (after)
                   // the initial time (if backward):
        if (tc.rollover) {
          if (tc.rollover && SMath.abs(val - lastVal) > 300.) {   // How to do it formally correct???
            // Probably one value is about 359.99 and the other one is in the area of 0.01
            if (val > lastVal) { lastVal += tc.rolloverVal; } else { val += tc.rolloverVal; }
            if (offset < 10.) { offset += tc.rolloverVal; } // How to do it formally correct???
          }
          // offset-lastVal and val-lastVal have to have equal signs
          if (val-lastVal < 0 && offset-lastVal > 0) {
            val += tc.rolloverVal;
          } else if (val-lastVal > 0 && offset-lastVal < 0) {
            offset += tc.rolloverVal;
          }
        }
        double jdRet = lastJD+(jdET-lastJD)*(offset-lastVal)/(val-lastVal);
        if (back) {
          return SMath.max(jdRet, jdET);
        } else {
          return SMath.min(jdRet, jdET);
        }
      }
      if ((back && jdET < jdMax) ||
          (!back && jdET > jdMax)) {
        throw new SwissephException(jdET, SwissephException.BEYOND_USER_TIME_LIMIT,
            -1, "User time limit of " + jdMax + " has been reached.");
      }
    }
  }

#ifdef TEST_ITERATIONS
  long getIterateCount() {
    return iterateCount;
  }
#endif /* TEST_ITERATIONS */



  // The precision of a distance calculation is related to the barycentric
  // distance
  // E.g.: java Swetest -b1.1.0 -p0 -n100000 -fR -bary | sort -n
  protected double maxBaryDist[] = new double[] {
     0.009570999,    // 0 Sun        ==  0                   1.017545559
     1.028809521,    // 1 Moon       ==  1
     0.466604085,    // 2 Mercury    ==  2
     0.728698831,    // 3 Venus      ==  3
     0.728698831,    // 4 Mars       ==  4
     4.955912195,    // 5 Jupiter    ==  5
     8.968685733,    // 6 Saturn     ==  6
    19.893326756,    // 7 Uranus     ==  7
    30.326750627,    // 8 Neptune    ==  8
    41.499626899,    // 9 Pluto      ==  9
     0.002569555,    // m MeanNode   == 10
     0.002774851,    // t TrueNode   == 11
     1.0,            // A Mean Apog. == 12            // Does not vary distance anyway
     0.002782378,    // B Oscu.Apog. == 13
     0.0,            // C Earth      == 14 (skip)
     0.05,           // D Chiron     == 15            // No distance available, is 0.05 good???
    31.901319663,    // E Pholus     == 16
     3.012409508,    // F Ceres      == 17
     3.721614106,    // G Pallas     == 18
     3.326307148,    // H Juno       == 19
     2.570197288,    // I Vesta      == 20
  };



}
#endif /* TRANSITS */
