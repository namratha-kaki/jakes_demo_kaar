/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package demojakesocc.controllers;

import com.jakes.jakescore.jalo.ToolsItem;
import com.jakes.jakesfacades.Data.ToolData;
import com.jakes.jakesfacades.Facades.ToolsFacade;

import javax.annotation.Resource;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Sample Controller
 */
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

//	@PostMapping(value = "/save", consumes = {"application/json"}, produces = {"application/json", "application/xml"})
//	@ResponseStatus(HttpStatus.CREATED)
//	@ResponseBody
//	public List<ToolData> saveTool(
//			@Parameter(description = "Tool data in JSON or XML format", required = true)
//			@RequestBody final ToolData toolData) {
//		toolsFacade.saveTool(toolData);
//		return toolsFacade.getAllTools();
//	}



	@PostMapping(
			value = "/save",
			consumes = {"application/json"},
			produces = {"application/json", "application/xml"}
	)
	public ResponseEntity<String> saveTool(
			@Parameter(description = "Tool data in JSON or XML format", required = true)
			@RequestBody final ToolData toolData) {
		toolsFacade.saveTool(toolData);
		return new ResponseEntity<>("Tool saved successfully", HttpStatus.CREATED);
	}


	@GetMapping(value = "/toolsByYear", produces = {"application/json", "application/xml"})
	public List<ToolData> getToolsByYear(
			@Parameter(description = "Year to filter tools by", required = false)
			@RequestParam(required = false) final Integer year) {
		final int queryYear = (year != null) ? year : 2025;
		return toolsFacade.getToolsByYear(queryYear);
	}

}
