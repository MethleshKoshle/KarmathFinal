package com.methleshkoshle.karmathfinal.constant;

import androidx.appcompat.app.AppCompatActivity;

import com.methleshkoshle.karmathfinal.R;
import java.util.HashMap;
import java.util.Map;

/**
 * Index mapping:
 * 0 - Vishesh
 * 1 - Bhagwan
 * 2 - Dard
 * 3 - Dosti
 * 4 - Guru
 * 5 - Lagan
 * 6 - Pyaar
 * 7 - Prerna
 * 8 - Tyag
 */

public class Constant extends AppCompatActivity {

    public static Integer CATEGORY_COUNT = 9;

    public static final String contentLocalUrl = "http://10.0.2.2:8090/content/";
    public static final String contentProdUrl = "https://drive.google.com/uc?export=download&id=1aYxImWjN5AyJ0LboNSioiKGMutgC3Gei";
    public static final String thoughtProdUrl = "https://drive.google.com/uc?export=download&id=1xWwRsiW7pO1jMWyYDSg3iEtJ3rqj3a8P";
    public static final String [] hindiName = {
            "विशेष", "भगवान", "दर्द", "दोस्ती",
            "गुरु", "लगन", "प्रेम", "प्रेरणा", "त्याग"
    };

    public static final String [] name = {
            "Vishesh", "Bhagwan", "Dard", "Dosti",
            "Guru", "Lagan", "Pyaar", "Prerna", "Tyag"
    };

    public static final String [] fileName = {
            "Vishesh.txt", "Bhagwan.txt", "Dard.txt", "Dosti.txt",
            "Guru.txt", "Lagan.txt", "Pyaar.txt", "Prerna.txt", "Tyag.txt"
    };

    public static final String [] tempFileName = {
            "VisheshTemp.txt", "BhagwanTemp.txt", "DardTemp.txt",
            "DostiTemp.txt", "GuruTemp.txt", "LaganTemp.txt",
            "PyaarTemp.txt", "PrernaTemp.txt", "TyagTemp.txt"
    };


    public static final String [] contentDescription = {
            "विशेष कवितायेँ", "परमेश्वर एक कल्पना", "जीवन में बहुत दर्द है",
            "दोस्त जीवन सरल कर देते है", "जीवन के मार्गदर्शक", "विद्यार्थी की ततपरता",
            "इस संसार में प्रेम से बढ़कर कुछ नहीं", "निराशा से मुक्त रहिए", "कुछ पाने के लिए कुछ खोना पड़ता है"
    };

    public static final Map<String, Integer> contentIndex = new HashMap<String, Integer>() {{
        put("Vishesh", 0); put("Bhagwan", 1); put("Dard", 2);
        put("Dosti", 3); put("Guru", 4); put("Lagan", 5);
        put("Pyaar", 6); put("Prerna", 7); put("Tyag", 8);
    }};

    public static final String songFileName="Song.txt";

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
            R.drawable.ic_vishesh, R.drawable.ic_bhagwan, R.drawable.ic_dard,
            R.drawable.ic_dosti, R.drawable.ic_guru, R.drawable.ic_lagan,
            R.drawable.ic_prema, R.drawable.ic_prerna, R.drawable.ic_tyag
    };

    public static final int [] menuResource = {
            R.menu.vishesh_menu, R.menu.bhagwan_menu, R.menu.dard_menu,
            R.menu.dosti_menu, R.menu.guru_menu, R.menu.lagan_menu,
            R.menu.prema_menu, R.menu.prerna_menu, R.menu.tyag_menu
    };

    public static final int [] labelResID = {
            R.string.VisheshHindi, R.string.BhagwanHindi, R.string.DardHindi,
            R.string.DostiHindi, R.string.GuruHindi, R.string.LaganHindi,
            R.string.PremaHindi, R.string.PrernaHindi, R.string.TyagHindi
    };

    public static String ownerMailId = "miitbh1@gmail.com";

    public static String playstoreUrl = "https://play.google.com/store/apps/details?id=com.methleshkoshle.myapplication";
    public static String linkedInUrl = "https://www.linkedin.com/in/methlesh-koshle-208a64145/";
    public static String gitHubUrl = "https://github.com/MethleshKoshle";
    public static String instagramUrl = "https://www.instagram.com/methlesh_koshle/";
    public static String facebookUrl = "https://www.facebook.com/methlesh.koshle";

    public static String NO_INTERNET_MESSAGE = "Connect to Internet to load new content!";
    public static String SONG_MODE_ON = "Song mode on.";
    public static String SONG_MODE_OFF = "Song mode off.";
    public static String SONG = "song";
    public static String CONTENT = "content";


}
