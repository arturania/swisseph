# Before `make install' is performed this script should be runnable with
# `make test'. After `make install' it should work as `perl SwissEph.t'


use Test::More tests => 232;

BEGIN { use_ok("SwissEph") };

#------------------------------------------------------------------------
# Sun for 2451544.5 ET : Longitude should be 279.858461
#------------------------------------------------------------------------
my $ref;
my $i;
my @xx;
my $serr="";

#------------------------------------------------------------------------
# Coordinate transformation
#------------------------------------------------------------------------
# ecliptic -> equator
my @rade = SwissEph::swe_cotrans([80, 5, 0], -23);
is(round_6($rade[0]), 78.741799, "swe_cotrans([80, 5, 0], -23) -> ra");
is(round_6($rade[1]), 27.616943, "swe_cotrans([80, 5, 0], -23) -> de");
# with speed
@rade = SwissEph::swe_cotrans_sp([80, 5, 0, 1, 0, 0], -23);
is(round_6($rade[0]), 78.741799, "swe_cotrans([80, 5, 0], -23) -> ra");
is(round_6($rade[1]), 27.616943, "swe_cotrans([80, 5, 0], -23) -> de");
is(round_6($rade[3]), 1.120988, "swe_cotrans([80, 5, 0], -23) -> ra_speed");
is(round_6($rade[4]), 0.076283, "swe_cotrans([80, 5, 0], -23) -> de_speed");
 
#------------------------------------------------------------------------
# Function Delta T - values may change, but the seconds for 2000-1-1 are sure 
#------------------------------------------------------------------------
is( round_6(SwissEph::swe_deltat( 2451545 )* 86400), 63.83, "swe_deltat(1 jan 2000)");

#------------------------------------------------------------------------
# Sidereal Time
#------------------------------------------------------------------------
is(round_6( SwissEph::swe_sidtime( 2451544.5 )), 6.664283, "swe_sidtime(1 jan 2000)");
is(round_6( SwissEph::swe_sidtime0( 2451544.5, 23.5, 0)), 6.664520, "swe_sidtime0(1 jan 2000,23.5,0)");

$ref = SwissEph::swe_time_equ(2415020.5);
is($ref->{retval}, 0, "swe_time_equ(1 jan 1900)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_time_equ(1 jan 1900)->serr = $ref->{serr}\n";
}
is(round_6($ref->{time_equ}), -0.002381, "swe_time_equ(1 jan 1900)->time_equ");

#------------------------------------------------------------------------
# Calendar conversion
#------------------------------------------------------------------------
is( SwissEph::swe_julday(2000,1,1,0.), 2451544.5, "swe_julday() 1 jan 2000");
is( SwissEph::swe_julday(2000,1,1,0.,1), 2451544.5, "swe_julday() 1 jan 2000, explicit greg flag");
is( SwissEph::swe_julday(2000,1,1,0.,0), 2451557.5, "swe_julday() 1 jan 2000, explicit jul flag");
$ref = SwissEph::swe_revjul(2436723.5888888889,1);
is($ref->{iyar}, 1959, "swe_revjul(2436723.5888888889), year");
is($ref->{imon}, 6, "swe_revjul(2436723.5888888889), month");
is($ref->{iday}, 4, "swe_revjul(2436723.5888888889), day");
is($ref->{ihou}, 2, "swe_revjul(2436723.5888888889), hour");
is($ref->{imin}, 8, "swe_revjul(2436723.5888888889), minute");
is($ref->{isec}, 0, "swe_revjul(2436723.5888888889), second");
is(round_6($ref->{dhou}), 2.133333, "swe_revjul(2436723.5888888889), hour/decimal");

