package cscie55.hw7;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.*;

/**
 * LinkParser class:
 * This class parse and accumulate tag by link using hadoop map reduce framework.
 *
 * @version     1.0
 * @since       1.0
 */
public class LinkParser extends Configured implements Tool {

    /**
     * main() method
     *
     * @params args A string array of arguments.
     */
    public static void main(String args[]) throws Exception {
        int res = ToolRunner.run(new LinkParser(), args);
        System.exit(res);
    }

    /**
     * run() method
     *
     * @params args A string array of arguments.
     */
    public int run(String[] args) throws Exception {
        Path inputPath = new Path(args[0]);
        Path outputPath = new Path(args[1]);

        Configuration conf = getConf();
        Job job = new Job(conf, this.getClass().toString());

        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        job.setJobName("WordCountByFile");
        job.setJarByClass(LinkParser.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(MyMap.class);
        //job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    /**
     * Map class:
     * A nested static class that extends Mapper class.
     */
    public static class MyMap extends Mapper<LongWritable, Text, Text, Text> {

        /**
         * map() method.
         * @param key
         * @param value
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void map(LongWritable key, Text value,
                        Mapper.Context context) throws IOException, InterruptedException {

            String line = value.toString();
            Link link = Link.parse(line);
            String linkString = link.url();
            List<String> tags = link.tags();

            Iterator<String> tagsIterator = tags.iterator();
            while (tagsIterator.hasNext()) {
                context.write(new Text(linkString), new Text(tagsIterator.next()));
            } //End of WHILE loop
        } //End of map() method
    } //End of nested Map class

    /**
     * Reduce class:
     * A nested static class that extends Reducer class.
     */
    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        private Map<String, Set<String>> linkTags = new TreeMap<String, Set<String>>();

        /**
         * reduce() method.
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            Set<String> tags = new HashSet<>();
            //Iterate through the values
            for (Text value : values) {
                tags.add(value.toString());
                //context.write(key, value);
            } //End of FOR loop

            linkTags.put(key.toString(),tags);
        } //End of reduce() method


        @Override
        public void cleanup(Context context) throws IOException, InterruptedException{

            String tagString = "";
            String key = "";
            Set<String> tags = null;
            Iterator<String> it = null;

            for (Map.Entry<String, Set<String>> entry : linkTags.entrySet()) {
                key = entry.getKey();
                tags = entry.getValue();
                it = tags.iterator();
                while(it.hasNext()) {
                    tagString += it.next() + ",";
                }
                context.write(new Text(key), new Text(tagString));
                tagString = "";
            }
        }
    } //End of nested Reduce class

} //End of LinkParser class

