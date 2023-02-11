package com.unipi.nickdap.p19039.smartalert;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTTS
{
    // A variable of type TextToSpeach
    private TextToSpeech tts;

    // The Constructor of class: MyTTS
    public MyTTS(Context ctx)
    {
        tts = new TextToSpeech(ctx,initListener);
    }

    TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener()
    {
        // Ώς παράμετρο περνάω το status της μηχανής
        @Override
        public void onInit(int status)
        {
            if (status==TextToSpeech.SUCCESS)
            {
                tts.setLanguage(Locale.ENGLISH);
            }
        }
    };

    public void speak(String message)
    {
        // Όταν στέλνω το string, Μπορεί να λέει κάτι άλλο εκείνη τη στιγμή,
        // οπότε το βάζει σε ουρά ( Δεύτερη παράμετρος )
        tts.speak(message,TextToSpeech.QUEUE_ADD,null,null);
    }
}