#------------------------------------------------------------------------
# Wrapper for swe_houses
#------------------------------------------------------------------------
$ref = SwissEph::swe_houses( 2451544.5, 52., 7., "P");
is( round_6( $ref->{cusps}->[1] ),  191.882508, "swe_houses()->{1}");
is( round_6( $ref->{cusps}->[2] ),  217.032709, "swe_houses()->{2}");
is( round_6( $ref->{cusps}->[3] ),  248.483717, "swe_houses()->{3}");
is( round_6( $ref->{cusps}->[4] ),  285.635864, "swe_houses()->{4}");
is( round_6( $ref->{cusps}->[5] ),  321.015093, "swe_houses()->{5}");
is( round_6( $ref->{cusps}->[6] ),  349.63744, "swe_houses()->{6}");
is( round_6( $ref->{cusps}->[7] ),   11.882508, "swe_houses()->{7}");
is( round_6( $ref->{cusps}->[8] ),   37.032709, "swe_houses()->{8}");
is( round_6( $ref->{cusps}->[9] ),   68.483717, "swe_houses()->{9}");
is( round_6( $ref->{cusps}->[10] ), 105.635864, "swe_houses()->{10}");
is( round_6( $ref->{cusps}->[11] ), 141.015093, "swe_houses()->{11}");
is( round_6( $ref->{cusps}->[12] ), 169.63744, "swe_houses()->{12}");
is( round_6( $ref->{asc} ), 191.882508, "swe_houses()->{asc}");
is( round_6( $ref->{mc} ), 105.635864, "swe_houses()->{mc}");
is( round_6( $ref->{armc} ), 106.964244, "swe_houses()->{armc}");
is( round_6( $ref->{vertex} ), 27.237775, "swe_houses()->{vertex}");
is( round_6( $ref->{equasc} ), 198.39095, "swe_houses()->{equasc}");
is( round_6( $ref->{coasc1} ), 218.373981, "swe_houses()->{coasc1}");
is( round_6( $ref->{coasc2} ), 193.79516, "swe_houses()->{coasc2}");
is( round_6( $ref->{polasc} ), 38.373981, "swe_houses()->{polasc}");

$ref = SwissEph::swe_houses_armc( 106.96424825, 52., 23.4376796111111, "P");
is( round_6( $ref->{cusps}->[1] ),  191.882511, "swe_houses_armc()->{1}");
is( round_6( $ref->{cusps}->[2] ),  217.032712, "swe_houses_armc()->{2}");
is( round_6( $ref->{cusps}->[3] ),  248.483721, "swe_houses_armc()->{3}");
is( round_6( $ref->{cusps}->[4] ),  285.635867, "swe_houses_armc()->{4}");
is( round_6( $ref->{cusps}->[5] ),  321.015097, "swe_houses_armc()->{5}");
is( round_6( $ref->{cusps}->[6] ),  349.637443, "swe_houses_armc()->{6}");
is( round_6( $ref->{cusps}->[7] ),   11.882511, "swe_houses_armc()->{7}");
is( round_6( $ref->{cusps}->[8] ),   37.032712, "swe_houses_armc()->{8}");
is( round_6( $ref->{cusps}->[9] ),   68.483721, "swe_houses_armc()->{9}");
is( round_6( $ref->{cusps}->[10] ), 105.635867, "swe_houses_armc()->{10}");
is( round_6( $ref->{cusps}->[11] ), 141.015097, "swe_houses_armc()->{11}");
is( round_6( $ref->{cusps}->[12] ), 169.637443, "swe_houses_armc()->{12}");
is( round_6( $ref->{asc} ), 191.882511, "swe_houses_armc()->{asc}");
is( round_6( $ref->{mc} ), 105.635867, "swe_houses_armc()->{mc}");
is( round_6( $ref->{armc} ), 106.964248, "swe_houses_armc()->{armc}");
is( round_6( $ref->{vertex} ), 27.237781, "swe_houses_armc()->{vertex}");
is( round_6( $ref->{equasc} ), 198.390954, "swe_houses_armc()->{equasc}");
is( round_6( $ref->{coasc1} ), 218.373989, "swe_houses_armc()->{coasc1}");
is( round_6( $ref->{coasc2} ), 193.795163, "swe_houses_armc()->{coasc2}");
is( round_6( $ref->{polasc} ), 38.373989, "swe_houses_armc()->{polasc}");

$ref = SwissEph::swe_houses_ex( 2451544.5, 0, 52., 7., "P");
is( round_6( $ref->{cusps}->[1] ),  191.882508, "swe_houses_ex()->{1}");
is( round_6( $ref->{cusps}->[2] ),  217.032709, "swe_houses_ex()->{2}");
is( round_6( $ref->{cusps}->[3] ),  248.483717, "swe_houses_ex()->{3}");
is( round_6( $ref->{cusps}->[4] ),  285.635864, "swe_houses_ex()->{4}");
is( round_6( $ref->{cusps}->[5] ),  321.015093, "swe_houses_ex()->{5}");
is( round_6( $ref->{cusps}->[6] ),  349.63744, "swe_houses_ex()->{6}");
is( round_6( $ref->{cusps}->[7] ),   11.882508, "swe_houses_ex()->{7}");
is( round_6( $ref->{cusps}->[8] ),   37.032709, "swe_houses_ex()->{8}");
is( round_6( $ref->{cusps}->[9] ),   68.483717, "swe_houses_ex()->{9}");
is( round_6( $ref->{cusps}->[10] ), 105.635864, "swe_houses_ex()->{10}");
is( round_6( $ref->{cusps}->[11] ), 141.015093, "swe_houses_ex()->{11}");
is( round_6( $ref->{cusps}->[12] ), 169.63744, "swe_houses_ex()->{12}");
is( round_6( $ref->{asc} ), 191.882508, "swe_houses_ex()->{asc}");
is( round_6( $ref->{mc} ), 105.635864, "swe_houses_ex()->{mc}");
is( round_6( $ref->{armc} ), 106.964244, "swe_houses_ex()->{armc}");
is( round_6( $ref->{vertex} ), 27.237775, "swe_houses_ex()->{vertex}");
is( round_6( $ref->{equasc} ), 198.39095, "swe_houses_ex()->{equasc}");
is( round_6( $ref->{coasc1} ), 218.373981, "swe_houses_ex()->{coasc1}");
is( round_6( $ref->{coasc2} ), 193.79516, "swe_houses_ex()->{coasc2}");
is( round_6( $ref->{polasc} ), 38.373981, "swe_houses_ex()->{polasc}");

