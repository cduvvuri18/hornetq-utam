package com.cduvvuri.hqutam.handler.send;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.cduvvuri.hqutam.vo.SampleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

public class TempTestJSONUtils {
	@Test
	public void testJson() throws JsonProcessingException {
		
		VirtuosoInfo info = new VirtuosoInfo();
		
		Info info1 = new Info("1", "Chaitanya");
		Info info2 = new Info("1", "Anitha");
		
		List<Info> infos = new ArrayList<Info>();
		infos.add(info1);
		infos.add(info2);

		Map<String, List<Info>> infoMap = new HashMap<String, List<Info>>();
		infoMap.put("Her2", infos);
		infoMap.put("ER", infos);
		
		info.setMap(infoMap);
		
		ObjectMapper mapper = new ObjectMapper();
	      try
	      {
	         mapper.defaultPrettyPrintingWriter().writeValue(new File("employee.json"), info);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace();
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      
	        com.fasterxml.jackson.databind.ObjectMapper mapper2 = new com.fasterxml.jackson.databind.ObjectMapper();
	        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
	        mapper2.acceptJsonFormatVisitor(mapper2.constructType(VirtuosoInfo.class), visitor);
	        com.fasterxml.jackson.module.jsonSchema.JsonSchema schema = visitor.finalSchema();
	        
	        try {
				System.out.println(mapper2.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	class VirtuosoInfo {
		
		private Map<String, List<Info>> map;

		public Map<String, List<Info>> getMap() {
			return map;
		}

		public void setMap(Map<String, List<Info>> map) {
			this.map = map;
		}
	}
	
	class Info {
		private String empId;
		private String empName;
		
		public Info(String empId, String empName) {
			this.empId = empId;
			this.empName = empName;
		}
		public String getEmpId() {
			return empId;
		}
		public void setEmpId(String empId) {
			this.empId = empId;
		}
		public String getEmpName() {
			return empName;
		}
		public void setEmpName(String empName) {
			this.empName = empName;
		}
	}
}
