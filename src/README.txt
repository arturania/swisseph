README.txt	12-jul-1998


Documention for the Astrodienst Placalc Package
===============================================

/***** source files *****/

The  package consists of the following files:

ourdef.h	general header file

astrolib.h	another general header file
		Note: not everything declared as extern in this header file
		is actually included in the Placalc package; only those
		items really needed by Placalc are included.

plalcalc.h	specific header file, defines functions exported
		by placalc module

placalc.c	actual code for calculations

d2l.c		double to long conversion with centisec rounding

deltat.c	deltat function

housasp.h	header for housasp.c

housasp.c	house calculation and very simple aspect calculation;
		the house routine includes placidus, Koch, Regio, Campanus,
		Equal and vehlow

helconst.c	helicentric constants used by placalc

houstest.c	a simple wrapper to demonstrate house calculation

julday.c	conversion to julian day numbers

makefile	A makefile for Borland-C make to create platest.exe and
		houstest.exe

outdeg.c	output functions for degrees etc

plalib.c	a few funtions usually required astrologers

platest.c	example and test program for placalc

revjul.c	conversion from julian day numbers

/**** Data files *****/

Can be downloaded from ftp://www.astro.com/pub/placalc

LRZ_nnn		stored ephemeris files for Jupiter..Pluto. 
		LRZ_20 contains jd 2'000'000 to 2'100'999
		etc, i.e. each file contains 100'000 days in 75000 bytes.
		The data contain longitude, radius vector and latitude
		in 80 day steps. Use of high order interpolation
		allows reconstruction of precise positions (0.01") from
		this ephemeris.

LRZ5_nnn	contains positions of the 5 outer planets for 5000 BC
		to 5000 AD.

CHI_nnn		stored Chiron ephemeris files, 100'000 days in 15000 bytes.
		The complete set consists of 31 LRZ and 21 CHI files for
		3'1000'000 days or approx. 8490 years, on 2.8 MBytes.
		Use files chi_19 .. chi_27 only with positions of Chiron between
		400 AD and 2952 AD.
		The Chiron data before 720 AD should be ignored.
		In 720 AD there is a close encounter between Saturn and Chiron
		and the precise orbit before this date cannot be computed
		precisely, as minimal changes in the orbital elements produce
		very different positions. The behaviour of Chiron before 720 AD must 
		be considered as chaotic.

CPJV_nnn	 contain positions of the 4 asteroids Ceres, Pallas, Juno
		and Vesta between 2795 BC and 2952 AD, with old stepsize of 80 days.

CPJ2_nnn	stored ephemeris files for Ceres, Pallas, Juno, Vesta
		between 2795 BC and 2952 AD. These files have 20 day steps.

		For the asteroid files, the internal ephemeris step has been
		shortened from 80 to 20 days. A step of 80 days is to long for
		correct interpolation of the asteroids and had interpolation
		errors of up to 15'. The shorter interpolation step reproduces
		the Swiss Ephemeris positions within 1 arc second.

		The Placalc source code needs to be adapted for the shorter
		asteroid ephemeris step.
		The required changes are described in file placast.txt.

		The files cpjv_ contain the old step size of 80 days. They can give
		large interpolation errors as mentioned above, but are compatible
		with Astrolog 5.30 and earlier versions.


Installation on you DOS system
==============================
create a directory \PAIR\EPHE on your harddisk
and copy all the LRZ_ and CHI_ files into it.

Create a subdirectory PLAEXP in your Borland-C environment,
and copy all other files into it.
The compiled PLATEST.EXE should run already.

Start Borland C and use the project file PLATEST.PRJ to create PLATEST.
We have tested our version only with the medium memory model on DOS
machines; we do not know how it behaves with other DOS versions.

Steps for compilation:
======================

Use the makefile for Borland-C to make platest.exe and all *.obj files.

With Borland-C, you can also use the provided platest.prj project file.

Translation from C to other languages
=====================================
Should not be done, learn C!

Integration into other programs
===============================
As you can see in the example platest.c, the placalc package
can easily be integrated into other programs. The main
source of difficulties is the use of globals in plalcalc.
We have used globals only very little, and may eliminate it
completely in future versions. 

nut (nutation) is set by helup() and used only by calc().
ekl and meanekl is set by helup() and used by nobody; You will need
	to use ekl() for ecliptic to equatorial conversions, for
	sidereal time and house calculations.

The global data constants declared by helconst.c are accessed
only by helup() and hel(). They could well be put into a special
structure and thus be handled to the users.


Zollikon/Zurich   12 July 1998    Dr. Alois Treindl
