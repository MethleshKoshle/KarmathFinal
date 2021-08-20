package com.methleshkoshle.karmathfinal;

import android.content.Intent;
import java.util.HashMap;
import java.util.Map;

/**
 * Index mapping:
 * 0 - Agyat
 * 1 - Bhagwan
 * 2 - Dard
 * 3 - Dosti
 * 4 - Guru
 * 5 - Lagan
 * 6 - Pyaar
 * 7 - Prerna
 * 8 - Tyag
 */

public class Constant {

    public static final String [] hindiName = {
            "अज्ञात", "भगवान", "दर्द", "दोस्ती",
            "गुरु", "लगन", "प्रेम", "प्रेरणा", "त्याग"
    };

    public static final String [] name = {
            "Agyat", "Bhagwan", "Dard", "Dosti",
            "Guru", "Lagan", "Pyaar", "Prerna", "Tyag"
    };

    public static final String [] fileName = {
            "Agyat.txt", "Bhagwan.txt", "Dard.txt", "Dosti.txt",
            "Guru.txt", "Lagan.txt", "Pyaar.txt", "Prerna.txt", "Tyag.txt"
    };

    public static final String [] tempFileName = {
            "AgyatTemp.txt", "BhagwanTemp.txt", "DardTemp.txt",
            "DostiTemp.txt", "GuruTemp.txt", "LaganTemp.txt",
            "PyaarTemp.txt", "PrernaTemp.txt", "TyagTemp.txt"
    };


    public static final String [] contentDescription = {
            "अज्ञात कवितायेँ", "परमेश्वर एक कल्पना", "जीवन में बहुत दर्द है",
            "दोस्त जीवन सरल कर देते है", "जीवन के मार्गदर्शक", "विद्यार्थी की ततपरता",
            "इस संसार में प्रेम से बढ़कर कुछ नहीं", "निराशा से मुक्त रहिए", "कुछ पाने के लिए कुछ खोना पड़ता है"
    };

    public static final Map<String, Integer> contentIndex = new HashMap<String, Integer>() {{
        put("Agyat", 0); put("Bhagwan", 1); put("Dard", 2);
        put("Dosti", 3); put("Guru", 4); put("Lagan", 5);
        put("Pyaar", 6); put("Prerna", 7); put("Tyag", 8);
    }};

    public static final String tempSongFile="SongTemp.txt";

    public static final String [] sourceURL = {
            "https://drive.google.com/uc?export=download&id=1ZLUs31rvs5mXpmBy8YaxIA0AYLfzXstH",
            "https://drive.google.com/uc?export=download&id=1PTktNCb6RNB93oStv-vxSDVCkfCNw86v",
            "https://drive.google.com/uc?export=download&id=1balIvuyQmBmxifybTpDm5fmK0tq46jGb",
            "https://drive.google.com/uc?export=download&id=19_cYlNdXjjbMnK0WPvMDssYAToHrmdue",
            "https://drive.google.com/uc?export=download&id=1BOgE_livzQX85QteF5NgowIJ8cBxVy3_",
            "https://drive.google.com/uc?export=download&id=1ctnu8bqHzgM0RSRnPWpmtPAqxA9ZA1Si",
            "https://drive.google.com/uc?export=download&id=1O47PhC-ZjFD3gTimwan3Sa0siCrf_PX-",
            "https://drive.google.com/uc?export=download&id=1k5Ot5xhQT6IbJ3blPZAbONAtnIq48Z_W",
            "https://drive.google.com/uc?export=download&id=1K5bW-KmuSg_WzUUW6vz7O0Kj1zW-J-c7"
    };

    public static final String sourceSongURL = "https://drive.google.com/uc?export=download&id=1NNferlDkfEJ9Q1zBgkQpFwSVzBO4Y5lb";

    public static final int [] imageResource = {
            R.drawable.ic_agyat, R.drawable.ic_bhagwan, R.drawable.ic_dard,
            R.drawable.ic_dosti, R.drawable.ic_guru, R.drawable.ic_lagan,
            R.drawable.ic_prema, R.drawable.ic_prerna, R.drawable.ic_tyag
    };

    public static final int [] labelResID = {
            R.string.AgyatHindi, R.string.BhagwanHindi, R.string.DardHindi,
            R.string.DostiHindi, R.string.GuruHindi, R.string.LaganHindi,
            R.string.PrematHindi, R.string.PrernaHindi, R.string.TyagHindi
    };

    public Intent shareApp(String shareMessage){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Karmath");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        return shareIntent;
    }

}