$ref = SwissEph::swe_house_pos(290,47,23.5,"P",72,0);
is($ref->{retval}, 0, "swe_house_pos()->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_house_pos()->serr = $ref->{serr}\n";
}
is($ref->{ihno}, 2, "swe_house_pos()->ihno");
is(round_6($ref->{dhpos}), 2.145890, "swe_house_pos()->dhpos");
is(round_6($ref->{dhpos_deg}), 34.376705, "swe_house_pos()->dhpos_deg");

$ref = SwissEph::swe_gauquelin_sector(2436723.5888888889,SwissEph::SE_MOON,"",0,0,[8.6,47.35,400],1013,15);
is($ref->{retval}, 0, "swe_gauquelin_sector()->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_gauquelin_sector(1 jan 1900)->serr = $ref->{serr}\n";
}
is(round_6($ref->{dsector}), 36.1724, "swe_gauquelin_sector()->dsector");

#------------------------------------------------------------------------
# Wrapper for swe_calc
#------------------------------------------------------------------------

$ref = SwissEph::swe_calc(2415020.5, 3, 260);
is($ref->{retval}, 260, "swe_calc(1900, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_calc(1900, Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{xx}->[0]), 306.374479, "swe_calc(1900, Venus)->xx[0]");
is(round_6($ref->{xx}->[1]), -1.683096, "swe_calc(1900, Venus)->xx[1]");
is(round_6($ref->{xx}->[2]), 1.464596, "swe_calc(1900, Venus)->xx[2]");
is(round_6($ref->{xx}->[3]), 1.243507, "swe_calc(1900, Venus)->xx[3]");

$ref = SwissEph::swe_calc_ut(2415020.5-SwissEph::swe_deltat(2415020.5), 3, 260);
is($ref->{retval}, 260, "swe_calc_ut(1900, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_calc_ut(1900, Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{xx}->[0]), 306.374479, "swe_calc_ut(1900, Venus)->xx[0]");
is(round_6($ref->{xx}->[1]), -1.683096, "swe_calc_ut(1900, Venus)->xx[1]");
is(round_6($ref->{xx}->[2]), 1.464596, "swe_calc_ut(1900, Venus)->xx[2]");
is(round_6($ref->{xx}->[3]), 1.243507, "swe_calc_ut(1900, Venus)->xx[3]");

#------------------------------------------------------------------------
# Wrapper for swe_fixstar
#------------------------------------------------------------------------

$ref = SwissEph::swe_fixstar("alcyone", 2415020.5, 4);
is($ref->{retval}, 4, "swe_fixstar(1900, Alcyone)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_fixstar(1900, Alcyone)->serr = $ref->{serr}\n";
}
is($ref->{starname}, "Alcyone,etTau", "swe_fixstar(1900, Alcyone)->starname");
is(round_6($ref->{xx}->[0]), 58.605145, "swe_fixstar(1900, Alcyone)->xx[0]");
is(round_6($ref->{xx}->[2]), 1, "swe_fixstar(1900, Alcyone)->xx[2]");

$ref = SwissEph::swe_fixstar_ut("alcyone", 2415020.5-SwissEph::swe_deltat(2415020.5), 4);
is($ref->{retval}, 4, "swe_fixstar_ut(1900, Alcyone)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_fixstar_ut(1900, Alcyone)->serr = $ref->{serr}\n";
}
is($ref->{starname}, "Alcyone,etTau", "swe_fixstar_ut(1900, Alcyone)->starname");
is(round_6($ref->{xx}->[0]), 58.605145, "swe_fixstar_ut(1900, Alcyone)->xx[0]");
is(round_6($ref->{xx}->[2]), 1, "swe_fixstar_ut(1900, Alcyone)->xx[2]");

$ref = SwissEph::swe_fixstar_mag("alcyone");
is($ref->{retval}, 0, "swe_fixstar_mag(Alcyone)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_fixstar_mag(Alcyone)->serr = $ref->{serr}\n";
}
is($ref->{starname}, "Alcyone,etTau", "swe_fixstar_mag(Alcyone)->starname");
is(round_6($ref->{dmag}), 2.873, "swe_fixstar_mag(Alcyone)->dmag");

$ref = SwissEph::swe_pheno(2415020.5, 3, 4);
is($ref->{retval}, 0, "swe_pheno(1900, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_pheno(1900, Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{phase_angle}), 36.744873, "swe_pheno(1900, Venus)->phase_angle");
is(round_6($ref->{phase}), 0.900654, "swe_pheno(1900, Venus)->phase");
is(round_6($ref->{elongation}), 26.271244, "swe_pheno(1900, Venus)->elongation");
is(round_6($ref->{disc_diameter}), 0.003165, "swe_pheno(1900, Venus)->disc_diameter");
is(round_6($ref->{magnitude}), -3.93892, "swe_pheno(1900, Venus)->magnitude");
is(round_6($ref->{hor_parallax}), 0, "swe_pheno(1900, Venus)->hor_parallax");
is(round_6($ref->{attr}->[0]), 36.744873, "swe_pheno(1900, Venus)->attr[0]");

$ref = SwissEph::swe_pheno_ut(2415020.5 - SwissEph::swe_deltat(2415020.5), 3, 4);
is($ref->{retval}, 0, "swe_pheno_ut(1900, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_pheno_ut(1900, Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{phase_angle}), 36.744873, "swe_pheno_ut(1900, Venus)->phase_angle");
is(round_6($ref->{phase}), 0.900654, "swe_pheno_ut(1900, Venus)->phase");
is(round_6($ref->{elongation}), 26.271244, "swe_pheno_ut(1900, Venus)->elongation");
is(round_6($ref->{disc_diameter}), 0.003165, "swe_pheno_ut(1900, Venus)->disc_diameter");
is(round_6($ref->{magnitude}), -3.93892, "swe_pheno_ut(1900, Venus)->magnitude");
is(round_6($ref->{hor_parallax}), 0, "swe_pheno_ut(1900, Venus)->hor_parallax");
is(round_6($ref->{attr}->[0]), 36.744873, "swe_pheno_ut(1900, Venus)->attr[0]");

#------------------------------------------------------------------------
# ayanamsha
#------------------------------------------------------------------------

is(round_6(SwissEph::swe_get_ayanamsa(2451544.5)), 24.740393, "swe_get_ayanamsa(2000)");
is(round_6(SwissEph::swe_get_ayanamsa_ut(2451544.5-SwissEph::swe_deltat(2451544.5))), 24.740393, "swe_get_ayanamsa(2000)");

is(SwissEph::swe_get_ayanamsa_name(3), "Raman", "swe_get_ayanamsa(Raman)");

is(SwissEph::swe_get_planet_name(SwissEph::SE_VENUS), "Venus", "swe_get_planet_name(Venus)");

#------------------------------------------------------------------------
# refraction, azimuth, and altitude, rise and transit
#------------------------------------------------------------------------

$ref = SwissEph::swe_refrac_extended(0.1,400,1013,15,0.02,1);
is($ref->{retval}, 0, "swe_refrac_extended()->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_refrac_extended()->serr = $ref->{serr}\n";
}
is(round_6($ref->{alt_true}), -0.439206, "swe_refrac_extended()->alt_true");
is(round_6($ref->{alt_apparent}), 0.1, "swe_refrac_extended()->alt_apparent");
is(round_6($ref->{refraction}), 0.539207, "swe_refrac_extended()->refraction");
is(round_6($ref->{dip}), -0.523897, "swe_refrac_extended()->dip");
is(round_6($ref->{dret}->[0]), -0.439206, "swe_refrac_extended()->dret[0]");

is(round_6(SwissEph::swe_refrac(0.6,1013,15,1)), 0.144079, "swe_refrac()");

@xx = SwissEph::swe_azalt(2436723.588888888,0,[8.55,47.35,0],1013,15,[252,0,1]);
is(round_6($xx[0]), 38.951162, "swe_azalt()->azimuth");
is(round_6($xx[1]), 10.897138, "swe_azalt()->alt_true");
is(round_6($xx[2]), 10.977087, "swe_azalt()->alt_apparent");

@xx = SwissEph::swe_azalt_rev(2436723.588888888,0,[8.55,47.35,0],\@xx);
is(round_6($xx[0]), 252, "swe_azalt_rev()->ecl_lon");
is(round_6($xx[1]), 0, "swe_azalt_rev()->ecl_lat");

$ref = SwissEph::swe_rise_trans(2415020.5,3,"", 4,0,[8.55,47.23,400],1013,15);
is($ref->{retval}, 0, "swe_rise_trans(1900, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_rise_trans(1900, Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{dret}), 2415020.869638, "swe_rise_trans(1900, Venus)->dret");

#------------------------------------------------------------------------
# eclipses and occultations
#------------------------------------------------------------------------

$ref = SwissEph::swe_lun_eclipse_how(2454517.643069,4,[278,0,0]);
is($ref->{retval}, 4, "swe_lun_eclipse_how(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_lun_eclipse_how(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{mag_umbral}), 1.105945, "swe_lun_eclipse_how(2008)->mag_umbral");
is(round_6($ref->{mag_penumbral}), 2.144966, "swe_lun_eclipse_how(2008)->mag_penumbral");
is(round_6($ref->{attr}->[0]), 1.105945, "swe_lun_eclipse_how(2008)->attr[0]");

$ref = SwissEph::swe_sol_eclipse_how(2454503.663212,4,[-150.270493,-67.547072,0]);
is($ref->{retval}, 137, "swe_sol_eclipse_how(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_sol_eclipse_how(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{disc_ratio}), 0.965763, "swe_sol_eclipse_how(2008)->disc_ratio");
is(round_6($ref->{fraction_diameter}), 0.980802, "swe_sol_eclipse_how(2008)->fraction_diameter");
is(round_6($ref->{fraction_disc}), 0.932698, "swe_sol_eclipse_how(2008)->fraction_disc");
is(round_6($ref->{core_shadow_km}), 123.532732, "swe_sol_eclipse_how(2008)->core_shadow_km");
is(round_6($ref->{sun_azimuth}), 88.570234, "swe_sol_eclipse_how(2008)->sun_azimuth");
is(round_6($ref->{sun_alt_true}), 16.23054, "swe_sol_eclipse_how(2008)->sun_alt_true");
is(round_6($ref->{separation_angle}), 0.001124, "swe_sol_eclipse_how(2008)->separation_angle");

$ref = SwissEph::swe_sol_eclipse_where(2454503.663212,4);
is($ref->{retval}, 9, "swe_sol_eclipse_where(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_sol_eclipse_where(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{geopos}->[0]), -150.345267, "swe_sol_eclipse_where(2008)->geopos[0]");
is(round_6($ref->{geopos}->[1]), -67.547758, "swe_sol_eclipse_where(2008)->geopos[1]");
is(round_6($ref->{disc_ratio}), 0.96577, "swe_sol_eclipse_where(2008)->disc_ratio");
is(round_6($ref->{fraction_diameter}), 0.98091, "swe_sol_eclipse_where(2008)->fraction_diameter");
is(round_6($ref->{fraction_disc}), 0.932712, "swe_sol_eclipse_where(2008)->fraction_disc");
is(round_6($ref->{core_shadow_km}), 123.532732, "swe_sol_eclipse_where(2008)->core_shadow_km");
is(round_6($ref->{sun_azimuth}), 88.639338, "swe_sol_eclipse_where(2008)->sun_azimuth");
is(round_6($ref->{sun_alt_true}), 16.259106, "swe_sol_eclipse_where(2008)->sun_alt_true");
is(round_6($ref->{separation_angle}), 0.001068, "swe_sol_eclipse_where(2008)->separation_angle");

$ref = SwissEph::swe_lun_occult_where(2454531.296945,SwissEph::SE_VENUS,"",4);
is($ref->{retval}, 5, "swe_lun_occult_where(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_lun_occult_where(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{geopos}->[0]), -132.429984, "swe_lun_occult_where(2008)->geopos[0]");
is(round_6($ref->{geopos}->[1]), -3.215471, "swe_lun_occult_where(2008)->geopos[1]");
is(round_6($ref->{disc_ratio}), 172.520187, "swe_lun_occult_where(2008)->disc_ratio");
is(round_6($ref->{fraction_diameter}), 1, "swe_lun_occult_where(2008)->fraction_diameter");
is(round_6($ref->{fraction_disc}), 1, "swe_lun_occult_where(2008)->fraction_disc");
is(round_6($ref->{core_shadow_km}), -3461.91306, "swe_lun_occult_where(2008)->core_shadow_km");
is(round_6($ref->{body_azimuth}), 336.290845, "swe_lun_occult_where(2008)->body_azimuth");
is(round_6($ref->{body_alt_true}), 76.844261, "swe_lun_occult_where(2008)->body_alt_true");
is(round_6($ref->{separation_angle}), 5.3e-05, "swe_lun_occult_where(2008)->separation_angle");

$ref = SwissEph::swe_lun_eclipse_when(2454466.500000,4,0,0);
is($ref->{retval}, 4, "swe_lun_eclipse_when(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_lun_eclipse_when(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{ecl_maximum}), 2454517.643076, "swe_lun_eclipse_when(2008)->ecl_maximum");
is(round_6($ref->{ecl_partial_begin}), 2454517.571734, "swe_lun_eclipse_when(2008)->partial_begin");
is(round_6($ref->{ecl_partial_end}), 2454517.714423, "swe_lun_eclipse_when(2008)->partial_end");
is(round_6($ref->{ecl_total_begin}), 2454517.625824, "swe_lun_eclipse_when(2008)->ecl_total_begin");
is(round_6($ref->{ecl_total_end}), 2454517.660346, "swe_lun_eclipse_when(2008)->ecl_total_end");
is(round_6($ref->{ecl_penumbral_begin}), 2454517.525399, "swe_lun_eclipse_when(2008)->ecl_penumbral_begin");
is(round_6($ref->{ecl_penumbral_end}), 2454517.76086, "swe_lun_eclipse_when(2008)->ecl_penumbral_end");

$ref = SwissEph::swe_sol_eclipse_when_glob(2454466.500000,4,0,0);
is($ref->{retval}, 9, "swe_sol_eclipse_when_glob(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_sol_eclipse_when_glob(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{ecl_maximum}), 2454503.663212, "swe_sol_eclipse_when_glob(2008)->ecl_maximum");
is(round_6($ref->{ecl_local_noon}), 2454503.63115, "swe_sol_eclipse_when_glob(2008)->ecl_local_noon");
is(round_6($ref->{ecl_begin}), 2454503.568618, "swe_sol_eclipse_when_glob(2008)->ecl_begin");
is(round_6($ref->{ecl_end}), 2454503.75823, "swe_sol_eclipse_when_glob(2008)->ecl_end");
is(round_6($ref->{ecl_total_begin}), 2454503.638798, "swe_sol_eclipse_when_glob(2008)->ecl_total_begin");
is(round_6($ref->{ecl_total_end}), 2454503.687859, "swe_sol_eclipse_when_glob(2008)->ecl_total_end");
is(round_6($ref->{ecl_central_begin}), 2454503.641666, "swe_sol_eclipse_when_glob(2008)->ecl_central_begin");
is(round_6($ref->{ecl_central_end}), 2454503.685017, "swe_sol_eclipse_when_glob(2008)->ecl_central_end");

$ref = SwissEph::swe_lun_occult_when_glob(2454466.500000,SwissEph::SE_VENUS,"",4,0,0);
is($ref->{retval}, 5, "swe_lun_occult_when_glob(2008, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_lun_occult_when_glob(2008 Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{occ_maximum}), 2454531.296918, "swe_lun_occult_when_glob(2008 Venus)->occ_maximum");
is(round_6($ref->{occ_local_noon}), 2454531.305114, "swe_lun_occult_when_glob(2008 Venus)->occ_local_noon");
is(round_6($ref->{occ_begin}), 2454531.198602, "swe_lun_occult_when_glob(2008 Venus)->occ_begin");
is(round_6($ref->{occ_end}), 2454531.395051, "swe_lun_occult_when_glob(2008 Venus)->occ_end");
is(round_6($ref->{occ_total_begin}), 2454531.198859, "swe_lun_occult_when_glob(2008 Venus)->occ_total_begin");
is(round_6($ref->{occ_total_end}), 2454531.394796, "swe_lun_occult_when_glob(2008 Venus)->occ_total_end");
is(round_6($ref->{occ_central_begin}), 2454531.220578, "swe_lun_occult_when_glob(2008 Venus)->occ_central_begin");
is(round_6($ref->{occ_central_end}), 2454531.373093, "swe_lun_occult_when_glob(2008 Venus)->occ_central_end");

$ref = SwissEph::swe_sol_eclipse_when_loc(2454466.500000,4,0,[8.55,47.35,400]);
is($ref->{retval}, 5008, "swe_sol_eclipse_when_loc(2008)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_sol_eclipse_when_loc(2008)->serr = $ref->{serr}\n";
}
is(round_6($ref->{ecl_maximum}), 2454679.896578, "swe_sol_eclipse_when_loc(2008)->ecl_maximum");
is(round_6($ref->{t1st_contact}), 2454679.87084, "swe_sol_eclipse_when_loc(2008)->1st_contact");
is(round_6($ref->{t2nd_contact}), 0, "swe_sol_eclipse_when_loc(2008)->2nd_contact");
is(round_6($ref->{t3rd_contact}), 0, "swe_sol_eclipse_when_loc(2008)->3rd_contact");
is(round_6($ref->{t4th_contact}), 2454679.922981, "swe_sol_eclipse_when_loc(2008)->4th_contact");
is(round_6($ref->{disc_ratio}), 1.04487, "swe_sol_eclipse_when_loc(2008)->disc_ratio");
is(round_6($ref->{fraction_diameter}), 0.120296, "swe_sol_eclipse_when_loc(2008)->fraction_diameter");
is(round_6($ref->{fraction_disc}), 0.049727, "swe_sol_eclipse_when_loc(2008)->fraction_disc");
is(round_6($ref->{core_shadow_km}), -120.625103, "swe_sol_eclipse_when_loc(2008)->core_shadow_km");
is(round_6($ref->{sun_azimuth}), 309.596927, "swe_sol_eclipse_when_loc(2008)->sun_azimuth");
is(round_6($ref->{sun_alt_true}), 51.508998, "swe_sol_eclipse_when_loc(2008)->sun_alt_true");
is(round_6($ref->{separation_angle}), 0.473893, "swe_sol_eclipse_when_loc(2008)->separation_angle");

$ref = SwissEph::swe_lun_occult_when_loc(2454466.500000,SwissEph::SE_VENUS,"",4,0,[8.55,47.35,400]);
is($ref->{retval}, 8070, "swe_lun_occult_when_loc(2008, Venus)->retval");
if (exists($ref->{serr})) {
  print STDERR "swe_lun_occult_when_loc(2008 Venus)->serr = $ref->{serr}\n";
}
is(round_6($ref->{occ_maximum}), 2454802.1986, "swe_lun_occult_when_loc(2008 Venus)->occ_maximum");
is(round_6($ref->{t1st_contact}), 2454802.169774, "swe_lun_occult_when_loc(2008 Venus)->1st_contact");
is(round_6($ref->{t2nd_contact}), 2454802.170456, "swe_lun_occult_when_loc(2008 Venus)->2nd_contact");
is(round_6($ref->{t3rd_contact}), 2454802.225297, "swe_lun_occult_when_loc(2008 Venus)->3rd_contact");
is(round_6($ref->{t4th_contact}), 2454802.22591, "swe_lun_occult_when_loc(2008 Venus)->4th_contact");
is(round_6($ref->{disc_ratio}), 106.889197, "swe_lun_occult_when_loc(2008 Venus)->disc_ratio");
is(round_6($ref->{fraction_diameter}), 1, "swe_lun_occult_when_loc(2008 Venus)->fraction_diameter");
is(round_6($ref->{fraction_disc}), 1, "swe_lun_occult_when_loc(2008 Venus)->fraction_disc");
is(round_6($ref->{core_shadow_km}), -3452.981367, "swe_lun_occult_when_loc(2008 Venus)->core_shadow_km");
is(round_6($ref->{body_azimuth}), 33.357876, "swe_lun_occult_when_loc(2008 Venus)->body_azimuth");
is(round_6($ref->{body_alt_true}), 11.690515, "swe_lun_occult_when_loc(2008 Venus)->body_alt_true");
is(round_6($ref->{separation_angle}), 0.099491, "swe_lun_occult_when_loc(2008 Venus)->separation_angle");

#------------------------------------------------------------------------
# planetary nodes and apsides
#------------------------------------------------------------------------

$ref = SwissEph::swe_nod_aps(2436723.588888888, 9, 4, 0);
is(round_6($ref->{xnasc}->[0]), 108.996948, "swe_nod_aps(pluto)->node_asc");
is(round_6($ref->{xndsc}->[0]), 290.905101, "swe_nod_aps(pluto)->node_dsc");
is(round_6($ref->{xperi}->[0]), 223.39105, "swe_nod_aps(pluto)->perhelion");
is(round_6($ref->{xaphe}->[0]), 44.947004, "swe_nod_aps(pluto)->aphelion");

#------------------------------------------------------------------------
# heliacal risings
#------------------------------------------------------------------------

$ref = SwissEph::swe_heliacal_ut(2454800,[8,47,400],[1000,15,15,0.17],[23,1,0,0,0,0],"venus",1,260);
is(round_6($ref->{topt}), 2454915.703996, "swe_heliacal_ut(2008 Venus)->topt");

if (0) {
print STDERR "\nend of official test section **************************\n";
my $year = 0.9; my $hour = 0.7;
my @aa = (60.0, 0, 1, 1, 0, 0);
my $xa = -1; my $xb = -1;
#my ($xa, $xb) = SwissEph::test1(\@aa);
#my $ref = SwissEph::swe_houses(2436723.588888888, 47.35, 8.72, "P");
#my $ref = SwissEph::swe_time_equ(2436723.588888888);
#my $ref = SwissEph::swe_revjul(2436723.58888);
#my $ref = SwissEph::swe_houses_armc($ref->{armc}, 47.35, 23.45, "P");
#my $ref = SwissEph::swe_houses_ex(2436723.588888888, 0, 47.35, 8.72, "P");
#my $ref = SwissEph::swe_house_pos($ref->{armc}, 47.35, 23.45, "P", 72.73, 0);
#my $ref = SwissEph::swe_calc(2436723.588888888, 8, 256);
#my $ref = SwissEph::swe_calc_ut(2436723.588888888, 8, 256);
#my $ref = SwissEph::swe_fixstar("aldeb", 2436723.588888888, 0);
#my $ref = SwissEph::swe_fixstar_ut("aldeb", 2436723.588888888, 0);
#my $ref = SwissEph::swe_fixstar_mag("sirius");
my @geopos = (8.33, 47.35,400);
#my $ref = SwissEph::swe_gauquelin_sector(2436723.588888888,0,"sirius",0,0,\@geopos,0,0);
#my $ref = SwissEph::swe_lun_occult_when_loc(2436723.588888888,0,"",0,0,\@geopos);
#my $ref = SwissEph::swe_sol_eclipse_when_loc(2436723.588888888,0,0,\@geopos);
#my $ref = SwissEph::swe_lun_occult_when_glob(2436723.588888888,0,"",0,0,0);
#my $ref = SwissEph::swe_sol_eclipse_when_glob(2452845.588888888,0,0,0);
#my $ref = SwissEph::swe_lun_eclipse_when(2452845.588888888,0,0,0);
#my $ref = SwissEph::swe_lun_occult_where($ref->{ecl_maximum},0, "",0);
#my $ref = SwissEph::swe_sol_eclipse_how($ref->{ecl_maximum},0,\@geopos);
#my $ref = SwissEph::swe_lun_eclipse_how($ref->{ecl_maximum},0,\@geopos);
#my $ref = SwissEph::swe_pheno(2436723.588888888,3,0);
#my $ref = SwissEph::swe_pheno_ut(2436723.588888888,3,0);
#my $ref = SwissEph::swe_refrac_extended(0.1,400,1013,15,0.02,1);
#my $dref = SwissEph::swe_refrac(1,1013,15,0);
#my $ref = SwissEph::swe_nod_aps(2436723.588888888, 9, 0, 0);
#for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}
#print STDERR "@{$ref->{xaphe}}\n";
#print STDERR "@{$ref->{xperi}}\n";
#print STDERR "@{$ref->{xnasc}}\n";
#print STDERR "@{$ref->{xndsc}}\n";
#@xin = (252, 0, 1);
#my @arf = SwissEph::swe_azalt(2436723.588888888,0,\@geopos,1013,15,\@xin);
#my @arf = SwissEph::swe_azalt_rev(2436723.588888888,0,\@geopos,\@arf);
#print STDERR "dref = @arf\n";
#my $ref = SwissEph::swe_revjul($ref->{ecl_maximum});
#for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}
#for (@{$ref->{tret}}) {print STDERR "xx $_\n";}
#for (@{$ref->{attr}}) {print STDERR "xx $_\n";}
#print STDERR "$retval\n@$cusp\n@$ascmc\n";

my $shnam = SwissEph::swe_house_name('c');
print STDERR "$shnam\n";

$ref = SwissEph::swe_version();
for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}

$ref = SwissEph::swe_heliacal_ut(2454800,[8,47,400],[1000,15,15,0.17],[23,1,0,0,0,0],"venus",1,260);
for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}
#print STDERR "dret: ".join(", ", @{$ref->{dret}})."\n";
$ref = SwissEph::swe_vis_limit_mag(2454915.7040719,[8,47,400],[1000,15,15,0.17],[23,1,0,0,0,0],"venus",256);
for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}

$ref = SwissEph::swe_utc_time_zone(2000,1,1,1,59,60.1,5.5);
for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}
#for (@{$ref->{dret}}) {print STDERR "$_\n";}

#$ref = SwissEph::swe_utc_to_jd(2008,12,31,23,59,57.3);
#for (sort keys(%$ref)) {print STDERR "$_ = $ref->{$_}\n";}
#my $tjd0 = $ref->{tjd_et};
#for (my $i = 0; $i < 10; $i++) {
#  $ref = SwissEph::swe_jdet_to_utc($tjd0 + $i/86400);
#  print STDERR "$ref->{iday}.$ref->{imon}.$ref->{iyar}, $ref->{ihou}:$ref->{imin}:$ref->{dsec}\n";
#}

#------------------------------------------------------------------------
# Obliquity
#------------------------------------------------------------------------
}

sub round6 {
  return sprintf( "%.0f", 1000000 * shift);
}  

sub round_6 {
  my $a = shift;
  $a = int(1000000 * ($a + 0.0000005));
  return $a / 1000000.0;
}  

