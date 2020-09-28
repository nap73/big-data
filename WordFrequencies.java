import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class WordFrequencies {

    public static void main(String[] args) throws IOException,InterruptedException,ClassNotFoundException {

        
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(WordFrequencies.class);
        job.setJobName("WordFrequencies");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path("output1"));

        job.setMapperClass(Mapper1.class);
        job.setReducerClass(Reducer1.class);
        job.setCombinerClass(AdjacentWordReducer.class);
        job.setPartitionerClass(AdjacentWordPartitioner.class);
        job.setNumReduceTasks(3);

        job.setOutputKeyClass(AdjacentWord.class);
        job.setOutputValueClass(IntWritable.class);
        job.waitForCompletion(true);

               
        Job job2 = Job.getInstance(new Configuration());
        job2.setJarByClass(WordFrequencies.class);
        job2.setJobName("WordFrequencies");

        job2.setSortComparatorClass(DescendingKeyComparator.class);
        FileInputFormat.addInputPath(job2, new Path("output1"));
        FileOutputFormat.setOutputPath(job2, new Path(args[1]));

        job2.setMapperClass(Mapper2.class);
        job2.setReducerClass(Reducer2.class);
        job2.setNumReduceTasks(1);

        job2.setOutputKeyClass(DoubleWritable.class);
        job2.setOutputValueClass(AdjacentWord.class);
        System.exit(job2.waitForCompletion(true) ? 0 : 1);
    }
}
