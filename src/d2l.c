/*******************************************
$Header: d2l.c,v 1.9 91/11/16 16:24:20 alois Exp $
********************************************/

/*************************************
double to long with rounding, no overflow check
*************************************/ 
long d2l (double x)		
{
  if (x >=0)
    return ((long) (x + 0.5));
  else
    return (- (long) (0.5 - x));
}
