package com.saturn.sort.merge;

public class Config {

    private String inputDir;
    private String outputDir;
    private String tempDir;
    private int MaxLinePerFile;
    private boolean antiDuplicate;
    private int progressShowIntervalSecond;

    private boolean needCut;

    public boolean isNeedCut() {
        return needCut;
    }

    public void setNeedCut(boolean needCut) {
        this.needCut = needCut;
    }

    public int getProgressShowIntervalSecond() {
        return progressShowIntervalSecond;
    }

    public void setProgressShowIntervalSecond(int progressShowIntervalSecond) {
        this.progressShowIntervalSecond = progressShowIntervalSecond;
    }

    public boolean isAntiDuplicate() {
        return antiDuplicate;
    }

    public void setAntiDuplicate(boolean antiDuplicate) {
        this.antiDuplicate = antiDuplicate;
    }

    public String getInputDir() {
        return inputDir;
    }

    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public int getMaxLinePerFile() {
        return MaxLinePerFile;
    }

    public void setMaxLinePerFile(int maxLinePerFile) {
        MaxLinePerFile = maxLinePerFile;
    }
}
