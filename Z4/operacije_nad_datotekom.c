
#include "operacije_nad_datotekom.h"

FILE *otvoriDatoteku(char *filename) {
	FILE *fajl = fopen(filename, "rb+");
	if (fajl == NULL) {
		printf("Doslo je do greske! Moguce da datoteka \"%s\" ne postoji.\n", filename);
	} else {
		printf("Datoteka \"%s\" otvorena.\n", filename);
	}
	return fajl;
}

void kreirajDatoteku(char *filename) {
    printf("ovde1");
	FILE *fajl = fopen(filename, "wb");
	if (fajl == NULL) {
		printf("Doslo je do greske prilikom kreiranja datoteke \"%s\"!\n", filename);
	} else {
		//upisi pocetni blok
		BLOK blok;
		blok.slogovi[0].sifraLeta = OZNAKA_KRAJA_DATOTEKE;
		fwrite(&blok, sizeof(BLOK), 1, fajl);
		printf("Datoteka \"%s\" uspesno kreirana.\n", filename);
		fclose(fajl);
	}
}

SLOG *pronadjiSlog(FILE *fajl, int sifraLeta) {
	if (fajl == NULL) {
		return NULL;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;

	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
            //const char* sifraLeta = toString().blok.slogovi[i].sifraLeta;
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
				//nema vise slogova
				return NULL;
			}

			if (blok.slogovi[i].sifraLeta == sifraLeta) {
                //Ako se evidBroj poklapa i slog NIJE logicki obrisan
                //(logicki obrisane slogove tretiramo kao da i ne
                //postoje u datoteci).

				SLOG *slog = (SLOG *)malloc(sizeof(SLOG));
				memcpy(slog, &blok.slogovi[i], sizeof(SLOG));
				return slog;
			}
		}
	}

	return NULL;
}

void dodajSlog(FILE *fajl, SLOG *slog) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	SLOG *slogStari = pronadjiSlog(fajl, slog->sifraLeta);
	if (slogStari != NULL) {
        //U datoteci vec postoji slog sa tim evid. brojem,
        //pa ne mozemo upisati novi slog.
        printf("Vec postoji slog sa tim evid brojem!\n");
        return;
    }

	BLOK blok;
	fseek(fajl, -sizeof(BLOK), SEEK_END); //u poslednji blok upisujemo novi slog
	fread(&blok, sizeof(BLOK), 1, fajl);

	int i;
	for (i = 0; i < FBLOKIRANJA; i++) {
		if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
            //Ovo je mesto gde se nalazi slog sa oznakom
            //kraja datoteke; tu treba upisati novi slog.
			memcpy(&blok.slogovi[i], slog, sizeof(SLOG));
			break;
		}
	}

	i++; //(na to mesto u bloku cemo upisati krajDatoteke)

	if (i < FBLOKIRANJA) {
        //Jos uvek ima mesta u ovom bloku, mozemo tu smestiti slog
        //sa oznakom kraja datoteke.
		blok.slogovi[i].sifraLeta = OZNAKA_KRAJA_DATOTEKE;

		//Sada blok mozemo vratiti u datoteku.
		fseek(fajl, -sizeof(BLOK), SEEK_CUR);
		fwrite(&blok, sizeof(BLOK), 1, fajl);
		fflush(fajl);
	} else {
		//Nema vise mesta u tom bloku, tako da moramo
        //praviti novi blok u koji cemo upisati slog
        //sa oznakom kraja datoteke.

		//Prvo ipak moramo vratiti u datoteku blok
        //koji smo upravo popunili:
		fseek(fajl, -sizeof(BLOK), SEEK_CUR);
		fwrite(&blok, sizeof(BLOK), 1, fajl);

		//Okej, sad pravimo novi blok:
		BLOK noviBlok;
		noviBlok.slogovi[0].sifraLeta = OZNAKA_KRAJA_DATOTEKE;
		//(vec smo na kraju datoteke, nema potrebe za fseekom)
		fwrite(&noviBlok, sizeof(BLOK), 1, fajl);
	}

	if (ferror(fajl)) {
		printf("Greska pri upisu u fajl!\n");
	} else {
		printf("Upis novog sloga zavrsen.\n");
	}
}

