package jakeocc.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakes.jakesfacades.Data.ToolData;
import com.jakes.jakesfacades.Facades.ToolsFacade;
import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Sample Controller
 */
@RestController
@RequestMapping(value = "/{baseSiteId}")
@Tag(name = "Tools")
public class ToolsController
{
    @Resource
    private ToolsFacade toolsFacade;

    @GetMapping(value = "/tools", produces = {"application/json"})
    public List<ToolData> getAllTools(@PathVariable String baseSiteId) {
        return toolsFacade.getAllTools();
    }

    @GetMapping(value = "/tools/year/{year}", produces = {"application/json"})
    public List<ToolData> getToolsByYear(
            @PathVariable String baseSiteId,
            @PathVariable("year") final int year) {
        return toolsFacade.getToolsByYear(year);
    }

    @PostMapping(value = "/tools/save", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Map<String, String>> saveTool(
            @PathVariable String baseSiteId,
            @RequestBody final String rawJson) {

        System.out.println("RAW JSON RECEIVED: " + rawJson);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ToolData toolData = mapper.readValue(rawJson, ToolData.class);
            toolsFacade.saveTool(toolData);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Tool saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/tools/{code}", produces = {"application/json"})
    public ResponseEntity<Map<String, String>> deleteTool(
            @PathVariable String baseSiteId,
            @PathVariable("code") final String code) {
        try {
            toolsFacade.removeTool(code);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tool with code " + code + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
