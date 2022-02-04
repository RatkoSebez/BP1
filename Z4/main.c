#include "stdio.h"
#include "stdlib.h"

#include "operacije_nad_datotekom.h"

// gcc *.c && ./a.out

int main() {

	int running = 1;
	int userInput;

	FILE *fajl = NULL;

	while (running) {
		printf("Odaberite opciju:\n");
		printf("1 - Otvaranje datoteke\n");
		printf("2 - Formiranje datoteke\n");
		printf("3 - Pretraga datoteke\n");
		printf("4 - Unos sloga\n");
		printf("5 - Ispis svi slogova\n");
		printf("6 - Brisanje sloga (fiziko)\n");
		printf("7 - Ispisivanje prosecnog broja putnika za zadati tip aviona\n");
		printf("8 - Brisanje svih letova na kojima je broj putnika manji od najmanjeg broja putnika u martu mesecu 2020. godine\n");
		printf("9 - Prikazivanje letova koji su obavljeni u 2021. godini za svaki tip aviona\n");
		printf("0 - Izlaz\n");
		if (fajl == NULL) {
			printf("!!! PAZNJA: datoteka jos uvek nije otvorena !!!\n");
		}
		scanf("%d", &userInput);
		getc(stdin);

		switch(userInput) {
			case 1:
				{
					char filename[20];
					printf("Unesite ime datoteke za otvaranje: ");
					scanf("%s", &filename[0]);
					fajl = otvoriDatoteku(filename);
					printf("\n");
					break;
				}
			case 2:
				{
					char filename[20];
					printf("Unesite ime datoteke za kreiranje: ");
					scanf("%s", filename);
					kreirajDatoteku(filename);
					printf("\n");
					break;
				}
			case 3:
				{
					int sifraLeta;
					printf("Unesite sifru leta: ");
					scanf("%d", &sifraLeta);
					SLOG *slog = pronadjiSlog(fajl, sifraLeta);
					if (slog == NULL) {
                        printf("Nema tog sloga u datoteci.\n");
					} else {
                        printf("SifraLeta          Datum          TipAviona        BrojPutnika   Sif.Aer.Polaska  Sif.Aer.Dolaska\n");
                        ispisiSlog(slog);
                        printf("\n");
					}
					free(slog);
					printf("\n");
					break;
				}
			case 4:
				{
					SLOG slog;
					printf("SIfra leta (celobrojna vrednost): ");
                    scanf("%d", &slog.sifraLeta);
                    printf("Datum leta (YYYY-MM-DD-HH:mm): ");
                    scanf("%s", slog.datum);
                    printf("Tip aviona (do 6 karaktera): ");
                    scanf("%s", slog.tipAviona);
                    printf("Broj putnika: ");
                    scanf("%d", &slog.brojPutnika);
                    printf("Kod aerodroma polaska (3 karaktera): ");
                    scanf("%s", slog.kodAerodromaPolaska);
                    printf("Kod aerodroma dolaska (3 karaktera): ");
                    scanf("%s", slog.kodAerodromaDolaska);
					dodajSlog(fajl, &slog);
					printf("\n");
					break;
				}
			case 5:
				{
					ispisiSveSlogove(fajl);
					printf("\n");
					break;
				}
			case 6:
				{
					int sifraLeta;
					printf("Unesite sifru leta za FIZICKO brisanje sloga: ");
					scanf("%d", &sifraLeta);
					obrisiSlogFizicki(fajl, sifraLeta);
					printf("\n");
					break;
				}
            case 7:
				{
				    char tipAviona[7];
					printf("Unesite tip aviona: ");
					scanf("%s", tipAviona);
					ispisiProsecanBrojPutnikaZaZadatiTipAviona(fajl, tipAviona);
					printf("\n");
					break;
				}
            case 8:
				{
				    obrisiLetoveSaManjePutnikaNegoUMartu2020(fajl);
					printf("\n");
					break;
				}
            case 9:
				{
					ispisiSveLetove2021(fajl);
					printf("\n");
					break;
				}
			case 0:
				{
					running = 0;
					if (fajl != NULL) {
						fclose(fajl);
					}
				}
		}
	}

	return 0;

}




