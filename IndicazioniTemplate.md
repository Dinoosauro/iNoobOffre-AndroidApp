# Modifica template di testo
Puoi personalizzare come vuoi il testo generato dall'app. Per farlo, utilizza il pulsante dedicato dalle impostazioni. 
## Codici utili

- _$NomeProdotto_ <-- ottiene il titolo del prodotto
- _$PrezzoNormale_ <-- prezzo a cui è possibile comprare il prodotto
- _$PrezzoConsigliato_ <-- prezzo non scontato
- _$Link_ <-- link del prodotto
-  ** <-- inizio/fine grassetto
- __  <-- inizio/fine corsivo
- \n <-- nuova riga
## Come creare il template
L'applicazione permette di creare uno script differente per gli articoli non scontati. Per questo, assicurarsi che, quando si vuole dichiarare il costo del prodotto, venga dichiarato nel seguente modo:
`$PrezzoNormale($PrezzoNormale _continua template esclusivo per gli articoli scontati..._)_continua template generale_
`

Non sono presenti altre limitazioni. 
