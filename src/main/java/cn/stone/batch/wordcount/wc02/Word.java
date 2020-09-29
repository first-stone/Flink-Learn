package cn.stone.batch.wordcount.wc02;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 20:42 2020/9/17
 * @Modified By:
 */
@SuppressWarnings("serial")
public class Word {
    public String word;
    public int frequency;

    public Word() {
    }

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
