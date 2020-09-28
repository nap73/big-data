import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class AdjacentWordPartitioner extends Partitioner<AdjacentWord,IntWritable> {

    @Override
    public int getPartition(AdjacentWord wordPair, IntWritable intWritable, int numPartitions) {
        return (wordPair.getWord().hashCode() & Integer.MAX_VALUE ) % numPartitions;
    }
}
