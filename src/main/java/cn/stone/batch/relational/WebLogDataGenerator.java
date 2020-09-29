package cn.stone.batch.relational;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 11:14 2020/9/21
 * @Modified By:
 */
public class WebLogDataGenerator {
    public static void main(String[] args) {
        /*if (args.length < 2) {
            System.out.println("WebLogDataGenerator <numberOfDocuments> <numberOfVisits>");
            System.exit(1);
        }
        int noDocs = Integer.parseInt(args[0]);
        int noVisits = Integer.parseInt(args[1]);*/
        int noDocs = 3;
        int noVisits = 5;
        String[] filterKWs = {"editors", "oscillations", "convection"};
        String[] words = {"Lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit", "sed", "diam", "nonummy",
                "nibh", "euismod", "tincidunt", "ut", "laoreet", "dolore", "magna", "aliquam", "erat", "volutpat", "Ut", "wisi", "enim",
                "ad", "minim", "veniam", "quis", "nostrud", "exerci", "tation", "ullamcorper", "suscipit", "lobortis", "nisl", "ut", "aliquip", "ex", "ea", "commodo"
        };
        final String outPath = System.getProperty("java.io.tmpdir");
        System.out.println(outPath);
        System.out.println("Generating documents files...");
        genDocs(noDocs, filterKWs, words, outPath + "/documents");
        System.out.println("Generating ranks files...");
        genRanks(noDocs, outPath + "/ranks");
        System.out.println("Generating visits files...");
        genVisits(noVisits, noDocs, outPath + "/visits");
    }

    private static void genDocs(int noDocs, String[] filterKeyWords, String[] words, String path) {
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < noDocs; i++) {
                int wordsInDoc = rand.nextInt(40) + 10;
                StringBuilder doc = new StringBuilder("url_" + i + "|");
                for (int j = 0; j < wordsInDoc; j++) {
                    if (rand.nextDouble() > 0.9) {
                        doc.append(filterKeyWords[rand.nextInt(filterKeyWords.length)] + " ");
                    } else {
                        doc.append(words[rand.nextInt(words.length)] + " ");
                    }
                    doc.append("|\n");
                    fw.write(doc.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void genRanks(int noDocs, String path) {
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < noDocs; i++) {
                StringBuilder rank = new StringBuilder(rand.nextInt(100) + "|");
                rank.append("url_" + i + "|");
                rank.append(rand.nextInt(10) + rand.nextInt(50) + "|\n");
                fw.write(rank.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void genVisits(int noVisits, int noDocs, String path) {
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < noVisits; i++) {
                int year = 2000 + rand.nextInt(10);
                int month = rand.nextInt(12) + 1;
                int day = rand.nextInt(27) + 1;
                StringBuilder visit = new StringBuilder(rand.nextInt(256) + "."
                        + rand.nextInt(256) + "."
                        + rand.nextInt(256) + "."
                        + rand.nextInt(256) + "|");
                visit.append("url_" + rand.nextInt(noDocs) + "|");
                visit.append(year + "-" + month + "-" + day + "|");
                visit.append("0.12|Mozilla Firefox 3.1|de|de|Nothing special|124|\\n");
                fw.append(visit.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
