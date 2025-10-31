/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package demojakesocc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakes.jakescore.jalo.ToolsItem;
import com.jakes.jakesfacades.Data.ToolData;
import com.jakes.jakesfacades.Facades.ToolsFacade;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/sample")
@Tag(name = "Sample")
public class SampleController
{
	@Resource
	private ToolsFacade toolsFacade;

	@GetMapping(value = "/all", produces = {"application/json", "application/xml"})
	public List<ToolData> getAllTools() {
		return toolsFacade.getAllTools();
	}

	@PostMapping(
			value = "/save",
			consumes = {"application/json"},
			produces = {"application/json"}
	)
	public ResponseEntity<Map<String, String>> saveTool(@RequestBody final String rawJson) {
		System.out.println("RAW JSON RECEIVED");
		System.out.println(rawJson);

		try {
			ObjectMapper mapper = new ObjectMapper();
			ToolData toolData = mapper.readValue(rawJson, ToolData.class);

			System.out.println("=== PARSED TOOL DATA ===");
			System.out.println(toolData.toString());
			toolsFacade.saveTool(toolData);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Tool saved successfully");
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		}catch (Exception e) {
			System.out.println("PARSING ERROR");
			System.out.println("Error type: " + e.getClass().getName());
			System.out.println("Error message: " + e.getMessage());
			e.printStackTrace();

			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			errorResponse.put("type", e.getClass().getSimpleName());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/toolsByYear", produces = {"application/json", "application/xml"})
	public List<ToolData> getToolsByYear(
			@Parameter(description = "Year to filter tools by", required = false)
			@RequestParam(required = false) final Integer year) {
		final int queryYear = (year != null) ? year : 2025;
		return toolsFacade.getToolsByYear(queryYear);
	}
}
