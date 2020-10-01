package com.handpoint.api.shared

import java.util.*

/**
 * An enum of most currencies in the world.
 *
 * Contains the ISO name, ISO number and the name of the currency.
 * Additionally contains information about how many decimals the
 * currency uses.
 */
enum class Currency(
        /**
         * Gets the currency ISO name.
         * @return `String` ISO name of the currency
         */
        val alpha: String,
        /**
         * Gets the currency ISO code.
         * @return `int` code of the currency
         */
        val code: Int,
        /**
         * Gets the currency symbol, if available.
         * @return `String` with the currency symbol.
         */
        val symbol: String,
        /**
         * Gets the currency minor units.
         * @return `int` number of minor units used by the currency
         */
        val fractionDigits: Int,
        /**
         * Gets the currency public name.
         * @return `String` name of the currency
         */
        val description: String) {

    AED("AED", 784, "د.إ", 2, "United Arab Emirates dirham"),
    AFN("AFN", 971, "Afs", 2, "Afghani"),
    ALL("ALL", 8, "Lek", 2, "Lek"),
    AMD("AMD", 51, "", 0, "Armenian dram"),
    ANG("ANG", 532, "ƒ", 2, "Netherlands Antillean guilder"),
    AOA("AOA", 973, "", 1, "Kwanza"),
    ARS("ARS", 32, "$", 2, "Argentine peso"),
    AUD("AUD", 36, "$", 2, "Australian dollar"),
    AWG("AWG", 533, "ƒ", 2, "Aruban guilder"),
    AZN("AZN", 944, "ман", 2, "Azerbaijanian manat"),
    BAM("BAM", 977, "KM", 2, "Convertible marks"),
    BBD("BBD", 52, "$", 2, "Barbados dollar"),
    BDT("BDT", 50, "৳", 2, "Bangladeshi taka"),
    BGN("BGN", 975, "лв", 2, "Bulgarian lev"),
    BHD("BHD", 48, "BD", 3, "Bahraini dinar"),
    BIF("BIF", 108, "FBu", 0, "Burundian franc"),
    BMD("BMD", 60, "$", 2, "Bermudian dollar"),
    BND("BND", 96, "$", 2, "Brunei dollar"),
    BOB("BOB", 68, "\$b", 2, "Boliviano"),
    BOV("BOV", 984, "", 2, "Bolivian Mvdol (funds code)"),
    BRL("BRL", 986, "R$", 2, "Brazilian real"),
    BSD("BSD", 44, "$", 2, "Bahamian dollar"),
    BTN("BTN", 64, "", 2, "Ngultrum"),
    BWP("BWP", 72, "", 2, "Pula"),
    BYR("BYR", 974, "p.", 0, "Belarusian ruble"),
    BZD("BZD", 84, "BZ$", 2, "Belize dollar"),
    CAD("CAD", 124, "$", 2, "Canadian dollar"),
    CDF("CDF", 976, "", 2, "Franc Congolais"),
    CHF("CHF", 756, "CHF", 2, "Swiss franc"),
    CLP("CLP", 152, "$", 0, "Chilean peso"),
    CNY("CNY", 156, "¥", 1, "Chinese Yuan"),
    COP("COP", 170, "$", 0, "Colombian peso"),
    COU("COU", 970, "", 2, "Unidad de Valor Real"),
    CRC("CRC", 188, "₡", 2, "Costa Rican colon"),
    CUC("CUC", 931, "\$MN", 2, "Cuban convertible peso"),
    CUP("CUP", 192, "₱", 2, "Cuban peso"),
    CVE("CVE", 132, "", 2, "Cape Verde escudo"),
    CZK("CZK", 203, "Kč", 2, "Czech Koruna"),
    DJF("DJF", 262, "", 0, "Djibouti franc"),
    DKK("DKK", 208, "kr", 2, "Danish krone"),
    DOP("DOP", 214, "RD$", 2, "Dominican peso"),
    DZD("DZD", 12, "", 2, "Algerian dinar"),
    EGP("EGP", 818, "£", 2, "Egyptian pound"),
    ERN("ERN", 232, "", 2, "Nakfa"),
    ETB("ETB", 230, "", 2, "Ethiopian birr"),
    EUR("EUR", 978, "€", 2, "euro"),
    FJD("FJD", 242, "$", 2, "Fiji dollar"),
    FKP("FKP", 238, "£", 2, "Falkland Islands pound"),
    GBP("GBP", 826, "£", 2, "Pound sterling"),
    GEL("GEL", 981, "", 2, "Lari"),
    GHS("GHS", 936, "", 2, "Cedi"),
    GIP("GIP", 292, "£", 2, "Gibraltar pound"),
    GMD("GMD", 270, "", 2, "Dalasi"),
    GNF("GNF", 324, "", 0, "Guinea franc"),
    GTQ("GTQ", 320, "Q", 2, "Quetzal"),
    GYD("GYD", 328, "$", 2, "Guyana dollar"),
    HKD("HKD", 344, "$", 2, "Hong Kong dollar"),
    HNL("HNL", 340, "L", 2, "Lempira"),
    HRK("HRK", 191, "kn", 2, "Croatian kuna"),
    HTG("HTG", 332, "", 2, "Haiti gourde"),
    HUF("HUF", 348, "Ft", 2, "Forint"),
    IDR("IDR", 360, "Rp", 0, "Rupiah"),
    ILS("ILS", 376, "₪", 2, "Israeli new sheqel"),
    INR("INR", 356, "₹", 2, "Indian rupee"),
    IQD("IQD", 368, "", 0, "Iraqi dinar"),
    IRR("IRR", 364, "﷼", 0, "Iranian rial"),
    ISK("ISK", 352, "kr", 0, "Iceland krona"),
    JMD("JMD", 388, "J$", 2, "Jamaican dollar"),
    JOD("JOD", 400, "", 3, "Jordanian dinar"),
    JPY("JPY", 392, "¥", 0, "Japanese yen"),
    KES("KES", 404, "", 2, "Kenyan shilling"),
    KGS("KGS", 417, "лв", 2, "Som"),
    KHR("KHR", 116, "៛", 0, "Riel"),
    KMF("KMF", 174, "", 0, "Comoro franc"),
    KPW("KPW", 408, "₩", 0, "North Korean won"),
    KRW("KRW", 410, "₩", 0, "South Korean won"),
    KWD("KWD", 414, "", 3, "Kuwaiti dinar"),
    KYD("KYD", 136, "$", 2, "Cayman Islands dollar"),
    KZT("KZT", 398, "лв", 2, "Tenge"),
    LAK("LAK", 418, "₭", 0, "Kip"),
    LBP("LBP", 422, "£", 0, "Lebanese pound"),
    LKR("LKR", 144, "₨", 2, "Sri Lanka rupee"),
    LRD("LRD", 430, "$", 2, "Liberian dollar"),
    LSL("LSL", 426, "", 2, "Lesotho loti"),
    LTL("LTL", 440, "Lt", 2, "Lithuanian litas"),
    LYD("LYD", 434, "", 3, "Libyan dinar"),
    MAD("MAD", 504, "", 2, "Moroccan dirham"),
    MDL("MDL", 498, "", 2, "Moldovan leu"),
    MGA("MGA", 969, "Ar", 2, "Malagasy ariary"),
    MKD("MKD", 807, "ден", 2, "Denar"),
    MMK("MMK", 104, "", 0, "Kyat"),
    MNT("MNT", 496, "₮", 2, "Tughrik"),
    MOP("MOP", 446, "", 1, "Pataca"),
    MRO("MRO", 478, "UM", 2, "Mauritanian ouguiya"),
    MUR("MUR", 480, "₨", 2, "Mauritius rupee"),
    MVR("MVR", 462, "", 2, "Rufiyaa"),
    MWK("MWK", 454, "", 2, "Kwacha"),
    MXN("MXN", 484, "$", 2, "Mexican peso"),
    MXV("MXV", 979, "", 2, "Mexican Unidad de Inversion"),
    MYR("MYR", 458, "RM", 2, "Malaysian ringgit"),
    MZN("MZN", 943, "MT", 2, "Metical"),
    NAD("NAD", 516, "$", 2, "Namibian dollar"),
    NGN("NGN", 566, "₦", 2, "Naira"),
    NIO("NIO", 558, "C$", 2, "Cordoba oro"),
    NOK("NOK", 578, "kr", 2, "Norwegian krone"),
    NPR("NPR", 524, "₨", 2, "Nepalese rupee"),
    NZD("NZD", 554, "$", 2, "New Zealand dollar"),
    OMR("OMR", 512, "﷼", 3, "Rial Omani"),
    PAB("PAB", 590, "B/.", 2, "Balboa"),
    PEN("PEN", 604, "S/.", 2, "Nuevo sol"),
    PGK("PGK", 598, "", 2, "Kina"),
    PHP("PHP", 608, "₱", 2, "Philippine peso"),
    PKR("PKR", 586, "₨", 2, "Pakistan rupee"),
    PLN("PLN", 985, "zł", 2, "Z?oty"),
    PYG("PYG", 600, "Gs", 0, "Guarani"),
    QAR("QAR", 634, "﷼", 2, "Qatari rial"),
    RON("RON", 946, "lei", 2, "Romanian new leu"),
    RSD("RSD", 941, "Дин.", 2, "Serbian dinar"),
    RUB("RUB", 643, "руб", 2, "Russian rouble"),
    RWF("RWF", 646, "", 0, "Rwanda franc"),
    SAR("SAR", 682, "﷼", 2, "Saudi riyal"),
    SBD("SBD", 90, "$", 2, "Solomon Islands dollar"),
    SCR("SCR", 690, "₨", 2, "Seychelles rupee"),
    SDG("SDG", 938, "", 2, "Sudanese pound"),
    SEK("SEK", 752, "kr", 2, "Swedish krona/kronor"),
    SGD("SGD", 702, "$", 2, "Singapore dollar"),
    SHP("SHP", 654, "£", 2, "Saint Helena pound"),
    SLL("SLL", 694, "", 0, "Leone"),
    SOS("SOS", 706, "S", 2, "Somali shilling"),
    SRD("SRD", 968, "$", 2, "Surinam dollar"),
    SSP("SSP", 728, "£", 2, "South Sudanese pound"),
    STD("STD", 678, "Db", 0, "Dobra"),
    SYP("SYP", 760, "£", 2, "Syrian pound"),
    SZL("SZL", 748, "", 2, "Lilangeni"),
    THB("THB", 764, "฿", 2, "Baht"),
    TJS("TJS", 972, "", 2, "Somoni"),
    TMT("TMT", 934, "", 2, "Manat"),
    TND("TND", 788, "", 3, "Tunisian dinar"),
    TOP("TOP", 776, "T$", 2, "Pa'anga"),
    TRY("TRY", 949, "", 2, "Turkish lira"),
    TTD("TTD", 780, "TT$", 2, "Trinidad and Tobago dollar"),
    TWD("TWD", 901, "NT$", 1, "New Taiwan dollar"),
    TZS("TZS", 834, "", 2, "Tanzanian shilling"),
    UAH("UAH", 980, "₴", 2, "Hryvnia"),
    UGX("UGX", 800, "USh", 0, "Uganda shilling"),
    USD("USD", 840, "$", 2, "US dollar"),
    UZS("UZS", 860, "лв", 2, "Uzbekistan som"),
    VEF("VEF", 937, "Bs", 2, "Venezuelan bolivar fuerte"),
    VND("VND", 704, "₫", 0, "Vietnamese Dong"),
    VUV("VUV", 548, "", 0, "Vatu"),
    WST("WST", 882, "", 2, "Samoan tala"),
    XAF("XAF", 950, "FCFA", 0, "CFA franc BEAC"),
    XCD("XCD", 951, "$", 2, "East Caribbean dollar"),
    XOF("XOF", 952, "CFA", 0, "CFA Franc BCEAO"),
    XPF("XPF", 953, "F", 0, "CFP franc"),
    YER("YER", 886, "﷼", 0, "Yemeni rial"),
    ZAR("ZAR", 710, "R", 2, "South African rand"),
    ZMW("ZMW", 967, "ZK", 2, "Kwacha"),
    ZWL("ZWL", 932, "Z$", 2, "Zimbabwe dollar"),
    Unknown("", 0, "", 0, "");

    /**
     * Gets the currency code formatted correctly for the device.
     * @return `String` code of the currency
     */
    val sendableCurrencyCode: String
        get() = String.format("%04d", this.code)

    fun getName() : String{
        return description
    }

    companion object {

        private val IntToCurrencyMap = HashMap<Int, Currency>()

        init {
            for (currency in values()) {
                IntToCurrencyMap[currency.code] = currency
            }
        }

        /**
         * Gets a `Currency` from a given currency code.
         * @param code the ISO number of the currency (f.x. getCurrency(894) returns ZMK)
         * @return Currency belonging to the currency code.
         */
        @JvmStatic
        fun getCurrency(code: Int): Currency {
            return IntToCurrencyMap[code] ?: return Unknown
        }

        @JvmStatic
        fun getCurrencyFromAlpha(alpha: String): Currency {
            for (currency in values()) {
                if(currency.alpha == alpha) {
                    return currency;
                }
            }

            return Unknown
        }
    }
}
