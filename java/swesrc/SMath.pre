#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef JAVAME
#undef STRICTMATH
#else
#ifndef STRICTMATH
#define USE_MATH
#endif /* STRICTMATH */
#endif /* JAVAME */
/* java.lang.SMath -- common mathematical functions
   Copyright (C) Sourav.*/


/*
 * Some of the algorithms in this class are in the public domain, as part
 * of fdlibm (freely-distributable math library), available at
 * http://www.netlib.org/fdlibm/, and carry the following copyright:
 * ====================================================
 * Copyright (C) 1993 by Sun Microsystems, Inc. All rights reserved.
 *
 * Developed at SunSoft, a Sun Microsystems, Inc. business.
 * Permission to use, copy, modify, and distribute this
 * software is freely granted, provided that this notice
 * is preserved.
 * ====================================================
 */
package swisseph;

/**
#ifdef JAVAME
 * Helper class containing useful mathematical functions and constants.
 * This class mirrors {@link Math}, but is 100% portable. Also, these algorithms are all accurate
 * to less than 1 ulp.
 *
 * <p>The source of the various algorithms used is the fdlibm library, at:<br>
 * <a href="http://www.netlib.org/fdlibm/">http://www.netlib.org/fdlibm/</a>
 *
 * Note that angles are specified in radians.  Conversion functions are
 * provided for your convenience.
 *
 * @author Sourav (sourav@gmx.ch)
#else
#ifdef STRICTMATH
 * Helper class mapping all mathematical functions and constants to
 * the java.lang.StrictMath class.
#else
#ifdef USE_MATH
 * Helper class mapping all mathematical functions and constants to
 * the java.lang.Math class.
#endif /* USE_MATH */
#endif /* STRICTMATH */
#endif /* JAVAME */
 */
public class SMath
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
#ifdef STRICTMATH
  public static final double E = StrictMath.E;
  public static final double PI = StrictMath.PI;
  public static int abs(int i) { return StrictMath.abs(i); }
  public static long abs(long l) { return StrictMath.abs(l); }
  public static float abs(float f) { return StrictMath.abs(f); }
  public static double abs(double d) { return StrictMath.abs(d); }
  public static int min(int a, int b) {return StrictMath.min(a, b); }
  public static long min(long a, long b) {return StrictMath.min(a, b); }
  public static float min(float a, float b) {return StrictMath.min(a, b); }
  public static double min(double a, double b) {return StrictMath.min(a, b); }
  public static int max(int a, int b) { return StrictMath.max(a, b); }
  public static long max(long a, long b) { return StrictMath.max(a, b); }
  public static float max(float a, float b) { return StrictMath.max(a, b); }
  public static double max(double a, double b) { return StrictMath.max(a, b); }
  public static double sin(double a) { return StrictMath.sin(a); }
  public static double cos(double a) { return StrictMath.cos(a); }
  public static double tan(double a) { return StrictMath.tan(a); }
  public static double asin(double x) { return StrictMath.asin(x); }
  public static double acos(double x) { return StrictMath.acos(x); }
  public static double atan(double x) { return StrictMath.atan(x); }
  public static double atan2(double y, double x) { return StrictMath.atan2(y, x); }
  public static double exp(double x) { return StrictMath.exp(x); }
  public static double log(double x) { return StrictMath.log(x); }
  public static double sqrt(double x) { return StrictMath.sqrt(x); }
  public static double pow(double x, double y) { return StrictMath.pow(x, y); }
  public static double IEEEremainder(double x, double y) { return StrictMath.IEEEremainder(x, y); }
  public static double ceil(double a) { return StrictMath.ceil(a); }
  public static double floor(double a) { return StrictMath.floor(a); }
  public static double rint(double a) { return StrictMath.rint(a); }
  public static int round(float f) { return StrictMath.round(f); }
  public static long round(double d) { return StrictMath.round(d); }
  public static synchronized double random() { return StrictMath.random(); }
