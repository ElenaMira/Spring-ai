package org.example.springai.tools;

import org.springframework.ai.tool.annotation.ToolParam;

public interface ToolService {
    String cancel(@ToolParam(description = "预约号") String ticketNumber,
                 @ToolParam(description = "姓名") String name);
}
