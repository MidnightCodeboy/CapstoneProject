/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Joe
 */
public class SoundClipper {
    
    //private ArrayList<Clip> clips;
    private ArrayList<AudioInputStream> streams;
    private HashMap<String, Integer> nameToIDMap;
    
    
    
    //
    private HashMap<String, String> soundPaths;
    private HashMap<String, Clip> clips;
    private HashMap<String, String> clipAssignments;
    
    public SoundClipper(){
        //clips = new ArrayList<>();
        //streams = new ArrayList<>();
        //nameToIDMap = new HashMap();
        
        soundPaths = new HashMap();
        clips = new HashMap();
        clipAssignments = new HashMap();
    }
    
    /*
    public int registerSound(String soundPath){
        int streamID = streams.size();
        
        AudioInputStream stream = getStream(soundPath);
        
        if (stream != null){
            streams.add(stream);
            return streamID;
        } else {
            return -1; // indicates error - no stream stored
        }
    }
    */
    
    /*
    public void play(int streamID, int loops){
        Clip clip = null;
        
        for (Clip c : clips){
            if (!c.isActive()){
                clip = c;
            }
        }
        
        if (clip == null) clip = getClip();
        clips.add(clip);
        
        try {
            clip.open(streams.get(streamID));
            clip.loop(loops);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace(System.out);
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        }
    }
    */
    
    // adds path to a list if stream can be got
    public boolean registerSoundPath(String soundName, String soundPath){
        
        AudioInputStream stream = getStream(soundPath);
        
        if (stream != null){
            soundPaths.put(soundName, soundPath);
            return true;
        }
        
        return false;
    }
    
    public void createClip(String clipName){
        if(!clips.containsKey(clipName)){
            clips.put(clipName, getClip());
        }
    }
    
    //
    public boolean assignSoundToClip(String soundName, String clipName){
        if (soundPaths.containsKey(soundName) && clips.containsKey(clipName)){
            //clipAssignments.put(clipName, soundName);
            
            Clip clip = clips.get(clipName);
            
            String soundPath = soundPaths.get(soundName);
            AudioInputStream stream = getStream(soundPath);
            
            try {
                clip.open(stream);
                
            } catch (LineUnavailableException lue) {
                lue.printStackTrace(System.out);
                return false;
            } catch (IOException ioe) {
                ioe.printStackTrace(System.out);
                return false;
            }
            return true;
        }
        
        return false;
    }
    
    /*
    public void play(String clipName, int loops){
        Clip clip = clips.get(clipName);
        
        String soundName = clipAssignments.get(clipName);
        String soundPath = soundPaths.get(soundName);
        AudioInputStream stream = getStream(soundPath);
        
        try {
            clip.open(stream);
            clip.loop(loops);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace(System.out);
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        }
    }

*/
    public void play(String clipName, int loops){
        Clip clip = clips.get(clipName);
        //clip.setFramePosition(0);
        
        clip.loop(loops);
    }
    
    public void stop(String clipName){
        Clip clip = clips.get(clipName);
        clip.stop();
    }
    
    public void start(String clipName){
        Clip clip = clips.get(clipName);
        clip.start();
    }
    
    //------------------------------------------------------------------------//
    
    private Clip getClip(){
        
        DataLine.Info lineInfo = new DataLine.Info(Clip.class, null);
        
        try {
            return (Clip)getMixer().getLine(lineInfo);

        } catch (LineUnavailableException lue) {
            lue.printStackTrace(System.out);
            return null;
        }
    }
    
    private AudioInputStream getStream(String soundPath){
        AudioInputStream stream;
        
        try {
            URL url = SoundClipper.class.getResource(soundPath);
            
            stream = AudioSystem.getAudioInputStream(url);
            stream.mark(1000000000);
        } catch (UnsupportedAudioFileException uafe) {
            System.out.println("made it here!! uafe");
            uafe.printStackTrace(System.out);
            return null;
        } catch (IOException ioe) {
            System.out.println("made it here!! ioe");
            ioe.printStackTrace(System.out);
            return null;
        }
        
        System.out.println("stream to return " + stream);
        return stream;
    }
    
    private Mixer getMixer(){
        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        return AudioSystem.getMixer(mixInfos[0]);
    }
    
    public void setStreamName(int id, String name){
        nameToIDMap.put(name, id);
    }
    
    public int getIDByName(String name){
        return nameToIDMap.get(name);
    }
}