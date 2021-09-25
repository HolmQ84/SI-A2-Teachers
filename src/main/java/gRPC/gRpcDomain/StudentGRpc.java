package gRPC.gRpcDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class StudentGRpc {
    @Id
    private int studentId;
    private String studentName;
    private String studentMail;
}
