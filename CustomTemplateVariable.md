 ## Custom template variables
 ### From version 1.3.0
 - $NomeProdotto <-- obtains the item's name
 - $PrezzoNormale <-- obtains the current price of the product
 - $PrezzoConsigliato <-- obtains the non-discounted price. Valid only for discounted products.
 - $Link <-- obtains the link to the webpage. It may be a Bitly link if the option is enabled.

You can also use Telegram formatting, like ** text ** for bold, and __ text __ for italics.
You can use every symbol, except for: ↺  ↻
### From version 2.0.0
Since version 2.0.0, you can use more variables:

 - $StelleProdotto <-- the number of stars a product has
 - $StelleRappresentazione <-- get a rappresentation of the stars using Unicode characters (e.g. ★★☆☆☆)
 - $AmazonChoice <-- search that makes that product Amazon's Choice. You must and add the ✬ symbol (copy and paste) at the beginning and at the end of the specific section for Amazon's Choice, that will be skipped if the product isn't Amazon's Choice. 
 - $NumberOfReviews <-- the number of people that have reviewed a product.
