import java.util.Calendar;
import java.util.Date;

import swisseph.*;

public class Yoga {
    private static final int SID_METHOD = SweConst.SE_SIDM_LAHIRI;
    public static String getYoga(Date when, double longWhere, double lattWhere)
    {
	String retVal = "";
	SwissEph sw = new SwissEph("E\\:\\tools\\Jagannatha Hora\\jhcore\\ephe");
	Calendar cal = Calendar.getInstance();
	cal.setTime(when);
	SweDate sd = new SweDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), ((double)(when.getTime()%3600)) / 3600);
	int flags = SweConst.SEFLG_SWIEPH | // fastest method, requires data files
		SweConst.SEFLG_SIDEREAL | // sidereal zodiac
		SweConst.SEFLG_NONUT | // will be set automatically for sidereal
					// calculations, if not set here
		SweConst.SEFLG_SPEED; // to determine retrograde vs. direct
	double[] xp = new double[6];
	StringBuffer serr = new StringBuffer();
	// Set sidereal mode:
	sw.swe_set_sid_mode(SID_METHOD, 0, 0);
	String planetName = sw.swe_get_planet_name(SweConst.SE_SUN);
	int ret = sw.swe_calc_ut(sd.getJulDay(), SweConst.SE_SUN, flags, xp, serr);
	if (ret != flags) {
	    if (serr.length() > 0) {
		System.err.println("Warning: " + serr);
	    } else {
		System.err.println(String.format("Warning, different flags used (0x%x)", ret));
	    }
	}

	System.out.println(VedicHouses.toDMS(xp[0]));
	return retVal;
    }

    
}
