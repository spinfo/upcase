package de.uni_koeln.spinfo.upcase.model.tagset;

import de.uni_koeln.spinfo.upcase.model.Annotatable;

/**
 * 
 * Erläuterungen zum Tagset

----------------------------------------------------------------------------
Das hier verwendete Tagset ist das ``Stuttgart/Tübinger Tagsets'' (STTS),
das von Anne Schiller (ehemals IMS/STR, jetzt RXRC/Grenoble), Christine
Thielen (SfS/TÜB), Simone Teufel (ehemals IMS/STR, jetzt Cogsci/Edinburgh)
und Christine Stöckert (IMS/STR) entwickelt wurde.

Siehe auch: Tagging-Guidelines etc.
----------------------------------------------------------------------------

ADJA    attributives Adjektiv                   [das] große [Haus]
ADJD    adverbiales oder                        [er fährt] schnell
        prädikatives Adjektiv                   [er ist] schnell

ADV     Adverb                                  schon, bald, doch

APPR    Präposition; Zirkumposition links       in [der Stadt], ohne [mich]
APPRART Präposition mit Artikel                 im [Haus], zur [Sache]
APPO    Postposition                            [ihm] zufolge, [der Sache] wegen
APZR    Zirkumposition rechts                   [von jetzt] an

ART     bestimmter oder                         der, die, das,
        unbestimmter Artikel                    ein, eine, ...

CARD    Kardinalzahl                            zwei [Männer], [im Jahre] 1994

FM      Fremdsprachliches Material              [Er hat das mit ``]
                                                A big fish ['' übersetzt]

ITJ     Interjektion                            mhm, ach, tja

ORD     Ordinalzahl                             [der] neunte [August]

KOUI    unterordnende Konjunktion               um [zu leben],
        mit ``zu'' und Infinitiv                anstatt [zu fragen]
KOUS    unterordnende Konjunktion               weil, daß, damit,
        mit Satz                                wenn, ob
KON     nebenordnende Konjunktion               und, oder, aber
KOKOM   Vergleichskonjunktion                   als, wie

NN      normales Nomen                          Tisch, Herr, [das] Reisen
NE      Eigennamen                              Hans, Hamburg, HSV

PDS     substituierendes Demonstrativ-          dieser, jener
        pronomen
PDAT    attribuierendes Demonstrativ-           jener [Mensch]
        pronomen

PIS     substituierendes Indefinit-             keiner, viele, man, niemand
        pronomen
PIAT    attribuierendes Indefinit-              kein [Mensch],
        pronomen ohne Determiner                irgendein [Glas]
PIDAT   attribuierendes Indefinit-              [ein] wenig [Wasser],
        pronomen mit Determiner                 [die] beiden [Brüder]

PPER    irreflexives Personalpronomen           ich, er, ihm, mich, dir

PPOSS   substituierendes Possessiv-             meins, deiner
        pronomen
PPOSAT  attribuierendes Possessivpronomen       mein [Buch], deine [Mutter]

PRELS   substituierendes Relativpronomen        [der Hund ,] der
PRELAT  attribuierendes Relativpronomen         [der Mann ,] dessen [Hund]

PRF     reflexives Personalpronomen             sich, einander, dich, mir

PWS     substituierendes                        wer, was
        Interrogativpronomen
PWAT    attribuierendes                         welche [Farbe],
        Interrogativpronomen                    wessen [Hut]
PWAV    adverbiales Interrogativ-               warum, wo, wann,
        oder Relativpronomen                    worüber, wobei

PAV     Pronominaladverb                        dafür, dabei, deswegen, trotzdem

PTKZU   ``zu'' vor Infinitiv                    zu [gehen]
PTKNEG  Negationspartikel                       nicht
PTKVZ   abgetrennter Verbzusatz                 [er kommt] an, [er fährt] rad
PTKANT  Antwortpartikel                         ja, nein, danke, bitte
PTKA    Partikel bei Adjektiv                   am [schönsten],
        oder Adverb                             zu [schnell]

SGML    SGML Markup

SPELL   Buchstabierfolge                        S-C-H-W-E-I-K-L

TRUNC   Kompositions-Erstglied                  An- [und Abreise]

VVFIN   finites Verb, voll                      [du] gehst, [wir] kommen [an]
VVIMP   Imperativ, voll                         komm [!]
VVINF   Infinitiv, voll                         gehen, ankommen
VVIZU   Infinitiv mit ``zu'', voll              anzukommen, loszulassen
VVPP    Partizip Perfekt, voll                  gegangen, angekommen
VAFIN   finites Verb, aux                       [du] bist, [wir] werden
VAIMP   Imperativ, aux                          sei [ruhig !]
VAINF   Infinitiv, aux                          werden, sein
VAPP    Partizip Perfekt, aux                   gewesen
VMFIN   finites Verb, modal                     dürfen
VMINF   Infinitiv, modal                        wollen
VMPP    Partizip Perfekt, modal                 gekonnt, [er hat gehen] können

XY      Nichtwort, Sonderzeichen                3:7, H2O,
        enthaltend                              D2XW3

\$,     Komma                                   ,
\$.     Satzbeendende Interpunktion             . ? ! ; :
\$(     sonstige Satzzeichen; satzintern        - [,]()

----------------------------------------------------------------------------
IMS Stuttgart / www@ims.uni-stuttgart.de / Thu Nov 9 12:58:03 1995 (oli)

 * 
 * @author matana
 *
 */
public enum STTS implements Annotatable {

	ADJA, ADJD, ADV, APPR, APPRART, APPO, APZR, ART, CARD, FM, ITJ, ORD, KOUI, KOUS, KON, KOKOM, NN, NE, PDS, PDAT, PIS, PIAT, PIDAT, PPER, PPOSS, PPOSAT, PRELS, PRELAT, PRF, PWS, PWAT, PWAV, PAV, PTKZU, PTKNEG, PTKVZ, PTKANT, PTKA, SGML, SPELL, TRUNC, VVFIN, VVIMP, VVINF, VVIZU, VVPP, VAFIN, VAIMP, VAINF, VAPP, VMFIN, VMINF, VMPP, XY
}
