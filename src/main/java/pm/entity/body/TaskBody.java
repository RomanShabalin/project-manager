package pm.entity.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaskBody {
    private String name;
    private String status;
    private String info;
    private String type;
    @JsonProperty("subproject_id")
    private Integer subprojectId;
    @JsonProperty("project_id")
    private Integer projectId;
}
