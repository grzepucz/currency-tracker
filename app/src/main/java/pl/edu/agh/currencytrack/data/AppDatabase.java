package pl.edu.agh.currencytrack.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.agh.currencytrack.data.DAO.FavouritesCurrencyDAO;
import pl.edu.agh.currencytrack.data.DAO.NotificationLimitDAO;

@Database(entities = {FavouriteCurrency.class, NotificationLimit.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavouritesCurrencyDAO currencyDao();
    public abstract NotificationLimitDAO notificationLimitDao();

    private static final String TAG = DbHelperExecutor.class.getName();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE `notification` (`notifyId` INTEGER, "
                    + "`short_name` TEXT, `to_currency` TEXT, `limit` DOUBLE, `shouldNotify` BOOLEAN, PRIMARY KEY(`notifyId`), FOREIGN KEY(`notifyId`) REFERENCES favourites(`uid`))"
            );
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "currency-tracker")
                            .addMigrations(MIGRATION_1_2)
                            .addCallback(sAppDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static AppDatabase.Callback sAppDatabaseCallback = new AppDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
//                FavouritesCurrencyDAO currencyDao = INSTANCE.currencyDao();
//                NotificationLimitDAO notificationLimitDao = INSTANCE.notificationLimitDao();
//
//                currencyDao.deleteAll();
//                notificationLimitDao.deleteAll();
//
//                currencyDao.insertAll(
//                        new FavouriteCurrency(  "AED", "United Arab Emirates Dirham","aed.png", false),
//                        new FavouriteCurrency("AFN","Afghan Afghani","afn.png", false),
//                        new FavouriteCurrency(  "ALL", "Albanian Lek","all.png", false),
//                        new FavouriteCurrency("AMD", "Armenian Dram","amd.png", false),
//                        new FavouriteCurrency("ANG", "Netherlands Antillean Guilder","ang.png", false),
//                        new FavouriteCurrency("AOA", "Angolan Kwanza","aoa.png", false),
//                        new FavouriteCurrency( "ARS", "Argentine Peso","ars.png", false),
//                        new FavouriteCurrency( "AUD", "Australian Dollar","aud.png", false),
//                        new FavouriteCurrency("AWG", "Aruban Florin","awg.png", false),
//                        new FavouriteCurrency("AZN", "Azerbaijani Manat","azn.png", false),
//                        new FavouriteCurrency("BAM", "Bosnia-Herzegovina Convertible Mark","bam.png", false),
//                        new FavouriteCurrency("BBD", "Barbadian Dollar","bbd.png", false),
//                        new FavouriteCurrency("BDT", "Bangladeshi Taka","bdt.png", false),
//                        new FavouriteCurrency("BGN", "Bulgarian Lev","bgn.png", false),
//                        new FavouriteCurrency("BHD", "Bahraini Dinar","bhd.png", false),
//                        new FavouriteCurrency("BIF", "Burundian Franc","bif.png", false),
//                        new FavouriteCurrency("BMD", "Bermudan Dollar","bmd.png", false),
//                        new FavouriteCurrency("BND", "Brunei Dollar","bnd.png", false),
//                        new FavouriteCurrency("BOB", "Bolivian Boliviano","bob.png", false),
//                        new FavouriteCurrency( "BRL", "Brazilian Real","brl.png", false),
//                        new FavouriteCurrency("BSD", "Bahamian Dollar","bsd.png", false),
//                        new FavouriteCurrency("BTC", "Bitcoin","btc.png", false),
//                        new FavouriteCurrency("BTN", "Bhutanese Ngultrum","btn.png", false),
//                        new FavouriteCurrency("BWP", "Botswanan Pula","bwp.png", false),
//                        new FavouriteCurrency("BYN", "Belarusian Ruble","byn.png", false),
//                        new FavouriteCurrency("BZD", "Belize Dollar","bzd.png", false),
//                        new FavouriteCurrency("CAD", "Canadian Dollar","caf.png", false),
//                        new FavouriteCurrency("CDF", "Congolese Franc","cdf.png", false),
//                        new FavouriteCurrency("CHF", "Swiss Franc","chf.png", false),
//                        new FavouriteCurrency("CLF", "Chilean Unit of Account (UF)","clf.png", false),
//                        new FavouriteCurrency("CLP", "Chilean Peso","clp.png", false),
//                        new FavouriteCurrency("CNH", "Chinese Yuan (Offshore)","cnh.png", false),
//                        new FavouriteCurrency("CNY", "Chinese Yuan","cny.png", false),
//                        new FavouriteCurrency("COP", "Colombian Peso","cop.png", false),
//                        new FavouriteCurrency("CRC", "Costa Rican Colón","crc.png", false),
//                        new FavouriteCurrency("CUC", "Cuban Convertible Peso","cuc.png", false),
//                        new FavouriteCurrency("CUP", "Cuban Peso","cup.png", false),
//                        new FavouriteCurrency("CVE", "Cape Verdean Escudo", "cve.png", false),
//                        new FavouriteCurrency("CZK", "Czech Republic Koruna","czk.png", false),
//                        new FavouriteCurrency("DJF", "Djiboutian Franc","djf.png", false),
//                        new FavouriteCurrency("DKK", "Danish Krone","dkk.png", false),
//                        new FavouriteCurrency("DOP", "Dominican Peso","dop.png", false),
//                        new FavouriteCurrency("DZD", "Algerian Dinar","dzd.png", false),
//                        new FavouriteCurrency("EGP", "Egyptian Pound","egp.png", false),
//                        new FavouriteCurrency("ERN", "Eritrean Nakfa","ern.png", false),
//                        new FavouriteCurrency("ETB", "Ethiopian Birr","etb.png", false),
//                        new FavouriteCurrency("EUR", "Euro","eur.png", true),
//                        new FavouriteCurrency("FJD", "Fijian Dollar","fjd.png", false),
//                        new FavouriteCurrency("FKP", "Falkland Islands Pound","fkp.png", false),
//                        new FavouriteCurrency("GBP", "British Pound Sterling","gbp.png", false),
//                        new FavouriteCurrency("GEL", "Georgian Lari","gel.png", false),
//                        new FavouriteCurrency("GGP", "Guernsey Pound","ggp.png", false),
//                        new FavouriteCurrency("GHS", "Ghanaian Cedi","ghs.png", false),
//                        new FavouriteCurrency("GIP", "Gibraltar Pound","gip.png", false),
//                        new FavouriteCurrency("GMD", "Gambian Dalasi","gmd.png", false),
//                        new FavouriteCurrency("GNF", "Guinean Franc","gnf.png", false),
//                        new FavouriteCurrency("GTQ", "Guatemalan Quetzal","gtq.png", false),
//                        new FavouriteCurrency("GYD", "Guyanaese Dollar","gyd.png", false),
//                        new FavouriteCurrency("HKD", "Hong Kong Dollar","hkd.png", false),
//                        new FavouriteCurrency("HNL", "Honduran Lempira","hnl.png", false),
//                        new FavouriteCurrency("HRK", "Croatian Kuna","hrk.png", false),
//                        new FavouriteCurrency("HTG", "Haitian Gourde","htg.png", false),
//                        new FavouriteCurrency("HUF", "Hungarian Forint","huf.png", false),
//                        new FavouriteCurrency("IDR", "Indonesian Rupiah","idr.png", false),
//                        new FavouriteCurrency("ILS", "Israeli New Sheqel","ils.png", false),
//                        new FavouriteCurrency("IMP", "Manx pound","imp.png", false),
//                        new FavouriteCurrency("INR", "Indian Rupee","inr.png", false),
//                        new FavouriteCurrency("IQD", "Iraqi Dinar","iqd.png", false),
//                        new FavouriteCurrency("IRR", "Iranian Rial","irr.png", false),
//                        new FavouriteCurrency("ISK", "Icelandic Króna","isk.png", false),
//                        new FavouriteCurrency("JEP", "Jersey Pound","jep.png", false),
//                        new FavouriteCurrency("JMD", "Jamaican Dollar","jmd.png", false),
//                        new FavouriteCurrency("JOD", "Jordanian Dinar","jod.png", false),
//                        new FavouriteCurrency("JPY", "Japanese Yen","jpy.png", false),
//                        new FavouriteCurrency("KES", "Kenyan Shilling","kes.png", false),
//                        new FavouriteCurrency("KGS", "Kyrgystani Som","kgs.png", false),
//                        new FavouriteCurrency("KHR", "Cambodian Riel","khr.png", false),
//                        new FavouriteCurrency("KMF", "Comorian Franc","kmf.png", false),
//                        new FavouriteCurrency("KPW", "North Korean Won","kpw.png", false),
//                        new FavouriteCurrency("KRW", "South Korean Won","krw.png", false),
//                        new FavouriteCurrency("KWD", "Kuwaiti Dinar","kwd.png", false),
//                        new FavouriteCurrency("KYD", "Cayman Islands Dollar","kyd.png", false),
//                        new FavouriteCurrency("KZT", "Kazakhstani Tenge","kzt.png", false),
//                        new FavouriteCurrency("LAK", "Laotian Kip","lak.png", false),
//                        new FavouriteCurrency("LBP", "Lebanese Pound","lbp.png", false),
//                        new FavouriteCurrency("LKR", "Sri Lankan Rupee","lkr.png", false),
//                        new FavouriteCurrency("LRD", "Liberian Dollar","lrd.png", false),
//                        new FavouriteCurrency("LSL", "Lesotho Loti","lsl.png", false),
//                        new FavouriteCurrency("LYD", "Libyan Dinar","lyd.png", false),
//                        new FavouriteCurrency("MAD", "Moroccan Dirham","mad.png", false),
//                        new FavouriteCurrency("MDL", "Moldovan Leu","mdl.png", false),
//                        new FavouriteCurrency("MGA", "Malagasy Ariary","mga.png", false),
//                        new FavouriteCurrency("MKD", "Macedonian Denar","mdk.png", false),
//                        new FavouriteCurrency("MMK", "Myanma Kyat","mmk.png", false),
//                        new FavouriteCurrency("MNT", "Mongolian Tugrik","mnt.png", false),
//                        new FavouriteCurrency("MOP", "Macanese Pataca","mop.png", false),
//                        new FavouriteCurrency("MRO", "Mauritanian Ouguiya (pre-2018)","mro.png", false),
//                        new FavouriteCurrency("MRU", "Mauritanian Ouguiya","mru.png", false),
//                        new FavouriteCurrency("MUR", "Mauritian Rupee","mur.png", false),
//                        new FavouriteCurrency("MVR", "Maldivian Rufiyaa","mvr.png", false),
//                        new FavouriteCurrency("MWK", "Malawian Kwacha","mwk.png", false),
//                        new FavouriteCurrency("MXN", "Mexican Peso","mxn.png", false),
//                        new FavouriteCurrency("MYR", "Malaysian Ringgit","myr.png", false),
//                        new FavouriteCurrency("MZN", "Mozambican Metical","mzn.png", false),
//                        new FavouriteCurrency("NAD", "Namibian Dollar","nad.png", false),
//                        new FavouriteCurrency("NGN", "Nigerian Naira","ngn.png", false),
//                        new FavouriteCurrency("NIO", "Nicaraguan Córdoba","nio.png", false),
//                        new FavouriteCurrency("NOK", "Norwegian Krone","nok.png", false),
//                        new FavouriteCurrency("NPR", "Nepalese Rupee","npr.png", false),
//                        new FavouriteCurrency("NZD", "New Zealand Dollar","nzd.png", false),
//                        new FavouriteCurrency("OMR", "Omani Rial","omr.png", false),
//                        new FavouriteCurrency("PAB", "Panamanian Balboa","pab.png", false),
//                        new FavouriteCurrency("PEN", "Peruvian Nuevo Sol","pen.png", false),
//                        new FavouriteCurrency("PGK", "Papua New Guinean Kina","pgk.png", false),
//                        new FavouriteCurrency("PHP", "Philippine Peso","php.png", false),
//                        new FavouriteCurrency("PKR", "Pakistani Rupee","pkr.png", false),
//                        new FavouriteCurrency("PLN", "Polish Zloty","pln.png", true),
//                        new FavouriteCurrency("PYG", "Paraguayan Guarani","pyg.png", false),
//                        new FavouriteCurrency("QAR", "Qatari Rial","qar.png", false),
//                        new FavouriteCurrency("RON", "Romanian Leu","ron.png", false),
//                        new FavouriteCurrency("RSD", "Serbian Dinar","rsd.png", false),
//                        new FavouriteCurrency("RUB", "Russian Ruble","rub.png", false),
//                        new FavouriteCurrency("RWF", "Rwandan Franc","rwf.png", false),
//                        new FavouriteCurrency("SAR", "Saudi Riyal","sar.png", false),
//                        new FavouriteCurrency("SBD", "Solomon Islands Dollar","sbd.png", false),
//                        new FavouriteCurrency("SCR", "Seychellois Rupee","scr.png", false),
//                        new FavouriteCurrency("SDG", "Sudanese Pound","sdg.png", false),
//                        new FavouriteCurrency("SEK", "Swedish Krona","sek.png", false),
//                        new FavouriteCurrency("SGD", "Singapore Dollar","sgd.png", false),
//                        new FavouriteCurrency("SHP", "Saint Helena Pound","shp.png", false),
//                        new FavouriteCurrency("SLL", "Sierra Leonean Leone","sll.png", false),
//                        new FavouriteCurrency("SOS", "Somali Shilling","sos.png", false),
//                        new FavouriteCurrency("SRD", "Surinamese Dollar","srd.png", false),
//                        new FavouriteCurrency("SSP", "South Sudanese Pound","ssp.png", false),
//                        new FavouriteCurrency("STD", "São Tomé and Príncipe Dobra (pre-2018)","std.png", false),
//                        new FavouriteCurrency("STN", "São Tomé and Príncipe Dobra","stn.png", false),
//                        new FavouriteCurrency("SVC", "Salvadoran Colón","svc.png", false),
//                        new FavouriteCurrency("SYP", "Syrian Pound","syp.png", false),
//                        new FavouriteCurrency("SZL", "Swazi Lilangeni","szl.png", false),
//                        new FavouriteCurrency("THB", "Thai Baht","thb.png", false),
//                        new FavouriteCurrency("TJS", "Tajikistani Somoni","tjs.png", false),
//                        new FavouriteCurrency("TMT", "Turkmenistani Manat","tmt.png", false),
//                        new FavouriteCurrency("TND", "Tunisian Dinar","tnd.png", false),
//                        new FavouriteCurrency("TOP", "Tongan Pa'anga","top.png", false),
//                        new FavouriteCurrency("TRY", "Turkish Lira","try.png", false),
//                        new FavouriteCurrency("TTD", "Trinidad and Tobago Dollar","ttd.png", false),
//                        new FavouriteCurrency("TWD", "New Taiwan Dollar","twd.png", false),
//                        new FavouriteCurrency("TZS", "Tanzanian Shilling","tzs.png", false),
//                        new FavouriteCurrency("UAH", "Ukrainian Hryvnia","uah.png", false),
//                        new FavouriteCurrency("UGX", "Ugandan Shilling","ugx.png", false),
//                        new FavouriteCurrency("USD", "United States Dollar","usd.png", true),
//                        new FavouriteCurrency("UYU", "Uruguayan Peso","uyu.png", false),
//                        new FavouriteCurrency("UZS", "Uzbekistan Som","uzs.png", false),
//                        new FavouriteCurrency("VEF", "Venezuelan Bolívar Fuerte","vef.png", false),
//                        new FavouriteCurrency("VND", "Vietnamese Dong","vnd.png", false),
//                        new FavouriteCurrency("VUV", "Vanuatu Vatu","vuv.png", false),
//                        new FavouriteCurrency("WST", "Samoan Tala","wst.png", false),
//                        new FavouriteCurrency("XAF", "CFA Franc BEAC","xaf.png", false),
//                        new FavouriteCurrency("XAG", "Silver Ounce","xag.png", false),
//                        new FavouriteCurrency("XAU", "Gold Ounce","xau.png", false),
//                        new FavouriteCurrency("XCD", "East Caribbean Dollar","xcd.png", false),
//                        new FavouriteCurrency("XDR", "Special Drawing Rights","xdr.png", false),
//                        new FavouriteCurrency("XOF", "CFA Franc BCEAO","xof.png", false),
//                        new FavouriteCurrency("XPD", "Palladium Ounce","xpd.png", false),
//                        new FavouriteCurrency("XPF", "CFP Franc","xpf.png", false),
//                        new FavouriteCurrency("XPT", "Platinum Ounce","xpt.png", false),
//                        new FavouriteCurrency("YER", "Yemeni Rial","yer.png", false),
//                        new FavouriteCurrency("ZAR", "South African Rand","zar.png", false),
//                        new FavouriteCurrency("ZMW", "Zambian Kwacha","zmw.png", false),
//                        new FavouriteCurrency("ZWL", "Zimbabwean Dollar","zwl.png", false)
//                );
//
//                notificationLimitDao.insertAll(
//                        new NotificationLimit("PLN","EUR", 5.2,true),
//                        new NotificationLimit("EUR", "USD", 2.1,false)
//                );

                System.out.println("CREATED!!!");
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                Log.d(AppDatabase.TAG, "DB mounted");
                Log.d(AppDatabase.TAG, "CURRENCIES: " + INSTANCE.currencyDao().countRows());
                Log.d(AppDatabase.TAG, "LIMITS: " + INSTANCE.notificationLimitDao().countRows());
            });
        }
    };
}