package android.inooboffre;

public class AmazonWebScraper {
    // This is the logic used to obtain all the values that iNoobOffre currently supports.

    public String ImmagineAmazon(String line) {
        String ImmagineAmazon = line.substring(line.indexOf("{\\\"landingImageUrl\\\":\\\""));
        ImmagineAmazon = ImmagineAmazon.replace("{\\\"landingImageUrl\\\":\\\"", "");
        ImmagineAmazon = ImmagineAmazon.substring(0, ImmagineAmazon.indexOf("\\\"}"));
        ImmagineAmazon = ImmagineAmazon.substring(0, ImmagineAmazon.indexOf("._AC_"));
        ImmagineAmazon = ImmagineAmazon + ".jpg";
        return ImmagineAmazon;
    }
    public String PrezzoCorrente(String line) {
        String PrezzoNormale = "";
        if (line.contains("\\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">\\u003Cspan dir=\\\"rtl\\\">درهم\\u003C/span>\u200E")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">\\u003Cspan dir=\\\"rtl\\\">درهم\\u003C/span>\u200E"));
            PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">\\u003Cspan dir=\\\"rtl\\\">درهم\\u003C/span>\u200E", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayPadding priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\"")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayPadding priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\""));
            PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayPadding priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\"", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
            PrezzoNormale = PrezzoNormale.replace(">", "");
        } else if (line.contains("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Saldi:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Saldi:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoNormale = PrezzoNormale.replace("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Saldi:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-price pitchPriceCssOverride\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price pitchPriceCssOverride\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price pitchPriceCssOverride\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));

        } else if (line.contains("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo in offerta:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoNormale = line.substring(line.indexOf("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo in offerta:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoNormale = PrezzoNormale.replace("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo in offerta:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
        }
        return PrezzoNormale;
    }
    public String NomeProdotto(String line) {
        String TitoloProdotto = "";
        if (line.contains("\\u003Ch1 id=\\\"title\\\" class=\\\"a-size-medium\\\">")) {
            TitoloProdotto = line.substring(line.indexOf("\\u003Ch1 id=\\\"title\\\" class=\\\"a-size-medium\\\">"));
            TitoloProdotto = TitoloProdotto.replace("\\u003Ch1 id=\\\"title\\\" class=\\\"a-size-medium\\\">", "");
            TitoloProdotto = TitoloProdotto.substring(0, TitoloProdotto.indexOf("\\u003Cspan class="));
        } else if (line.contains("data-feature-name=\\\"title\\\" data-template-name=\\\"title\\\" class=\\\"a-size-small a-color-secondary a-text-normal\\\">    \\u003Cspan id=\\\"title\\\" class=\\\"a-size-small\\\">")) {
            TitoloProdotto = line.substring(line.indexOf("data-feature-name=\\\"title\\\" data-template-name=\\\"title\\\" class=\\\"a-size-small a-color-secondary a-text-normal\\\">    \\u003Cspan id=\\\"title\\\" class=\\\"a-size-small\\\">"));
            TitoloProdotto = TitoloProdotto.replace("data-feature-name=\\\"title\\\" data-template-name=\\\"title\\\" class=\\\"a-size-small a-color-secondary a-text-normal\\\">    \\u003Cspan id=\\\"title\\\" class=\\\"a-size-small\\\"> ", "");
            // TitoloProdotto = Html.fromHtml(TitoloProdotto).toString();
            TitoloProdotto = TitoloProdotto.substring(0, TitoloProdotto.indexOf("\\u003C/span>"));
        }
        return TitoloProdotto;
    }
    public String StelleDelProdotto(String line) {
        String StelleProdotto = "";
        if (line.contains("\\u003Cspan data-hook=\\\"average-stars-rating-text\\\" class=\\\"a-size-base a-text-beside-button\\\">")) {
            StelleProdotto = line.substring(line.indexOf("\\u003Cspan data-hook=\\\"average-stars-rating-text\\\" class=\\\"a-size-base a-text-beside-button\\\">"));
            StelleProdotto = StelleProdotto.replace("\\u003Cspan data-hook=\\\"average-stars-rating-text\\\" class=\\\"a-size-base a-text-beside-button\\\">", "");
            StelleProdotto = StelleProdotto.substring(0, StelleProdotto.indexOf("\\u003C/span>"));
            StelleProdotto = StelleProdotto + " stars";
        } else if (line.contains("\\u003Ci class=\\\"a-icon a-icon-star-mini a-star-mini-4-5\\\">\\u003Cspan class=\\\"a-icon-alt\\\">")) {
            StelleProdotto = line.substring(line.indexOf("\\u003Ci class=\\\"a-icon a-icon-star-mini"));
            StelleProdotto = StelleProdotto.replace("\\u003Ci class=\\\"a-icon a-icon-star-mini", "");
            StelleProdotto = StelleProdotto.substring(StelleProdotto.indexOf("span class=\\\"a-icon-alt\\\">"));
            StelleProdotto = StelleProdotto.replace("span class=\\\"a-icon-alt\\\">", "");
            StelleProdotto = StelleProdotto.substring(0, StelleProdotto.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-icon-alt\\\">")) {
            StelleProdotto = line.substring(line.indexOf("\\u003Cspan class=\\\"a-icon-alt\\\">"));
            StelleProdotto = StelleProdotto.replace("\\u003Cspan class=\\\"a-icon-alt\\\">", "");
            StelleProdotto = StelleProdotto.substring(0, StelleProdotto.indexOf("\\u003C/span>"));
        }
        return StelleProdotto;
    }
    public String PrezzoNonScontato(String line) {
        String PrezzoConsigliato = "";
        if (line.contains("\\u003Cspan class=\\\"aok-offscreen aok-hidden inline-show-experience\\\"> السعر الأصلي سابقا \\u003C/span> \\u003Cspan class=\\\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan class=\\\"aok-offscreen aok-hidden inline-show-experience\\\"> السعر الأصلي سابقا \\u003C/span> \\u003Cspan class=\\\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan class=\\\"aok-offscreen aok-hidden inline-show-experience\\\"> السعر الأصلي سابقا \\u003C/span> \\u003Cspan class=\\\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf(".") + 3);
        } else if (line.contains("\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\">      \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\">      \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\">      \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan class=\\\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan class=\\\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("Precio más bajo reciente:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\">       \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("Precio más bajo reciente:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\">       \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("Precio más bajo reciente:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\">       \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("</span>"));
        } else if (line.contains("\\u003Cdiv class=\\\"a-column a-span12\\\">                            \\u003Ctable class=\\\"a-normal a-align-top\\\">  \\u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo consigliato:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\"")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Cdiv class=\\\"a-column a-span12\\\">                            \\u003Ctable class=\\\"a-normal a-align-top\\\">  \\u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo consigliato:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\""));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cdiv class=\\\"a-column a-span12\\\">                            \\u003Ctable class=\\\"a-normal a-align-top\\\">  \\u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo consigliato:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\"", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
            PrezzoConsigliato = PrezzoConsigliato.replace(">", "");
        } else if (line.contains("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo precedente: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo precedente: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo precedente: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Ctable class=\\\"a-normal a-align-top\\\">  \\u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo precedente:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Ctable class=\\\"a-normal a-align-top\\\">  \\u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo precedente:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Ctable class=\\\"a-normal a-align-top\\\">  \\u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo precedente:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-price a-text-price inlineBlock\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"tertiary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
            PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price a-text-price inlineBlock\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"tertiary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
            PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan class=\\\"a-price a-text-price inlineBlock\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"tertiary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
            PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
        }
        return PrezzoConsigliato;
    }
    public boolean getIfAmazonChoice(String line) {
        if (line.contains("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">")) {
            return true;
        } else {
            return false;
        }
    }
    public String AmazonChoiceSearch(String line) {
        String AmazonChoice = "";
        if (line.contains("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">")) {
            AmazonChoice = line.substring(line.indexOf("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">"));
            AmazonChoice = AmazonChoice.replace("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">", "");
            AmazonChoice = AmazonChoice.substring(AmazonChoice.indexOf("\\u003C/span> \\u003Cspan class=\\\"aok-float-left ac-badge-triangle\\\">\\u003C/span>  \\u003Cspan class=\\\"ac-mobile-for-text\\\">\\n\\u003Cspan>"));
            AmazonChoice = AmazonChoice.replace("\\u003C/span> \\u003Cspan class=\\\"aok-float-left ac-badge-triangle\\\">\\u003C/span>  \\u003Cspan class=\\\"ac-mobile-for-text\\\">\\n\\u003Cspan>", "");
            AmazonChoice = AmazonChoice.substring(0, AmazonChoice.indexOf("\\u003C/span>"));
            AmazonChoice = AmazonChoice.replace("\\\"", "\"");
        }
        if (AmazonChoice.contains("\"")) {
            AmazonChoice = AmazonChoice.substring(AmazonChoice.indexOf("\"") + 1);
            AmazonChoice = AmazonChoice.substring(0, AmazonChoice.indexOf("\""));
        }
        return AmazonChoice;
    }
    public String NumeroRecensioni(String line) {
        String NumberOfReviews = "";
         if (line.contains("productTitleGroupSprite\\\">\\u003C/span> \\u003Cspan class=\\\"a-size-small a-color-base\\\"> ")) {
            NumberOfReviews = line.substring(line.indexOf("productTitleGroupSprite\\\">\\u003C/span> \\u003Cspan class=\\\"a-size-small a-color-base\\\"> "));
            NumberOfReviews = NumberOfReviews.replace("productTitleGroupSprite\\\">\\u003C/span> \\u003Cspan class=\\\"a-size-small a-color-base\\\"> ", "");
            NumberOfReviews = NumberOfReviews.substring(0, NumberOfReviews.indexOf(" \\u003C/span>"));
        } else if (line.contains("\\u003Cspan data-hook=\\\"total-rating-count\\\" class=\\\"a-size-base a-color-tertiary totalRatingCount\\\">")) {
            NumberOfReviews = line.substring(line.indexOf("\\u003Cspan data-hook=\\\"total-rating-count\\\" class=\\\"a-size-base a-color-tertiary totalRatingCount\\\">"));
            NumberOfReviews = NumberOfReviews.replace("\\u003Cspan data-hook=\\\"total-rating-count\\\" class=\\\"a-size-base a-color-tertiary totalRatingCount\\\">", "");
            NumberOfReviews = NumberOfReviews.substring(0, NumberOfReviews.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-size-mini cm-cr-review-stars-text-sm\\\">")) {
            NumberOfReviews = line.substring(line.indexOf("\\u003Cspan class=\\\"a-size-mini cm-cr-review-stars-text-sm\\\">"));
            NumberOfReviews = NumberOfReviews.replace("\\u003Cspan class=\\\"a-size-mini cm-cr-review-stars-text-sm\\\">", "");
            NumberOfReviews = NumberOfReviews.substring(0, NumberOfReviews.indexOf("\\u003C/span>"));
        } else if (line.contains("\\u003Cspan class=\\\"a-size-small cm-cr-review-stars-text-md\\\">")) {
            NumberOfReviews = line.substring(line.indexOf("\\u003Cspan class=\\\"a-size-small cm-cr-review-stars-text-md\\\">"));
            NumberOfReviews = NumberOfReviews.replace("\\u003Cspan class=\\\"a-size-small cm-cr-review-stars-text-md\\\">", "");
            NumberOfReviews = NumberOfReviews.substring(0, NumberOfReviews.indexOf("\\u003C/span>"));
            NumberOfReviews = NumberOfReviews + " global ratings";
        }
        return NumberOfReviews;
    }

}
