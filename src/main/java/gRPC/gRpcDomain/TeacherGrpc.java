package gRPC.gRpcDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class TeacherGrpc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teacherId;
    private String name;
    private int age;
    private String mail;
    private String subject;
}
