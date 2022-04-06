Questa pagina è in inglese. La versione in italiano è disponibile [qui](https://github.com/Lorenzo-Effe/iNoobOffre-AndroidApp/blob/main/IndicazioniTemplate.md).
# Edit text template
You can personalize the description created by iNoobOffre using a button into the Settings page. 
## Useful codes

- _$NomeProdotto_ <-- obtain the name of the product
- _$PrezzoNormale_ <-- price (discounted) of the product 
- _$PrezzoConsigliato_ <-- non-discounted price
- _$Link_ <-- product link
-  ** <-- start/end bold
- __  <-- start/end italic
- \n <-- new line
## Create a template
iNoobOffre permits to create a different script for non-discounted articles. Remember to delcare in this way the product's price:
`$PrezzoNormale($PrezzoNormale _continue making the template for discounted articles..._)_continue the normal template_
`

It's forbidden the use of round brackets ()
There aren't any other limitations.
