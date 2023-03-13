package pm.entity.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubprojectBody {
    private String name;
    @JsonProperty("project_id")
    private Integer projectId;
}
