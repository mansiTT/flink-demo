package com.batch.filetoes.transform;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.batch.filetoes.es.dto.SalesReport;
import com.batch.filetoes.utils.Constants;
import com.batch.filetoes.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

/**
 * Data Transformation Layer Convert CSV to JAVA Object and then write it to
 * pipeline in ES JSON format
 * 
 */
public class ParseFileRecordFn<T extends Serializable> extends DoFn<String, String> {

	private static final long serialVersionUID = 3154917720792929301L;

	final Class<T> classType;

	public ParseFileRecordFn(Class<T> classType) {
		this.classType = classType;
	}

	private static final Logger LOG = LoggerFactory.getLogger(ParseFileRecordFn.class);

	@ProcessElement
	public void processElement(@Element String message, OutputReceiver<String> out) throws Exception {
		LOG.trace("enter :: processElement");
		ObjectMapper csvObjectMapper = new CsvMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		LOG.debug("Input message ", message);
		String[] csvData = csvObjectMapper.readValue(message, String[].class);

		String[] csvHeaders = csvObjectMapper.readValue(Utils.HEADERS, String[].class);

		Map<String, String> map = new HashMap<String, String>();

		// Avoid Processing header
		if (!message.equalsIgnoreCase(Utils.HEADERS)) {
			for (int i = 0; i < csvData.length; i++) {
				// Map header files with record value
				map.put(csvHeaders[i], csvData[i]);
			}
			SalesReport saleReport = this.tranfromRecords(map);
			System.out.println(saleReport.toString());

			LOG.trace("exit :: processElement");
			out.output(objectMapper.writeValueAsString(saleReport));
		}

	}

	/**
	 * Transform CSV records to ElasticSearch Document format
	 * 
	 * @param fileRecord A key/value map of csv records
	 * @return salesReport ES tranformed object
	 */
	private SalesReport tranfromRecords(Map<String, String> fileRecord) {
		System.out.println(fileRecord.get("Region"));
		SalesReport salesReport = new SalesReport();
		salesReport.setRegion(fileRecord.get(Constants.REGION));
		salesReport.setCountry(fileRecord.get(Constants.COUNTRY));
		salesReport.setItemType(fileRecord.get(Constants.ITEM_TYPE));
		salesReport.setSalesChannel(fileRecord.get(Constants.SALES_CHANNEL));
		salesReport.setOrderPriority(this.parseOrderPriortity(fileRecord.get(Constants.ORDER_PRIORITY)));
		salesReport.setOrderDate(Utils.formatDate(fileRecord.get((Constants.ORDER_DATE))));
		salesReport.setShipDate(Utils.formatDate(fileRecord.get((Constants.SHIP_DATE))));
		// Convert CSV string type to respective format before writing it to
		// ElasticSearch
		salesReport.setUnitSold(Integer.parseInt(fileRecord.get(Constants.UNITS_SOLD)));
		salesReport.setUnitPrice(Double.parseDouble(fileRecord.get(Constants.UNIT_PRICE)));
		salesReport.setTotalRevenue(Double.parseDouble(fileRecord.get(Constants.TOTAL_REVENUE)));
		salesReport.setCost(Double.parseDouble(fileRecord.get(Constants.TOTAL_COST)));
		salesReport.setProfit(Double.parseDouble(fileRecord.get(Constants.TOTAL_PROFIT)));
		return salesReport;
	}

	private String parseOrderPriortity(String priorityCode) {
		switch (priorityCode) {
		case "C":
			return "CRITICAL";
		case "H":
			return "HIGH";
		case "M":
			return "MEDIUM";
		case "L":
			return "LOW";
		default:
			return priorityCode;
		}
	}

}
