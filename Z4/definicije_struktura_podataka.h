#define FBLOKIRANJA 3
#define OZNAKA_KRAJA_DATOTEKE -1

typedef struct Slog{
    int sifraLeta;
    char datum[17];
    char tipAviona[7];
    int brojPutnika;
    char kodAerodromaPolaska[4];
    char kodAerodromaDolaska[4];
} SLOG;

typedef struct Blok{
    SLOG slogovi[FBLOKIRANJA];
} BLOK;
