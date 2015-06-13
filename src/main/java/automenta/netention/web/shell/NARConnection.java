///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package automenta.netention.web.shell;
//
//import nars.NAR;
//import nars.io.out.TextOutput;
//import nars.io.out.TextOutput.LineOutput;
//
///**
// * An instance of a web socket session to a NAR
// * @author me
// */
//abstract public class NARConnection implements LineOutput {
//    public final NAR nar;
//    protected final TextOutput writer;
//    int cycleIntervalMS;
//    //private final TextReaction extraParser;
//
//
//    public NARConnection(NAR nar, int cycleIntervalMS) {
//        this.nar = nar;
//        this.cycleIntervalMS = cycleIntervalMS;
//
//        this.writer = new TextOutput(nar, this);
//    }
//
//    public void read(final String message) {
//        nar.input(message);
//
//        if (!running)
//            resume();
//    }
//
//    @Override
//    abstract public void println(CharSequence output);
//
//
//    boolean running = false;
//
//    public void resume() {
//        if (!running) {
//            running = true;
//            nar.start(cycleIntervalMS);
//        }
//    }
//    public void stop() {
//        running = false;
//        nar.stop();
//    }
//
//
//}
