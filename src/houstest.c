/************************************************
program houstest.c     21-oct-87
test the housasp module
*************************************************/
#include "ourdef.h"
#include "astrolib.h"
#include "housasp.h"

char *ssgn[] = {
		"ari","tau","gem","can","leo","vir",
	        "lib","sco","sag","cap","aqu","pis"
		};

struct houses hs;

main()
{
  REAL8 x,lx;
  char hsy[80];
  centisec armc, ecl, lat;
  int i,k;
  ecl = 23*DEG + 26*6000L + 3312; 	/* 23 deg 26 ' 33.12 " */
  while (1) {
    printf("lat (dd.fraction), sid.time (hh.fraction), house system:");
    if (scanf("%lf%lf%s",&lx,&x,hsy) == EOF) exit(0);
    lat = d2l (lx * DEG);
    printf ("latitude= %lf, time= %lf, hsy=%c\n",lx, x, hsy[0]);
    armc = d2l (x * 360000.0 *15.0);
    CalcHouses (armc, lat, ecl, hsy[0],0, &hs);
    for (i = 1; i <= 12; i++)  {
    hs.cusp[i] = roundsec (hs.cusp[i]);
    k = hs.cusp[i]  / DEG30;
      printf("house %2d  %s %s\n", i, ssgn[k], DegreeString (hs.cusp[i]));
    }
  }
}

