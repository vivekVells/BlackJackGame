/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package charlie.audio;

import static charlie.audio.Effect.CHIPS_IN;
import static charlie.audio.Effect.PUSH;
import java.util.Random;

/**
 * This class implements the factory pattern for managing and playing sounds.
 * @author Ron Coleman
 */
public class SoundFactory {   
//    private final static Sound DEAL_SOUND0 = new Sound("audio/PlayingCardsPo_eOnFelt01_87.wav");
    private final static Sound DEAL_SOUND1 = new Sound("audio/tap.wav");
//    private final static Sound DEAL_SOUND2 = new Sound("audio/Telemet_33G_HD2-32076.wav");
    private final static Sound[] CHARLIE_SOUNDS = { 
        new Sound("audio/shazam2.wav")
    };
    private final static Sound[] BJ_SOUNDS = { 
        new Sound("audio/you-can-do-it.wav"), 
        new Sound("audio/you-got-it-1.wav"),
        new Sound("audio/wahoo.wav"),
//        new Sound("audio/mostimprs-2.wav")
    };
    private final static Sound[] NICE_SOUNDS = { 
        new Sound("audio/wow.wav"), 
        new Sound("audio/austin_yeahbaby_converted.wav"),
        new Sound("audio/woow.wav"),
        new Sound("audio/goodshot-2.wav"),
//        new Sound("audio/impressive-2.wav")
    };
    private final static Sound[] OUCH_SOUNDS = {
        new Sound("audio/evil_laf.wav"), 
        new Sound("audio/aaaah.wav"), 
        new Sound("audio/bone_converted.wav"), 
        new Sound("audio/glass.wav"),
        new Sound("audio/job-1.wav"),
        new Sound("audio/better-1.wav"),
        new Sound("audio/1doh.wav"),
        new Sound("audio/ow2.wav"),
        new Sound("audio/awwcrap.wav"),
//        new Sound("audio/good_grief.wav")
//        new Sound("audio/vplaugh.wav")
    };
    private final static Sound[] PUSH_SOUNDS ={ 
        new Sound("audio/trap.wav") ,
//        new Sound("audio/whatwsth.wav")
    };
    private final static Sound[] BREAK_SOUNDS = { 
        new Sound("audio/ouch.wav")
    };
    private final static Sound CHIPS_IN_SOUIND = new Sound("audio/Games_Poker_Chip_08950004.wav");
    private final static Sound CHIPS_OUT_SOUND = new Sound("audio/Games_Poker_Chip_08950003.wav");
    private final static Sound SHUFFLE_SOUND = new Sound("audio/013012_Casino-Cards_28_A1.wav");
    private final static Sound TURN_SOUND = new Sound("audio/Telemet_33G_HD2-32076.wav");
    private static long lastTime = System.currentTimeMillis();   
    protected static Random toss = new Random();
    private static boolean enabled = true;

    /**
     * Primes the sound line
     */
    public static void prime() {
        // Get any sound
        Sound sound = NICE_SOUNDS[0];
        
        // Set volume to allowed minimum
        sound.setVolume(-80.0f);
        
        // Play the sound
        sound.play();
        
        // Restore volume to allowed maximum
        sound.setVolume(6.0f);
    }
    
    /**
     * Enable sounds to be played.
     * @param state True if sounds are enabled.
     */
    public static void enable(boolean state) {
        enabled = state;
    }
    
    /**
     * Plays a sound
     * @param e Effect Effect to play
     */
    public static void play(Effect e) {
        if(!enabled)
            return;
        
        switch(e) {
            case TURN:
                TURN_SOUND.play();
                break;
            case SHUFFLING:
                backgroundPlay(SHUFFLE_SOUND,2);
                break;
            case DEAL:
                backgroundPlay(DEAL_SOUND1,1);
                break;
            case CHARLIE:
                CHARLIE_SOUNDS[toss.nextInt(CHARLIE_SOUNDS.length)].play();
                break;
            case BJ:
                BJ_SOUNDS[toss.nextInt(BJ_SOUNDS.length)].play();
                break;
            case NICE:
                NICE_SOUNDS[toss.nextInt(NICE_SOUNDS.length)].play();
                break;
            case TOUGH:
                OUCH_SOUNDS[toss.nextInt(OUCH_SOUNDS.length)].play();  
                break;
            case PUSH:
                PUSH_SOUNDS[toss.nextInt(PUSH_SOUNDS.length)].play();
                break;                
            case BUST:
                BREAK_SOUNDS[toss.nextInt(BREAK_SOUNDS.length)].play();
                break; 
            case CHIPS_IN:
                backgroundPlay(CHIPS_IN_SOUIND,1);
                break;
            case CHIPS_OUT:
                backgroundPlay(CHIPS_OUT_SOUND,1);
                break;                
        }        
    }
    
    protected static void backgroundPlay(final Sound sound,final int loop) {
        long now = System.currentTimeMillis();
        
        if(now - lastTime < 500)
            return;
        
        lastTime = now;
        
        new Thread(new Runnable() { 
            @Override
            public void run() {
                for(int i=0; i < loop; i++)
                    sound.play();
            }
        }).start();
    } 
}
