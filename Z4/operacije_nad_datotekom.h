#ifndef OPERACIJE_NAD_DATOTEKOM_H
#define OPERACIJE_NAD_DATOTEKOM_H
#define MAX(x, y) (((x) > (y)) ? (x) : (y))
#define MIN(x, y) (((x) < (y)) ? (x) : (y))

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

#include <unistd.h>
#include <sys/types.h>

#include "definicije_struktura_podataka.h"

FILE *otvoriDatoteku(char *filename);
void kreirajDatoteku(char *filename);
SLOG *pronadjiSlog(FILE *fajl, int sifraLeta);
void dodajSlog(FILE *fajl, SLOG *slog);
void ispisiSveSlogove(FILE *fajl);
void ispisiSlog(SLOG *slog);
void obrisiSlogFizicki(FILE *fajl, int sifraLeta);

#endif

