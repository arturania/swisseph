import swisseph.*;

// When is Aries rising on May 8, 2014 in India
// 80.1 E, 13.08 N in Krishnamurti ayanamsa system
//
// - "Aries rising" means, ascendant is at 0 deg. longitude
// - This is a transit calculation.
// - Transit calculations over AC or similar are done with
//   the TCHouses TransitCalculator
// - Times are UTC always
public class AriesRising {

	private static final double tzOffset = 5.5;

	public static void main(String[] p) {

 		SwissEph sw = new SwissEph("./ephe");

		// 0:00h on May 8, 2014 Indian Standard Time = UTC + 5.5
		// So, use a starting date of 2014-05-08, 0:00h - 5.5h:
		SweDate jDate = new SweDate(2014, 5, 8, 0-tzOffset, true);           
		System.out.println("Start searching on    " + printDate(jDate.getJulDay() + tzOffset / 24.) + " IST");
		jDate.makeValidDate();
		System.out.println("                     (" + printDate(jDate.getJulDay()) + " UTC)");

		// Sidereal Krishnamurti mode:
		sw.swe_set_sid_mode(SweConst.SE_SIDM_KRISHNAMURTI, 0, 0);
		int flags = SweConst.SEFLG_SIDEREAL | SweConst.SEFLG_TRANSIT_LONGITUDE;
		boolean backwards = false;

		// House system is irrelevant, as long as the ascendant
		// calculation matches your requirements
		TransitCalculator tc = new TCHouses(
			sw,
			SweConst.SE_ASC,
			SweConst.SE_HSYS_PLACIDUS,
			80.1, 13.08,
			flags,
			0);	// 0 degreee aries

		double nextTransitUT = sw.getTransitUT(tc, jDate.getJulDay(), backwards);
		System.out.println("Next rise of aries on " + printDate(nextTransitUT + tzOffset / 24.0) + " IST");
		System.out.println("                     (" + printDate(nextTransitUT) + " UTC)");
	}

	static String printDate(double jd) {
		SweDate sd = new SweDate(jd);
		double time = sd.getHour();
		int hour = (int)time;
		time = 60 * (time - hour);
		int min = (int)time;
		double sec = 60 * (time - min);

		return String.format("%4s-%02d-%02d %2d:%02d:%05.2f", sd.getYear(), sd.getMonth(), sd.getDay(), hour, min, sec);
	}
}
