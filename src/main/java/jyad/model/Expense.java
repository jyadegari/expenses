package jyad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about the expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The Expense id", example = "89")
    private Integer id;

    @ApiModelProperty(notes = "The expense name", example = "bus ticket", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(notes = "The expense description", example = "gives some more details about expenses")
    private String description;

    @ApiModelProperty(notes = "The expense amount", example = "45.5", required = true)
    @NotNull
    private Double amount;

    @ApiModelProperty(notes = "The expense date", example = "2018-1-2", required = true)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