void ispisiSveSlogove(FILE *fajl) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	printf("BL SL SifraLeta          Datum          TipAviona        BrojPutnika   Sif.Aer.Polaska  Sif.Aer.Dolaska\n");
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
				printf("B%d S%d *\n", rbBloka, i);
                break; //citaj sledeci blok
			}
            printf("B%d S%d ", rbBloka, i);
            ispisiSlog(&blok.slogovi[i]);
            printf("\n");
		}

		rbBloka++;
	}
}

void ispisiSlog(SLOG *slog){
    printf("%d %25s %10s %15d %15s %15s", slog->sifraLeta, slog->datum, slog->tipAviona, slog->brojPutnika, slog->kodAerodromaPolaska, slog->kodAerodromaDolaska);
}


void obrisiSlogFizicki(FILE *fajl, int sifraLeta) {

    SLOG *slog = pronadjiSlog(fajl, sifraLeta);
    if (slog == NULL) {
        printf("Slog koji zelite obrisati ne postoji!\n");
        return;
    }

    //Trazimo slog sa odgovarajucom vrednoscu kljuca, i potom sve
    //slogove ispred njega povlacimo jedno mesto unazad.

    BLOK blok, naredniBlok;
    int sifraLetaZaBrisanje;
    sifraLetaZaBrisanje = sifraLeta;

    fseek(fajl, 0, SEEK_SET);
    while (fread(&blok, 1, sizeof(BLOK), fajl)) {
        for (int i = 0; i < FBLOKIRANJA; i++) {

            if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {

                if (i == 0) {
                    //Ako je oznaka kraja bila prvi slog u poslednjem bloku,
                    //posle brisanja onog sloga, ovaj poslednji blok nam
                    //vise ne treba;
                    printf("(skracujem fajl...)\n");
                    fseek(fajl, -sizeof(BLOK), SEEK_END);
					long bytesToKeep = ftell(fajl);
					ftruncate(fileno(fajl), bytesToKeep);
					fflush(fajl); //(da bi se primenile promene usled poziva truncate)
                }

                printf("Slog je fizicki obrisan.\n");
                return;
            }

            if (blok.slogovi[i].sifraLeta == sifraLetaZaBrisanje) {

                //Obrisemo taj slog iz bloka tako sto sve slogove ispred njega
                //povucemo jedno mesto unazad.
                for (int j = i+1; j < FBLOKIRANJA; j++) {
                    memcpy(&(blok.slogovi[j-1]), &(blok.slogovi[j]), sizeof(SLOG));
                }

                //Jos bi hteli na poslednju poziciju u tom bloku upisati prvi
                //slog iz narednog bloka, pa cemo zato ucitati naredni blok...
                int podatakaProcitano = fread(&naredniBlok, sizeof(BLOK), 1, fajl);

                //...i pod uslovom da uopste ima jos blokova posle trenutnog...
                if (podatakaProcitano) {

                    //(ako smo nesto procitali, poziciju u fajlu treba vratiti nazad)
                    fseek(fajl, -sizeof(BLOK), SEEK_CUR);

                    //...prepisati njegov prvi slog na mesto poslednjeg sloga u trenutnom bloku.
                    memcpy(&(blok.slogovi[FBLOKIRANJA-1]), &(naredniBlok.slogovi[0]), sizeof(SLOG));

                    //U narednoj iteraciji while loopa, brisemo prvi slog iz narednog bloka.
                    sifraLetaZaBrisanje = naredniBlok.slogovi[0].sifraLeta;
                }

                //Vratimo trenutni blok u fajl.
                fseek(fajl, -sizeof(BLOK), SEEK_CUR);
                fwrite(&blok, sizeof(BLOK), 1, fajl);
                fflush(fajl);

                if (!podatakaProcitano) {
                    //Ako nema vise blokova posle trentnog, mozemo prekinuti algoritam.
                    printf("Slog je fizicki obrisan.\n");
                    return;
                }

                //To je to, citaj sledeci blok
                break;
            }

        }
    }
}

