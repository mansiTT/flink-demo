package com.batch.filetoes;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.elasticsearch.ElasticsearchIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Watch;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.batch.filetoes.common.BatchOptions;
import com.batch.filetoes.es.dto.SalesReport;
import com.batch.filetoes.transform.ParseFileRecordFn;

/**
 * Example to process file content
 *
 * <p>
 * This class, {@link BatchFileToESProcessing}, is simple batch file procesor example.
 * Input source is .csv file and output is elasticsearch. Here, for simplicity, we
 * don't show any error-checking or argument processing, and focus on
 * construction of the pipeline, which chains together the application of core
 * transforms.
 *
 * Concepts:
 *
 * <pre>
 *   1. Reading data from text files
 *   2. Specifying 'inline' transforms
 *   3. Writing data to ES
 * </pre>
 *
 * <p>
 * No arguments are required to run this pipeline. It will be executed with the
 * DirectRunner. You can see the results in the output files in your current
 * working directory, with names like "wordcounts-00001-of-00005. When running
 * on a distributed service, you would use an appropriate file service.
 */
public class BatchFileToESProcessing {

	private static final Logger LOG = LoggerFactory.getLogger(BatchFileToESProcessing.class);

	static void FileProcessor(BatchOptions options) {
		Pipeline pipeline = Pipeline.create(options);
		String[] es = { "http://localhost:9200" };

		pipeline.apply(TextIO.read().from(options.getFileInputPath()).watchForNewFiles(
				// Check for new files every minute.
				Duration.standardMinutes(1),
				// Stop watching the file pattern if no new files appear for an hour.
				Watch.Growth.afterTimeSinceNewOutput(Duration.standardHours(1))))
				.apply("Transform File Content", ParDo.of(new ParseFileRecordFn<SalesReport>(SalesReport.class)))
				.apply("Write to ES", ElasticsearchIO.write().withConnectionConfiguration(
						ElasticsearchIO.ConnectionConfiguration.create(es, options.getESIndexName(), "_doc")));
		pipeline.run().waitUntilFinish();
	}

	public static void main(String[] args) {
		LOG.trace("Enter main");
		BatchOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(BatchOptions.class);
		FileProcessor(options);
	}
}