#else
#ifdef USE_MATH
  public static final double E = Math.E;
  public static final double PI = Math.PI;
  public static int abs(int i) { return Math.abs(i); }
  public static long abs(long l) { return Math.abs(l); }
  public static float abs(float f) { return Math.abs(f); }
  public static double abs(double d) { return Math.abs(d); }
  public static int min(int a, int b) {return Math.min(a, b); }
  public static long min(long a, long b) {return Math.min(a, b); }
  public static float min(float a, float b) {return Math.min(a, b); }
  public static double min(double a, double b) {return Math.min(a, b); }
  public static int max(int a, int b) { return Math.max(a, b); }
  public static long max(long a, long b) { return Math.max(a, b); }
  public static float max(float a, float b) { return Math.max(a, b); }
  public static double max(double a, double b) { return Math.max(a, b); }
  public static double sin(double a) { return Math.sin(a); }
  public static double cos(double a) { return Math.cos(a); }
  public static double tan(double a) { return Math.tan(a); }
  public static double asin(double x) { return Math.asin(x); }
  public static double acos(double x) { return Math.acos(x); }
  public static double atan(double x) { return Math.atan(x); }
  public static double atan2(double y, double x) { return Math.atan2(y, x); }
  public static double exp(double x) { return Math.exp(x); }
  public static double log(double x) { return Math.log(x); }
  public static double sqrt(double x) { return Math.sqrt(x); }
  public static double pow(double x, double y) { return Math.pow(x, y); }
  public static double IEEEremainder(double x, double y) { return Math.IEEEremainder(x, y); }
  public static double ceil(double a) { return Math.ceil(a); }
  public static double floor(double a) { return Math.floor(a); }
  public static double rint(double a) { return Math.rint(a); }
  public static int round(float f) { return Math.round(f); }
  public static long round(double d) { return Math.round(d); }
  public static synchronized double random() { return Math.random(); }
