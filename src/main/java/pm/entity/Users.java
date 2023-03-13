package pm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
    private List<Task> tasks;

    public Users() {

    }

    public Users(Integer id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
