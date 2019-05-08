// Calculates one cycle of tithis
//
// Be sure, your swisseph sources had been "precompiled" with the -DTRANSITS switch.
//
import swisseph.*;

public class Tithis {

	static final boolean calcTopocentric = false;

	static final String[] tithi_names = {
			"Prathama",
			"Dwitiya",
			"Tritiya",
			"Chaturthi",
			"Panchami",
			"Shashti",
			"Saptami",
			"Ashtami",
			"Navami",
			"Dasami",
			"Ekadasi",
			"Dvadasi",
			"Trayodasi",
			"Chaturdashi",
			"Purnima",
		};


	public static void main(String[] p) {
		SwissEph sw = new SwissEph("./ephe");
		if (calcTopocentric) {
			sw.swe_set_topo(73.827298, 15.500439, 0);
		}

		SweDate sd = new SweDate(2013, 9, 30, 0);

		int flags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_TRANSIT_LONGITUDE;
		if (calcTopocentric) {
			flags |= SweConst.SEFLG_TOPOCTR;
		}
		boolean backwards = false;

		int tithi = 0;
		double tithi_deg = 0;

		// Calculates positions of a planet relative to another planet
		TransitCalculator tc = new TCPlanetPlanet(
				sw,
				SweConst.SE_MOON,
				SweConst.SE_SUN,
				flags,
				tithi_deg);

		while(tithi_deg < 360.) {
			double nextTransitUT = sw.getTransitUT(tc, sd.getJulDay(), backwards);

			String name = tithi_names[tithi%15];
			if (tithi == 14 || tithi == 29) {
				name = (tithi < 15 ? "Purnima" : "Amavasya");
			} else {
				name += (tithi < 15 ? " shukla" : " krishna") + " paksha";
			}

			SweDate sout = new SweDate(nextTransitUT);
			int mon = sout.getMonth();
			int day = sout.getDay();
			int year = sout.getYear();
			double h = sout.getHour();
			h += 0.5/3600.;
			int hour = (int)h;
			int min = (int)((h-hour)*60);
			int sec = (int)(((h-hour)*60-min)*60);

			System.out.printf("%26s: %2d/%02d/%04d %2d:%02d:%02dh, JD: %.10f\n",
					name, day, mon, year, hour, min, sec, nextTransitUT);

			tithi++;
			tithi_deg += 360./30.;

			tc.setOffset(tithi_deg);
		}
	}
}
