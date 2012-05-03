#ifdef TRANSITS
#ifdef J2ME
#define JAVAME
#endif /* J2ME */
package swisseph;

public abstract class TransitCalculator
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  SwissEph sw;

  // This method changes the offset value for the transit
  /**
  * @return Returns true, if one position value is identical to another
  * position value. E.g., 360 degree is identical to 0 degree in
  * circular angles.
  * @see #rolloverVal
  */
  public abstract boolean getRollover();
  /**
  * This sets the degree or other value for the position or speed of
  * the planet to transit. It will be used on the next call to getTransit().
  * @param value The desired offset value.
  * @see #getOffset()
  */
  public abstract void setOffset(double value);
  /**
  * This returns the degree or other value of the position or speed of
  * the planet to transit.
  * @return The currently set offset value.
  * @see #setOffset(double)
  */
  public abstract double getOffset();
  /**
  * This returns all the &quot;object identifiers s&quot; used in this
  * TransitCalculator. It may be the planet number or planet numbers,
  * when calculating planets.
  * @return An array of identifiers identifying the calculated objects.
  */
  public Object[] getObjectIdentifiers() {
    return null;
  }

#ifdef EXTPRECISION
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
  public abstract void setPrecisionFactor(double pfac);
  /**
  * Returns the factor used to control stopping of the calculation
  * iterations.
  * @return The precision factor as set by the setPrecisionFactor()
  * method.
  * @see #setPrecisionFactor(double)
  */
  public abstract double getPrecisionFactor();
#endif /* EXTPRECISION */




  //////////////////////////////////////////////////////////////////////////////


  // Rollover from 360 degrees to 0 degrees for planetary longitudinal positions
  // or similar, or continuous and unlimited values:
  protected boolean rollover = false; // We need a rollover of 360 degrees being
                                      // equal to 0 degrees for longitudinal
                                      // position transits only.
  protected double rolloverVal = 360.; // if rollover, we roll over from 360 to 0
                                       // as default. Other values than 0.0 for the
                                       // minimum values are not supported for now.

  // These methods have to return the maxima of the first derivative of the
  // function, mathematically spoken...
  protected abstract double getMaxSpeed();
  protected abstract double getMinSpeed();

  // This method returns the precision in x-direction in an x-y-coordinate
  // system for the transit calculation routine.
  protected abstract double getDegreePrecision(double jdET);

  // This method returns the precision in y-direction in an x-y-coordinate
  // system from the x-direction precision.
  protected abstract double getTimePrecision(double degPrec);

  // This is the main routine, mathematically speaking: returning f(x):
  protected abstract double calc(double jdET);
}
#endif /* TRANSITS */

