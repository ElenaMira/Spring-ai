package org.example.springai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class ToolServiceImpl implements ToolService{
    @Tool(description = "退票")
    public String cancel(@ToolParam(description = "预约号") String ticketNumber,
                         @ToolParam(description = "姓名") String name) {
        return "退票";
    }
}