void ispisiProsecanBrojPutnikaZaZadatiTipAviona(FILE *fajl, char *tipAviona) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0, brojPutnika = 0, cnt = 0;
	//double cnt = 0;
	//printf("BL SL SifraLeta          Datum          TipAviona        BrojPutnika   Sif.Aer.Polaska  Sif.Aer.Dolaska\n");
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
				//printf("B%d S%d *\n", rbBloka, i);
                break; //citaj sledeci blok
			}
            //printf("B%d S%d ", rbBloka, i);
            if(strcmp(&blok.slogovi[i].tipAviona, tipAviona) == 0){
                brojPutnika += blok.slogovi[i].brojPutnika;
                cnt++;
            }
		}

		rbBloka++;
	}
	printf("Prosecan broj putnika na tipu aviona %s je %d.", tipAviona, brojPutnika/cnt);
}

int pronadjiNajmanjiBrojPutnikaUMartu2020(FILE *fajl) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0, brojPutnika = INT_MAX;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}
			if (blok.slogovi[i].datum[0] == '2' && blok.slogovi[i].datum[1] == '0' && blok.slogovi[i].datum[2] == '2' && blok.slogovi[i].datum[3] == '0' && blok.slogovi[i].datum[5] == '0' && blok.slogovi[i].datum[6] == '3') {
				brojPutnika = MIN(brojPutnika, blok.slogovi[i].brojPutnika);
			}
		}

		rbBloka++;
	}
	return brojPutnika;
}

void obrisiLetoveSaManjePutnikaNegoUMartu2020(FILE *fajl) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	int minPutnika = pronadjiNajmanjiBrojPutnikaUMartu2020(fajl);

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0, brojPutnika = INT_MAX;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}
			if(blok.slogovi[i].brojPutnika < minPutnika){
                obrisiSlogFizicki(fajl, blok.slogovi[i].sifraLeta);
			}
		}

		rbBloka++;
	}
}

void ispisiSveLetoveZaTipAviona(FILE *fajl, char *tipAviona) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	printf("Svi letovi 2021. godine za tip aviona %s\n", tipAviona);
	printf("SifraLeta          Datum          TipAviona        BrojPutnika   Sif.Aer.Polaska  Sif.Aer.Dolaska\n");
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}
			if (blok.slogovi[i].datum[0] == '2' && blok.slogovi[i].datum[1] == '0' && blok.slogovi[i].datum[2] == '2' && blok.slogovi[i].datum[3] == '1' && strcmp(&blok.slogovi[i].tipAviona, tipAviona) == 0) {
                ispisiSlog(&blok.slogovi[i]);
                printf("\n");
			}
		}

		rbBloka++;
	}
}

void ispisiSveLetove2021(FILE *fajl) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0, cnt = 0;
    char tipoviAviona[100][7];
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraLeta == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}
			int ok = 1;
			//printf("%s\n", blok.slogovi[i].tipAviona);
			for(int j=0; j<cnt; j++){
			    //printf("%d %s\n", j, tipoviAviona[j]);
                if(strcmp(&blok.slogovi[i].tipAviona, tipoviAviona[j]) == 0) ok = 0;
                if(!ok) break;
			}
			if(ok){
                memcpy(&tipoviAviona[cnt], &blok.slogovi[i].tipAviona, sizeof tipoviAviona[cnt]);
                cnt++;
			}
			//printf("%s %d\n", &blok.slogovi[i].tipAviona, ok);
			//if(ok) ispisiSveLetoveZaTipAviona(fajl, blok.slogovi[i].tipAviona);
		}

		rbBloka++;
	}
	for(int j=0; j<cnt; j++){
        ispisiSveLetoveZaTipAviona(fajl, tipoviAviona[j]);
    }
}
