import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CountableBloomFilter {
    public Integer getCount() {
        return count;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getHfAmount() {
        return hfAmount;
    }

    public Double getPrecision() {
        return precision;
    }

    private Set<String> worlds;

    private int[] arr;

    private Integer count;

    private final Integer size;

    private final Integer hfAmount;

    private final Double precision;

    private Integer[] seeds;

    public CountableBloomFilter(Double precision, Integer count) {
        this.precision = precision;
        this.count = count;
        this.size = (int) (- (count * Math.log(precision)) /(Math.log(2) * Math.log(2)));
        this.hfAmount = (int) ((double) (size) /(double) (count) * Math.log(2));
        arr = new int[size];
        Set<Integer> seedsSet = new HashSet<>();
        Random random = new Random();
        while (seedsSet.size() != hfAmount){
            seedsSet.add(Math.abs(random.nextInt()));
        }
        seeds = seedsSet.toArray(Integer[]::new);
    }


    private Integer getHash(String s, Integer seed, Integer m){
        Long hash = 1L;
        for(int i = 0; i < s.length(); i++){
            hash = (hash * seed.longValue() + s.charAt(i));
        }
        hash = Math.abs(hash % m);
        return  hash.intValue();
    }

    public void addValue(String s){
        for (int i = 0; i < seeds.length; i++){
            arr[getHash(s, seeds[i], size)]++;
        }
    }

    public void delete(String s){
        if (contains(s)){
            for (int i = 0; i < seeds.length; i++){
                arr[getHash(s, seeds[i], size)]--;
            }
        } else {
            throw new IllegalArgumentException("CBF not contains \"" + s + "\"");
        }

    }

    public boolean contains(String s){
        for (int i = 0; i < seeds.length; i++){
            if (arr[getHash(s, seeds[i], size)] <= 0){
                return false;
            }
        }
        return true;
    }
}
