# Android YHC3L 2017

## Välkommen till Android-kursen på YHC3L!

Kursen är 8 veckor lång och har som ändamål att introducera er för Android och UI-programmering. Kursen är uppdelad i två stycken delar:
- 4 veckor med föreläsningar och uppgifter
- 4 veckor med en laboration där G-delen görs i grupp och vg-delen enskilt. 

# Läsning och referenser
- [Android studio](https://developer.android.com/studio/index.html)
- [Android programming, bok av Big Nerd Ranch](https://www.adlibris.com/se/bok/android-programming-9780134706054)
- [Android developers](https://developer.android.com/)

# Övningar

## Uppgift dag 1

Jag vill att ni bygger en applikation innehållandes två Activities. 
Den activity som visas när appen startar ska visa upp en [EditText](https://developer.android.com/reference/android/widget/EditText.html) och en [Button](https://developer.android.com/reference/android/widget/Button.html)

När användaren trycker på knappen så vill jag att ni genom en [explicit intent](https://developer.android.com/guide/components/intents-filters.html) öppnar den andra aktiviteten och där presenterar ett välkomstmeddelande baserat på det som användaren skrivit i EditText-fältet på aktiviteten innan. 

## Utmaning, returnera resultat

Läs på om [startActivityForResult](https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent, int)) och låt SecondActivity returnera någonting till MainActivity.

Tillexempel, låt secondactivity returnera strängen "Hello World From SecondActivity" 
till MainActivity och visa upp det i en Toast när SecondActivity stängs, vid finish().
