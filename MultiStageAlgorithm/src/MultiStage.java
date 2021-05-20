import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class MultiStage {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("D:\\DataMaining\\DataMining_gvozdev_11-904\\MultiStageAlgorithm\\src\\data\\data.txt"));
        int count = scanner.nextInt();

        int[] arr = new int[count + 1];
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(new ArrayList<>());


        while (scanner.hasNext()){
            String s = scanner.next();
            if (!s.equals(";")){
                arr[Integer.parseInt(s)]++;
                lists.get(lists.size() - 1).add(Integer.parseInt(s));
            } else {
                lists.add(new ArrayList<>());
            }
        }

        int support = 3;

        Set<Integer> set = new HashSet<>();

        for (int i = 1; i < arr.length; i++){
            if (arr[i] >= support){
                set.add(i);
            }
        }

        class Pair{
            int a, b, count1, hash1, hash2;

            public Pair(int a, int b) {
                this.a = a;
                this.b = b;
                count1 = 0;
                hash1 = (a + b) % count;
                hash2 = (a + 2 * b) % count;
            }

            @Override
            public boolean equals(Object obj){
                return this.a == ((Pair)(obj)).a && this.b == ((Pair)(obj)).b ||
                        this.a == ((Pair)(obj)).b && this.b == ((Pair)(obj)).a;
            }

            @Override
            public int hashCode(){
                return Integer.hashCode(a) + Integer.hashCode(b);
            }
        }
        Set<Pair> pairs = new HashSet<>();
        Map<Pair, Integer> pairIntegerMap = new HashMap<>();

        for(int i = 0; i < 8; i++){
            List<Integer> list = lists.get(i);
            for (int j = 0; j < list.size(); j++){
                for (int k = j + 1; k < list.size(); k++){
                        Pair pair = new Pair(list.get(j), list.get(k));
                        pairIntegerMap.put(pair,
                                pairIntegerMap.get(pair) != null ?
                                        pairIntegerMap.get(pair) + 1 : 1);
                }
            }
        }

        List<List<Pair>> buckets1 = new ArrayList();
        int[] pairCount1 = new int[count + 1];
        List<List<Pair>> buckets2 = new ArrayList();
        int[] pairCount2 = new int[count + 1];

        for (int i = 0; i < count; i++){
            List<Pair> pairList = new ArrayList<>();

            for(Pair pair: pairIntegerMap.keySet()){
                if (pair.hash1 == i){
                    pairList.add(pair);
                    pairCount1[i] += pairIntegerMap.get(pair);
                }
            }
            buckets1.add(pairList);
        }

        for (int i = 0; i < count; i++){
            if (pairCount1[i] >= support){
                    pairs.addAll(buckets1.get(i));

            }
        }

        for (int i = 0; i < count; i++){
            List<Pair> pairList = new ArrayList<>();

            for(Pair pair: pairs){
                if (pair.hash2 == i){
                    pairList.add(pair);
                    pairCount2[i] += pairIntegerMap.get(pair);
                }
            }
            buckets2.add(pairList);
        }

        pairs.clear();
        for (int i = 0; i < count; i++){
            if (pairCount2[i] >= support){
                for (Pair pair: buckets2.get(i))
                    if (set.contains(pair.a) && set.contains(pair.b)) {
                        pairs.add(pair);
                    }
            }
        }

        System.out.println("count: " + (set.size() + pairs.size()));

        for (Integer integer: set){
            System.out.println("{" + integer + "}");
        }

        for (Pair p: pairs){
            System.out.println("{" + p.a + ", " + p.b + "}");
        }


    }
}
