#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef TRANSITS
package swisseph;

/**
* This class implements a TransitCalculator for one planets
* position or speed.<p>
* You would create a TransitCalculator from this class and
* use the SwissEph.getTransit() methods to actually calculate
* a transit, e.g.:<p>
* <pre>
* SwissEph sw = new SwissEph(...);
* ...
* int flags = SweConst.SEFLG_SWIEPH |
*             SweConst.SEFLG_TRANSIT_LONGITUDE |
*             SweConst.SEFLG_TRANSIT_SPEED;
* boolean backwards = false;
* 
* TransitCalculator tc = new TCPlanet(
*                                  sw,
*                                  SweConst.SE_SATURN,
*                                  flags,
*                                  0);
* ...
* double nextTransitET = sw.getTransitET(tc, jdET, backwards);
* </pre>
* This would calculate the (ET-) date, when the Saturn will
* change from retrograde to direct movement or vice versa.
*/
public class TCPlanet extends TransitCalculator
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{


  private int planet;
  private int idx = 0; // The index into the xx[] array in swe_calc() to use:
  private int tflags = 0; // The transit flags
  private  int flags = 0;  // The calculation flags for swe_calc()
  private  double min = 0;
  private  double max = 0;
  // The y = f(x) value to reach, speaking mathematically...
  private double offset = 0.;
//#ifdef EXTPRECISION
  private double extPrecision = 1.;
//#endif /* EXTPRECISION */



double minVal = 0., maxVal = 0.;  // Thinking about it...


  /**
  * Creates a new TransitCalculator for transits of any of the planets
  * positions (longitudinal / latitudinal and distance) or speeds, be
  * it in the geocentric or topocentric coordinate system, or in tropical
  * or sidereal zodiac.<p>
  * @param sw A SwissEph object, if you have one available. Can be null.
  * @param planet The transiting planet. Valid planets are SweConst.SE_SUN
  * up to SweConst.SE_INTP_PERG with the exception of SweConst.SE_EARTH.
  * @param flags The calculation type flags (SweConst.SEFLG_TRANSIT_LONGITUDE,
  * SweConst.SEFLG_TRANSIT_LATITUDE or SweConst.SEFLG_TRANSIT_DISTANCE in
  * conjunction with SweConst.SEFLG_TRANSIT_SPEED for transits over a speed
  * value).<br><br>
  * Also flags modifying the basic planet calculations, these are
  * SweConst.SEFLG_TOPOCTR, SweConst.SEFLG_EQUATORIAL, SweConst.SEFLG_HELCTR,
  * SweConst.SEFLG_TRUEPOS, and SweConst.SEFLG_SIDEREAL, plus the ephemeris
//#ifdef NO_JPL
  * flags SweConst.SEFLG_MOSEPH, SweConst.SEFLG_SWIEPH.
//#else
  * flags SweConst.SEFLG_MOSEPH, SweConst.SEFLG_SWIEPH or
  * SweConst.SEFLG_JPLEPH optionally.
//#endif /* NO_JPL */
  * <br>For <i>right ascension</i> use <code>SEFLG_TRANSIT_LONGITUDE | SEFLG_EQUATORIAL</code>,
  * for <i>declination</i> <code>SEFLG_TRANSIT_LATITUDE | SEFLG_EQUATORIAL</code>.
  * @param offset This is the desired transit degree or distance in AU or transit speed
  * in deg/day.
  * @see swisseph.TCPlanetPlanet#TCPlanetPlanet(SwissEph, int, int, int, double)
  * @see swisseph.SweConst#SEFLG_TRANSIT_LONGITUDE
  * @see swisseph.SweConst#SEFLG_TRANSIT_LATITUDE
  * @see swisseph.SweConst#SEFLG_TRANSIT_DISTANCE
  * @see swisseph.SweConst#SEFLG_TRANSIT_SPEED
  * @see swisseph.SweConst#SEFLG_YOGA_TRANSIT
  * @see swisseph.SweConst#SEFLG_TOPOCTR
  * @see swisseph.SweConst#SEFLG_EQUATORIAL
  * @see swisseph.SweConst#SEFLG_HELCTR
  * @see swisseph.SweConst#SEFLG_TRUEPOS
  * @see swisseph.SweConst#SEFLG_SIDEREAL
  * @see swisseph.SweConst#SEFLG_MOSEPH
  * @see swisseph.SweConst#SEFLG_SWIEPH
//#ifndef NO_JPL
  * @see swisseph.SweConst#SEFLG_JPLEPH
//#endif /* NO_JPL */
  */
  public TCPlanet(SwissEph sw, int planet, int flags, double offset) {
    // Check parameter: //////////////////////////////////////////////////////
    // List of all valid flags:
    this.tflags = flags;
    int vFlags = SweConst.SEFLG_EPHMASK |
                 SweConst.SEFLG_TOPOCTR |
                 SweConst.SEFLG_EQUATORIAL |
                 SweConst.SEFLG_HELCTR |
                 SweConst.SEFLG_NOABERR |
                 SweConst.SEFLG_NOGDEFL |
                 SweConst.SEFLG_SIDEREAL |
                 SweConst.SEFLG_TRUEPOS |
                 SweConst.SEFLG_TRANSIT_LONGITUDE |
                 SweConst.SEFLG_TRANSIT_LATITUDE |
                 SweConst.SEFLG_TRANSIT_DISTANCE |
                 SweConst.SEFLG_TRANSIT_SPEED;
    // NOABERR and NOGDEFL is allowed for HELCTR, as they get set
    // anyway.
    if ((flags & SweConst.SEFLG_HELCTR) != 0) {
      vFlags |= SweConst.SEFLG_NOABERR | SweConst.SEFLG_NOGDEFL;
    }
    if ((flags&~vFlags) != 0) {
      throw new IllegalArgumentException("Invalid flag(s): "+(flags&~vFlags));
    }

    // Allow only one of SEFLG_TRANSIT_LONGITUDE, SEFLG_TRANSIT_LATITUDE, SEFLG_TRANSIT_DISTANCE:
    int type = flags&(SweConst.SEFLG_TRANSIT_LONGITUDE |
                      SweConst.SEFLG_TRANSIT_LATITUDE |
                      SweConst.SEFLG_TRANSIT_DISTANCE);
    if (type != SweConst.SEFLG_TRANSIT_LONGITUDE &&
        type != SweConst.SEFLG_TRANSIT_LATITUDE &&
        type != SweConst.SEFLG_TRANSIT_DISTANCE) {
      throw new IllegalArgumentException("Invalid flag combination '" + flags +
        "': specify exactly one of SEFLG_TRANSIT_LONGITUDE (" +
        SweConst.SEFLG_TRANSIT_LONGITUDE + "), SEFLG_TRANSIT_LATITUDE (" +
        SweConst.SEFLG_TRANSIT_LATITUDE + "), SEFLG_TRANSIT_DISTANCE (" +
        SweConst.SEFLG_TRANSIT_DISTANCE + ").");
    }
    if (planet<SweConst.SE_SUN ||
//#ifdef ASTROLOGY
        planet>SweConst.SE_MEAN_APOG) {
//#else
        planet>SweConst.SE_INTP_PERG ||
        planet==SweConst.SE_EARTH) {
//#endif /* ASTROLOGY */
      throw new IllegalArgumentException(
          "Unsupported planet number " + planet + " ("+
              sw.swe_get_planet_name(planet) + ")");
    }
    if ((flags & SweConst.SEFLG_HELCTR) != 0 &&
        (planet == SweConst.SE_MEAN_APOG ||
         planet == SweConst.SE_OSCU_APOG ||
         planet == SweConst.SE_MEAN_NODE ||
         planet == SweConst.SE_TRUE_NODE)) {
      throw new IllegalArgumentException(
          "Unsupported planet number " + planet + " (" +
              sw.swe_get_planet_name(planet) + ") for heliocentric " +
              "calculations");
    }

    this.planet = planet;

    this.sw = sw;
    if (this.sw == null) {
      this.sw = new SwissEph();
    }


    // The index into the xx[] array in swe_calc() to use:
    if ((flags&SweConst.SEFLG_TRANSIT_LATITUDE) != 0) { // Calculate latitudinal transits
      idx = 1;
    } else if ((flags&SweConst.SEFLG_TRANSIT_DISTANCE) != 0) { // Calculate distance transits
      idx = 2;
    }
    if ((flags&SweConst.SEFLG_TRANSIT_SPEED) != 0) { // Calculate speed transits
      idx += 3;
      flags |= SweConst.SEFLG_SPEED;
    }

    // Eliminate SEFLG_TRANSIT_* flags for use in swe_calc():
    flags &= ~(SweConst.SEFLG_TRANSIT_LONGITUDE |
               SweConst.SEFLG_TRANSIT_LATITUDE |
               SweConst.SEFLG_TRANSIT_DISTANCE |
               SweConst.SEFLG_TRANSIT_SPEED);
    this.flags = flags;


    rollover = (idx == 0);  // Ok - idx==1 as well, but the range will be from -90 to +90
                            // then, which does not fit the scheme. We need a rolloverMin
                            // value or similar for this to work

    this.offset = checkOffset(offset);

    max = getSpeed(false);
    min = getSpeed(true);
    if (Double.isInfinite(max) || Double.isInfinite(min)) {
      throw new IllegalArgumentException(
          ((flags&SweConst.SEFLG_TOPOCTR)!=0?"Topo":((flags&SweConst.SEFLG_HELCTR)!=0?"Helio":"Geo")) +
          		"centric transit calculations of planet number " + planet + " ("+
          		sw.swe_get_planet_name(planet) + ") not possible: extreme " +
			((flags & SweConst.SEFLG_SPEED) != 0 ? "accelerations" : "speeds") +
			" of the planet " +
			((flags & SweConst.SEFLG_EQUATORIAL) != 0 ? "in equatorial system " : "") +
			"not available.");
    }
  }

  /**
  * @return Returns true, if one position value is identical to another
  * position value. E.g., 360 degree is identical to 0 degree in
  * circular angles.
  * @see #rolloverVal
  */
  public boolean getRollover() {
    return rollover;
  }
  /**
  * This sets the degree or other value for the position or speed of
  * the planet to transit. It will be used on the next call to getTransit().
  * @param value The desired offset value.
  * @see #getOffset()
  */
  public void setOffset(double value) {
    offset = checkOffset(value);
  }
  /**
  * This returns the degree or other value of the position or speed of
  * the planet to transit.
  * @return The currently set offset value.
  * @see #setOffset(double)
  */
  public double getOffset() {
    return offset;
  }
  /**
  * This returns the planet number as an Integer object.
  * @return An array of identifiers identifying the calculated objects.
  */
  public Object[] getObjectIdentifiers() {
    return new Integer[]{planet};
  }
//#ifdef EXTPRECISION
  /**
  * Set the factor, when to stop the calculation.
  * The getTransit*() methods will iterate calculations until the maximum
  * precision in the planetary calculation routines has been passed. With
  * this method, you specify a factor to the calculation precision used.
  * E.g., 100 means, stop the calculation, when the difference between the
  * calculated value and requested value has crossed a value of 100 times
  * less than the precision available in the calculation routines. 0.01 on
  * the other hand would stop the calculation, BEFORE the maximum available
  * precision had been reached. The default for the precision factor is 1.<p>
  * @param pfac The factor for the precision as explained above.
  * <b>Note:</b> A value greater than one will NOT really increase precision,
  * it will just <i>appear</i> to do so. It can be handy to show more equal
  * values when approaching the transit point from different sides (forward
  * or backward) or from different starting points.
  * @see #getPrecisionFactor()
  */
  public void setPrecisionFactor(double pfac) {
    extPrecision = pfac;
  }
  /**
  * Returns the factor used to control stopping of the calculation
  * iterations.
  * @return The precision factor as set by the setPrecisionFactor()
  * method.
  * @see #setPrecisionFactor(double)
  */
  public double getPrecisionFactor() {
    return extPrecision;
  }
//#endif /* EXTPRECISION */




  //////////////////////////////////////////////////////////////////////////////

  protected double calc(double jdET) {
    StringBuffer serr = new StringBuffer();
    double[] xx = new double[6];

    int ret = sw.swe_calc(jdET, planet, flags, xx, serr);
    if (ret<0) {
      throw new SwissephException(jdET, SwissephException.UNDEFINED_ERROR,
          "Calculation failed with return code "+ret+":\n"+serr.toString());
    }

    return xx[idx];
  }


  protected double getMaxSpeed() {
    return max;
  }
  protected double getMinSpeed() {
    return min;
  }


  protected double getTimePrecision(double degPrec) {
    // Recalculate degPrec to mean the minimum  time, in which the planet can
    // possibly move that degree:
    double maxTimePerDeg = SMath.max(SMath.abs(min),SMath.abs(max));
    if (maxTimePerDeg != 0.) {
      return degPrec / maxTimePerDeg;
    }
    return 1E-9;
  }

  protected double getDegreePrecision(double jd) {
    // Calculate the planet's minimum movement regarding the maximum available
    // precision.
    //
    // For all calculations, we assume the following minimum exactnesses
    // based on the discussions on http://www.astro.com/swisseph, even though
    // these values are nothing more than very crude estimations which should
    // leave us on the save side always, even more, when seeing that we always
    // consider the maximum possible speed / acceleration of a planet in the
    // transit calculations and not the real speed.
    //
    // Take degPrec to be the minimum exact degree in longitude
    double degPrec = 0.005;
    if (idx>2) { // Speed
      // "The speed precision is now better than 0.002" for all planets"
      degPrec = 0.002;
    } else { // Degrees
      // years 1980 to 2099:              0.005"
      // years before 1980:               0.08"   (from sun to jupiter)
      // years 1900 to 1980:              0.08"   (from saturn to neptune) (added: nodes)
      // years before 1900:               1"      (from saturn to neptune) (added: nodes)
      // years after 2099:                same as before 1900
      //
      if (planet>=SweConst.SE_SUN && planet<=SweConst.SE_JUPITER) {
        if (jd<1980 || jd>2099) {
          degPrec = 0.08;
        }
      } else {
        if (jd>=1900 && jd<1980) {
          degPrec = 0.08;
        } else if (jd<1900 || jd>2099) { // Unclear about true nodes...
          degPrec = 1;
        }
      }
    }
    degPrec/=3600.;
    degPrec*=0.5; // We take the precision to BETTER THAN ... as it is stated somewhere

    // We recalculate these degrees to the minimum time difference that CAN
    // possibly give us data differing more than the above given precision.
    switch (idx) {
      case 0: // Longitude
      case 1: // Latitude
      case 3: // Speed in longitude
      case 4: // Speed in latitude
        break;
      case 2: // Distance
      case 5: // Speed in distance
        // We need to recalculate the precision in degrees to a distance value.
        // For this we need the maximum distance to the centre of calculation,
        // which is the barycentre for the main planets.
        degPrec *= sw.ext.maxBaryDist[planet];
    }

    return degPrec;

    // Barycentre:
    //            0.981683040      1.017099581  (Barycenter of the earth!)
    // Sun:       0.982747149 AU   1.017261973 AU
    // Moon:      0.980136691 AU   1.019846623 AU
    // Mercury:   0.307590579 AU   0.466604085 AU
    // Venus:     0.717960758 AU   0.728698831 AU
    // Mars:      1.382830768 AU   0.728698831 AU
    // Jupiter:   5.448547595 AU   4.955912195 AU
    // Saturn:   10.117683425 AU   8.968685733 AU
    // Uranus:   18.327870391 AU  19.893326756 AU
    // Neptune:  29.935653168 AU  30.326750627 AU
    // Pluto:    29.830132096 AU  41.499626899 AU
    // MeanNode:  0.002569555 AU   0.002569555 AU
    // TrueNode:  0.002361814 AU   0.002774851 AU

    //
    // Minimum and maximum (barycentric) distances:
    // Sun:       0.000095 AU      0.01034 AU
    // Moon:      0.972939 AU      1.02625 AU
    // Mercury:   0.298782 AU      0.47569 AU
    // Venus:     0.709190 AU      0.73723 AU
    // Mars:      1.370003 AU      1.67685 AU
    // Jupiter:   4.912031 AU      5.47705 AU
    // Saturn:    8.948669 AU     10.13792 AU
    // Uranus:   18.257511 AU     20.12033 AU
    // Neptune:  29.780622 AU     30.36938 AU
    // Pluto:    29.636944 AU     49.43648 AU
    // MeanNode:  -        AU      -       AU ?
    // TrueNode:  -        AU      -       AU ?


    // Maximum and minimum (geocentric) distances:
    // Sun:        1.016688129 AU   0.983320477 AU
    // Moon:       0.002710279 AU   0.002439921 AU
    // Mercury:    0.549188094 AU   1.448731236 AU

    // Saturn:     7.84 / 7.85 AU  11.25/11.26  AU
    // Uranus:    21.147/21.148 AU              AU

  }


  //////////////////////////////////////////////////////////////////////////////

  private double checkOffset(double val) {
    // Similar rollover considerations for the latitude will be necessary, if
    // swe_calc() would return latitudinal values beyond -90 and +90 degrees.

    if (rollover) {        // Longitude from 0 to 360 degrees:
      while (val < 0.) { val += 360.; }
      val %= 360.;
      minVal = 0.;
      maxVal = 360.;
    } else if (idx == 1) { // Latitude from -90 to +90 degrees:
      while (val < -90.) { val += 180.; }
      while (val >  90.) { val -= 180.; }
      minVal = -90.;
      maxVal = +90.;
    }
    return val;
  }


  private double getSpeed(boolean min) {
if (planet >= SwephData.minTopoLonSpeed.length) { return (min?-0.100:0.0157); } // Q == Poseidon
    boolean lat = ((tflags&SweConst.SEFLG_TRANSIT_LATITUDE) != 0);
    boolean dist = ((tflags&SweConst.SEFLG_TRANSIT_DISTANCE) != 0);
    boolean speed = ((tflags&SweConst.SEFLG_TRANSIT_SPEED) != 0);
    boolean topo = ((tflags&SweConst.SEFLG_TOPOCTR) != 0);
    boolean helio = ((tflags&SweConst.SEFLG_HELCTR) != 0);
    boolean rect = ((tflags&SweConst.SEFLG_EQUATORIAL) != 0 && !lat && !dist);
    boolean decl = ((tflags&SweConst.SEFLG_EQUATORIAL) != 0 && lat);

    // Some topocentric speeds are very different to the geocentric
    // speeds, so we use other values than for geocentric calculations:
    if (topo) {
      if (!sw.swed.geopos_is_set) {
        throw new IllegalArgumentException("Geographic position is not set for "+
                                           "requested topocentric calculations.");
      }
      if (sw.swed.topd.geoalt>50000.) {
        throw new IllegalArgumentException("Topocentric transit calculations "+
                                           "are restricted to a maximum "+
                                           "altitude of 50km so far.");
      } else if (sw.swed.topd.geoalt<-12000000) {
        throw new IllegalArgumentException("Topocentric transit calculations "+
                                           "are restricted to a minimum "+
                                           "altitude of -12000km so far.");
      }
      if (speed) {
        if (rect) {
          return (min?SwephData.minTopoRectAccel[planet]:SwephData.maxTopoRectAccel[planet]);
        } else if (decl) {
          return (min?SwephData.minTopoDeclAccel[planet]:SwephData.maxTopoDeclAccel[planet]);
        } else if (lat) {
          return (min?SwephData.minTopoLatAccel[planet]:SwephData.maxTopoLatAccel[planet]);
        } else if (dist) {
          return (min?SwephData.minTopoDistAccel[planet]:SwephData.maxTopoDistAccel[planet]);
        } else {
          return (min?SwephData.minTopoLonAccel[planet]:SwephData.maxTopoLonAccel[planet]);
        }
      } else {
        if (rect) {
          return (min?SwephData.minTopoRectSpeed[planet]:SwephData.maxTopoRectSpeed[planet]);
        } else if (decl) {
          return (min?SwephData.minTopoDeclSpeed[planet]:SwephData.maxTopoDeclSpeed[planet]);
        } else if (lat) {
          return (min?SwephData.minTopoLatSpeed[planet]:SwephData.maxTopoLatSpeed[planet]);
        } else if (dist) {
          return (min?SwephData.minTopoDistSpeed[planet]:SwephData.maxTopoDistSpeed[planet]);
        } else {
          return (min?SwephData.minTopoLonSpeed[planet]:SwephData.maxTopoLonSpeed[planet]);
        }
      }
    }

    // Heliocentric speeds are very different to the geocentric speeds, so
    // we use other values than for geocentric calculations:
    if (helio) {
      if (speed) {
        if (rect) {
          return (min?SwephData.minHelioRectAccel[planet]:SwephData.maxHelioRectAccel[planet]);
        } else if (decl) {
          return (min?SwephData.minHelioDeclAccel[planet]:SwephData.maxHelioDeclAccel[planet]);
        } else if (lat) {
          return (min?SwephData.minHelioLatAccel[planet]:SwephData.maxHelioLatAccel[planet]);
        } else if (dist) {
          return (min?SwephData.minHelioDistAccel[planet]:SwephData.maxHelioDistAccel[planet]);
        } else {
          return (min?SwephData.minHelioLonAccel[planet]:SwephData.maxHelioLonAccel[planet]);
        }
      } else {
        if (rect) {
          return (min?SwephData.minHelioRectSpeed[planet]:SwephData.maxHelioRectSpeed[planet]);
        } else if (decl) {
          return (min?SwephData.minHelioDeclSpeed[planet]:SwephData.maxHelioDeclSpeed[planet]);
        } else if (lat) {
          return (min?SwephData.minHelioLatSpeed[planet]:SwephData.maxHelioLatSpeed[planet]);
        } else if (dist) {
          return (min?SwephData.minHelioDistSpeed[planet]:SwephData.maxHelioDistSpeed[planet]);
        } else {
          return (min?SwephData.minHelioLonSpeed[planet]:SwephData.maxHelioLonSpeed[planet]);
        }
      }
    }


    // Geocentric:
    if (speed) {
      if (rect) {
        return (min?SwephData.minRectAccel[planet]:SwephData.maxRectAccel[planet]);
      } else if (decl) {
        return (min?SwephData.minDeclAccel[planet]:SwephData.maxDeclAccel[planet]);
      } else if (lat) {
        return (min?SwephData.minLatAccel[planet]:SwephData.maxLatAccel[planet]);
      } else if (dist) {
        return (min?SwephData.minDistAccel[planet]:SwephData.maxDistAccel[planet]);
      } else {
        return (min?SwephData.minLonAccel[planet]:SwephData.maxLonAccel[planet]);
      }
    } else {
      if (rect) {
        return (min?SwephData.minRectSpeed[planet]:SwephData.maxRectSpeed[planet]);
      } else if (decl) {
        return (min?SwephData.minDeclSpeed[planet]:SwephData.maxDeclSpeed[planet]);
      } else if (lat) {
        return (min?SwephData.minLatSpeed[planet]:SwephData.maxLatSpeed[planet]);
      } else if (dist) {
        return (min?SwephData.minDistSpeed[planet]:SwephData.maxDistSpeed[planet]);
      } else {
        return (min?SwephData.minLonSpeed[planet]:SwephData.maxLonSpeed[planet]);
      }
    }
  }

  public String toString() {
    return "[Planet:" + planet + "];Offset:" + getOffset();
  }
}
#endif /* TRANSITS */