//  public static double toRadians(double degrees) { return Math.toRadians(degrees); }
//  public static double toDegrees(double rads) { return Math.toDegrees(rads); }
//  public static double signum(double a)
//  public static float signum(float a)
//  public static double ulp(double d)
//  public static float ulp(float f)
#else

 
  private static final double
    TWO_16 = 0x10000, // Long bits 0x40f0000000000000L.
    TWO_20 = 0x100000, // Long bits 0x4130000000000000L.
    TWO_24 = 0x1000000, // Long bits 0x4170000000000000L.
    TWO_27 = 0x8000000, // Long bits 0x41a0000000000000L.
    TWO_28 = 0x10000000, // Long bits 0x41b0000000000000L.
    TWO_29 = 0x20000000, // Long bits 0x41c0000000000000L.
    TWO_31 = 0x80000000L, // Long bits 0x41e0000000000000L.
    TWO_49 = 0x2000000000000L, // Long bits 0x4300000000000000L.
    TWO_52 = 0x10000000000000L, // Long bits 0x4330000000000000L.
    TWO_54 = 0x40000000000000L, // Long bits 0x4350000000000000L.
    TWO_57 = 0x200000000000000L, // Long bits 0x4380000000000000L.
    TWO_60 = 0x1000000000000000L, // Long bits 0x43b0000000000000L.
    TWO_64 = 1.8446744073709552e19, // Long bits 0x43f0000000000000L.
    TWO_66 = 7.378697629483821e19, // Long bits 0x4410000000000000L.
    TWO_1023 = 8.98846567431158e307; // Long bits 0x7fe0000000000000L.

  /**
   * Super precision for 2/pi in 24-bit chunks, for use in
   * {@link #remPiOver2(double, double[])}.
   */
  

  /**
   * Super precision for pi/2 in 24-bit chunks, for use in
   * {@link #remPiOver2(double, double[])}.
   */
 
  /**
   * More constants related to pi, used in
   * {@link #remPiOver2(double, double[])} and elsewhere.
   */
  private static final double
    PI_L = 1.2246467991473532e-16, // Long bits 0x3ca1a62633145c07L.
    PIO2_1 = 1.5707963267341256, // Long bits 0x3ff921fb54400000L.
    PIO2_1L = 6.077100506506192e-11, // Long bits 0x3dd0b4611a626331L.
    PIO2_2 = 6.077100506303966e-11, // Long bits 0x3dd0b4611a600000L.
    PIO2_2L = 2.0222662487959506e-21, // Long bits 0x3ba3198a2e037073L.
    PIO2_3 = 2.0222662487111665e-21, // Long bits 0x3ba3198a2e000000L.
    PIO2_3L = 8.4784276603689e-32; // Long bits 0x397b839a252049c1L.

  /**
   * Natural log and square root constants, for calculation of
   * {@link #exp(double)}, {@link #log(double)} and
   * {@link #pow(double, double)}. CP is 2/(3*ln(2)).
   */
  private static final double
    SQRT_1_5 = 1.224744871391589, // Long bits 0x3ff3988e1409212eL.
    SQRT_2 = 1.4142135623730951, // Long bits 0x3ff6a09e667f3bcdL.
    SQRT_3 = 1.7320508075688772, // Long bits 0x3ffbb67ae8584caaL.
    EXP_LIMIT_H = 709.782712893384, // Long bits 0x40862e42fefa39efL.
    EXP_LIMIT_L = -745.1332191019411, // Long bits 0xc0874910d52d3051L.
    CP = 0.9617966939259756, // Long bits 0x3feec709dc3a03fdL.
    CP_H = 0.9617967009544373, // Long bits 0x3feec709e0000000L.
    CP_L = -7.028461650952758e-9, // Long bits 0xbe3e2fe0145b01f5L.
    LN2 = 0.6931471805599453, // Long bits 0x3fe62e42fefa39efL.
    LN2_H = 0.6931471803691238, // Long bits 0x3fe62e42fee00000L.
    LN2_L = 1.9082149292705877e-10, // Long bits 0x3dea39ef35793c76L.
    INV_LN2 = 1.4426950408889634, // Long bits 0x3ff71547652b82feL.
    INV_LN2_H = 1.4426950216293335, // Long bits 0x3ff7154760000000L.
    INV_LN2_L = 1.9259629911266175e-8; // Long bits 0x3e54ae0bf85ddf44L.

  /**
   * Constants for computing {@link #log(double)}.
   */
  private static final double
    LG1 = 0.6666666666666735, // Long bits 0x3fe5555555555593L.
    LG2 = 0.3999999999940942, // Long bits 0x3fd999999997fa04L.
    LG3 = 0.2857142874366239, // Long bits 0x3fd2492494229359L.
    LG4 = 0.22222198432149784, // Long bits 0x3fcc71c51d8e78afL.
    LG5 = 0.1818357216161805, // Long bits 0x3fc7466496cb03deL.
    LG6 = 0.15313837699209373, // Long bits 0x3fc39a09d078c69fL.
    LG7 = 0.14798198605116586; // Long bits 0x3fc2f112df3e5244L.

  /**
   * Constants for computing {@link #pow(double, double)}. L and P are
   * coefficients for series; OVT is -(1024-log2(ovfl+.5ulp)); and DP is ???.
   * The P coefficients also calculate {@link #exp(double)}.
   */
  private static final double
    L1 = 0.5999999999999946, // Long bits 0x3fe3333333333303L.
    L2 = 0.4285714285785502, // Long bits 0x3fdb6db6db6fabffL.
    L3 = 0.33333332981837743, // Long bits 0x3fd55555518f264dL.
    L4 = 0.272728123808534, // Long bits 0x3fd17460a91d4101L.
    L5 = 0.23066074577556175, // Long bits 0x3fcd864a93c9db65L.
    L6 = 0.20697501780033842, // Long bits 0x3fca7e284a454eefL.
    P1 = 0.16666666666666602, // Long bits 0x3fc555555555553eL.
    P2 = -2.7777777777015593e-3, // Long bits 0xbf66c16c16bebd93L.
    P3 = 6.613756321437934e-5, // Long bits 0x3f11566aaf25de2cL.
    P4 = -1.6533902205465252e-6, // Long bits 0xbebbbd41c5d26bf1L.
    P5 = 4.1381367970572385e-8, // Long bits 0x3e66376972bea4d0L.
    DP_H = 0.5849624872207642, // Long bits 0x3fe2b80340000000L.
    DP_L = 1.350039202129749e-8, // Long bits 0x3e4cfdeb43cfd006L.
    OVT = 8.008566259537294e-17; // Long bits 0x3c971547652b82feL.

  /**
   * Coefficients for computing {@link #sin(double)}.
   */
  private static final double
    S1 = -0.16666666666666632, // Long bits 0xbfc5555555555549L.
    S2 = 8.33333333332249e-3, // Long bits 0x3f8111111110f8a6L.
    S3 = -1.984126982985795e-4, // Long bits 0xbf2a01a019c161d5L.
    S4 = 2.7557313707070068e-6, // Long bits 0x3ec71de357b1fe7dL.
    S5 = -2.5050760253406863e-8, // Long bits 0xbe5ae5e68a2b9cebL.
    S6 = 1.58969099521155e-10; // Long bits 0x3de5d93a5acfd57cL.

  /**
   * Coefficients for computing {@link #cos(double)}.
   */
  private static final double
    C1 = 0.0416666666666666, // Long bits 0x3fa555555555554cL.
    C2 = -1.388888888887411e-3, // Long bits 0xbf56c16c16c15177L.
    C3 = 2.480158728947673e-5, // Long bits 0x3efa01a019cb1590L.
    C4 = -2.7557314351390663e-7, // Long bits 0xbe927e4f809c52adL.
    C5 = 2.087572321298175e-9, // Long bits 0x3e21ee9ebdb4b1c4L.
    C6 = -1.1359647557788195e-11; // Long bits 0xbda8fae9be8838d4L.

  /**
   * Coefficients for computing {@link #tan(double)}.
   */
  private static final double
    T0 = 0.3333333333333341, // Long bits 0x3fd5555555555563L.
    T1 = 0.13333333333320124, // Long bits 0x3fc111111110fe7aL.
    T2 = 0.05396825397622605, // Long bits 0x3faba1ba1bb341feL.
    T3 = 0.021869488294859542, // Long bits 0x3f9664f48406d637L.
    T4 = 8.8632398235993e-3, // Long bits 0x3f8226e3e96e8493L.
    T5 = 3.5920791075913124e-3, // Long bits 0x3f6d6d22c9560328L.
    T6 = 1.4562094543252903e-3, // Long bits 0x3f57dbc8fee08315L.
    T7 = 5.880412408202641e-4, // Long bits 0x3f4344d8f2f26501L.
    T8 = 2.464631348184699e-4, // Long bits 0x3f3026f71a8d1068L.
    T9 = 7.817944429395571e-5, // Long bits 0x3f147e88a03792a6L.
    T10 = 7.140724913826082e-5, // Long bits 0x3f12b80f32f0a7e9L.
    T11 = -1.8558637485527546e-5, // Long bits 0xbef375cbdb605373L.
    T12 = 2.590730518636337e-5; // Long bits 0x3efb2a7074bf7ad4L.

  /**
   * Coefficients for computing {@link #asin(double)} and
   * {@link #acos(double)}.
   */
  private static final double
    PS0 = 0.16666666666666666, // Long bits 0x3fc5555555555555L.
    PS1 = -0.3255658186224009, // Long bits 0xbfd4d61203eb6f7dL.
    PS2 = 0.20121253213486293, // Long bits 0x3fc9c1550e884455L.
    PS3 = -0.04005553450067941, // Long bits 0xbfa48228b5688f3bL.
    PS4 = 7.915349942898145e-4, // Long bits 0x3f49efe07501b288L.
    PS5 = 3.479331075960212e-5, // Long bits 0x3f023de10dfdf709L.
    QS1 = -2.403394911734414, // Long bits 0xc0033a271c8a2d4bL.
    QS2 = 2.0209457602335057, // Long bits 0x40002ae59c598ac8L.
    QS3 = -0.6882839716054533, // Long bits 0xbfe6066c1b8d0159L.
    QS4 = 0.07703815055590194; // Long bits 0x3fb3b8c5b12e9282L.

  /**
   * Coefficients for computing {@link #atan(double)}.
   */
  private static final double
    ATAN_0_5H = 0.4636476090008061, // Long bits 0x3fddac670561bb4fL.
    ATAN_0_5L = 2.2698777452961687e-17, // Long bits 0x3c7a2b7f222f65e2L.
    ATAN_1_5H = 0.982793723247329, // Long bits 0x3fef730bd281f69bL.
    ATAN_1_5L = 1.3903311031230998e-17, // Long bits 0x3c7007887af0cbbdL.
    AT0 = 0.3333333333333293, // Long bits 0x3fd555555555550dL.
    AT1 = -0.19999999999876483, // Long bits 0xbfc999999998ebc4L.
    AT2 = 0.14285714272503466, // Long bits 0x3fc24924920083ffL.
    AT3 = -0.11111110405462356, // Long bits 0xbfbc71c6fe231671L.
    AT4 = 0.09090887133436507, // Long bits 0x3fb745cdc54c206eL.
    AT5 = -0.0769187620504483, // Long bits 0xbfb3b0f2af749a6dL.
    AT6 = 0.06661073137387531, // Long bits 0x3fb10d66a0d03d51L.
    AT7 = -0.058335701337905735, // Long bits 0xbfadde2d52defd9aL.
    AT8 = 0.049768779946159324, // Long bits 0x3fa97b4b24760debL.
    AT9 = -0.036531572744216916, // Long bits 0xbfa2b4442c6a6c2fL.
    AT10 = 0.016285820115365782; // Long bits 0x3f90ad3ae322da11L.







  public static final double E = Math.E;
  public static final double PI = Math.PI;
  public static int abs(int i) { return Math.abs(i); }
  public static long abs(long l) { return Math.abs(l); }
  public static float abs(float f) { return Math.abs(f); }
  public static double abs(double d) { return Math.abs(d); }



  public static int min(int a, int b) {return Math.min(a, b); }
  public static long min(long a, long b) {return Math.min(a, b); }
  public static float min(float a, float b) {return Math.min(a, b); }
  public static double min(double a, double b) {return Math.min(a, b); }
  public static int max(int a, int b) { return Math.max(a, b); }
  public static long max(long a, long b) { return Math.max(a, b); }
  public static float max(float a, float b) { return Math.max(a, b); }
  public static double max(double a, double b) { return Math.max(a, b); }
  public static double sin(double a) { return Math.sin(a); }
  public static double cos(double a) { return Math.cos(a); }
  public static double tan(double a) { return Math.tan(a); }



  
  
  
  
  public static double sqrt(double x) { return Math.sqrt(x); }
  
  public static double ceil(double a) { return Math.ceil(a); }
  public static double floor(double a) { return Math.floor(a); }
  
  













   public static double acos(double x)
  {
    boolean negative = x < 0;
    if (negative)
      x = -x;
    if (! (x <= 1))
      return Double.NaN;
    if (x == 1)
      return negative ? PI : 0;
    if (x < 0.5)
      {
        if (x < 1 / TWO_57)
          return PI / 2;
        double z = x * x;
        double p = z * (PS0 + z * (PS1 + z * (PS2 + z * (PS3 + z
                                                         * (PS4 + z * PS5)))));
        double q = 1 + z * (QS1 + z * (QS2 + z * (QS3 + z * QS4)));
        double r = x - (PI_L / 2 - x * (p / q));
        return negative ? PI / 2 + r : PI / 2 - r;
      }
    if (negative) // x<=-0.5.
      {
        double z = (1 - x) * 0.5;
        double p = z * (PS0 + z * (PS1 + z * (PS2 + z * (PS3 + z
                                                         * (PS4 + z * PS5)))));
        double q = 1 + z * (QS1 + z * (QS2 + z * (QS3 + z * QS4)));
        double s = sqrt(z);
        double w = p / q * s - PI_L / 2;
        return PI - 2 * (s + w);
      }
    double z = (1 - x) * 0.5; // x>0.5.
    double s = sqrt(z);
    double df = (float) s;
    double c = (z - df * df) / (s + df);
    double p = z * (PS0 + z * (PS1 + z * (PS2 + z * (PS3 + z
                                                     * (PS4 + z * PS5)))));
    double q = 1 + z * (QS1 + z * (QS2 + z * (QS3 + z * QS4)));
    double w = p / q * s + c;
    return 2 * (df + w);
  }





 public static double asin(double x)
  {
    boolean negative = x < 0;
    if (negative)
      x = -x;
    if (! (x <= 1))
      return Double.NaN;
    if (x == 1)
      return negative ? -PI / 2 : PI / 2;
    if (x < 0.5)
      {
        if (x < 1 / TWO_27)
          return negative ? -x : x;
        double t = x * x;
        double p = t * (PS0 + t * (PS1 + t * (PS2 + t * (PS3 + t
                                                         * (PS4 + t * PS5)))));
        double q = 1 + t * (QS1 + t * (QS2 + t * (QS3 + t * QS4)));
        return negative ? -x - x * (p / q) : x + x * (p / q);
      }
    double w = 1 - x; // 1>|x|>=0.5.
    double t = w * 0.5;
    double p = t * (PS0 + t * (PS1 + t * (PS2 + t * (PS3 + t
                                                     * (PS4 + t * PS5)))));
    double q = 1 + t * (QS1 + t * (QS2 + t * (QS3 + t * QS4)));
    double s = sqrt(t);
    if (x >= 0.975)
      {
        w = p / q;
        t = PI / 2 - (2 * (s + s * w) - PI_L / 2);
      }
    else
      {
        w = (float) s;
        double c = (t - w * w) / (s + w);
        p = 2 * s * (p / q) - (PI_L / 2 - 2 * c);
        q = PI / 4 - 2 * w;
        t = PI / 4 - (p - q);
      }
    return negative ? -t : t;
  }





  public static double atan(double x)
  {
    double lo;
    double hi;
    boolean negative = x < 0;
    if (negative)
      x = -x;
    if (x >= TWO_66)
      return negative ? -PI / 2 : PI / 2;
    if (! (x >= 0.4375)) // |x|<7/16, or NaN.
      {
        if (! (x >= 1 / TWO_29)) // Small, or NaN.
          return negative ? -x : x;
        lo = hi = 0;
      }
    else if (x < 1.1875)
      {
        if (x < 0.6875) // 7/16<=|x|<11/16.
          {
            x = (2 * x - 1) / (2 + x);
            hi = ATAN_0_5H;
            lo = ATAN_0_5L;
          }
        else // 11/16<=|x|<19/16.
          {
            x = (x - 1) / (x + 1);
            hi = PI / 4;
            lo = PI_L / 4;
          }
      }
    else if (x < 2.4375) // 19/16<=|x|<39/16.
      {
        x = (x - 1.5) / (1 + 1.5 * x);
        hi = ATAN_1_5H;
        lo = ATAN_1_5L;
      }
    else // 39/16<=|x|<2**66.
      {
        x = -1 / x;
        hi = PI / 2;
        lo = PI_L / 2;
      }

    // Break sum from i=0 to 10 ATi*z**(i+1) into odd and even poly.
    double z = x * x;
    double w = z * z;
    double s1 = z * (AT0 + w * (AT2 + w * (AT4 + w * (AT6 + w
                                                      * (AT8 + w * AT10)))));
    double s2 = w * (AT1 + w * (AT3 + w * (AT5 + w * (AT7 + w * AT9))));
    if (hi == 0)
      return negative ? x * (s1 + s2) - x : x - x * (s1 + s2);
    z = hi - ((x * (s1 + s2) - lo) - x);
    return negative ? -z : z;
  }


 public static double atan2(double y, double x)
  {
    if (x != x || y != y)
      return Double.NaN;
    if (x == 1)
      return atan(y);
    if (x == Double.POSITIVE_INFINITY)
      {
        if (y == Double.POSITIVE_INFINITY)
          return PI / 4;
        if (y == Double.NEGATIVE_INFINITY)
          return -PI / 4;
        return 0 * y;
      }
    if (x == Double.NEGATIVE_INFINITY)
      {
        if (y == Double.POSITIVE_INFINITY)
          return 3 * PI / 4;
        if (y == Double.NEGATIVE_INFINITY)
          return -3 * PI / 4;
        return (1 / (0 * y) == Double.POSITIVE_INFINITY) ? PI : -PI;
      }
    if (y == 0)
      {
        if (1 / (0 * x) == Double.POSITIVE_INFINITY)
          return y;
        return (1 / y == Double.POSITIVE_INFINITY) ? PI : -PI;
      }
    if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY
        || x == 0)
      return y < 0 ? -PI / 2 : PI / 2;

    double z = abs(y / x); // Safe to do y/x.
    if (z > TWO_60)
      z = PI / 2 + 0.5 * PI_L;
    else if (x < 0 && z < 1 / TWO_60)
      z = 0;
    else
      z = atan(z);
    if (x > 0)
      return y > 0 ? z : -z;
    return y > 0 ? PI - (z - PI_L) : z - PI_L - PI;
  }



 public static double exp(double x)
  {
    if (x != x)
      return x;
    if (x > EXP_LIMIT_H)
      return Double.POSITIVE_INFINITY;
    if (x < EXP_LIMIT_L)
      return 0;

    // Argument reduction.
    double hi;
    double lo;
    int k;
    double t = abs(x);
    if (t > 0.5 * LN2)
      {
        if (t < 1.5 * LN2)
          {
            hi = t - LN2_H;
            lo = LN2_L;
            k = 1;
          }
        else
          {
            k = (int) (INV_LN2 * t + 0.5);
            hi = t - k * LN2_H;
            lo = k * LN2_L;
          }
        if (x < 0)
          {
            hi = -hi;
            lo = -lo;
            k = -k;
          }
        x = hi - lo;
      }
    else if (t < 1 / TWO_28)
      return 1;
    else
      lo = hi = k = 0;

    // Now x is in primary range.
    t = x * x;
    double c = x - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
    if (k == 0)
      return 1 - (x * c / (c - 2) - x);
    double y = 1 - (lo - x * c / (2 - c) - hi);
    return scale(y, k);
  }









 public static double log(double x)
  {
    if (x == 0)
      return Double.NEGATIVE_INFINITY;
    if (x < 0)
      return Double.NaN;
    if (! (x < Double.POSITIVE_INFINITY))
      return x;

    // Normalize x.
    long bits = Double.doubleToLongBits(x);
    int exp = (int) (bits >> 52);
    if (exp == 0) // Subnormal x.
      {
        x *= TWO_54;
        bits = Double.doubleToLongBits(x);
        exp = (int) (bits >> 52) - 54;
      }
    exp -= 1023; // Unbias exponent.
    bits = (bits & 0x000fffffffffffffL) | 0x3ff0000000000000L;
    x = Double.longBitsToDouble(bits);
    if (x >= SQRT_2)
      {
        x *= 0.5;
        exp++;
      }
    x--;
    if (abs(x) < 1 / TWO_20)
      {
        if (x == 0)
          return exp * LN2_H + exp * LN2_L;
        double r = x * x * (0.5 - 1 / 3.0 * x);
        if (exp == 0)
          return x - r;
        return exp * LN2_H - ((r - exp * LN2_L) - x);
      }
    double s = x / (2 + x);
    double z = s * s;
    double w = z * z;
    double t1 = w * (LG2 + w * (LG4 + w * LG6));
    double t2 = z * (LG1 + w * (LG3 + w * (LG5 + w * LG7)));
    double r = t2 + t1;
    if (bits >= 0x3ff6174a00000000L && bits < 0x3ff6b85200000000L)
      {
        double h = 0.5 * x * x; // Need more accuracy for x near sqrt(2).
        if (exp == 0)
          return x - (h - s * (h + r));
        return exp * LN2_H - ((h - (s * (h + r) + exp * LN2_L)) - x);
      }
    if (exp == 0)
      return x - s * (x - r);
    return exp * LN2_H - ((s * (x - r) - exp * LN2_L) - x);
  }
 





  public static double pow(double x, double y)
  {
    // Special cases first.
    if (y == 0)
      return 1;
    if (y == 1)
      return x;
    if (y == -1)
      return 1 / x;
    if (x != x || y != y)
      return Double.NaN;

    // When x < 0, yisint tells if y is not an integer (0), even(1),
    // or odd (2).
    int yisint = 0;
    if (x < 0 && floor(y) == y)
      yisint = (y % 2 == 0) ? 2 : 1;
    double ax = abs(x);
    double ay = abs(y);

    // More special cases, of y.
    if (ay == Double.POSITIVE_INFINITY)
      {
        if (ax == 1)
          return Double.NaN;
        if (ax > 1)
          return y > 0 ? y : 0;
        return y < 0 ? -y : 0;
      }
    if (y == 2)
      return x * x;
    if (y == 0.5)
      return sqrt(x);

    // More special cases, of x.
    if (x == 0 || ax == Double.POSITIVE_INFINITY || ax == 1)
      {
        if (y < 0)
          ax = 1 / ax;
        if (x < 0)
          {
            if (x == -1 && yisint == 0)
              ax = Double.NaN;
            else if (yisint == 1)
              ax = -ax;
          }
        return ax;
      }
    if (x < 0 && yisint == 0)
      return Double.NaN;

    // Now we can start!
    double t;
    double t1;
    double t2;
    double u;
    double v;
    double w;
    if (ay > TWO_31)
      {
        if (ay > TWO_64) // Automatic over/underflow.
          return ((ax < 1) ? y < 0 : y > 0) ? Double.POSITIVE_INFINITY : 0;
        // Over/underflow if x is not close to one.
        if (ax < 0.9999995231628418)
          return y < 0 ? Double.POSITIVE_INFINITY : 0;
        if (ax >= 1.0000009536743164)
          return y > 0 ? Double.POSITIVE_INFINITY : 0;
        // Now |1-x| is <= 2**-20, sufficient to compute
        // log(x) by x-x^2/2+x^3/3-x^4/4.
        t = x - 1;
        w = t * t * (0.5 - t * (1 / 3.0 - t * 0.25));
        u = INV_LN2_H * t;
        v = t * INV_LN2_L - w * INV_LN2;
        t1 = (float) (u + v);
        t2 = v - (t1 - u);
      }
    else
    {
      long bits = Double.doubleToLongBits(ax);
      int exp = (int) (bits >> 52);
      if (exp == 0) // Subnormal x.
        {
          ax *= TWO_54;
          bits = Double.doubleToLongBits(ax);
          exp = (int) (bits >> 52) - 54;
        }
      exp -= 1023; // Unbias exponent.
      ax = Double.longBitsToDouble((bits & 0x000fffffffffffffL)
                                   | 0x3ff0000000000000L);
      boolean k;
      if (ax < SQRT_1_5)  // |x|<sqrt(3/2).
        k = false;
      else if (ax < SQRT_3) // |x|<sqrt(3).
        k = true;
      else
        {
          k = false;
          ax *= 0.5;
          exp++;
        }

      // Compute s = s_h+s_l = (x-1)/(x+1) or (x-1.5)/(x+1.5).
      u = ax - (k ? 1.5 : 1);
      v = 1 / (ax + (k ? 1.5 : 1));
      double s = u * v;
      double s_h = (float) s;
      double t_h = (float) (ax + (k ? 1.5 : 1));
      double t_l = ax - (t_h - (k ? 1.5 : 1));
      double s_l = v * ((u - s_h * t_h) - s_h * t_l);
      // Compute log(ax).
      double s2 = s * s;
      double r = s_l * (s_h + s) + s2 * s2
        * (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * (L5 + s2 * L6)))));
      s2 = s_h * s_h;
      t_h = (float) (3.0 + s2 + r);
      t_l = r - (t_h - 3.0 - s2);
      // u+v = s*(1+...).
      u = s_h * t_h;
      v = s_l * t_h + t_l * s;
      // 2/(3log2)*(s+...).
      double p_h = (float) (u + v);
      double p_l = v - (p_h - u);
      double z_h = CP_H * p_h;
      double z_l = CP_L * p_h + p_l * CP + (k ? DP_L : 0);
      // log2(ax) = (s+..)*2/(3*log2) = exp + dp_h + z_h + z_l.
      t = exp;
      t1 = (float) (z_h + z_l + (k ? DP_H : 0) + t);
      t2 = z_l - (t1 - t - (k ? DP_H : 0) - z_h);
    }

    // Split up y into y1+y2 and compute (y1+y2)*(t1+t2).
    boolean negative = x < 0 && yisint == 1;
    double y1 = (float) y;
    double p_l = (y - y1) * t1 + y * t2;
    double p_h = y1 * t1;
    double z = p_l + p_h;
    if (z >= 1024) // Detect overflow.
      {
        if (z > 1024 || p_l + OVT > z - p_h)
          return negative ? Double.NEGATIVE_INFINITY
            : Double.POSITIVE_INFINITY;
      }
    else if (z <= -1075) // Detect underflow.
      {
        if (z < -1075 || p_l <= z - p_h)
          return negative ? -0.0 : 0;
      }

    // Compute 2**(p_h+p_l).
    int n = round((float) z);
    p_h -= n;
    t = (float) (p_l + p_h);
    u = t * LN2_H;
    v = (p_l - (t - p_h)) * LN2 + t * LN2_L;
    z = u + v;
    w = v - (z - u);
    t = z * z;
    t1 = z - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
    double r = (z * t1) / (t1 - 2) - (w + z * w);
    z = scale(1 - (r - z), n);
    return negative ? -z : z;
  }

 

public static int round(float f)
  {
    return (int) floor(f + 0.5f);
  }


private static double scale(double x, int n)
  {
    if (false && abs(n) >= 2048);
     // throw new InternalError("Assertion failure");
    if (x == 0 || x == Double.NEGATIVE_INFINITY
        || ! (x < Double.POSITIVE_INFINITY) || n == 0)
      return x;
    long bits = Double.doubleToLongBits(x);
    int exp = (int) (bits >> 52) & 0x7ff;
    if (exp == 0) // Subnormal x.
      {
        x *= TWO_54;
        exp = ((int) (Double.doubleToLongBits(x) >> 52) & 0x7ff) - 54;
      }
    exp += n;
    if (exp > 0x7fe) // Overflow.
      return Double.POSITIVE_INFINITY * x;
    if (exp > 0) // Normal.
      return Double.longBitsToDouble((bits & 0x800fffffffffffffL)
                                     | ((long) exp << 52));
    if (exp <= -54)
      return 0 * x; // Underflow.
    exp += 54; // Subnormal result.
    x = Double.longBitsToDouble((bits & 0x800fffffffffffffL)
                                | ((long) exp << 52));
    return x * (1 / TWO_54);
  }

#endif /* USE_MATH */
#endif /* STRICTMATH */
}
