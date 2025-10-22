/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.jakes.jakesocc.controllers;

import com.jakes.jakesfacades.Data.ToolData;
import com.jakes.jakesfacades.Facades.ToolsFacade;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


//@Controller
//@RequestMapping(value = "/occ/v2/tools")
//@Tag(name = "Sample")
//public class SampleController
//{
//	@Resource(name = "toolsFacade")
//	private ToolsFacade toolsFacade;
//
//	@GetMapping(value = "/all", produces = {"application/json", "application/xml"})
//	@ResponseBody
//	public List<ToolData> getAllTools() {
//		return toolsFacade.getAllTools();
//	}
//}

@RestController
@RequestMapping(value = "/tools", produces = "application/json")
public class SampleController {

	@Resource(name = "toolsFacade")
	private ToolsFacade toolsFacade;

	@GetMapping("/all")
	public List<ToolData> getAllTools() {
		return toolsFacade.getAllTools();
	}
}


