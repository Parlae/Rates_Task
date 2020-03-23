package com.ewe.parlae.ratestask;


import androidx.lifecycle.MutableLiveData;

import com.ewe.parlae.ratestask.remote.APIService;
import com.ewe.parlae.ratestask.remote.RetroClass;

import retrofit2.Call;
import retrofit2.Callback;

public class DataRepository {
    private static final String TAG = "DataRepository";

    static DataRepository instance;
    private MutableLiveData<String> baseRate = new MutableLiveData<>();
    private MutableLiveData<Double> baseValue = new MutableLiveData<>();

    public MutableLiveData<String> getBaseRate() { return baseRate; }
    public MutableLiveData<Double> getBaseValue() { return baseValue; }

    public DataRepository() {
        getBaseRate().setValue("EUR");
        getBaseValue().setValue(1.0);
    }

    //obtaining the instance of the repository
    public static DataRepository get() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    //method for fetching data
    public void getRatesFor(Callback<String> callback) {
        APIService apiService = RetroClass.getStringApiService();
        Call<String> getRates = apiService.getRatesFor(getBaseRate().getValue());

        getRates.enqueue(callback);

    }

    //currency description bank function
    public String getCurrencyDescription(String currency) {
        switch (currency) {
            case "AUD" :  return "Australia Dollar";
            case "GBP" :  return "Great Britain Pound";
            case "EUR" :  return "Euro";
            case "JPY" :  return "Japan Yen";
            case "CHF" :  return "Switzerland Franc";
            case "USD" :  return "USA Dollar";
            case "AFN" :  return "Afghanistan Afghani";
            case "ALL" :  return "Albania Lek";
            case "DZD" :  return "Algeria Dinar";
            case "AOA" :  return "Angola Kwanza";
            case "ARS" :  return "Argentina Peso";
            case "AMD" :  return "Armenia Dram";
            case "AWG" :  return "Aruba Florin";
            case "ATS" :  return "Austria Schilling";
            case "BEF" :  return "Belgium Franc";
            case "AZN" :  return "Azerbaijan New Manat";
            case "BSD" :  return "Bahamas Dollar";
            case "BHD" :  return "Bahrain Dinar";
            case "BDT" :  return "Bangladesh Taka";
            case "BBD" :  return "Barbados Dollar";
            case "BYR" :  return "Belarus Ruble";
            case "BZD" :  return "Belize Dollar";
            case "BMD" :  return "Bermuda Dollar";
            case "BTN" :  return "Bhutan Ngultrum";
            case "BOB" :  return "Bolivia Boliviano";
            case "BAM" :  return "Bosnia Mark";
            case "BWP" :  return "Botswana Pula";
            case "BRL" :  return "Brazil Real";
            case "BND" :  return "Brunei Dollar";
            case "BGN" :  return "Bulgaria Lev";
            case "BIF" :  return "Burundi Franc";
            case "XOF" :  return "CFA Franc BCEAO";
            case "XAF" :  return "CFA Franc BEAC";
            case "XPF" :  return "CFP Franc";
            case "KHR" :  return "Cambodia Riel";
            case "CAD" :  return "Canada Dollar";
            case "CVE" :  return "Cape Verde Escudo";
            case "KYD" :  return "Cayman Islands Dollar";
            case "CLP" :  return "Chili Peso";
            case "CNY" :  return "China Yuan/Renminbi";
            case "COP" :  return "Colombia Peso";
            case "KMF" :  return "Comoros Franc";
            case "CDF" :  return "Congo Franc";
            case "CRC" :  return "Costa Rica Colon";
            case "HRK" :  return "Croatia Kuna";
            case "CUC" :  return "Cuba Convertible Peso";
            case "CUP" :  return "Cuba Peso";
            case "CYP" :  return "Cyprus Pound";
            case "CZK" :  return "Czech Koruna";
            case "DKK" :  return "Denmark Krone";
            case "DJF" :  return "Djibouti Franc";
            case "DOP" :  return "Dominican Republich Peso";
            case "XCD" :  return "East Caribbean Dollar";
            case "EGP" :  return "Egypt Pound";
            case "SVC" :  return "El Salvador Colon";
            case "EEK" :  return "Estonia Kroon";
            case "ETB" :  return "Ethiopia Birr";
            case "FKP" :  return "Falkland Islands Pound";
            case "FIM" :  return "Finland Markka";
            case "FJD" :  return "Fiji Dollar";
            case "GMD" :  return "Gambia Dalasi";
            case "GEL" :  return "Georgia Lari";
            case "DMK" :  return "Germany Mark";
            case "GHS" :  return "Ghana New Cedi";
            case "GIP" :  return "Gibraltar Pound";
            case "GRD" :  return "Greece Drachma";
            case "GTQ" :  return "Guatemala Quetzal";
            case "GNF" :  return "Guinea Franc";
            case "GYD" :  return "Guyana Dollar";
            case "HTG" :  return "Haiti Gourde";
            case "HNL" :  return "Honduras Lempira";
            case "HKD" :  return "Hong Kong Dollar";
            case "HUF" :  return "Hungary Forint";
            case "ISK" :  return "Iceland Krona";
            case "INR" :  return "India Rupee";
            case "IDR" :  return "Indonesia Rupiah";
            case "IRR" :  return "Iran Rial";
            case "IQD" :  return "Iraq Dinar";
            case "IED" :  return "Ireland Pound";
            case "ILS" :  return "Israel New Shekel";
            case "ITL" :  return "Italy Lira";
            case "JMD" :  return "Jamaica Dollar";
            case "JOD" :  return "Jordan Dinar";
            case "KZT" :  return "Kazakhstan Tenge";
            case "KES" :  return "Kenya Shilling";
            case "KWD" :  return "Kuwait Dinar";
            case "KGS" :  return "Kyrgyzstan Som";
            case "LAK" :  return "Laos Kip";
            case "LVL" :  return "Latvia Lats";
            case "LBP" :  return "Lebanon Pound";
            case "LSL" :  return "Lesotho Loti";
            case "LRD" :  return "Liberia Dollar";
            case "LYD" :  return "Libya Dinar";
            case "LTL" :  return "Lithuania Litas";
            case "LUF" :  return "Luxembourg Franc";
            case "MOP" :  return "Macau Pataca";
            case "MKD" :  return "Macedonia Denar";
            case "MGA" :  return "Malagasy Ariary";
            case "MWK" :  return "Malawi Kwacha";
            case "MYR" :  return "Malaysia Ringgit";
            case "MVR" :  return "Maldives Rufiyaa";
            case "MTL" :  return "Malta Lira";
            case "MRO" :  return "Mauritania Ouguiya";
            case "MUR" :  return "Mauritius Rupee";
            case "MXN" :  return "Mexico Peso";
            case "MDL" :  return "Moldova Leu";
            case "MNT" :  return "Mongolia Tugrik";
            case "MAD" :  return "Morocco Dirham";
            case "MZN" :  return "Mozambique New Metical";
            case "MMK" :  return "Myanmar Kyat";
            case "ANG" :  return "NL Antilles Guilder";
            case "NAD" :  return "Namibia Dollar";
            case "NPR" :  return "Nepal Rupee";
            case "NLG" :  return "Netherlands Guilder";
            case "NZD" :  return "New Zealand Dollar";
            case "NIO" :  return "Nicaragua Cordoba Oro";
            case "NGN" :  return "Nigeria Naira";
            case "KPW" :  return "North Korea Won";
            case "NOK" :  return "Norway Kroner";
            case "OMR" :  return "Oman Rial";
            case "PKR" :  return "Pakistan Rupee";
            case "PAB" :  return "Panama Balboa";
            case "PGK" :  return "Papua New Guinea Kina";
            case "PYG" :  return "Paraguay Guarani";
            case "PEN" :  return "Peru Nuevo Sol";
            case "PHP" :  return "Philippines Peso";
            case "PLN" :  return "Poland Zloty";
            case "PTE" :  return "Portugal Escudo";
            case "QAR" :  return "Qatar Rial";
            case "RON" :  return "Romania New Lei";
            case "RUB" :  return "Russia Rouble";
            case "RWF" :  return "Rwanda Franc";
            case "WST" :  return "Samoa Tala";
            case "STD" :  return "Sao Tome/Principe Dobra";
            case "SAR" :  return "Saudi Arabia Riyal";
            case "RSD" :  return "Serbia Dinar";
            case "SCR" :  return "Seychelles Rupee";
            case "SLL" :  return "Sierra Leone Leone";
            case "SGD" :  return "Singapore Dollar";
            case "SKK" :  return "Slovakia Koruna";
            case "SIT" :  return "Slovenia Tolar";
            case "SBD" :  return "Solomon Islands Dollar";
            case "SOS" :  return "Somali Shilling";
            case "ZAR" :  return "South Africa Rand";
            case "KRW" :  return "South Korea Won";
            case "ESP" :  return "Spain Peseta";
            case "LKR" :  return "Sri Lanka Rupee";
            case "SHP" :  return "St Helena Pound";
            case "SDG" :  return "Sudan Pound";
            case "SRD" :  return "Suriname Dollar";
            case "SZL" :  return "Swaziland Lilangeni";
            case "SEK" :  return "Sweden Krona";
            case "SYP" :  return "Syria Pound";
            case "TWD" :  return "Taiwan Dollar";
            case "TZS" :  return "Tanzania Shilling";
            case "THB" :  return "Thailand Baht";
            case "TOP" :  return "Tonga Pa'anga";
            case "TTD" :  return "Trinidad/Tobago Dollar";
            case "TND" :  return "Tunisia Dinar";
            case "TRY" :  return "Turkish New Lira";
            case "TMM" :  return "Turkmenistan Manat";
            case "UGX" :  return "Uganda Shilling";
            case "UAH" :  return "Ukraine Hryvnia";
            case "UYU" :  return "Uruguay Peso";
            case "AED" :  return "United Arab Emirates Dirham";
            case "VUV" :  return "Vanuatu Vatu";
            case "VEB" :  return "Venezuela Bolivar";
            case "VND" :  return "Vietnam Dong";
            case "YER" :  return "Yemen Rial";
            case "ZMK" :  return "Zambia Kwacha";
            case "ZWD" :  return "Zimbabwe Dollar";
            default: {
                String res = currency + " currency";
                return res;
            }
        }
    }

}
