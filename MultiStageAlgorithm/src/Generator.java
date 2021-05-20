import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Generator {

    public static void main(String[] args) {

        try {
            BufferedWriter writer = new BufferedWriter(new PrintWriter(
                    "D:\\DataMaining\\DataMining_gvozdev_11-904\\MultiStageAlgorithm\\src\\data\\data.txt",
                    "UTF-8"));
            int size;
            Random random = new Random();
            int count = 20;

            for (int i = 0; i < 60; i++){
                size = random.nextInt(9) + 1;
                for (int j = 0; j < size; j++) {
                    writer.write(Integer.toString(random.nextInt(count - 1) + 1));
                    writer.write(" ");
                }
                writer.write(" ;\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

}
