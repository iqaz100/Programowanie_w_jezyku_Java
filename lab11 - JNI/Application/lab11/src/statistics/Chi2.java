package statistics;


import java.util.Arrays;

/**
 * Klasa z metodami natywnymi wykorzystywana do wykonywania testu statystycznego chi2
 * @author tkubik
 *
 */
public class Chi2 {

    static{System.loadLibrary("lab11");}
    public Double[] observed = {1.4d,1.4d};
    public Double[] expected = {1.4d,3.5d};
    public Double[] calculated;


    /**
     * Metoda natywna pozwalająca przeprowadzić test statystyczny.
     * Aplikacja Javy z niej korzystająca powinna:
     * - dostarczyć formularz na wprowadzenie obserwowanych oraz oczekiwanych danych (z opcją wczytania danych z pliku)
     * - obliczyć wartość chi2 za pomocą opisywanej metody natywnej (przekazując jej atrybuty)
     * - wyświetlić wyniki
     *
     * @param observed - tablica z danymi wejściowymi obserwowanymi
     * @param expected - tablica z danymi wejściowymi oczekiwanymi
     * @return - tablica z danymi wynikowymi
     */
    public native Double[] calculate0(Double[] observed, Double[] expected);


    /**
     * Metoda natywna pozwalająca przeprowadzić test statystyczny.
     * Aplikacja Javy z niej korzystająca powinna:
     * - dostarczyć formularz na wprowadzenie obserwowanych oraz oczekiwanych danych (z opcją wczytania danych z pliku),
     *      dane oczekiwane mają być wstawione do środka instancji wykorzystywanej klasy z opisywaną metodą natywną,
     *      dane obserwowane mają być przekazane do metody natywnej,
     * - obliczyć wartość chi2 za pomocą metody natywnej,
     * 		Po stronie kodu natywnego trzeba będzie sięgnąć do pola expected instancji klasy,
     *      wziąć przekazany atrybut observed i przeprowadzić test,
     *      wpisać wyniki do pola calculated instancji klasy,
     * - wyświetlić wyniki
     *
     * @param observed - tablica z danymi wejściowymi obserwowanymi
     */
    public native void calculate(Double[] observed);

    /**
     * Metoda natywna pozwalająca przeprowadzić test statystyczny.
     * Aplikacja Javy z niej korzystająca powinna:
     *  - uruchomić obliczenia i wyświetlić wyniki
     * Po stronie kodu natywnego trzeba będzie
     * 	- wprowadzić dane wejściowe obserwowane i oczekiwane (za pomocą formularza lub wczytując dane z pliku,
     *    przy czym implementacja "okienek formularzy" po stonie kodu natywnego może być zrobiona natywnie
     *    lub z wykorzystaniem klas Java)
     *  - przekazać wynik testu do kodu Java
     *
     * @return - tablica z danymi wynikowymi
     */
    public native Double[] calculate();

    public static void main(String[] args){
        Chi2 chi2 = new Chi2();
       // System.out.println("METODA 1\n" + Arrays.toString(chi2.calculate0(chi2.observed, chi2.expected)));
        //System.out.println("\nMETODA 2\n" + Arrays.toString(chi2.calculate()));
        System.out.println("\nMETODA 3\n");
        chi2.calculate(chi2.observed);
    }

}
