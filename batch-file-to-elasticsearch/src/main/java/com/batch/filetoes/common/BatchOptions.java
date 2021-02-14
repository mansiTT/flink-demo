package com.batch.filetoes.common;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface BatchOptions extends PipelineOptions {
	
	@Description("Local File Path")
	@Default.String("/Users/mansi/Documents/test/*.csv")
	String getFileInputPath();
	
	void setFileInputPath(String fileInputPath);
	
	  /**
	   * ElasticSearch connection URL
	   */
	  @Description("ElasticSearch connection URL")
	  @Default.String("localhost:9002")
	  String getESConnUrl();

	  void setESConnUrl(String esConnUrl);
	  
	  
	  /**
	   * ElasticSearch Index name
	   */
	  @Description("ElasticSearch Index")
	  @Default.String("sales")
	  String getESIndexName();

	  void setESIndexName(String eSIndexName);

	
}
